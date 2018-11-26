/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.onlineedit.handlers;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.deployment.ui.handlers.AbstractDeploymentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public abstract class AbstractOnlineFBHandler extends AbstractDeploymentCommand{
	
	FB fb = null;
	Resource resource = null;
	FB resFB = null;
	
	@Override
	protected boolean prepareParametersToExecute(Object element){
		if (element instanceof FBEditPart){
			fb = ((FBEditPart) element).getModel();
		}else if(element instanceof FB){
			fb = (FB)element;
		}
		if(null != fb){
			if(fb.isMapped()){
  				resource = fb.getResource();
  				if(null != resource){
  					device = resource.getDevice();
  					resFB = (FB)fb.getOpposite();
  					return (null != device && null != resFB);
  				}
			}
		}
		return false;
	}
	
	@Override
	protected String getCurrentElementName() {
		return "Function Block: " + fb.getName();
	}

}
