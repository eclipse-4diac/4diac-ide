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
package org.eclipse.fordiac.ide.typeeditor;

import java.util.Map;

import org.eclipse.fordiac.ide.gef.editors.GraphicalMultipageEditorContributor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;

public class TypeEditorContributor extends GraphicalMultipageEditorContributor {
	private final TextEditorActionContributor textContributor = createTextContributor();
	private SubActionBars textActionBars;

	@Override
	public void init(final IActionBars bars) {
		super.init(bars);
		textActionBars = new SubActionBars(bars);
		textContributor.init(textActionBars);
	}

	@Override
	public void dispose() {
		textActionBars.dispose();
		textContributor.dispose();
		super.dispose();
	}

	@Override
	public void setActivePage(final IEditorPart newEditor) {
		if (newEditor instanceof final ITextEditor newTextEditor) {
			super.setActivePage(null);
			textContributor.setActiveEditor(newTextEditor);
			textActionBars.activate();
			forwardGlobalActionHandlers(true);
		} else {
			forwardGlobalActionHandlers(false);
			textActionBars.deactivate();
			textContributor.setActiveEditor(null);
			super.setActivePage(newEditor);
		}
		getActionBars().updateActionBars();
	}

	protected void forwardGlobalActionHandlers(final boolean active) {
		final Map<String, IAction> handlers = textActionBars.getGlobalActionHandlers();
		if (handlers != null) {
			for (final var action : handlers.entrySet()) {
				getActionBars().setGlobalActionHandler(action.getKey(), active ? action.getValue() : null);
			}
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected TextEditorActionContributor createTextContributor() {
		return new TextEditorActionContributor();
	}

	public IEditorActionBarContributor getTextContributor() {
		return textContributor;
	}
}
