package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionTypeConversion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionTypeConversionMapper {
    @Insert("insert into instructiontypeconversion(conversionType,information,outName,indent) " +
            "values(#{conversionType},#{information},#{outName},#{indent})")
    void insert(instructionTypeConversion typeconversion);

    @Select("select max(id) from instructiontypeconversion")
    int maxId();
}
