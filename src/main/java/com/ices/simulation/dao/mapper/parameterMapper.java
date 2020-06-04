package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.parameter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface parameterMapper {
    @Insert("insert into parameter(parameterName,parameterType) " +
            "values(#{parameterName},#{parameterType})")
    void insert(parameter para);

    @Select("select * from elema.parameter")
    @ResultType(value = parameter.class)
    List<parameter> getAllParameter();

}
