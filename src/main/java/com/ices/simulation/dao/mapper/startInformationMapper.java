package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.startInformation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;;import java.util.List;

public interface startInformationMapper {
    @Insert("insert into startinformation(federateId,isFirst,firstTask,initialInstanceTask,updateInstanceTask,listIds,variableIds) " +
            "values(#{federateId},#{isFirst},#{firstTask},#{initialInstanceTask},,#{updateInstanceTask},,#{listIds},,#{variableIds})")
    void insert(startInformation startinformation);

    @Select("select federateId from startinformation where isFirst=-1")
    List<Integer> queryExternalId();

    @Select("select listIds from startinformation where federateId=#{federateId}")
    String queryFederateListById(int federateId);

    @Update("update startinformation set startinformation.listIds = #{listIds}" +
            " where federateId = #{federateId}")
    void updateFederateListByFederateId(@Param("federateId")int federateId,
                                        @Param("listIds")String listIds);


    @Select("select variableIds from startinformation where federateId=#{federateId}")
    String queryFederateVariableById(int federateId);

    @Update("update startinformation set startinformation.variableIds = #{variableIds}" +
            " where federateId = #{federateId}")
    void updateFederateVariableByFederateId(@Param("federateId")int federateId,
                                            @Param("variableIds")String variableIds);


    @Select("select objects from startinformation where federateId=#{federateId}")
    String queryFederateObjectsById(int federateId);

    @Update("update startinformation set startinformation.objects = #{objects}" +
            " where federateId = #{federateId}")
    void updateFederateObjectByFederateId(@Param("federateId")int federateId,
                                          @Param("objects")String objects);

}
