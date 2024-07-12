package com.tico.pomoro_do.global.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppInfoDTO {
    private String application;
    private String version;
    private String description;
    private String message;
    private String swaggerUrl;
}
