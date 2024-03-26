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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.refactoring;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameStrategy;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.ide.refactoring.RenameChange;
import org.eclipse.xtext.ide.refactoring.RenameContext;

@SuppressWarnings("restriction")
public class STFunctionRenameStrategy extends STCoreRenameStrategy {

	@Override
	protected void doRename(final EObject target, final RenameChange change, final RenameContext context) {
		if (target.eResource() instanceof final STFunctionResource resource) {
			if (target instanceof final FunctionFBType functionType
					&& resource.getParseResult().getRootASTElement() instanceof final STFunctionSource source) {
				source.getFunctions().stream().filter(function -> function.getName().equals(functionType.getName()))
						.forEach(function -> function.setName(change.getNewName()));
				renameResource(resource, change);
			} else if (target instanceof final STFunction function && shouldRenameType(function, resource)) {
				resource.getInternalLibraryElement().setName(change.getNewName());
				renameResource(resource, change);
			}
		}
		super.doRename(target, change, context);
	}

	protected static void renameResource(final STFunctionResource resource, final RenameChange change) {
		resource.setURI(resource.getURI().trimSegments(1).appendSegment(change.getNewName())
				.appendFileExtension(resource.getURI().fileExtension()));
	}

	protected static boolean shouldRenameType(final STFunction function, final STFunctionResource resource) {
		return Objects.equals(function.getName(), TypeEntry.getTypeNameFromFileName(resource.getURI().lastSegment()))
				&& TypeLibraryTags.FC_TYPE_FILE_ENDING.equalsIgnoreCase(resource.getURI().fileExtension());
	}
}
