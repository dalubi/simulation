package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.instructionExpression;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionExpressionMapper {

    @Insert("insert into instructionExpression (expressionInformation,fillType,fillInformation,outType,outName,indent) " +
            "values(#{expressionInformation},#{fillType},#{fillInformation},#{outType},#{outName},#{indent})")
    void insert(instructionExpression expression);

    @Select("select max(expressionId) from instructionExpression")
    int maxId();
}
