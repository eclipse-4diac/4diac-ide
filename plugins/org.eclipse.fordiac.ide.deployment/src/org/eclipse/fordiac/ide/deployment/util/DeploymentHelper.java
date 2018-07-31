/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.util;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public interface DeploymentHelper {
	
	public static String getVariableValue(VarDeclaration varDecl, AutomationSystem system) {
		Value value = varDecl.getValue();
		if (null != value && null != value.getValue() && !"".equals(value.getValue())){ //$NON-NLS-1$
			String val = value.getValue();
			if (val.contains("%")) { //$NON-NLS-1$
				String replaced = SystemManager.INSTANCE.getReplacedString(system, val);
				if (replaced != null) {
					val = replaced;
				}
			}
			return val;
		}		
		return null;	
	}
	
	public static String getMgrID(final Device dev) {
		for(VarDeclaration varDecl : dev.getVarDeclarations()) {
			if (varDecl.getName().equalsIgnoreCase("MGR_ID")) { //$NON-NLS-1$
				String val = DeploymentHelper.getVariableValue(varDecl, dev.getAutomationSystem());
				if(null != val){				
					return val;
				}
			}
		}
		return ""; //$NON-NLS-1$
	}
	
}
