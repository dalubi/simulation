package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.instructionListRemove;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionListRemoveMapper {
    @Insert("insert into instructionlistremove(listId,accordingToParameterName,removeType,removeName,indent) " +
            "values(#{listId},#{accordingToParameterName},#{removeType},#{removeName},#{indent})")
    void insert(instructionListRemove listremove);

    @Select("select max(removeId) from instructionlistremove")
    int maxId();
}
