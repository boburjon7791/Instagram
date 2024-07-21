package com.social_media.instagram.model.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.social_media.instagram.exceptions.BadRequestException;
import com.social_media.instagram.exceptions.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class Header<T> {
    Timestamp timestamp;
    int responseCode;
    String responseMessage;
    public T data;
    PaginationData pagination;

    public Header(Timestamp timestamp, int responseCode, String responseMessage, T data, PaginationData pagination) {
        LocalDateTime transactionTime = ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plussedSeconds = now.plusSeconds(30);
        LocalDateTime minusSeconds = now.minusSeconds(30);
        if (transactionTime.isBefore(minusSeconds) || transactionTime.isAfter(plussedSeconds)) {
            throw new ForbiddenException("");
        }
        this.timestamp = timestamp;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
        this.pagination = pagination;
    }

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
