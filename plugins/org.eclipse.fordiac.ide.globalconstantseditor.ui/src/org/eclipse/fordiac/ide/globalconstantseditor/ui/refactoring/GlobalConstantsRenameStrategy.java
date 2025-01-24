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
package org.eclipse.fordiac.ide.globalconstantseditor.ui.refactoring;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstants;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameStrategy;
import org.eclipse.xtext.ide.refactoring.RenameChange;
import org.eclipse.xtext.ide.refactoring.RenameContext;

@SuppressWarnings("restriction")
public class GlobalConstantsRenameStrategy extends STCoreRenameStrategy {

	@Override
	protected void doRename(final EObject target, final RenameChange change, final RenameContext context) {
		if (target.eResource() instanceof final GlobalConstantsResource resource) {
			if (target instanceof final GlobalConstants globalConstants
					&& resource.getParseResult().getRootASTElement() instanceof final STGlobalConstsSource source) {
				if (source.getConstants() != null
						&& Objects.equals(source.getConstants().getName(), globalConstants.getName())) {
					source.getConstants().setName(change.getNewName());
				}
				renameResource(resource, change);
			} else if (target instanceof final STGlobalConstants globalConstants
					&& shouldRenameType(globalConstants, resource)) {
				resource.getInternalLibraryElement().setName(change.getNewName());
				renameResource(resource, change);
			}
		}
		super.doRename(target, change, context);
	}

	protected static void renameResource(final GlobalConstantsResource resource, final RenameChange change) {
		resource.setURI(resource.getURI().trimSegments(1).appendSegment(change.getNewName())
				.appendFileExtension(resource.getURI().fileExtension()));
	}

	protected static boolean shouldRenameType(final STGlobalConstants globalConstants,
			final GlobalConstantsResource resource) {
		return Objects.equals(globalConstants.getName(),
				TypeEntry.getTypeNameFromFileName(resource.getURI().lastSegment()))
				&& TypeLibraryTags.GLOBAL_CONST_FILE_ENDING.equalsIgnoreCase(resource.getURI().fileExtension());
	}
}
