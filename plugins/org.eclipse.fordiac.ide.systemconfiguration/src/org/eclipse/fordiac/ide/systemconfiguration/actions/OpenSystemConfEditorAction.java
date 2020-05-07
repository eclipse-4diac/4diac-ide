/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2017 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInput;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.util.OpenListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

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
	public ImageDescriptor getImageDescriptor() {
		return FordiacImage.ICON_SYSTEM_CONFIGURATION.getImageDescriptor();
	}

	@Override
	public Class<? extends EObject> getHandledClass() {
		return SystemConfiguration.class;
	}

	@Override
	public String getOpenListenerID() {
		return OPEN_SYSTEM_LISTENER_ID;
	}

}
