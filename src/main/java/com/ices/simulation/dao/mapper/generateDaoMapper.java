package com.ices.simulation.dao.mapper;

import com.ices.simulation.dao.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface generateDaoMapper {
    @Select("select * from federate")
    List<federate> findAllFederate();

    @Select("select * from federate where federateId=#{federateId}")
    federate findFederateById(Integer federateId);

    @Select("select * from interaction")
    List<interaction> findAllInteraction();

    @Select("select * from interaction where interactionId=#{interactionId}")
    interaction findInteractionById(Integer interactionId);

    @Select("select * from parameter")
    List<parameter> findAllParameter();

    @Select("select * from parameter where parameterId=#{parameterId}")
    parameter findParameterById(Integer parameterId);

    @Select("select * from startInformation where federateId=#{federateId}")
    startInformation findStartInformationById(Integer federateId);

    @Select("select * from editInformation where fromFederateId=#{federateId}  and fromInteractionId=#{interactionId} ")
    editInformation findEditInformationByInteractionId(@Param("federateId") Integer federateId, @Param("interactionId") Integer interactionId);

    @Select("select * from Task where taskId=#{taskId}")
    task findTaskByTaskId(Integer taskId);

    @Select("select * from federateObject where objectId=#{objectId}")
    federateObject findFederateObjectById(Integer objectId);

    @Select("select * from federateList where listId=#{listId}")
    federateList findFederateListById(Integer listId);

    @Select("select * from federateVariable where variableId=#{variableId}")
    federateVariable findFederateVariableById(Integer variableId);

    @Select("select * from externalInformation where Id=#{Id} ")
    externalInformation findExternalInformation(Integer Id);

    @Select("select * from federateObject")
    List<federateObject> findFederateObject();

    @Select("select * from instructionselect where selectId=#{instructionId}")
    instructionSelect findInstructionSelectById(Integer instructionId);

    @Select("select * from instructionCreate where createId=#{instructionId}")
    instructionCreate findInstructionCreateById(Integer instructionId);

    @Select("select * from instructionDelay where delayId=#{instructionId}")
    instructionDelay findInstructionDelayById(Integer instructionId);

    @Select("select * from instructionListAdd where addId=#{instructionId}")
    instructionListAdd findInstructionListAddById(Integer instructionId);

    @Select("select * from instructionListRemove where removeId=#{instructionId}")
    instructionListRemove findInstructionListRemoveById(Integer instructionId);

    @Select("select * from instructionObjectGet where objectGetId=#{instructionId}")
    instructionObjectGet findInstructionObjectGetById(Integer instructionId);

    @Select("select * from instructionObjectSet where objectSetId=#{instructionId}")
    instructionObjectSet findInstructionObjectSetById(Integer instructionId);

    @Select("select * from instructionSend where sendId=#{instructionId}")
    instructionSend findInstructionSendById(Integer instructionId);

    @Select("select * from instructionListSize where sizeId=#{instructionId}")
    instructionListSize findInstructionListSizeById(Integer instructionId);

    @Select("select * from instructionRandomInt where randomId=#{instructionId}")
    instructionRandomInt findInstructionRandomIntById(Integer instructionId);

    @Select("select * from instructionRandomOrderName where randomId=#{instructionId}")
    instructionRandomOrderName findInstructionRandomOrderNameById(Integer instructionId);

    @Select("select * from InstructionExpression where expressionId=#{instructionId}")
    instructionExpression findInstructionExpressionById(Integer instructionId);

    @Select("select * from InstructionMathAbs where id=#{instructionId}")
    instructionMathAbs findInstructionMathAbsById(Integer instructionId);

    @Select("select * from InstructionForNumber where id=#{instructionId}")
    instructionFornumber findInstructionForNumberById(Integer instructionId);

    @Select("select * from InstructionForeach where id=#{instructionId}")
    instructionForeach findInstructionForeachById(Integer instructionId);

    @Select("select * from InstructionListGet where id=#{instructionId}")
    instructionListGet findInstructionListGetById(Integer instructionId);

    @Select("select * from InstructionListGetIndex where id=#{instructionId}")
    instructionListGetIndex findInstructionListGetIndexById(Integer instructionId);

    @Select("select * from InstructionUpdateTimePeriod where id=#{instructionId}")
    instructionUpdateTimePeriod findInstructionUpdateTimePeriodById(Integer instructionId);

    @Select("select * from InstructionTypeConversion where id=#{instructionId}")
    instructionTypeConversion findInstructionTypeConversionById(Integer instructionId);

    @Select("select * from InstructionListClear where id=#{instructionId}")
    instructionListClear findInstructionListClearById(Integer instructionId);

    @Insert("insert into federate(federateName) " +
            "values(#{federateName})")
    void insertExternal(String federateName);

    @Select("select max(Id) from externalinformation")
    int ExternalInformationMaxId();

    @Select("select max(Id) from runtimecrtl")
    int runtimeMaxId();

    @Select("select runtime from runtimecrtl where id=#{id}")
    int queryRuntime(int id);

    @Insert("insert into runtimecrtl(runtime) values(#{runtime})")
    void insertRuntime(int runtime);
}
