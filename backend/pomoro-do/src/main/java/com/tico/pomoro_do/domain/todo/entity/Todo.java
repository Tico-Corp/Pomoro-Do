package com.tico.pomoro_do.domain.todo.entity;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "todos")
public class Todo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;      // 할 일 유효 날짜 -> 새벽 5시 기준 날짜로 저장

    @Column(nullable = false)
    private String title;       // 할 일 제목
}