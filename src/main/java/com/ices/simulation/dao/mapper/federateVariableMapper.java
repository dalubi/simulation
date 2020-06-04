package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.federateVariable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface federateVariableMapper {
    @Insert("insert into federatevariable(variableType,variableName,isStatic,InitialValue)"
            +"values(#{variableType},#{variableName},#{isStatic},#{InitialValue})")
    void insert(federateVariable federateVariable);

    @Select("select max(variableId) from federatevariable")
    int maxId();
}
