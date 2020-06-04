package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.instructionListGet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionListGetMapper {
    @Insert("insert into instructionlistget(listName,objectName,getType,fillInformation,outName,accordingParameter,indent) " +
            "values(#{listName},#{objectName},#{getType},#{fillInformation},#{outName},#{accordingParameter},#{indent})")
    void insert(instructionListGet listget);

    @Select("select max(id) from instructionlistget")
    int maxId();
}
