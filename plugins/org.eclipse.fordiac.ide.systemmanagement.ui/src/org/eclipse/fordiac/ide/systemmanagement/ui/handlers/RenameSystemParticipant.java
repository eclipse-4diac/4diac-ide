/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.fordiac.ide.typemanagement.RenameType;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameSystemParticipant extends RenameType {
	
	@Override
	protected boolean initialize(Object element) {
		//TODO check if 4DIAC nature
		return (element instanceof IProject);
	}
	
	@Override
	protected String getTypeName(IResourceDelta resourceDelta) {
		return resourceDelta.getResource().getName();
	}
	
	@Override
	protected RefactoringStatus getWrongIdentifierErrorStatus() {
		return RefactoringStatus.createFatalErrorStatus("System name is not a valid Identifier");
	}

}
