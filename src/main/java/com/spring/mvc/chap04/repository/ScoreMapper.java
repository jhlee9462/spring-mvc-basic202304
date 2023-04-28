package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {

    boolean save(Score score);

    boolean deleteByStuNum(int stuNum);

    Score findByStuNum(int stuNum);

    List<Score> findAll(String sort);

    boolean modify(Score score);

}
