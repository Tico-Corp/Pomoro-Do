package com.tico.pomoro_do.global.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppInfoResponse {
    private String application;
    private String version;
    private String description;
    private String message;
    private String swaggerUrl;
}
