package com.ices.simulation.dao.mapper.utils;

import com.ices.simulation.dao.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface taskInfoMapper {
    @Select("select * from instructionlistadd where addId = #{id}")
    @ResultType(value = instructionListAdd.class)
    instructionListAdd findListAddInstById(@Param("id")int id);

    @Select("select * from instructioncreate where createId = #{id}")
    @ResultType(value = instructionCreate.class)
    instructionCreate findCreateInstById(@Param("id")int id);


    @Select("select * from instructiondelay where delayId = #{id}")
    @ResultType(value = instructionDelay.class)
    instructionDelay findDelayInstById(@Param("id")int id);

    @Select("select * from instructionexpression where expressionId = #{id}")
    @ResultType(value = instructionExpression.class)
    instructionExpression findExpressionInstById(@Param("id")int id);

    @Select("select * from instructionforeach where id = #{id}")
    @ResultType(value = instructionForeach.class)
    instructionForeach findForeachInstById(@Param("id")int id);

    @Select("select * from instructionfornumber where id = #{id}")
    @ResultType(value = instructionFornumber.class)
    instructionFornumber findFornumberInstById(@Param("id")int id);

    @Select("select * from instructionlistclear where id = #{id}")
    @ResultType(value = instructionListClear.class)
    instructionListClear findListClearInstById(@Param("id")int id);

    @Select("select * from instructionlistget where id = #{id}")
    @ResultType(value = instructionListGet.class)
    instructionListGet findListGetInstById(@Param("id")int id);

    @Select("select * from instructionlistgetindex where id = #{id}")
    @ResultType(value = instructionListGetIndex.class)
    instructionListGetIndex findListGetIndexInstById(@Param("id")int id);

    @Select("select * from instructionlistremove where removeId = #{id}")
    @ResultType(value = instructionListRemove.class)
    instructionListRemove findListRemoveInstById(@Param("id")int id);

    @Select("select * from instructionlistsize where sizeId = #{id}")
    @ResultType(value = instructionListSize.class)
    instructionListSize findListSizeInstById(@Param("id")int id);

    @Select("select * from instructionmathabs where id = #{id}")
    @ResultType(value = instructionMathAbs.class)
    instructionMathAbs findMathAbsInstById(@Param("id")int id);

    @Select("select * from instructionobjectget where objectGetId = #{id}")
    @ResultType(value = instructionObjectGet.class)
    instructionObjectGet findObjectGetInstById(@Param("id")int id);

    @Select("select * from instructionobjectset where objectSetid = #{id}")
    @ResultType(value = instructionObjectSet.class)
    instructionObjectSet findObjectSetInstById(@Param("id")int id);

    @Select("select * from instructionrandomint where randomId = #{id}")
    @ResultType(value = instructionRandomInt.class)
    instructionRandomInt findRandomIntInstById(@Param("id")int id);

    @Select("select * from instructionrandomordername where randomId = #{id}")
    @ResultType(value = instructionRandomOrderName.class)
    instructionRandomOrderName findRandomOrderNameInstById(@Param("id")int id);

    @Select("select * from instructionselect where selectId = #{id}")
    @ResultType(value = instructionSelect.class)
    instructionSelect findSelectInstById(@Param("id")int id);

    @Select("select * from instructiontypeconversion where id = #{id}")
    @ResultType(value = instructionTypeConversion.class)
    instructionTypeConversion findTypeConversionInstById(@Param("id")int id);

    @Select("select * from instructionupdatetimeperiod where updatetimeperiodId = #{id}")
    @ResultType(value = instructionUpdateTimePeriod.class)
    instructionUpdateTimePeriod findUpdateTimePeriodInstById(@Param("id")int id);

}
