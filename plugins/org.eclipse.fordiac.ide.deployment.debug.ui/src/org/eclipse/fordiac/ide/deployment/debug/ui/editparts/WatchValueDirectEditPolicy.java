/*******************************************************************************
 * Copyright (c) 2012, 2024 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                		    Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Lukas Wais - implemented hex conversion for AnyBit types
 *   Alois Zoitl - added value validation for direct edit of values
 *   Daniel Lindhuber - multi-line struct editing
 *   Martin Jobst - adopt new ST editor for values
 *                - adapt for new deployment monitoring framework
 *                - extracted from MonitoringEditPart
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.editparts;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.preferences.DeploymentDebugPreferences;
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.dialogs.ErrorDialog;

public class WatchValueDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		final String value = (String) request.getCellEditor().getValue();
		applyNewValue(value);
		return null;
	}

	private void applyNewValue(final String value) {
		final WatchValueEditPart editPart = (WatchValueEditPart) getHost();
		if (editPart.getModel().getWatch() instanceof final IVarDeclarationWatch varDeclarationWatch) {
			try {
				varDeclarationWatch.setValue(value);
			} catch (final DebugException e) {
				ErrorDialog.openError(null, null, null, Status.error(e.getLocalizedMessage(), e));
			}
		}
		if (DeploymentDebugPreferences.isMonitoringValueWriteThrough()
				&& editPart.getInterfaceElement() instanceof final VarDeclaration varDeclaration
				&& varDeclaration.isIsInput()
				&& !FBNetworkElementHelper.isContainedInTypedInstance(varDeclaration.getFBNetworkElement())
				&& EcoreUtil.getRootContainer(varDeclaration) instanceof final AutomationSystem system
				&& system.getCommandStack() != null) {
			system.getCommandStack().execute(new ChangeValueCommand(varDeclaration, value));
		}
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		final String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof final WatchValueEditPart editPart) {
			editPart.getFigure().setText(value);
		}
	}
}