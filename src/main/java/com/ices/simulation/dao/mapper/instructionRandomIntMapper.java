package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.instructionRandomInt;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionRandomIntMapper {
    @Insert("insert into instructionrandomint(fromInt,toInt,outName,indent) " +
            "values(#{fromInt},#{toInt},#{outName},#{indent})")
    void insert(instructionRandomInt randomint);

    @Select("select max(randomId) from instructionrandomint")
    int maxId();
}
