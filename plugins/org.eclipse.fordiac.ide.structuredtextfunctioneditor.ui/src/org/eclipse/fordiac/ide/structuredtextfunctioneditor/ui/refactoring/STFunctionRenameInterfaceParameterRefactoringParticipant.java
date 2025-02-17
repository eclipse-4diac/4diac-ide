/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.refactoring;

import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.RenameElementRefactoringParticipant;
import org.eclipse.xtext.ui.refactoring.impl.ProjectUtil;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STFunctionRenameInterfaceParameterRefactoringParticipant extends RenameElementRefactoringParticipant {

	@Inject
	private ProjectUtil projectUtil;

	@Inject
	private RefactoringResourceSetProvider resourceSetProvider;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IRenameElementContext context) {
			final IProject project = projectUtil.getProject(context.getTargetElementURI());
			if (project != null) {
				final ResourceSet resourceSet = resourceSetProvider.get(project);
				final EObject originalTarget = resourceSet.getEObject(context.getTargetElementURI(), true);
				if (originalTarget instanceof final STVarDeclaration parameter
						&& parameter.eContainer() instanceof final STVarDeclarationBlock block
						&& block.eContainer() instanceof final STFunction function
						&& function.eResource() instanceof final STFunctionResource resource
						&& resource.getInternalLibraryElement() instanceof final FunctionFBType functionFBType
						&& Objects.equals(function.getName(), functionFBType.getName())) {
					final VarDeclaration renamedElement = functionFBType.getInterfaceList()
							.getVariable(parameter.getName());
					if (renamedElement != null) {
						return super.initialize(new IRenameElementContext.Impl(EcoreUtil.getURI(renamedElement),
								renamedElement.eClass(), context.getTriggeringEditor(),
								context.getTriggeringEditorSelection(), context.getContextResourceURI()));
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return Messages.STFunctionRenameInterfaceParameterRefactoringParticipant_Name;
	}
}
