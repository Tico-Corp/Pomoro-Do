package com.tico.pomoro_do.global.enums;

public enum CategoryVisibility {
    PUBLIC,      // 공개: 모두에게 공개
    FOLLOWERS,  // 팔로워 공개: 팔로워에게만 공개
    PRIVATE,    // 비공개: 본인만 볼 수 있음
    GROUP       // 그룹 공개: 해당 카테고리의 그룹원만 볼 수 있음 (그룹 카테고리 전용)
}
