package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.federateObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface federateObjectMapper {

    @Insert("insert into federateObject(objectName,InitialId,parameterTypes,parameterNames) " +
            "values(#{objectName},#{InitialId},#{parameterTypes},#{parameterNames})")
    void insert(federateObject federateobject);
}
