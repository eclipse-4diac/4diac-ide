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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreLinkedPositionGroupCalculator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

import com.google.inject.Provider;

@SuppressWarnings("restriction")
public class STFunctionLinkedPositionGroupCalculator extends STCoreLinkedPositionGroupCalculator {

	@Override
	public Provider<LinkedPositionGroup> getLinkedPositionGroup(final IRenameElementContext renameElementContext,
			final IProgressMonitor monitor) {
		if (STFunctionPackage.Literals.ST_FUNCTION.equals(renameElementContext.getTargetElementEClass())
				|| STCorePackage.Literals.ST_VAR_DECLARATION.equals(renameElementContext.getTargetElementEClass())) {
			return LinkedPositionGroup::new; // do not use linked editing for function refactorings
		}
		return super.getLinkedPositionGroup(renameElementContext, monitor);
	}
}
