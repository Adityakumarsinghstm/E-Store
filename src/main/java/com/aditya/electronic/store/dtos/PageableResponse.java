package com.aditya.electronic.store.dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {
    private List<T> content;
    private int pageSize;
    private int pageNumber;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
