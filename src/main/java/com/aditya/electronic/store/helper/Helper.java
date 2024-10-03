package com.aditya.electronic.store.helper;

import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.dtos.UserDto;
import com.aditya.electronic.store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> pages,Class<V> type)
    {
        List<U> entity = pages.getContent();
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponse response = new PageableResponse();
        //response.setContent(pages.getContent());
        response.setContent(dtoList);
        response.setPageNumber(pages.getNumber()+1);
        response.setPageSize(pages.getSize());
        response.setTotalElement(pages.getTotalElements());
        response.setTotalPages(pages.getTotalPages());
        response.setLastPage(pages.isLast());
        return response;
    }
}
