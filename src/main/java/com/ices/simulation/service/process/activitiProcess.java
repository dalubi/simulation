package com.ices.simulation.service.process;

import com.ices.simulation.dto.taskDTO;
import lombok.Data;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class activitiProcess {

    private ProcessEngineConfiguration config;
    private ProcessEngine engine;
    private String processDefinitionKey;
    private String pathName;
    private List<taskDTO> taskDTOs=new ArrayList<>();

    private static int taskCount=0;

    //创建流程引擎
    public void init(String processDefinitionKey,String pathName){
        config=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("processes/activiti.cfg.xml");
        engine =config.buildProcessEngine();
        this.processDefinitionKey=processDefinitionKey;
        this.pathName=pathName;
    }

    public void process(){
        //得到流程存储服务实例
        RepositoryService repositoryService = engine.getRepositoryService();

        //部署流程描述文件与流程图
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(pathName).deploy();

        ProcessInstance processInstance = engine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
    }


    public void getTaskList(){
        List<Task> list = engine.getTaskService().createTaskQuery().list();
        for(Task task:list){
            taskDTO taskDTO = new taskDTO();
            taskDTO.setTaskId(task.getId());
            taskDTO.setTaskName(task.getName());
            taskDTO.setInteraction(task.getDescription());
            taskDTOs.add(taskDTO);
        }
    }

    public taskDTO queryTask(){
        if (taskCount==0){
            getTaskList();
        }
        if(taskCount<taskDTOs.size()){
            return taskDTOs.get(taskCount++);
        }else{//==的情况
            taskDTOs.clear();
            taskCount=0;
            getTaskList();
            if(taskDTOs.size()!=0){
                return taskDTOs.get(taskCount++);
            }else{
                return null;
            }
        }
    }


    public void completeTask(String taskId){
        engine.getTaskService().complete(taskId);
    }

}
