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

    CustomAnswer<D> get(final Map<String, String> headers, U email) throws Exception;

    CustomListAnswer<List<D>> getAll(final Map<String, String> headers, int page, int size) throws Exception;

    CustomAnswer<D> add(final Map<String, String> headers, D parameter) throws Exception;

    CustomAnswer<D> update(final Map<String, String> headers, D parameter, U email) throws Exception;

    CustomAnswer<D> delete(final Map<String, String> headers, U email) throws Exception;
}
