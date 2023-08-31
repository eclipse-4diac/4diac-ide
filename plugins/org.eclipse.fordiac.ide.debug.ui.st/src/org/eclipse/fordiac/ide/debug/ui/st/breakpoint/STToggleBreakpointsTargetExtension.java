/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.st.breakpoint;

import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.debug.st.breakpoint.STLineBreakpoint;
import org.eclipse.fordiac.ide.debug.ui.breakpoint.CommonToggleBreakpointsTargetExtension;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class STToggleBreakpointsTargetExtension extends CommonToggleBreakpointsTargetExtension {

	private static final Set<String> APPLICABLE_LANGUAGES = Set.of(
			"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm", //$NON-NLS-1$
			"org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction"); //$NON-NLS-1$

	@Override
	protected STLineBreakpoint createBreakpoint(final IResource res, final int line) throws CoreException {
		return new STLineBreakpoint(res, line);
	}

	@Override
	protected boolean isValidSematicElementForBreakpoint(final EObject element) {
		if (element instanceof STExpression) {
			final EObject container = element.eContainer();
			if (container instanceof final STAssignment assignment) {
				return assignment.getRight() == element;
			}
			if (container instanceof final STForStatement forStatement) {
				return forStatement.getFrom() == element || forStatement.getBy() == element;
			}
			return container instanceof STStatement || container instanceof STElseIfPart
					|| container instanceof STCaseCases || container instanceof STContinue
					|| container instanceof STReturn || container instanceof STExit;
		}
		return false;
	}

	@Override
	protected boolean isApplicable(final XtextEditor editor) {
		return APPLICABLE_LANGUAGES.contains(editor.getLanguageName());
	}

	@Override
	protected boolean isValidBreakpointType(final IBreakpoint breakpoint) {
		return breakpoint instanceof STLineBreakpoint;
	}
}
