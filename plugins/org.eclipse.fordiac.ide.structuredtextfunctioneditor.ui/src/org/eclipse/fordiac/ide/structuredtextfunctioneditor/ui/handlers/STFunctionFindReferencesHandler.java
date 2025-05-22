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
 *   Qemal Alliu
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.model.search.ModelSearchQuery;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.xtext.ui.editor.findrefs.FindReferencesHandler;

@SuppressWarnings("restriction")
public class STFunctionFindReferencesHandler extends FindReferencesHandler {

	@Override
	protected void findReferences(final EObject target) {
		if (target instanceof final STFunction function) {
			// @formatter:off
			final ModelQuerySpec searchSpec = new ModelQuerySpec(
					function.getName(),
					false,
					false,
					true,
					false,
					false,
					true,
					true, // search initial value
					SearchScope.PROJECT,
					getProject(target),
					null
					);
			// @formatter:on

			final ModelSearchQuery searchJob = new ModelSearchQuery(searchSpec);
			NewSearchUI.runQueryInBackground(searchJob, NewSearchUI.getSearchResultView());
		} else {
			super.findReferences(target);
		}

	}

	private static IProject getProject(final EObject target) {
		final var resource = target.eResource();
		final IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(resource.getURI().toPlatformString(true)));
		return file.getProject();
	}
}
