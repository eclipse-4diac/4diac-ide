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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;
import org.eclipse.xtext.ui.refactoring2.rename.DefaultLinkedPositionGroupCalculator2;

import com.google.inject.Provider;

@SuppressWarnings("restriction")
public class STCoreLinkedPositionGroupCalculator extends DefaultLinkedPositionGroupCalculator2 {

	@Override
	public Provider<LinkedPositionGroup> getLinkedPositionGroup(final IRenameElementContext renameElementContext,
			final IProgressMonitor monitor) {
		final XtextEditor editor = (XtextEditor) renameElementContext.getTriggeringEditor();
		if (!editor.getDocument().getResourceURI().equals(renameElementContext.getTargetElementURI().trimFragment())) {
			return LinkedPositionGroup::new; // do not use linked editing for out-of-editor refactorings
		}
		return super.getLinkedPositionGroup(renameElementContext, monitor);
	}
}
