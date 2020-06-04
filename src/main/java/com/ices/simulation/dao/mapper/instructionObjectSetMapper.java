package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionObjectSet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionObjectSetMapper {

    @Insert("insert into instructionobjectset(objectName,parameterName,setType,setName,indent) " +
            "values(#{objectName},#{parameterName},#{setType},#{setName},#{indent})")
    void insert(instructionObjectSet objectset);

    @Select("select max(objectSetId) from instructionobjectset")
    int maxId();


}
