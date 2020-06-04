package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.federateList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface federateListMapper {
    @Insert("insert into federateList(objectName,listName)"
            +"values(#{objectName},#{listName})")
    void insert(federateList federatelist);
}
