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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.search.ModelSearchQuery;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
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

		final QuerySpecConfig config = getQuerySpecConfig(target, nameProvider);
		if (config != null) {
			// @formatter:off
			final ModelQuerySpec searchSpec = new ModelQuerySpec(
					config.searchString(),	// Search String
					false, 					// Instance Name
					false, 					// Pin Name
					true, 					// Type
					false, 					// Comments
					false, 					// Case Sensitive
					true, 					// ExaktMatching
					true, 					// Search Initial Value
					config.scope(), 		// Search Scope
					config.source(),		// Source
					target 					// Reference Object
					);
			// @formatter:on

			final ModelSearchQuery searchJob = new ModelSearchQuery(searchSpec);
			NewSearchUI.runQueryInBackground(searchJob, NewSearchUI.getSearchResultView());
		} else {
			super.findReferences(target);
		}
	}

	private static IFile getFile(final EObject target) {
		final var resource = target.eResource();
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resource.getURI().toPlatformString(true)));
	}

	private static IProject getProject(final EObject target) {
		return getFile(target).getProject();
	}

	private static QuerySpecConfig getQuerySpecConfig(final EObject target, final IQualifiedNameProvider nameProvider) {
		return switch (target) {
		// @formatter:off
		case final STFunction function -> new QuerySpecConfig(
				nameProvider.getFullyQualifiedName(function).skipLast(1).toString(PackageNameHelper.PACKAGE_NAME_DELIMITER),
				SearchScope.PROJECT,
				getProject(target));
		case final FunctionFBType function -> new QuerySpecConfig(
				nameProvider.getFullyQualifiedName(function).toString(PackageNameHelper.PACKAGE_NAME_DELIMITER),
				SearchScope.PROJECT,
				getProject(target));
		case final STVarDeclaration varDec when varDec.eContainer() instanceof STVarGlobalDeclarationBlock ->
			new QuerySpecConfig(
					nameProvider.getFullyQualifiedName(varDec).toString(PackageNameHelper.PACKAGE_NAME_DELIMITER),
					SearchScope.PROJECT,
					getProject(target));
		case final VarDeclaration varDec -> new QuerySpecConfig(
				varDec.getName(),
				SearchScope.FILE,
				getFile(target));
		default -> null;
		// @formatter:on
		};
	}

	private record QuerySpecConfig(String searchString, SearchScope scope, IResource source) {
	}
}
