/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public abstract class DeploymentHelper {

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
	
}
