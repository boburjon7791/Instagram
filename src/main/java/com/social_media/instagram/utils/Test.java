package com.social_media.instagram.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String description= """
                hello world #hello. world #uzb
                """;

        String[] s = description.split(" ");
        List<String> list = Pattern.compile("(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)")
                .matcher(description)
                .results()
                .map(MatchResult::group)
                .toList();

        list.forEach(System.out::println);
    }
}
