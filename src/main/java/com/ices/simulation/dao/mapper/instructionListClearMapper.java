package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionListClear;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionListClearMapper {
    @Insert("insert into instructionlistclear(listName,indent) " +
            "values(#{listName},#{indent})")
    void insert(instructionListClear listclear);

    @Select("select max(id) from instructionlistclear")
    int maxId();
}
