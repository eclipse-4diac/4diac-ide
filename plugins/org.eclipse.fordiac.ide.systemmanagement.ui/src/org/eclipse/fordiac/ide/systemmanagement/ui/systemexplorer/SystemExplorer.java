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

import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SystemExplorer extends CommonNavigator implements ITabbedPropertySheetPageContributor {

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.application.editors.DiagramEditor"; //$NON-NLS-1$
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new TabbedPropertySheetPage(this));
		}
		return super.getAdapter(adapter);
	}
}
