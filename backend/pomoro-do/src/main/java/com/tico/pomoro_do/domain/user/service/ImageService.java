package com.tico.pomoro_do.domain.user.service;

import org.springframework.web.multipart.MultipartFile;


public interface ImageService {

    String imageUpload(MultipartFile file, String dirName);
}
