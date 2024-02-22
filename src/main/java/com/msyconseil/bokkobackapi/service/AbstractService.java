package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class AbstractService <D extends Object, E extends Object> {

    private final SessionService sessionService;

    public AbstractService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    protected SessionModel getActiveSession(Map<String, String> headers) throws ErrorException {
        if(!headers.containsKey("token")){
            throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
        }
        System.out.println("token "+headers.get("token"));
        return sessionService.getActiveSessionByToken(headers.get("token"));
    }

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
