package com.spring.mvc.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUtil {

    /*
        1. 사용자가 파일을 업로드했을 때 중복이 없는 새로운 파일명을 생성해서 해당 파일명으로 업로드하는 메서드
     */

    /**
     *
     * @param thumbnail - 사용자가 업로드한 파일 객체
     * @param rootPath - 서버의 파일업로드 루트 경로
     *                 (ex: D:/spring-prj/upload/)
     * @return - 업로드가 완료된 파일의 위치 경로
     *          (ex: /2023/05/16)
     */
    public static String uploadFile(
            MultipartFile thumbnail,
            String rootPath
    ) {
        // 원본 파일명을 중복이 없는 랜덤 이름으로 변경
        // ex) 상어.png -> asdiofnujweibnfiebguidnfjgnasjo_상어.png
        String newFileName = UUID.randomUUID() + "_" + thumbnail.getOriginalFilename();

        // 이 파일을 저장할 날짜별 폴더를 생성
        // ex) D:/spring-prj/upload/2023/05/16/asndofnjwanrjaw_상어.png
        String newPath = makeDateFormatDirectory(rootPath);

        try {
            thumbnail.transferTo(new File(newPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 저장된 파일의 전체 경로
        String fullPath = newPath + "/" + newFileName;
        log.info("전체 경로 : {} ", fullPath);

        return "/local" + fullPath.substring(rootPath.length());
    }

    /**
     * 루트 경로를 받아서 일자별로 폴더를 생성하고 루트 경로 + 날짜 폴더 경로를 리턴
     * @param rootPath - 파일 업로드 루트 경로
     * @return - 날짜 폴더 경로가 포함된 새로운 업로드 경로
     */
    private static String makeDateFormatDirectory(String rootPath) {

        // 오늘 연월일 날짜정보 가져오기
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        List<String> date = List.of(
                String.valueOf(year),
                len2(month),
                len2(day)
        );

        StringBuilder directoryPath = new StringBuilder(rootPath);
        for (String s : date) {
            directoryPath.append("/").append(s);
            File f = new File(String.valueOf(directoryPath));
            if (!f.exists()) f.mkdir();
        }

        return String.valueOf(directoryPath);
    }

    // 월, 일을 두 글자로 저장
    private static String len2(int n) {
        return new DecimalFormat("00").format(n);
    }
}
