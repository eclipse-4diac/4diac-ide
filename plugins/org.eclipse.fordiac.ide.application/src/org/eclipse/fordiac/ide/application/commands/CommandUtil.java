/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils  
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.ui.editors.EditorFilter;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * The Class CommandUtil.
 */
public final class CommandUtil {

	/**
	 * Close opened subApp.
	 * 
	 * @param diagram the diagram
	 * 
	 * @return the i editor input
	 */
	public static IEditorInput closeOpenedSubApp(final FBNetwork network) {
		EditorFilter filter = ((IEditorPart editor) -> (editor instanceof DiagramEditorWithFlyoutPalette)
				&& (network.equals(((DiagramEditorWithFlyoutPalette) editor).getModel())));

		IEditorPart editor = EditorUtils.findEditor(filter);

		if (null != editor) {
			IEditorInput input = editor.getEditorInput();
			EditorUtils.CloseEditor.run(editor);
			return input;
		}
		return null;
	}

	private CommandUtil() {
		throw new UnsupportedOperationException("CommandUtil utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
