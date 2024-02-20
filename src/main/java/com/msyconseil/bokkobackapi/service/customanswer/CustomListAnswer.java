package com.msyconseil.bokkobackapi.service.customanswer;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

/**
 * author : Mourad Si Yakoub
 * @param <T>
 */

public class CustomListAnswer <T extends List<? extends Object>> extends CustomAnswer<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private int actualPageNumber;

    @Getter @Setter
    private long totalNumberOfElements;

    @Getter @Setter
    private long totalNumberOfPages;

    @Getter @Setter
    private int numberOfElementByPage;

    @Getter @Setter
    private int numberOfFoundElement;

    public CustomListAnswer(){
        super();
    }



}
