package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.instructionRandomOrderName;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionrandomordernameMapper {

    @Insert("insert into instructionrandomordername(outName,indent) " +
            "values(#{outName},#{indent})")
    void insert(instructionRandomOrderName randomordername);

    @Select("select max(randomId) from instructionrandomordername")
    int maxId();
}
