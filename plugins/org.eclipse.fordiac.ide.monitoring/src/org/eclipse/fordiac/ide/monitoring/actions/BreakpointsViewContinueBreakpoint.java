/*******************************************************************************
 * Copyright (c) 2012, 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.actions;

import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;


public class BreakpointsViewContinueBreakpoint extends ContinueBreakpoint {

	public BreakpointsViewContinueBreakpoint() {
		super();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			// this.selection = (StructuredSelection) selection;
			if (((StructuredSelection) selection).getFirstElement() instanceof MonitoringElement) {
				MonitoringElement element = (MonitoringElement) ((StructuredSelection) selection)
						.getFirstElement();
				if (element != null
						&& element.getPort().getInterfaceElement() instanceof org.eclipse.fordiac.ide.model.libraryElement.Event) {
					action.setEnabled(element.isBreakpointActive());
					this.selection = new StructuredSelection(element.getPort().getInterfaceElement());
				} else {
					action.setEnabled(false);
				}
			}
		}
	}
}
