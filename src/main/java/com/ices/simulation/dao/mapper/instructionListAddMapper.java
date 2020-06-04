package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionListAdd;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionListAddMapper {
    @Insert("insert into instructionlistadd(listName,objectName,indent) " +
            "values(#{listName},#{objectName},#{indent})")
    void insert(instructionListAdd listadd);

    @Select("select max(addId) from instructionlistadd")
    int maxId();
}
