/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GbmH, fortiss GmbH
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
import org.eclipse.fordiac.ide.model.ui.actions.AbstractOpenSystemElementListener;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class OpenSystemConfEditorAction extends AbstractOpenSystemElementListener {
	private static final String OPEN_SYSTEM_LISTENER_ID = "org.eclipse.fordiac.ide.systemconfiguration.actions.OpenSystemConfEditorAction"; //$NON-NLS-1$

	/** The sys conf. */
	private SystemConfiguration sysConf;

	@Override
	public void run(final IAction action) {
		openInSystemEditor(sysConf.getAutomationSystem().getTypeEntry().getFile(), sysConf);
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		if (selection instanceof final IStructuredSelection structuredSel
				&& structuredSel.getFirstElement() instanceof final SystemConfiguration firstEl) {
			sysConf = firstEl;
		}
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
