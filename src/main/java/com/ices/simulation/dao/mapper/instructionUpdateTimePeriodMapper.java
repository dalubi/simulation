package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionUpdateTimePeriod;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionUpdateTimePeriodMapper {

    @Insert("insert into instructionupdatetimeperiod(instanceName,activeListName,dormantListName) " +
            "values(#{instanceName},#{activeListName},#{dormantListName})")
    void insert(instructionUpdateTimePeriod updatetimeperiod);

    @Select("select max(updatetimeperiodId) from instructionupdatetimeperiod")
    int maxId();
}
