package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.instructionDelay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionDelayMapper {
    @Insert("insert into instructionDelay (delayTime,logInformation,indent) " +
            "values(#{delayTime},#{logInformation},#{indent})")
    void insert(instructionDelay delay);

    @Select("select max(delayId) from instructionDelay")
    int maxId();
}
