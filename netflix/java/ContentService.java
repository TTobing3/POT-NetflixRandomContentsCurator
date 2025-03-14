package com.example.jpademo;

import java.util.List;

public interface ContentService {

    ContentDto findById(long idx);

    List<ContentDto> findByName(String name);
    List<ContentDto> findAll();

    List<ContentDto> getRandomContents(int count);
    List<ContentDto> getContentsByChoice(int count);

    List<ContentDto> filterGenre(List<ContentDto> contents, String genre);
    List<ContentDto> findContentByGenre(String name, String genre);

    List<String> getAllContentsGenre();

    void addReview(long idx, ReviewDTO reviewDto);
    void deleteReviewById(long id);

    void increaseChoiceScore(long idx);
}
