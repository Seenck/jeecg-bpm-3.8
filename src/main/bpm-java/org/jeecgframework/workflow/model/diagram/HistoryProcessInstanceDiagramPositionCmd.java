package org.jeecgframework.workflow.model.diagram;

import java.util.List;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class HistoryProcessInstanceDiagramPositionCmd implements Command {
	protected String historyProcessInstanceId;

	public HistoryProcessInstanceDiagramPositionCmd(
			String historyProcessInstanceId) {
		this.historyProcessInstanceId = historyProcessInstanceId;
	}

	public List execute(CommandContext commandContext) {
		 try {
			 CustomProcessDiagramPositionGenerator customProcessDiagramGenerator = new CustomProcessDiagramPositionGenerator();

	            return customProcessDiagramGenerator
	                    .generateDiagram(historyProcessInstanceId);
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	}

}
