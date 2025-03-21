package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAllByOrderByChoiceDesc();
    List<Content> findByName(String name);
}
