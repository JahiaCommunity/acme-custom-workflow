package org.jahia.modules.acmeCustomWorkflows;

import java.util.List;

import org.jahia.registries.ServicesRegistry;
import org.jahia.services.mail.MailServiceImpl;
import org.jahia.services.workflow.jbpm.custom.AbstractWorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;

public class AcmeCustomWorkItemHandler extends AbstractWorkItemHandler implements WorkItemHandler {

	private transient static Logger logger = LoggerFactory.getLogger(AcmeCustomWorkItemHandler.class);
	
    private MailServiceImpl mailService;
    
    public void setMailService(MailServiceImpl mailService) {
        this.mailService = mailService;
    }
	
	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		logger.debug("ACME CustomWorkItemHandler has been canceled");
		
		workItemManager.abortWorkItem(workItem.getId());
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		logger.debug("ACME CustomWorkItemHandler has been called");

		// Retrieving input data
		List<String> nodeIds = (List<String>) workItem.getParameter("nodeIds");
		String workspace = (String) workItem.getParameter("workspace");
		
		Boolean result = false;
		
		logger.debug("MailService is enable : " + mailService.isEnabled());

		mailService.sendMessage("admin@test.com", "verifier@test.com", "copy@test.comm", null, "Hi, please check me", "Hi, please, could you have a look in Web site, content is ready to publish....");
		
		/*
		 * Write business code here and eventually modify the result
		 * (call to webservices, checking data trying to be published, ...)
		 */
		result = false;
		
		// Setting output data
		workItem.getResults().put("valid", result);
		workItemManager.completeWorkItem(workItem.getId(), null);
	}

}
