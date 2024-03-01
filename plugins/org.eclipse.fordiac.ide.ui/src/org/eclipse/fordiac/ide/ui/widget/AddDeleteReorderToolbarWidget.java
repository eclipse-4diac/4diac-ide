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
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.menus.IMenuService;

public class AddDeleteReorderToolbarWidget extends AddDeleteReorderListWidget {

	public static final String LOCATION = "toolbar:org.eclipse.fordiac.ide.toolbar.table"; //$NON-NLS-1$

	private ToolBarManager toolBarManager;

	@Override
	public void createControls(final Composite parent, final FormToolkit widgetFactory) {
		super.createControls(parent, widgetFactory);

		toolBarManager = new ToolBarManager(SWT.FLAT | SWT.VERTICAL);
		final IMenuService menuService = PlatformUI.getWorkbench().getService(IMenuService.class);
		menuService.populateContributionManager(toolBarManager, LOCATION);
		final ToolBar control = toolBarManager.createControl(container);
		widgetFactory.adapt(control);
		GridDataFactory.fillDefaults().grab(true, false).indent(0, MAJOR_BUTTON_INTERLEAVE).applyTo(control);
	}

	public void dispose() {
		if (toolBarManager != null) {
			final IMenuService menuService = PlatformUI.getWorkbench().getService(IMenuService.class);
			menuService.releaseContributions(toolBarManager);
			toolBarManager.dispose();
			toolBarManager.removeAll();
			toolBarManager = null;
		}
	}
}
