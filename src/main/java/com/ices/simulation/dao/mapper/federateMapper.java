package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.federate;
import com.ices.simulation.dto.federateDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface federateMapper {
    @Insert("insert into federate(federateName,publishInteractionIds,subscribeInteractionIds) " +
            "values(#{federateName},#{publishInteractionIds},#{subscribeInteractionIds})")
    void insert(federate federate);

    @Select("select federateId,federateName from federate")
    @ResultType(value= federateDTO.class)
    List<federateDTO> allFederateInfo();

    @Select("select federateId from federate where federateName = #{federateName}")
    int findFederateIdByName(String federateName);
}
