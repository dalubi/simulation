package com.ices.simulation.dao.mapper;


import com.ices.simulation.dao.model.task;
import org.apache.ibatis.annotations.*;

@Mapper
public interface taskMapper {
    @Insert("insert into task(taskId) values(#{taskId})")
    void insertTaskId(String taskId);

    //每个任务用来构造指令序列用的
    @Select("select instructionSequence from elema.task where taskId=#{taskId}")
    String findInstructionSequenceById(String taskId);

    @Select("select instructionIds from elema.task where taskId=#{taskId}")
    String findInstructionIdsById(String taskId);

    //更新指令序列
    @Update("update task set task.InstructionSequence = #{InstructionSequence} ," +
            "task.InstructionIds = #{InstructionIds} where taskId = #{taskId}")
    void updateSequenceAndinstructionIdsById(@Param("InstructionSequence") String InstructionSequance,
                                             @Param("InstructionIds") String InstructionIds,
                                             @Param("taskId") String taskId);

    //根据一个taskId，得到这个指令的任务序列
    @Select("select * from elema.task where taskId = #{taskId}")
    @ResultType(value = task.class)
    task findTaskInfoById(String taskId);

}
