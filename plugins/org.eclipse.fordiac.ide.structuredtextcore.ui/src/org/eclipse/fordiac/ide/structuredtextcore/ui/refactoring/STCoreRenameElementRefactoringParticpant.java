/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.xtext.ui.refactoring.impl.AbstractProcessorBasedRenameParticipant;
import org.eclipse.xtext.ui.refactoring.impl.ProjectUtil;
import org.eclipse.xtext.ui.refactoring.impl.RefactoringResourceSetProvider;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreRenameElementRefactoringParticpant extends AbstractProcessorBasedRenameParticipant {

	@Inject
	private RefactoringResourceSetProvider resourceSetProvider;

	@Inject
	private ProjectUtil projectUtil;

	@Override
	protected List<? extends IRenameElementContext> createRenameElementContexts(final Object element) {
		if (element instanceof final URI elementURI) {
			final IProject project = projectUtil.getProject(elementURI);
			if (project != null) {
				final ResourceSet resourceSet = resourceSetProvider.get(project);
				final EObject originalTarget = resourceSet.getEObject(getTypeURI(elementURI), true);
				final List<EObject> renamedElements = getRenamedElementsOrProxies(originalTarget);
				if (renamedElements == null || renamedElements.isEmpty()) {
					// we really need to return null here
					return null; // NOSONAR
				}
				return renamedElements.stream()
						.map(renamedElement -> new IRenameElementContext.Impl(EcoreUtil.getURI(renamedElement),
								renamedElement.eClass()))
						.toList();
			}
		}
		return super.createRenameElementContexts(element);
	}

	@Override
	protected List<EObject> getRenamedElementsOrProxies(final EObject originalTarget) {
		if (originalTarget != null) {
			return List.of(originalTarget);
		}
		return List.of();
	}

	protected static URI getTypeURI(final URI uri) {
		if (isCanonicalURI(uri) && !isDTPResourceURI(uri)) {
			return uri.trimFragment().appendFragment("/1" + uri.fragment().substring(1)); //$NON-NLS-1$
		}
		return uri;
	}

	protected static boolean isCanonicalURI(final URI uri) {
		return uri != null && uri.hasFragment() && uri.fragment().startsWith("/") && !uri.fragment().startsWith("/1"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected static boolean isDTPResourceURI(final URI uri) {
		return TypeLibraryTags.DATA_TYPE_FILE_ENDING.equalsIgnoreCase(uri.fileExtension());
	}

	@Override
	public String getName() {
		return Messages.STCoreRenameElementRefactoringParticpant_Name;
	}
}
