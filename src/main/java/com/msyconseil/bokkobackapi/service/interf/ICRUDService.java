package com.msyconseil.bokkobackapi.service.interf;

import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;

import java.util.List;
import java.util.Map;

/**
 * Author: Mourad Si Yakoub
 * @param <D>
 * @param <U>
 */

public interface ICRUDService <D extends Object, U> {

    CustomAnswer<D> get(Map<String, String> headers, U email) throws ErrorException;

    CustomListAnswer<List<D>> getAll(Map<String, String> headers, int page, int size) throws ErrorException;

    CustomAnswer<D> add(Map<String, String> headers, D parameter) throws ErrorException;

    CustomAnswer<D> update(Map<String, String> headers, D parameter, U email) throws ErrorException;

    CustomAnswer<D> delete(Map<String, String> headers, U email) throws ErrorException;
}
