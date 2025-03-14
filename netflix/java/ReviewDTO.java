package com.example.jpademo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO
{
    private long id;
    private String review;
    private int point;

    private Long contentId;
}
