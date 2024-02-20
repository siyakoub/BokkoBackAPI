package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AbstractService <D extends Object, E extends Object> {

    protected Pageable createPage(int page, int size) {
        return PageRequest.of(page, size);
    }

    protected void addPaginationElementOnResponse(CustomListAnswer<List<D>> response, Page<E> pages, int requestedSize) {

        response.setActualPageNumber(pages.getNumber());
        response.setTotalNumberOfElements(pages.getTotalElements());
        response.setTotalNumberOfPages(pages.getTotalPages());
        response.setNumberOfElementByPage(requestedSize);
        response.setNumberOfFoundElement(pages.getSize());
    }

    protected void addPaginationElementOnResponse(CustomListAnswer<List<D>> response, int pageNumber, int requestedSize) {

        response.setActualPageNumber(pageNumber);
        response.setNumberOfElementByPage(requestedSize);
        response.setNumberOfFoundElement(response.getContent().size());
    }

}
