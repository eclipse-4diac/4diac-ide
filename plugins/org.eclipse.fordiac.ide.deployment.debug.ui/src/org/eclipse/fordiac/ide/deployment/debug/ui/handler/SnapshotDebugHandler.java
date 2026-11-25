/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.util.List;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.FBSnapshotTarget;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Debugs the selected function block instance in the evaluator, starting from
 * the values it currently has in the runtime.
 */
public class SnapshotDebugHandler extends AbstractDeploymentHandler<FB> {

	@Override
	protected List<FB> getTargets(final Object selectedElement) {
		try {
			return List.of(FBSnapshotTarget.of(Adapters.adapt(selectedElement, FBNetworkElement.class)).instance());
		} catch (final CoreException e) {
			// reject unsupported function blocks before monitoring is started for them
			ErrorDialog.openError(Display.getDefault().getActiveShell(), Messages.SnapshotDebugHandler_Title, null,
					e.getStatus());
			return null;
		}
	}

	@Override
	protected void perform(final FB target, final Resource resource, final IDeviceManagementInteractor interactor,
			final Shell shell) throws DeploymentException {
		try {
			SnapshotDebugJob.forTarget(FBSnapshotTarget.of(target), interactor).schedule();
		} catch (final CoreException e) {
			throw new DeploymentException(e.getStatus().getMessage(), e);
		}
	}
}
