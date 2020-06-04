package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionListGetIndex;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionListGetIndexMapper {
    @Insert("insert into instructionlistgetindex(listName,objectName,fillInformation,outName,indent) " +
            "values(#{listName},#{objectName},#{fillInformation},#{outName},#{indent})")
    void insert(instructionListGetIndex listindex);

    @Select("select max(id) from instructionlistgetindex")
    int maxId();
}
