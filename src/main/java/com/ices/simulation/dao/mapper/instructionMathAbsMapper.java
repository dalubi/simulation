package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionMathAbs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionMathAbsMapper {

    @Insert("insert into instructionmathabs(fillInformation,outName,indent) " +
            "values(#{fillInformation},#{outName},#{indent})")
    void insert(instructionMathAbs mathabs);

    @Select("select max(id) from instructionmathabs")
    int maxId();
}
