package com.example.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
}
