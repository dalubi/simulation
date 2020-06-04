package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionFornumber;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionFornumberMapper {
    @Insert("insert into instructionfornumber(number,insideTaskId,indent) " +
            "values(#{number},#{insideTaskId},#{indent})")
    void insert(instructionFornumber fornumber);

    @Select("select max(id) from instructionfornumber")
    int maxId();
}
