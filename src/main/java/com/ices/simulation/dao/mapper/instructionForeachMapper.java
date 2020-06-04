package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionForeach;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionForeachMapper {
    @Insert("insert into instructionforeach(listName,objectName,outName,insideTaskId,indent) " +
            "values(#{listName},#{objectName},#{outName},#{insideTaskId},#{indent})")
    void insert(instructionForeach foreach);

    @Select("select max(id) from instructionforeach")
    int maxId();
}
