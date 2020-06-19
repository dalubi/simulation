package com.ices.simulation.service.taskInfoService;

import com.ices.simulation.dao.mapper.taskMapper;
import com.ices.simulation.dao.model.instructionListAdd;
import com.ices.simulation.dao.model.task;
import com.ices.simulation.dao.mapper.utils.taskInfoMapper;
import com.ices.simulation.service.taskInfoService.pojo.taskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class taskInfoService {

    @Autowired
    taskMapper taskMapper;

    @Autowired
    taskInfoMapper taskInfoMapper;

    private String[] sequence;
    private String[] ids;

    //返回这个task的所有指令的序列
    public List<taskInfo> getTaskDetailsByTaskId(int taskId){
        List<taskInfo> infos = new ArrayList<>();
        task taskInfoById = taskMapper.findTaskInfoById(taskId + "");
        this.sequence = taskInfoById.getInstructionSequence().split(",");
        this.ids = taskInfoById.getInstructionIds().split(",");
        for(int i=0;i<sequence.length;i++){
            taskInfo instructionInfoById = getInstructionInfoById(i,sequence[i], Integer.parseInt(ids[i]));
            infos.add(instructionInfoById);
        }
        return infos;
    }

    private taskInfo getInstructionInfoById(int count,String instruction,int instructionId){
        taskInfo taskInfo = new taskInfo();
        switch (instruction){
            case "listadd":
                instructionListAdd listAddById = taskInfoMapper.findListAddById(instructionId);
                StringBuilder sb = new StringBuilder();
                sb.append("指令id:"+listAddById.getAddId()+"，")
                        .append("向名为:"+listAddById.getListName()+"的list中增加一个对象，该对象名为")
                        .append(listAddById.getObjectName()+"。");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listadd_"+instructionId);
                break;

        }
        return taskInfo;
    }

}
