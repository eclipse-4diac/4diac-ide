/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GbmH, fortiss GmbH, Johannes Kepler 
 * 							 University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.actions;

import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


//this class is still here to support BreakpointsViewConitueBreakpoint. Shall be removed when the breakpoint view is reworked. 
public abstract class ContinueBreakpoint extends AbstractMonitoringAction {

	public ContinueBreakpoint() {
		// empty constructor
	}

	protected Shell shell;
	protected StructuredSelection selection;

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		super.setActivePart(action, targetPart);
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@Override
	public void run(IAction action) {			
		if (null != selection) {
			if (selection.getFirstElement() instanceof InterfaceEditPart) {
				InterfaceEditPart editPart = (InterfaceEditPart) selection.getFirstElement();
				
				PortElement port = MonitoringManagerUtils.createPortElement(editPart);
				if(null != port){
					//MonitoringManager.getInstance().toggleBreakpoint(port, BreakPoint.clear);
				}
			}
		}
	}

}
