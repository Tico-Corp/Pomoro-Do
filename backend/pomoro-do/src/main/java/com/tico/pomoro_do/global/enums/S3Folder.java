package com.tico.pomoro_do.global.enums;

import lombok.Getter;

@Getter
public enum S3Folder {
    IMAGES("images"),
    PROFILES("profiles"),
    DOCUMENTS("documents");

    private final String folderName;

    S3Folder(String folderName) {
        this.folderName = folderName;
    }
}
