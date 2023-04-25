package com.spring.mvc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
//public class DatabaseConfig {
//
//    // Datasource 설정 : 데이터베이스 연결 정보
//    // URL : DBMS 가 설치된 경로
//    // USERNAME : DB 계정명
//    // PASSWORD : DB 비밀번호
//    // DRIVER CLASS : DBMS 마다 설치한 커넥터 드라이버
//
//    // 커넥션 풀 설정
//    // DB 접속시 사용하는 리소스를 관리하는 프로그램
//
////    @Bean
////    public DataSource dataSource() {
////        // 히카리 설정
////        HikariConfig config = new HikariConfig();
////        config.setUsername("root");
////        config.setPassword("1234");
////        config.setJdbcUrl("jdbc:mariadb://localhost:3306/spring");
////        config.setDriverClassName("org.mariadb.jdbc.Driver");
////
////        return new HikariDataSource(config);
////    }
//
//
//}
