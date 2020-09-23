package com.ssafy.smaheal.repository;

import java.util.List;

import com.ssafy.smaheal.model.Youtube;

import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeRepository extends JpaRepository<Youtube, Long>{
    public Youtube findByVideoId(String videoId);
    public Youtube findByVideoIdAndAges(String videoId, int ages);
    public List<Youtube> findByAges(int age);
}
