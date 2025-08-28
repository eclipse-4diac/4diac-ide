/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from StructuredTextFBTypeEditor, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.edit.TypeEntryAdapter;
import org.eclipse.fordiac.ide.typeeditor.XtextTypeEditorPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.MultiPageEditorSite;

public abstract class FBTypeXtextEditor extends XtextTypeEditorPage implements IFBTEditorPart {

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		installFBTypeUpdater();
	}

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		removeFBTypeUpdater();
		super.doSetInput(input);
	}

	@Override
	public void dispose() {
		removeFBTypeUpdater();
		super.dispose();
	}

	public TypeEntryAdapter getTypeEntryAdapter() {
		if (getEditorSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			return multiPageEditorSite.getMultiPageEditor().getAdapter(TypeEntryAdapter.class);
		}
		return null;
	}

	protected abstract void removeFBTypeUpdater();

	protected abstract void installFBTypeUpdater();

	protected abstract boolean selectAndReveal(Object selectedElement, boolean b);
}
