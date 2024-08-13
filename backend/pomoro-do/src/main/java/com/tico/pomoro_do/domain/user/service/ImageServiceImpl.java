package com.tico.pomoro_do.domain.user.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.config.S3Config;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor // 파이널 필드만 가지고 생성자 주입 함수를 만든다.
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 임시 파일을 저장할 디렉토리
//    private String localLocation = "로컬환경경로";
    private final String localLocation = System.getProperty("java.io.tmpdir") + "/";

    /**
     * 이미지 업로드 메서드
     * MultipartFile에서 파일을 추출하여 S3에 업로드하고 업로드된 파일의 URL을 반환합니다.
     *
     * @param file MultipartFile로 받은 파일
     * @return String 업로드된 이미지의 URL
     */
    @Override
    public String imageUpload(MultipartFile file, String dirName) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_MISSING);
        }

        // 고유 파일 이름 생성
        String uuidFileName = generateUniqueFileName(file);

        // 서버 환경에 저장할 경로 생성
        String localPath = localLocation + uuidFileName;

        // 서버 환경에 이미지 파일 저장
        // MultipartFile을 File로 변환 및 저장
        File localFile = convertToFile(file, localPath);

        // S3에 파일을 업로드 및 URL 가져오기
        String s3Url = uploadToS3(localFile, uuidFileName, dirName);

        // 로컬 파일 삭제
        if (!localFile.delete()) {
            throw new CustomException(ErrorCode.LOCAL_FILE_DELETION_FAILED);
        }

        return s3Url;
    }

    /**
     * 파일 이름을 생성하는 메서드
     *
     * @param file MultipartFile 객체
     * @return 고유한 파일 이름
     */
    private String generateUniqueFileName(MultipartFile file) {
        // 파일에서 파일 이름, 확장자 추출
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new CustomException(ErrorCode.FILE_NAME_NULL);
        }
        // 파일 확장자 추출
        String ext = fileName.substring(fileName.lastIndexOf("."));
        // 고유 파일 이름 생성 (이미지 파일 이름 유일성을 위해 uuid 사용)
        // 파일 이름을 고유 식별 번호로 변경(이름이 중복되어 덮어쓰는 것을 방지) + 확장자 명
        return UUID.randomUUID() + ext;
    }

    /**
     * MultipartFile을 File로 변환하는 메서드
     *
     * @param file MultipartFile 객체
     * @param localPath 로컬에 저장할 경로
     * @return File 변환된 File 객체
     */
    private File convertToFile(MultipartFile file, String localPath) {
        // 서버 환경에 이미지 파일 저장
        File localFile = new File(localPath);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            log.error("Error occurred while converting MultipartFile to File: " + e.getMessage());
            throw new CustomException(ErrorCode.FILE_CONVERSION_FAILED);
        }
        return localFile;
    }

    /**
     * S3에 파일을 업로드하는 메서드
     *
     * @param localFile 업로드할 로컬 파일
     * @param fileName S3에 저장할 파일 이름
     * @param dirName S3에 저장할 폴더 이름
     * @return String 업로드된 파일의 S3 URL
     */
    private String uploadToS3(File localFile, String fileName, String dirName) {
        // S3에 저장할 파일 경로 설정
        String s3Key = fileName; // 기본적으로는 파일 이름만 사용

        // dirName이 비어 있지 않다면 폴더 경로를 포함시킴
        if (dirName != null && !dirName.isEmpty()) {
            s3Key = dirName + "/" + fileName;
        }

        try {
            s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, s3Key, localFile));
            // S3에서 업로드된 파일의 URL 가져오기
            String s3Url = s3Config.amazonS3Client().getUrl(bucket, s3Key).toString();
            log.info("파일을 S3에 업로드했습니다: {}", s3Url);
            return s3Url;

        } catch (AmazonServiceException e) {
            // AWS 서비스 예외 처리
            log.error("S3 서비스에서 오류 발생: {}", e.getMessage());

            // AWS 서비스 오류 처리
            switch (e.getErrorCode()) {
                case "AccessDenied":
                    throw new CustomException(ErrorCode.AWS_ACCESS_DENIED);
                case "NoSuchBucket":
                    throw new CustomException(ErrorCode.AWS_INVALID_BUCKET);
                case "EntityTooLarge":
                    throw new CustomException(ErrorCode.AWS_FILE_TOO_LARGE);
                case "AccessControlListNotSupported":
                    // ACL이 지원되지 않는 오류 처리
                    throw new CustomException(ErrorCode.AWS_ACL_NOT_SUPPORTED);
                default:
                    throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);
            }

        } catch (SdkClientException e) {
            // AWS SDK 클라이언트 예외 처리
            log.error("S3 클라이언트에서 오류 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);

        } catch (Exception e) {
            // 기타 예외 처리
            log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
            throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);
        }
    }
}