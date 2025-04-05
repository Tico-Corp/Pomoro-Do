package com.tico.pomoro_do.global.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)  // JPA에게 해당 Entity는 Auditing 기능을 사용함을 알림
@MappedSuperclass  // JPA Entity 클래스들이 이 클래스를 상속할 경우 필드들도 컬럼으로 인식
public abstract class BaseTimeEntity {

    @CreatedDate  // Entity가 생성되어 저장될 때 시간이 자동 저장
    @Column(name = "created_at", nullable = false, updatable = false) // 한번 저장되면 수정되지 않도록 설정
    private LocalDateTime createdAt;

    @LastModifiedDate  // Entity의 값이 변경될 때 시간이 자동 저장
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}