/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.debug.st.breakpoint.STLineBreakpoint;
import org.eclipse.fordiac.ide.debug.ui.breakpoint.CommonToggleBreakpointsTargetExtension;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class STToggleBreakpointsTargetExtension extends CommonToggleBreakpointsTargetExtension {

	private static final Set<String> APPLICABLE_LANGUAGES = Set.of(
			"org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithm", //$NON-NLS-1$
			"org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction"); //$NON-NLS-1$

	private static final Set<EReference> ADDITIONAL_VALID_REFERENCES = Set.of(
			STCorePackage.Literals.ST_IF_STATEMENT__CONDITION, STCorePackage.Literals.ST_ELSE_IF_PART__CONDITION,
			STCorePackage.Literals.ST_CASE_STATEMENT__SELECTOR, STCorePackage.Literals.ST_CASE_CASES__CONDITIONS,
			STCorePackage.Literals.ST_FOR_STATEMENT__FROM, STCorePackage.Literals.ST_FOR_STATEMENT__BY,
			STCorePackage.Literals.ST_WHILE_STATEMENT__CONDITION,
			STCorePackage.Literals.ST_REPEAT_STATEMENT__CONDITION);

	@Override
	protected STLineBreakpoint createBreakpoint(final IResource res, final int line) throws CoreException {
		return new STLineBreakpoint(res, line);
	}

	@Override
	protected boolean isValidSematicElementForBreakpoint(final EObject element) {
		if (element instanceof STExpression) {
			final EReference containment = element.eContainmentFeature();
			// statement lists OR additional valid references
			return (containment.isMany() && containment.getEReferenceType() == STCorePackage.Literals.ST_STATEMENT)
					|| ADDITIONAL_VALID_REFERENCES.contains(containment);
		}
		return element instanceof STContinue || element instanceof STReturn || element instanceof STExit;
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
