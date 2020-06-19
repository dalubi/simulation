package com.ices.simulation.dao.mapper.utils;

import com.ices.simulation.dao.model.instructionListAdd;
import com.ices.simulation.dao.model.task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface taskInfoMapper {
    @Select("select * from instructionlistadd where addId = #{id}")
    @ResultType(value = instructionListAdd.class)
    instructionListAdd findListAddById(@Param("id")int id);
}
