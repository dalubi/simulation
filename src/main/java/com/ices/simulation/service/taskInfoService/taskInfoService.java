package com.ices.simulation.service.taskInfoService;

import com.ices.simulation.dao.mapper.*;
import com.ices.simulation.dao.mapper.utils.taskInfoMapper;
import com.ices.simulation.dao.model.*;
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

        if(taskInfoById.getInstructionIds().length()==0||taskInfoById.getInstructionSequence().length()==0){
            taskInfo taskInfo = new taskInfo();
            taskInfo.setInstructionId(-1);
            taskInfo.setInstructionCount(-1);
            taskInfo.setButtonValue("-1");
            taskInfo.setInstructionDesc("本命令没有仿真指令");
            taskInfo.setInstructionName("无指令");
            infos.add(taskInfo);
        }else{
            sequence = taskInfoById.getInstructionSequence().split(",");
            ids = taskInfoById.getInstructionIds().split(",");

            for(int i=0;i<sequence.length;i++){
                //组装一个指令
                taskInfo instructionInfoById = getInstructionInfoById(i+1,sequence[i], Integer.parseInt(ids[i]));
                infos.add(instructionInfoById);
            }
        }
        return infos;
    }

    private taskInfo getInstructionInfoById(int count,String instruction,int instructionId){
        taskInfo taskInfo = new taskInfo();
        StringBuilder sb=new StringBuilder();
        switch (instruction){
            case "listadd":
                instructionListAdd listAddById = taskInfoMapper.findListAddInstById(instructionId);
                sb.append("指令id:"+listAddById.getAddId()+"，")
                        .append("向名为:"+listAddById.getListName()+"的list中增加一个对象，该对象名为")
                        .append(listAddById.getObjectName()+"。");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listadd_"+instructionId);
                break;

            case "create":
                instructionCreate createById = taskInfoMapper.findCreateInstById(instructionId);
                sb.append("指令id:"+createById.getCreateId()+"，")
                        .append("创建一个"+createById.getObjectId()+"类型的对象，这个对象的一一对应的参数信息是{[类型]:[")
                        .append(createById.getTypeInformation())
                        .append("];[变量名]:[")
                        .append(createById.getActualInformation())
                        .append("]}，用")
                        .append(createById.getOutName()+"保存这个对象");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("create_"+instructionId);
                break;

            case "delay":
                instructionDelay delayById = taskInfoMapper.findDelayInstById(instructionId);
                sb.append("指令id:"+delayById.getDelayId()+"，")
                        .append("设置延时时间为:"+delayById.getDelayTime()+"，并添加日志信息：")
                        .append(delayById.getLogInformation());
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("delay_"+instructionId);
                break;

            case "expression":
                instructionExpression expressionById = taskInfoMapper.findExpressionInstById(instructionId);
                sb.append("指令id:"+expressionById.getExpressId()+"，")
                        .append("设置了一个表达式:"+expressionById.getExpressionInformation()+"。表达式中每个？对应的[填充类型]:[")
                        .append(expressionById.getFillType())
                        .append("]；[具体信息]:[")
                        .append(expressionById.getFillInformation())
                        .append("]；表达式的名称为"+expressionById.getOutName())
                        .append(",表达式的类型为"+expressionById.getOutType());
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("expression_"+instructionId);
                break;

            case "foreach":
                instructionForeach foreachById = taskInfoMapper.findForeachInstById(instructionId);
                sb.append("指令id:"+foreachById.getId()+"，")
                        .append("列表名("+foreachById.getListName()+")中的每个对象(")
                        .append(foreachById.getObjectName())
                        .append(")将执行Id(")
                        .append(foreachById.getInsideTaskId())
                        .append(")的内部任务，在执行内部任务时每个对象设置了别名(")
                        .append(foreachById.getOutName())
                        .append(")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("foreach_"+instructionId);
                break;

            case "fornumber":
                instructionFornumber forNumberById = taskInfoMapper.findFornumberInstById(instructionId);
                sb.append("指令id:"+forNumberById.getId()+"，")
                        .append("将执行"+forNumberById.getNumber()+"次内部任务，该任务的Id为")
                        .append(forNumberById.getInsideTaskId());
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("fornumber_"+instructionId);
                break;

            case "listclear":
                instructionListClear listClearById = taskInfoMapper.findListClearInstById(instructionId);
                sb.append("指令id:"+listClearById.getId()+"，")
                        .append("清空列表("+listClearById.getListName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listclear_"+instructionId);
                break;

            case "listget":
                instructionListGet listGetById = taskInfoMapper.findListGetInstById(instructionId);
                sb.append("指令id:"+listGetById.getId()+"，")
                        .append("从列表("+listGetById.getListName()+")")
                        .append("中根据参数("+listGetById.getAccordingParameter()+")得到对象(")
                        .append(listGetById.getObjectName()+"),参数的填充类型(")
                        .append(listGetById.getGetType()+"),参数的填充内容(")
                        .append(listGetById.getFillInformation()+"),为得到的对象命名(")
                        .append(listGetById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listget_"+instructionId);
                break;

            case "listgetindex":
                instructionListGetIndex listGetIndexById = taskInfoMapper.findListGetIndexInstById(instructionId);
                sb.append("指令id:"+listGetIndexById.getId()+"，")
                        .append("从列表("+listGetIndexById.getListName()+")")
                        .append("中随机得到一个对象("+listGetIndexById.getObjectName()+"),命名该对象为(")
                        .append(listGetIndexById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listgetindex_"+instructionId);
                break;

            case "listremove":
                instructionListRemove listRemoveById = taskInfoMapper.findListRemoveInstById(instructionId);
                sb.append("指令id:"+listRemoveById.getRemoveId()+"，")
                        .append("从列表Id("+listRemoveById.getListId()+")")
                        .append("中根据[移除参数方式]("+listRemoveById.getRemoveType()+")和[移除参数名](")
                        .append(listRemoveById.getRemoveName()+")以及[移除参数类型](")
                        .append(listRemoveById.getAccordingToParameterName()+")移除一个对象");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listremove_"+instructionId);
                break;

            case "listsize":
                instructionListSize listSizeById = taskInfoMapper.findListSizeInstById(instructionId);
                sb.append("指令id:"+listSizeById.getSizeId()+"，")
                        .append("得到列表("+listSizeById.getListName()+")")
                        .append("的size，命名size为("+listSizeById.getOutName());
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("listsize_"+instructionId);
                break;

            case "mathabs":
                instructionMathAbs mathAbsById = taskInfoMapper.findMathAbsInstById(instructionId);
                sb.append("指令id:"+mathAbsById.getId()+"，")
                        .append("为("+mathAbsById.getFillInformation()+")取绝对值，命名绝对值为")
                        .append("("+mathAbsById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("mathabs_"+instructionId);
                break;

            case "objectget":
                instructionObjectGet objectGetById = taskInfoMapper.findObjectGetInstById(instructionId);
                sb.append("指令id:"+objectGetById.getObjectGetId()+"，")
                        .append("从对象("+objectGetById.getObjectName()+")中得到参数(")
                        .append(objectGetById.getParameterName()+")的值，值的类型为(")
                        .append(objectGetById.getOutType()+"),为值命名(")
                        .append(objectGetById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("objectget_"+instructionId);
                break;

            case "objectset":
                instructionObjectSet objectSetById = taskInfoMapper.findObjectSetInstById(instructionId);
                sb.append("指令id:"+objectSetById.getObjectSetId()+"，")
                        .append("为对象("+objectSetById.getObjectName()+")的参数(")
                        .append(objectSetById.getParameterName()+")设置一个新值；设置的方式(")
                        .append(objectSetById.getSetType()+"),设置内容(")
                        .append(objectSetById.getSetName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("objectset_"+instructionId);
                break;

            case "randomint":
                instructionRandomInt randomIntById = taskInfoMapper.findRandomIntInstById(instructionId);
                sb.append("指令id:"+randomIntById.getRandomId()+"，")
                        .append("从("+randomIntById.getFromInt()+")到(")
                        .append(randomIntById.getToInt()+")中得到一个随机整数，为该整数命名(")
                        .append(randomIntById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("randomint_"+instructionId);
                break;

            case "randomordername":
                instructionRandomOrderName randomOrderNameInstById = taskInfoMapper.findRandomOrderNameInstById(instructionId);
                sb.append("指令id:"+randomOrderNameInstById.getRandomId()+"，")
                        .append("得到一个随机菜名变量,")
                        .append("为该变量命名(")
                        .append(randomOrderNameInstById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("randomordername_"+instructionId);
                break;

            case "select":
                instructionSelect selectById = taskInfoMapper.findSelectInstById(instructionId);
                sb.append("指令id:"+selectById.getSelectId()+"，")
                        .append("满足条件("+selectById.getInformationName()+")时执行分支1(")
                        .append(selectById.getBranch1id()+"),不满足时执行分支2(")
                        .append(selectById.getBranch2id()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("select_"+instructionId);
                break;

            case "send":
                instructionSend sendById = taskInfoMapper.findSendInstById(instructionId);
                sb.append("指令id:"+sendById.getSendInteractionId()+"，")
                        .append("发送交互类Id("+sendById.getSendInteractionId()+"),[发送参数填充形式]:[")
                        .append(sendById.getSendTypes()+"],[参数填充内容]:[")
                        .append(sendById.getSendInformation()+"]");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("send_"+instructionId);
                break;

            case "typeconversion":
                instructionTypeConversion typeConversionById = taskInfoMapper.findTypeConversionInstById(instructionId);
                sb.append("指令id:"+typeConversionById.getId()+"，")
                        .append("将("+typeConversionById.getInformation()+"强制转换成(")
                        .append(typeConversionById.getConversionType()+")类型，为其命名(")
                        .append(typeConversionById.getOutName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("typeconversion_"+instructionId);
                break;

            case "timeperiod":
                instructionUpdateTimePeriod timePeriodById = taskInfoMapper.findUpdateTimePeriodInstById(instructionId);
                sb.append("指令id:"+timePeriodById.getUpdatetimeperiodId()+"，")
                        .append("为实例对象("+timePeriodById.getInstanceName()+")设置生命周期；活跃列表(")
                        .append(timePeriodById.getActiveListName()+")，休眠列表(")
                        .append(timePeriodById.getDormantListName()+")");
                taskInfo.setInstructionCount(count);
                taskInfo.setInstructionId(instructionId);
                taskInfo.setInstructionName(instruction);
                taskInfo.setInstructionDesc(sb.toString());
                taskInfo.setButtonValue("timeperiod_"+instructionId);
                break;
        }
        return taskInfo;
    }

}
