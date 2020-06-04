package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionObjectGet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionObjectGetMapper {
    @Insert("insert into instructionobjectget(objectName,parameterName,outType,outName,indent) " +
            "values(#{objectName},#{parameterName},#{outType},#{outName},#{indent})")
    void insert(instructionObjectGet objectget);

    @Select("select max(objectGetId) from instructionobjectget")
    int maxId();

}
