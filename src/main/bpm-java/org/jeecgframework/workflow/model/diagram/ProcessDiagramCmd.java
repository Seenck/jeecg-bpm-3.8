package org.jeecgframework.workflow.model.diagram;

import java.io.InputStream;

import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class ProcessDiagramCmd implements Command<InputStream> {
    protected String deploymentId;

    public ProcessDiagramCmd(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public InputStream execute(CommandContext commandContext) {
        try {
        	CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();
            return customProcessDiagramGenerator.generateDiagram(deploymentId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
