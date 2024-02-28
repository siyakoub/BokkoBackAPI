package com.msyconseil.bokkobackapi.service.interf;

import com.msyconseil.bokkobackapi.service.exception.ErrorException;

public interface IService <E, D>{

    E  generateEntityByDTO(D dto) throws ErrorException;
    E  mapEntityByWithDTO(E entity, D dto) throws ErrorException;
    D generateDTOByEntity(E entity) throws ErrorException;

    E mapDTOToEntity(D dto) throws ErrorException;
}
