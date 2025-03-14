package com.example.jpademo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    private long idx;
    private String name;
    private String contentGenre;
    private int epCount;
    private String image;
    private int choice;
    private List<ReviewDTO> reviews;
}