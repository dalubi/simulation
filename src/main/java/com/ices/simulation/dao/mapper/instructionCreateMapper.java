package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.instructionCreate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionCreateMapper {
    @Insert("insert into instructioncreate(objectId,typeInformation,actualInformation,outName,indent) " +
            "values(#{objectId},#{typeInformation},#{actualInformation},#{outName},#{indent})")
    void insert(instructionCreate create);

    @Select("select max(createId) from instructioncreate")
    Integer maxId();

    @Select("select objectId from federateObject where objectName = #{federateObject}")
    Integer findObjectIdByName(@Param("federateObject") String federateObject);
}
