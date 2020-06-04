package com.ices.simulation.dao.mapper;
import com.ices.simulation.dao.model.instructionListSize;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface instructionListSizeMapper {

    @Insert("insert into instructionlistsize(listName,outName,indent) " +
            "values(#{listName},#{outName},#{indent})")
    void insert(instructionListSize listsize);

    @Select("select max(sizeId) from instructionlistsize")
    int maxId();

}
