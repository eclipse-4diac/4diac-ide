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

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;

public class SWTBot4diacGefEditor extends SWTBotGefEditor {

	protected SWTBot4diacGefViewer viewer4diac;

	/**
	 * Create a new bot GEF editor instance.
	 *
	 * @param reference the editor reference
	 * @param bot       the workbench bot
	 * @throws WidgetNotFoundException if widget could not be found
	 */
	public SWTBot4diacGefEditor(final IEditorReference reference, final SWTWorkbenchBot bot)
			throws WidgetNotFoundException {
		super(reference, bot);
		final GraphicalViewer graphicalViewer = UIThreadRunnable.syncExec((Result<GraphicalViewer>) () -> {
			final IEditorPart editor = partReference.getEditor(true);
			return editor.getAdapter(GraphicalViewer.class);
		});
		viewer4diac = new SWTBot4diacGefViewer(graphicalViewer);
		viewer4diac.init();
	}

	@Override
	public SWTBot4diacGefViewer getSWTBotGefViewer() {
		return viewer4diac;
	}

	/**
	 * Checks if there is a Selection of FBEditParts. Throws an exception if no such
	 * part can be found after 1 second.
	 *
	 * @throws Exception When no Selection of a FBEditPart can be found in the list.
	 */
	public void waitForSelectedFBEditPart() {

		bot().waitUntil(new ICondition() {

			@Override
			public boolean test() throws Exception {
				final List<SWTBotGefEditPart> selectedEditParts = selectedEditParts();
				return selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart).count() > 0;
			}

			@Override
			public void init(final SWTBot bot) {
				// method must be implemented but empty since not needed
			}

			@Override
			public String getFailureMessage() {
				return "no selection found";
			}

		}, 1000);
	}

}
