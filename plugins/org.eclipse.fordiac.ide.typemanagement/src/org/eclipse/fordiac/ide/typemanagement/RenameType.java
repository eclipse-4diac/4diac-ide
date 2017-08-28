/*******************************************************************************
 * Copyright (c) 2014 -2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

public class RenameType extends RenameParticipant {
	
	@Override
	protected boolean initialize(Object element) {
		return (element instanceof IFile);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		
		ResourceChangeChecker resChecker = context.getChecker(ResourceChangeChecker.class);
		IResourceChangeDescriptionFactory deltaFactory = resChecker.getDeltaFactory();
		IResourceDelta[] affectedChildren = deltaFactory.getDelta().getAffectedChildren();

		return verifyAffectedChildren(affectedChildren);
	}
	
	private RefactoringStatus verifyAffectedChildren(IResourceDelta[] affectedChildren) {
		for (IResourceDelta resourceDelta : affectedChildren) {
			if (resourceDelta.getMovedFromPath() != null) {
				String name = getTypeName(resourceDelta);
				if(null != name){					
					if (!IdentifierVerifyer.isValidIdentifier(name)) {
						return getWrongIdentifierErrorStatus();
					}
				}
			}
			else if (resourceDelta.getMovedToPath() == null){
				return verifyAffectedChildren(resourceDelta.getAffectedChildren());
			}
		}
		
		return new RefactoringStatus();
	}

	protected String getTypeName(IResourceDelta resourceDelta) {
		if(resourceDelta.getResource() instanceof IFile){
			return TypeLibrary.getTypeNameFromFile((IFile)resourceDelta.getResource());
		}
		return null;
	}

	protected RefactoringStatus getWrongIdentifierErrorStatus() {
		return RefactoringStatus.createFatalErrorStatus("Type Name is not a valid Identifier");
	}


	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return null;
	}

}
