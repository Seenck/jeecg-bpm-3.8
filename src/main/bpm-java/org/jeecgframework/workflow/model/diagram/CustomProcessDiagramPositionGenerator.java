package org.jeecgframework.workflow.model.diagram;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.io.FilenameUtils;

/**
 * 获取流程任务节点
 */
public class CustomProcessDiagramPositionGenerator {
    public static final int OFFSET_SUBPROCESS = 5;
    public static final int OFFSET_TASK = 20;
    private static List<String> taskType = new ArrayList<String>();
    private static List<String> eventType = new ArrayList<String>();
    private static List<String> gatewayType = new ArrayList<String>();
    private static List<String> subProcessType = new ArrayList<String>();

    public CustomProcessDiagramPositionGenerator() {
        init();
    }

    protected static void init() {
        taskType.add(BpmnXMLConstants.ELEMENT_TASK_MANUAL);
        taskType.add(BpmnXMLConstants.ELEMENT_TASK_RECEIVE);
        taskType.add(BpmnXMLConstants.ELEMENT_TASK_SCRIPT);
        taskType.add(BpmnXMLConstants.ELEMENT_TASK_SEND);
        taskType.add(BpmnXMLConstants.ELEMENT_TASK_SERVICE);
        taskType.add(BpmnXMLConstants.ELEMENT_TASK_USER);

        gatewayType.add(BpmnXMLConstants.ELEMENT_GATEWAY_EXCLUSIVE);
        gatewayType.add(BpmnXMLConstants.ELEMENT_GATEWAY_INCLUSIVE);
        gatewayType.add(BpmnXMLConstants.ELEMENT_GATEWAY_EVENT);
        gatewayType.add(BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL);

        eventType.add("intermediateTimer");
        eventType.add("intermediateMessageCatch");
        eventType.add("intermediateSignalCatch");
        eventType.add("intermediateSignalThrow");
        eventType.add("messageStartEvent");
        eventType.add("startTimerEvent");
        eventType.add(BpmnXMLConstants.ELEMENT_ERROR);
        eventType.add(BpmnXMLConstants.ELEMENT_EVENT_START);
        eventType.add("errorEndEvent");
        eventType.add(BpmnXMLConstants.ELEMENT_EVENT_END);

        subProcessType.add(BpmnXMLConstants.ELEMENT_SUBPROCESS);
        subProcessType.add(BpmnXMLConstants.ELEMENT_CALL_ACTIVITY);
    }

    public List generateDiagram(String processInstanceId)
            throws IOException {
        HistoricProcessInstance historicProcessInstance = Context
                .getCommandContext().getHistoricProcessInstanceEntityManager()
                .findHistoricProcessInstance(processInstanceId);
        String processDefinitionId = historicProcessInstance
                .getProcessDefinitionId();
        GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(
                processDefinitionId);
        BpmnModel bpmnModel = getBpmnModelCmd.execute(Context
                .getCommandContext());
        
       bpmnModel.getLocationMap();
        ProcessDefinitionEntity definition = new GetDeploymentProcessDefinitionCmd(
                processDefinitionId).execute(Context.getCommandContext());
//        String diagramResourceName = definition.getDiagramResourceName();
//        String deploymentId = definition.getDeploymentId();
//        byte[] bytes = Context
//                .getCommandContext()
//                .getResourceEntityManager()
//                .findResourceByDeploymentIdAndResourceName(deploymentId,
//                        diagramResourceName).getBytes();
//        InputStream originDiagram = new ByteArrayInputStream(bytes);
//        BufferedImage image = ImageIO.read(originDiagram);

        HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();
        historicActivityInstanceQueryImpl.processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().asc();

        Page page = new Page(0, 100);
        List<HistoricActivityInstance> activityInstances = Context
                .getCommandContext()
                .getHistoricActivityInstanceEntityManager()
                .findHistoricActivityInstancesByQueryCriteria(
                        historicActivityInstanceQueryImpl, page);
        List<ActivityImpl>  listResult=new ArrayList<ActivityImpl>();

        for (HistoricActivityInstance historicActivityInstance : activityInstances) {
            String historicActivityId = historicActivityInstance.getActivityId();       
            String activityTypeName=historicActivityInstance.getActivityType();
            ActivityImpl activity = definition.findActivity(historicActivityId);
            if (activity != null) {
            	
                if (historicActivityInstance.getEndTime() != null&&activityTypeName.equals("userTask")) {
                	
                	listResult.add(activity);
                } 

            }
        }

        return listResult;
    }

    private static String getDiagramExtension(String diagramResourceName) {
        return FilenameUtils.getExtension(diagramResourceName);
    }

   


  
}
