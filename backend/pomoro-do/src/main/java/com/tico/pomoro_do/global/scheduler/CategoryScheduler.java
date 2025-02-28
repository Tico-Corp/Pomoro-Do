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

        // 전날 존재하는 모든 카테고리 조회
        List<Category> yesterdayCategories = categoryService.findByDate(yesterday);

        // 오늘 날짜로 복사하여 새로운 카테고리 생성
        for (Category category : yesterdayCategories) {

            Category newCategory = categoryService.createNewCategory(
                    category.getHost(),
                    today,
                    category.getTitle(),
                    category.getColor(),
                    category.getVisibility(),
                    category.getType()
            );

            // 그룹 카테고리면 그룹 멤버도 복사하여 생성
            if (category.getType() == CategoryType.GROUP){
                // 승낙한 멤버만 생성
                List<CategoryMember> categoryMembers = categoryService.findAcceptedMembersByCategory(category);

                for (CategoryMember categoryMember : categoryMembers) {

                    categoryService.createGroupMember(
                            newCategory,
                            categoryMember.getUser(),
                            categoryMember.getStatus(),
                            categoryMember.getRole()
                    );
                }
            }
        }
    }

}
