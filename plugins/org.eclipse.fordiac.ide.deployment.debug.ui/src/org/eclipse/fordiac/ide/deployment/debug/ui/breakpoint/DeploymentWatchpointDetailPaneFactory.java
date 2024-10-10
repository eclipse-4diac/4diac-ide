/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint;

import java.util.Collections;
import java.util.Set;

import org.eclipse.debug.ui.IDetailPane;
import org.eclipse.debug.ui.IDetailPaneFactory;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;

public class DeploymentWatchpointDetailPaneFactory implements IDetailPaneFactory {

	@Override
	public Set<String> getDetailPaneTypes(final IStructuredSelection selection) {
		if (selection.size() == 1 && selection.getFirstElement() instanceof DeploymentWatchpoint) {
			return Set.of(DeploymentWatchpointDetailPane.ID);
		}
		return Collections.emptySet();
	}

	@Override
	public String getDefaultDetailPane(final IStructuredSelection selection) {
		if (selection.size() == 1 && selection.getFirstElement() instanceof DeploymentWatchpoint) {
			return DeploymentWatchpointDetailPane.ID;
		}
		return null;
	}

	@Override
	public IDetailPane createDetailPane(final String paneID) {
		if (DeploymentWatchpointDetailPane.ID.equals(paneID)) {
			return new DeploymentWatchpointDetailPane();
		}
		return null;
	}

	@Override
	public String getDetailPaneName(final String paneID) {
		if (DeploymentWatchpointDetailPane.ID.equals(paneID)) {
			return Messages.DeploymentWatchpointDetailPane_Name;
		}
		return null;
	}

	@Override
	public String getDetailPaneDescription(final String paneID) {
		if (DeploymentWatchpointDetailPane.ID.equals(paneID)) {
			return Messages.DeploymentWatchpointDetailPane_Description;
		}
		return null;
	}
}
