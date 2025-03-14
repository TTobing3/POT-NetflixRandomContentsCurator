package com.example.jpademo;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "contents")
@ToString
@Data
@Builder
@NoArgsConstructor()
@AllArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String name;
    private String contentGenre;
    private int epCount;
    private String image;
    private int choice;

    // CascadeType.ALL : 외래키의 수정 사항이 객체에 자동 전파
    // orphanRemoval = true : 부모 엔티티에서 삭제하면 해당 데이터가 데이터베이스에서 삭제
    // mapping 하기 때문에 joincolumn이 불필요
    //@OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="content_id")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}