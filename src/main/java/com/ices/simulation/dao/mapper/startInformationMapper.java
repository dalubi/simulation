package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.startInformation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;;

public interface startInformationMapper {
    @Insert("insert into elema.startinformation(federateId,isFirst,firstTask,initialInstanceTask,updateInstanceTask,listIds,variableIds) " +
            "values(#{federateId},#{isFirst},#{firstTask},#{initialInstanceTask},,#{updateInstanceTask},,#{listIds},,#{variableIds})")
    void insert(startInformation startinformation);

    @Select("select ")
    String queryFederateObjectById(int federateId);
}
