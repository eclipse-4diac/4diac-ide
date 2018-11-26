/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.actions;

import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInput;
import org.eclipse.fordiac.ide.util.OpenListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * The Class OpenSystemConfEditorAction.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class OpenSystemConfEditorAction extends OpenListener {
	private static final String OPEN_SYSTEM_LISTENER_ID = "org.eclipse.fordiac.ide.systemconfiguration.actions.OpenSystemConfEditorAction"; //$NON-NLS-1$

	/** The sys conf. */
	private SystemConfiguration sysConf;

	@Override
	public void run(final IAction action) {		
		SystemConfigurationEditorInput input = new SystemConfigurationEditorInput(sysConf);
		openEditor(input, SystemConfigurationEditor.class.getName());
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof SystemConfiguration) {
				sysConf = (SystemConfiguration) structuredSel.getFirstElement();
			}
		}
	}

	@Override
	public String getActionText() {
		return Messages.OpenSystemConfEditorAction_Name;
	}

	@Override
	public Class<? extends I4DIACElement> getHandledClass() {
		return SystemConfiguration.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_SYSTEM_LISTENER_ID;
	}

}
