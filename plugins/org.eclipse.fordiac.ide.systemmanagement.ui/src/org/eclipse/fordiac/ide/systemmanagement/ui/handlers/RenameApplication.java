/*******************************************************************************
 * Copyright (c) 2013 - 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class RenameApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof TreeSelection) {
			if (((TreeSelection) selection).getFirstElement() instanceof Application) {
				Application application = (Application) ((TreeSelection) selection)
						.getFirstElement();
				
				String newName = showRenameDialog(application);
				if(null != newName){
					performApplicationRename(application, newName);					
				}
			}
		}
		return null;
	}

	private static String showRenameDialog(final Application application) {
		InputDialog dialog = new InputDialog(Display.getDefault()
				.getActiveShell(), "Rename Application", "Enter new application name", application.getName(), 
				new IInputValidator(){
					@Override
					public String isValid(String newText) {
						if(application.getName().equals(newText)){
							return "Application name not different!";
						}
						if (!NameRepository.isValidName(application, newText)) {
							return Messages.NewApplicationPage_ErrorMessageInvalidAppName;
						}
						return null;
					}			
		}){
			@Override
			protected Control createDialogArea(Composite parent){
				Control retval = super.createDialogArea(parent);
				getText().addVerifyListener(new IdentifierVerifyListener());
				return retval;
			}
		};		
		int ret = dialog.open();
		if (ret == Window.OK) {
			return dialog.getValue();
		}		
		return null;
	}

	private static void performApplicationRename(Application application, String newName) {		
		ChangeNameCommand cmd = new ChangeNameCommand(application, newName);
		CommandStack cmdStack = SystemManager.INSTANCE.getCommandStack(application.getAutomationSystem());
		cmdStack.execute(cmd);
	}
}