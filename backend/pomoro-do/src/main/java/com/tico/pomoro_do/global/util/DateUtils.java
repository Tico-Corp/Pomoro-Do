package com.tico.pomoro_do.global.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class DateUtils {

    // 한국 시간(KST)을 기준으로 현재 날짜를 결정하는 메서드
    public static LocalDate getCurrentDate() {
        // 한국 표준시를 기준으로 설정
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        // 현재 한국 시간 가져오기
        LocalTime currentTimeInKorea = LocalTime.now(koreaZoneId);
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now(koreaZoneId);

        // 현재 시간이 5시 이전이면 전날 날짜 반환, 아니면 현재 날짜 반환
        return currentTimeInKorea.isBefore(LocalTime.of(5, 0)) ? currentDate.minusDays(1) : currentDate;
    }

}
