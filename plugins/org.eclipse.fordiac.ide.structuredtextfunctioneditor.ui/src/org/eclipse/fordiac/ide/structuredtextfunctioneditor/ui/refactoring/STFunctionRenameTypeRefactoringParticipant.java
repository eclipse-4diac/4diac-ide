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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RenameTypeRefactoringParticipant;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

@SuppressWarnings("restriction")
public class STFunctionRenameTypeRefactoringParticipant extends RenameTypeRefactoringParticipant {

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IRenameElementContext context && isRelevant(context)
				&& context.getTargetElementURI().isPlatformResource()) {
			return super.initialize(ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(context.getTargetElementURI().toPlatformString(true))));
		}
		return super.initialize(element);
	}

	protected static boolean isRelevant(final IRenameElementContext context) {
		return STFunctionPackage.Literals.ST_FUNCTION.isSuperTypeOf(context.getTargetElementEClass());
	}

	@Override
	public void checkFileEnding(final RefactoringStatus result, final TypeEntry typeEntry) {
		// not necessary
	}
}
