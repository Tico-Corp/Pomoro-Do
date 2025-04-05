package com.tico.pomoro_do.global.scheduler;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.entity.CategoryMember;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryScheduler {

    private final CategoryService categoryService;

    // 한국 표준시 (KST) ZoneId를 상수로 정의
    private static final ZoneId KOREA_ZONE_ID = ZoneId.of("Asia/Seoul");

    // 카테고리 자동 생성
    @Scheduled(cron = "0 0 5 * * *") // 매일 새벽 5시에 실행
    public void createTodayCategories(){
        // 오늘 날짜 설정
        LocalDate today = LocalDate.now(KOREA_ZONE_ID);
        // 전날 날짜 설정
        LocalDate yesterday = today.minusDays(1);
    }
}
