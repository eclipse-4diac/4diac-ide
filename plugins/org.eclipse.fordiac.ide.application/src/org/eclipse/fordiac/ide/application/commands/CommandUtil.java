/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.fordiac.ide.ui.controls.editors.EditorFilter;
import org.eclipse.fordiac.ide.ui.controls.editors.EditorUtils;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * The Class CommandUtil.
 */
public class CommandUtil {

	/**
	 * Close opened subApp.
	 * 
	 * @param diagram the diagram
	 * 
	 * @return the i editor input
	 */
	public static IEditorInput closeOpenedSubApp(final FBNetwork network) {
		EditorFilter filter = ((IEditorPart editor) -> {
									return (editor instanceof DiagramEditorWithFlyoutPalette) && 
											(network.equals(((DiagramEditorWithFlyoutPalette)editor).getModel()));
								});
		
		IEditorPart editor = EditorUtils.findEditor(filter);

		if(null != editor){
			IEditorInput input = editor.getEditorInput();			
			EditorUtils.CloseEditor.run(editor);
			return input;
		}		
		return null;
	}

	/**
	 * Open subApp editor.
	 * 
	 * @param input the input
	 */
	public static void openSubAppEditor(final IEditorInput input) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart part = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		try {
			activePage.openEditor(input, SubAppNetworkEditor.class.getName());
		} catch (PartInitException e) {
			// TODO log error
			ApplicationPlugin.getDefault().logError(
					Messages.CommandUtil_ERROR_ReopenSubApp, e);
		}
		activePage.activate(part);
	}

}
