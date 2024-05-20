package com.social_media.instagram.model.common;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Header<T> {
    Timestamp timestamp;
    int responseCode;
    String responseMessage;
    T data;
    PaginationData pagination;
    public static <T> Header<T> created(T data){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .data(data)
            .responseCode(ResponseCodes.CREATED.code)
            .responseMessage("Created")
            .build();
    }
    public static <T> Header<T> ok(T data){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .data(data)
            .responseCode(ResponseCodes.OK.code)
            .responseMessage("OK")
            .build();
    }
    public static <T> Header<T> ok(T data, PaginationData pagination){
        return Header.<T>builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .data(data)
                .responseCode(ResponseCodes.OK.code)
                .responseMessage("OK")
                .pagination(pagination)
                .build();
    }
    public static <T> Header<T> notFound(String message){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.NOT_FOUND.code)
            .responseMessage(message)
            .build();
    }
    public static <T> Header<T> badRequest(String message){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.BAD_REQUEST.code)
            .responseMessage(message)
            .build();
    }
    public static <T> Header<T> unauthorized(String message){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.UNAUTHORIZED.code)
            .responseMessage(message)
            .build();
    }
    public static <T> Header<T> forbidden(String message){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.FORBIDDEN.code)
            .responseMessage(message)
            .build();
    }
    public static <T> Header<T> error(String message){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.INTERNAL_SERVER_ERROR.code)
            .responseMessage(message)
            .build();
    }
    public static <T> Header<T> error(){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.INTERNAL_SERVER_ERROR.code)
            .responseMessage("INTERNAL SERVER ERROR")
            .build();
    }
    public static <T> Header<T> conflict(String message){
        return Header.<T>builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .responseCode(ResponseCodes.CONFLICT.code)
            .responseMessage(message)
            .build();
    }

    public static <T> Header<T> modifying() {
        return Header.<T>builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .responseCode(ResponseCodes.NO_CONTENT.code)
                .responseMessage("OK")
                .build();
    }
    public static <T> Header<T> modifying(T data) {
        return Header.<T>builder()
                .data(data)
                .responseMessage("OK")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .responseCode(ResponseCodes.NO_CONTENT.code)
                .build();
    }
}
