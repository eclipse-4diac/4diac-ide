/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.views.helpers.EmptyWorkspaceHelper;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

@SuppressWarnings("restriction") // The SystemExplorer uses EmptyWorkspaceHelper which is currently eclipse
									// internal api
public class SystemExplorer extends CommonNavigator implements ITabbedPropertySheetPageContributor {

	private EmptyWorkspaceHelper emptyWorkspaceHelper;

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.application.editors.DiagramEditor"; //$NON-NLS-1$
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new TabbedPropertySheetPage(this));
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void createPartControl(final Composite aParent) {
		emptyWorkspaceHelper = new EmptyWorkspaceHelper();
		final Composite displayAreas = emptyWorkspaceHelper.getComposite(aParent);
		removeCreateProjectLink(displayAreas);
		super.createPartControl(displayAreas);
	}

	@Override
	protected CommonViewer createCommonViewer(final Composite aParent) {
		final CommonViewer viewer = super.createCommonViewer(aParent);
		emptyWorkspaceHelper.setNonEmptyControl(viewer.getControl());
		return viewer;
	}

	private static void removeCreateProjectLink(final Composite displayAreas) {
		final Composite emptyArea = (Composite) ((Composite) displayAreas.getChildren()[0]).getChildren()[0];
		final Composite optionsArea = (Composite) emptyArea.getChildren()[1];
		if (optionsArea.getChildren().length >= 4) {
			final var createProjectlabel = optionsArea.getChildren()[optionsArea.getChildren().length - 4];
			final var createProjektLink = optionsArea.getChildren()[optionsArea.getChildren().length - 3];
			createProjectlabel.dispose();
			createProjektLink.dispose();
			optionsArea.pack();
		}
	}

}
