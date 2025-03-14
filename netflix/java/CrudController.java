package com.example.jpademo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CrudController
{
    @Autowired
    ContentService contentService;

    List<ContentDto> randomContents = null;

    // 추천 페이지
    @RequestMapping("/recommend")
    public String recommend(Model model) {
        // 모든 클라이언트가 공유하게 되지만, 로컬 테스트라 이런 방식으로 구현
        // 세션이나 쿠키 관리는 안 배워서 활용 x
        if(randomContents == null) randomContents = contentService.getRandomContents(3);

        model.addAttribute("contents", randomContents);
        return "recommend";
    }

    @RequestMapping("/roll")
    public String roll(Model model) {

        randomContents = contentService.getRandomContents(3);
        model.addAttribute("contents", randomContents);
        model.addAttribute("genres", contentService.getAllContentsGenre());

        return "redirect:/recommend";
    }

    @RequestMapping("/choice/{idx}")
    public String choice(@PathVariable long idx, Model model) {
        // choice 점수 증가
        contentService.increaseChoiceScore(idx);
        model.addAttribute("content", contentService.findById(idx));
        model.addAttribute("reviews", contentService.findById(idx).getReviews());
        return "redirect:/content/" + idx;
    }

    // 랭킹 페이지
    @RequestMapping("/ranking")
    public String ranking(Model model)
    {
        var count = contentService.findAll().size();
        model.addAttribute("contents", contentService.getContentsByChoice(count));
        model.addAttribute("genres", contentService.getAllContentsGenre());
        return "ranking";
    }

    // 상세 페이지
    @RequestMapping("/content/{idx}")
    public String read(@PathVariable long idx, Model model) {
        model.addAttribute("content", contentService.findById(idx));
        model.addAttribute("reviews", contentService.findById(idx).getReviews());
        return "read";
    }

    @PostMapping("/content/{idx}/addReview")
    public String addReview(@PathVariable long idx,
                            @ModelAttribute ReviewDTO reviewDto) {
        // 받아서 구현
        contentService.addReview(idx, reviewDto);
        return "redirect:/content/" + idx;
    }

    @PostMapping("/deleteReview/{idx}/{reviewId}")
    public String deleteReview(@PathVariable long idx,
                               @PathVariable long reviewId) {
        contentService.deleteReviewById(reviewId);
        return "redirect:/content/" + idx;
    }
    @RequestMapping("/searchResult")
    public String searchResult(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "genre", required = false, defaultValue = "") String genre,
            Model model) {

        List<ContentDto> results;

        // 검색어(이름)가 입력되었을 때
        if (!query.isEmpty()) {
            if (!genre.isEmpty()) {
                results = contentService.findContentByGenre(query, genre);
            }
            else {
                results = contentService.findByName(query);
            }
        }
        // 장르만 선택되었을 때
        else {
            if (!genre.isEmpty()) {
                results = contentService.filterGenre(contentService.findAll(), genre);
            }
            else {// 둘 다 없으면 전체 목록 반환
                results = contentService.findAll();
            }
        }

        // 모델에 데이터 추가
        List<String> genres = contentService.getAllContentsGenre();
        model.addAttribute("genres", genres);
        model.addAttribute("contents", results);

        if (results.isEmpty()) {
            model.addAttribute("message", "해당 데이터가 없습니다.");
        }

        return "searchResult";
    }
}