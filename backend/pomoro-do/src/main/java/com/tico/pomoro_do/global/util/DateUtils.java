package com.tico.pomoro_do.global.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class DateUtils {

    // 한국 표준시 (KST) ZoneId를 상수로 정의
    private static final ZoneId KOREA_ZONE_ID = ZoneId.of("Asia/Seoul");
    private static final LocalTime CUTOFF_TIME = LocalTime.of(5, 0);


    // 한국 시간(KST)을 기준으로 현재 날짜를 결정하는 메서드
    public static LocalDate getCurrentDate() {
        // 현재 한국 시간 가져오기
        LocalTime currentTimeInKorea = LocalTime.now(KOREA_ZONE_ID);
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now(KOREA_ZONE_ID);

        // 현재 시간이 5시 이전이면 전날 날짜 반환, 아니면 현재 날짜 반환
        return currentTimeInKorea.isBefore(CUTOFF_TIME) ? currentDate.minusDays(1) : currentDate;
    }


    /**
     * targetDate가 null일 경우 현재 날짜 반환
     *
     * @param targetDate 조회할 날짜
     * @return 유효한 LocalDate 객체
     */
    public static LocalDate getValidDate(LocalDate targetDate) {
        return targetDate != null ? targetDate : getCurrentDate();
    }
}
