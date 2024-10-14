/*******************************************************************************
 * Copyright (c) 2023 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.swtbot;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;

public class SWT4diacGefBot extends SWTGefBot {

	@Override
	public SWTBot4diacGefEditor gefEditor(final String fileName) throws WidgetNotFoundException {
		return (SWTBot4diacGefEditor) super.gefEditor(fileName);
	}

	@Override
	protected SWTBot4diacGefEditor createEditor(final IEditorReference reference, final SWTWorkbenchBot bot) {
		return new SWTBot4diacGefEditor(reference, bot);
	}

	/**
	 * Attempts to locate a gef viewer that is embedded in a workbench part with the
	 * specified title.
	 *
	 * @param partTitle the workbench part title
	 * @return The gef viewer
	 * @throws WidgetNotFoundException if a workbench part with the specified title
	 *                                 cannot be found.
	 */
	@Override
	public SWTBot4diacGefViewer gefViewer(final String partTitle) throws WidgetNotFoundException {

		Object editorOrView = null;
		try {
			editorOrView = gefEditor(partTitle);
		} catch (final WidgetNotFoundException exception) {
			/* NOOP, means that it is not an editor. Search for a view instead then. */
			try {
				editorOrView = gefView(partTitle);
			} catch (final WidgetNotFoundException e) {
				/* It's not a view either! */
				throw new WidgetNotFoundException("Unable to find a part with title " + partTitle); //$NON-NLS-1$
			}
		}
		if (editorOrView instanceof final SWTBot4diacGefEditor gefEditor) {
			return gefEditor.getSWTBotGefViewer();
		}
		return null;
	}

}
