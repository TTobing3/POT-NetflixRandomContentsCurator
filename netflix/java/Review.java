package com.example.jpademo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table(name = "reviews")
@ToString
@Data
@Builder
@NoArgsConstructor()
@AllArgsConstructor
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String review;
    private int point;

    @Column(name = "content_id") // content_id와 연관 설정
    private Long contentId;
}
