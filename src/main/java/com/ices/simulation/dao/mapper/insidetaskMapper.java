package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.insideTask;
import org.apache.ibatis.annotations.*;

@Mapper
public interface insidetaskMapper {
    @Insert("insert into insideTask(insidetaskId,information,iscomplete)"
            +"values(#{insidetaskId},#{information},#{iscomplete})")
    void insert(insideTask inside);

    @Update("update insideTask set insideTask.iscomplete=#{iscomplete}"+
    "where insideTask.insidetaskId=#{insidetaskId}")
    void update(@Param("insidetaskId") String insidetaskId,
                @Param("iscomplete") String iscomplete);

    @Select("select iscomplete from insideTask where insidetaskId=#{insidetaskId}")
    String checkCompleteById(@Param("insidetaskId")String insidetaskId);
}
