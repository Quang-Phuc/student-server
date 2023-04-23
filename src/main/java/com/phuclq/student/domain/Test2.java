package com.code.apidemo.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Test2 {

    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()));
        System.out.println(timestamp);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        System.out.println(timestamp);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Timestamp.valueOf("2016-10-26 23:00:00").getTime();
        System.out.println(timestamp);
//        res16: Long = 1477522800000 // This is what we want

        TimeZone.setDefault(TimeZone.getTimeZone("GMT-1"));
        Timestamp.valueOf("2016-10-26 23:00:00").getTime();
        System.out.println(timestamp);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(timestamp);
//        res14: Long = 1477526400000

//        new Timestamp(OffsetDateTime.of(2016,10,26,23,0,0,0, ZoneOffset.UTC).toInstant.toEpochMilli).getTime;

//        res15: Long = 1477522800000 // We get the same result at in GMT
        String[] id = TimeZone.getAvailableIDs();
        System.out.println("Danh sach id co san la: ");
        for (int i = 0; i < id.length; i++) {
            System.out.println(id[i]);
        }
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.out.println(timestamp);
    }
}