/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;

class TypeLibRootElement implements IAdaptable{

	private AutomationSystem system;
	
	TypeLibRootElement(AutomationSystem system) {
		this.system = system;
	}
	
	public AutomationSystem getSystem(){
		return system;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAdapter(Class adapter) {
		if((adapter == IResource.class) || (adapter == IProject.class)){
			return getSystem().getProject();
		}
		return null;
	}

	
}
