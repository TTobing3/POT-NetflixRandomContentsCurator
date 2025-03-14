package com.example.jpademo;

import java.util.List;
import java.util.stream.Collectors;

public class Utils
{
    public static ContentDto toContentDTO(Content entity) {

        /*
        return ContentDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .contentGenre(entity.getContentGenre())
                .epCount(entity.getEpCount())
                .reviews(entity.getReviews())
                .build();
         */
        List<ReviewDTO> reviewDTOs = entity.getReviews().stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getReview(),
                        review.getPoint(),
                        review.getContentId()
                ))
                .toList();

        return new ContentDto(
                entity.getIdx(),
                entity.getName(),
                entity.getContentGenre(),
                entity.getEpCount(),
                entity.getImage(),
                entity.getChoice(),
                reviewDTOs
        );
    }

    public static Content toContentEntity(ContentDto dto) {
        return Content.builder()
                .idx(dto.getIdx())
                .name(dto.getName())
                .contentGenre(dto.getContentGenre())
                .epCount(dto.getEpCount())// .reviews(dto.getReviews())
                .image(dto.getImage())
                .choice(dto.getChoice())
                .build();
    }

    public static Review toReviewEntity(ReviewDTO dto)
    {
        return Review.builder()
                .id(dto.getId())
                .review(dto.getReview())
                .point(dto.getPoint())
                .contentId(dto.getContentId())
                .build();
    }

    public static ReviewDTO toReviewDTO(Review review)
    {
        return ReviewDTO.builder()
                .id(review.getId())
                .review(review.getReview())
                .point(review.getPoint())
                .contentId(review.getContentId())
                .build();
    }


    public static List<ContentDto> toContentDTOList(List<Content> contents)
    {
        return contents.stream()
                .map(content -> new ContentDto(
                        content.getIdx(),
                        content.getName(),
                        content.getContentGenre(),
                        content.getEpCount(),
                        content.getImage(),
                        content.getChoice(),
                        content.getReviews().stream()
                                .map(review -> new ReviewDTO(
                                        review.getId(),
                                        review.getReview(),
                                        review.getPoint(),
                                        review.getContentId()
                                ))
                                .toList()
                )).toList();
    }
}