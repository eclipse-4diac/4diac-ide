/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.systemconfiguration.api;

import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class CommunicationConfigurationDetails {

	protected CommunicationConfigurationDetails() {
	}

	public abstract Composite createUi(final Composite parent, final CommunicationConfiguration config,
			final CommandExecutor executor, final TabbedPropertySheetWidgetFactory widgetFactory);

	public abstract CommunicationConfiguration createModel();

}
