package service.integration;

import models.Component;

public class ComponentIntegrationServ {
	
	/**
	 * @pre parentComp, childComp is not null
	 * @post childComp is updated, parentComp is set as its parent
	 * @param parentComp
	 * @param childComp
	 */
	
	public static void setComponentParentChild(Component parentComp, Component childComp) {
		
		childComp.parent_component = parentComp;
		childComp.merge();
	}

}
