/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.search.ModelSearchQuery;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.ui.editor.findrefs.FindReferencesHandler;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreFindReferencesHandler extends FindReferencesHandler {
	@Inject
	private IQualifiedNameProvider nameProvider;

	@Override
	protected void findReferences(final EObject target) {
		if (target instanceof final STVarDeclaration varDec
				&& varDec.eContainer() instanceof STVarGlobalDeclarationBlock) {
			// @formatter:off
			final ModelQuerySpec searchSpec = new ModelQuerySpec(
					nameProvider.getFullyQualifiedName(varDec).toString("::"), //$NON-NLS-1$
					false,
					false,
					true,
					false,
					false,
					true,
					true, // search initial value
					SearchScope.PROJECT,
					getProject(varDec));
			// @formatter:on

			final ModelSearchQuery searchJob = new ModelSearchQuery(searchSpec);
			NewSearchUI.runQueryInBackground(searchJob, NewSearchUI.getSearchResultView());
		} else {
			super.findReferences(target);
		}
	}

	private static IProject getProject(final STVarDeclaration varDec) {
		final var resource = varDec.eResource();
		final IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(resource.getURI().toPlatformString(true)));
		return file.getProject();
	}

}
