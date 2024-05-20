package com.social_media.instagram.model.common;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaginationData {
    private final int currentPage;
    private final long totalCount;
    private final int elementCount;
    private final int totalPages;
    public static PaginationData of(Page<?> page) {
        return new PaginationData(
            page.getNumber(), page.getTotalElements(), page.getNumberOfElements(), page.getTotalPages()
        );
    }
}
