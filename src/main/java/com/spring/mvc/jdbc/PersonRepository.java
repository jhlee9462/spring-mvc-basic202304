package com.spring.mvc.jdbc;

import com.spring.mvc.jdbc.Person;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private final String url = "jdbc:mariadb://localhost:3306/spring";
    private final String username = "root";
    private final String password = "1234";

    public PersonRepository() {
        // 1. 드라이버 클래스를 로딩 ( mariaDB 커넥터 로딩 )
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 사람정보 저장 기능
    public void save(Person person) {

        Connection conn = null;
        // DB 연결
        // Connection : db 연결 정보를 가지며, SQL 을 작성할 수 있는 statement객체를 받을 수 있음
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 트랜잭션 시작
            conn.setAutoCommit(false); // 오토 커밋 비활성화

            // SQL을 실행해주는 객체 얻기
            String sql = "INSERT INTO person (person_name, person_age) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // ? 값 세팅하기
            pstmt.setString(1, person.getPersonName());
            pstmt.setInt(2, person.getPersonAge());

            // sql 실행하기
            // INSERT, UPDATE, DELETE : executeUpdate();
            // SELECT : executeQuery();

            // executeUpdate() : 성공한 쿼리의 수를 알려줌
            int result = pstmt.executeUpdate();

            if (result == 1) {
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(Person person) {
        Connection conn = null;
        // DB 연결
        // Connection : db 연결 정보를 가지며, SQL 을 작성할 수 있는 statement객체를 받을 수 있음
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 트랜잭션 시작
            conn.setAutoCommit(false); // 오토 커밋 비활성화

            // SQL을 실행해주는 객체 얻기
            String sql = "UPDATE person SET person_name = ?, person_age = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // ? 값 세팅하기
            pstmt.setString(1, person.getPersonName());
            pstmt.setInt(2, person.getPersonAge());
            pstmt.setLong(3, person.getId());

            // sql 실행하기
            // INSERT, UPDATE, DELETE : executeUpdate();
            // SELECT : executeQuery();

            // executeUpdate() : 성공한 쿼리의 수를 알려줌
            int result = pstmt.executeUpdate();

            if (result == 1) {
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void delete(Person person) {
        Connection conn = null;
        // DB 연결
        // Connection : db 연결 정보를 가지며, SQL 을 작성할 수 있는 statement객체를 받을 수 있음
        try {
            conn = DriverManager.getConnection(url, username, password);
            // 트랜잭션 시작
            conn.setAutoCommit(false); // 오토 커밋 비활성화

            // SQL을 실행해주는 객체 얻기
            String sql = "DELETE FROM person WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // ? 값 세팅하기
            pstmt.setLong(1, person.getId());

            // sql 실행하기
            // INSERT, UPDATE, DELETE : executeUpdate();
            // SELECT : executeQuery();

            // executeUpdate() : 성공한 쿼리의 수를 알려줌
            int result = pstmt.executeUpdate();

            if (result == 1) {
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 사람 정보 전체 조회
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();

        // try - with - resource : close 자동화 ( AutoClosable )
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            conn.setAutoCommit(false);

            String sql = "SELECT id, person_name, person_age FROM person";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            // 포인터 커서로 첫번째 행부터 next 호출시 마다 매 다음 행을 지목
            // rs.next();

            while (rs.next()) {
                Person p = new Person(rs.getLong("id"), rs.getString("person_name"), rs.getInt("person_age"));
                System.out.println("p = " + p);
                people.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    // 사람 정보 개별 조회
    public Person findOne(long id) {
        // try - with - resource : close 자동화 ( AutoClosable )
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            conn.setAutoCommit(false);

            String sql = "SELECT id, person_name, person_age FROM person WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            // 포인터 커서로 첫번째 행부터 next 호출시 마다 매 다음 행을 지목
            // rs.next();

            if (rs.next()) {
                return new Person(rs.getLong("id"), rs.getString("person_name"), rs.getInt("person_age"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
