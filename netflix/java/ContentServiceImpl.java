package com.example.jpademo;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ContentDto findById(long idx) {
        return contentRepository.findById(idx)
                .map(Utils::toContentDTO)
                .orElse(null);
    }

    @Override
    public List<ContentDto> findAll()
    {
        return contentRepository.findAll().stream()
                .map(Utils::toContentDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ContentDto> findByName(String name) {
        return contentRepository.findByName(name).stream()
                .map(Utils::toContentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentDto> getRandomContents(int count)
    {
        var totalContents = findAll();
        var totalCount = totalContents.size();

        // 전체 콘텐츠 크기에서 무작위 인덱스 세 개 추출
        var randomIndexArr = new Random().ints(0,totalCount)
                .distinct()
                .limit(3)
                .toArray();

        // 추출한 int 값을 전체 콘텐츠 리스트의 인덱스로 매핑하여 반환
        return Arrays.stream(randomIndexArr)
            .mapToObj(totalContents::get)
            .toList();
    }

    @Override
    public List<ContentDto> getContentsByChoice(int count)
    {
        List<Content> contents = contentRepository.findAllByOrderByChoiceDesc();
        return Utils.toContentDTOList(contents.stream()
                .limit(count)
                .collect(Collectors.toList()));
    }



    @Override
    public List<ContentDto> filterGenre(List<ContentDto> contents, String genre)
    {
        return contents.stream()
                .filter(content -> content.getContentGenre().equals(genre))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentDto> findContentByGenre(String name, String genre)
    {
        var contents = filterGenre(findAll(), genre);

        return contents.stream()
                .filter(content -> content.getName().equalsIgnoreCase(name))
                .toList();
    }

    @Override
    public List<String> getAllContentsGenre()
    {
        return contentRepository.findAll().stream()
                .map(Content::getContentGenre)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void addReview(long idx, ReviewDTO reviewDto) {
        Review review =  Utils.toReviewEntity(reviewDto);
        review.setContentId(idx);
        reviewRepository.save(review);
    }

    @Override
    public void deleteReviewById(long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void increaseChoiceScore(long idx)
    {
        Content content = contentRepository.findById(idx)
                .orElse(null);
        if(content != null)
        {
            content.setChoice(content.getChoice() + 1); // 점수 증가
            contentRepository.save(content); // 변경 저장
        }
    }
}