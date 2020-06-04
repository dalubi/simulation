package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionSelect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionSelectMapper {
    @Insert("insert into instructionselect(informationName,branch1id,branch2id,indent) " +
            "values(#{informationName},#{branch1id},#{branch2id},#{indent})")
    void insert(instructionSelect select);

    @Select("select max(selectId) from instructionselect")
    int maxId();
}
