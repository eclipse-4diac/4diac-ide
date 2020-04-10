/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.RenameType;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameSystemParticipant extends RenameType {

	@Override
	protected boolean initialize(Object element) {
		// TODO check if 4DIAC nature
		return (element instanceof IProject);
	}

	@Override
	protected String getTypeName(IResourceDelta resourceDelta) {
		return resourceDelta.getResource().getName();
	}

	@Override
	protected RefactoringStatus getWrongIdentifierErrorStatus() {
		return RefactoringStatus.createFatalErrorStatus(Messages.SystemNameNotValid);
	}

}
