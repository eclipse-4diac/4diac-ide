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

import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreChangeSerializer;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.ide.refactoring.IResourceRelocationStrategy;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationChange;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext;

@SuppressWarnings("restriction")
public class STFunctionResourceRelocationStrategy implements IResourceRelocationStrategy {

	@Override
	public void applyChange(final ResourceRelocationContext context) {
		if (!(context.getChangeSerializer() instanceof STCoreChangeSerializer)) {
			return; // do not execute for shared rename contribution
		}
		context.getChanges().stream().filter(STFunctionResourceRelocationStrategy::isRelevant)
				.forEach(change -> applyChange(context, change));
	}

	protected static void applyChange(final ResourceRelocationContext context, final ResourceRelocationChange change) {
		context.addModification(change, resource -> modifyResource((STFunctionResource) resource, change));
	}

	protected static void modifyResource(final STFunctionResource resource, final ResourceRelocationChange change) {
		final String oldName = TypeEntry.getTypeNameFromFileName(change.getFromURI().lastSegment());
		final String newName = TypeEntry.getTypeNameFromFileName(change.getToURI().lastSegment());
		if (resource.getParseResult().getRootASTElement() instanceof final STFunctionSource source) {
			source.getFunctions().stream().filter(func -> Objects.equals(func.getName(), oldName))
					.forEach(func -> func.setName(newName));
		}
		if (resource.getInternalLibraryElement() instanceof final FunctionFBType functionType) {
			functionType.setName(newName);
		}
	}

	protected static boolean isRelevant(final ResourceRelocationChange change) {
		return TypeLibraryTags.FC_TYPE_FILE_ENDING.equalsIgnoreCase(change.getFromURI().fileExtension());
	}
}
