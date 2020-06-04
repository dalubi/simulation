package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.editInformation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface editInformationMapper {
   @Select("select federateId from federate where federateName = #{federateName}")
   Integer findFederateIdByFederateName(@Param("federateName") String federateName);

   @Select("select interactionId from interaction where interactionName = #{interactionName}")
   Integer findInteractionIdByInteractionName(@Param("interactionName") String interactionName);

   @Insert("insert into editinformation(fromFederateId,fromInteractionId,taskId) " +
           "values(#{fromFederateId},#{fromInteractionId},#{taskId})")
   void insert(editInformation editInformation);

}
