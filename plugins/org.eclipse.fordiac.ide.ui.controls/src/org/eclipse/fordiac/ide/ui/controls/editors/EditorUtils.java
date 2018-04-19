/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.controls.editors;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;

public class EditorUtils {
	
	public static final EditorAction CloseEditor = (IEditorPart part) -> { 
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(part, false);
	};
	
	private EditorUtils(){
		throw new AssertionError();
	}
	
	public static IEditorPart findEditor(final EditorFilter filter){
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		
		for (IEditorReference editorReference : editorReferences) {
			IEditorPart editor = editorReference.getEditor(false);
			if(null != editor){
				if(filter.filter(editor)){
					return editor;					
				}
			}
		}
		return null;
	}
	
		
	public static void forEachOpenEditorFiltered(final EditorFilter filter, final EditorAction action){
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		
		for (IEditorReference editorReference : editorReferences) {
			IEditorPart editor = editorReference.getEditor(false);
			if(null != editor){
				if(filter.filter(editor)){
					action.run(editor);					
				}
			}
		}
	}
	
	public static void closeEditorsFiltered(final EditorFilter filter){
		forEachOpenEditorFiltered(filter, CloseEditor);
	}
}
