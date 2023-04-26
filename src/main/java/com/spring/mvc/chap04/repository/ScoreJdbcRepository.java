package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("jdbc")
public class ScoreJdbcRepository implements ScoreRepository {

    private final String url = "jdbc:mariadb://localhost:3306/spring";
    private final String username = "root";
    private final String password = "1234";

    public ScoreJdbcRepository() {
        // 1. 드라이버 클래스를 로딩 ( mariaDB 커넥터 로딩 )
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Score> findAll() {
        List<Score> scoreList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT * FROM tbl_score";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                scoreList.add(new Score(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreList;
    }

    @Override
    public List<Score> findAll(String sort) {
        return ScoreRepository.super.findAll(sort);
    }

    @Override
    public boolean save(Score score) {

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            conn.setAutoCommit(false);

            String sql = "INSERT INTO tbl_score (name, kor, eng, math, total, average, grade) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, score.getName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());
            pstmt.setString(7, String.valueOf(score.getGrade()));

            int result = pstmt.executeUpdate();

            if (result == 1) {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByStuNum(int stuNum) {

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "DELETE FROM tbl_score WHERE stu_num = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);

            int result = pstmt.executeUpdate();

            if (result == 1) {
                conn.commit();
                return true;
            }

            conn.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Score findByStuNum(int stuNum) {

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT * FROM tbl_score WHERE stu_num = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return new Score(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
