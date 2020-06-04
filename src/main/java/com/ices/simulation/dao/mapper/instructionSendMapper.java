package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionSend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionSendMapper {

    @Insert("insert into instructionsend(sendInteractionId,sendTypes,sendInformation,indent) " +
            "values(#{sendInteractionId},#{sendTypes},#{sendInformation},#{indent})")
    void insert(instructionSend send);

    @Select("select max(sendId) from instructionsend")
    int maxId();
}
