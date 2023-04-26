package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jdbcTem")
@RequiredArgsConstructor
public class ScoreJdbcTemplateRepository implements ScoreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Score> findAll() {
        String sql = "SELECT * FROM tbl_score";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Score(rs));
    }

    @Override
    public List<Score> findAll(String sort) {
        return ScoreRepository.super.findAll(sort);
    }

    @Override
    public boolean save(Score score) {
        String sql = "INSERT INTO tbl_score (name, kor, eng, math, total, average, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int result = jdbcTemplate.update(sql, score.getName(), score.getKor(), score.getEng(), score.getMath(), score.getTotal(), score.getAverage(), String.valueOf(score.getGrade()));

        return result == 1;
    }

    @Override
    public boolean deleteByStuNum(int stuNum) {
        String sql = "DELETE FROM tbl_score WHERE stu_num = ?";

        int result = jdbcTemplate.update(sql, stuNum);

        return result == 1;
    }

    @Override
    public Score findByStuNum(int stuNum) {
        String sql = "SELECT * FROM tbl_score WHERE stu_num = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Score(rs), stuNum);
    }
}
