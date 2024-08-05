package com.tico.pomoro_do.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Schema(description = "Image Response")
public class ImageDTO {

    @Schema(description = "이미지 URL", example = "https://s3.amazonaws.com/bucketname/uuid.jpg")
    private final String url;

    @Builder
    public ImageDTO(String url){
        this.url = url;
    }
}