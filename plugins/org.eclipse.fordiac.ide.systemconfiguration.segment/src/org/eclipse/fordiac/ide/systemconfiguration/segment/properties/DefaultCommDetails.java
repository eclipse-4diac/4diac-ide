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
package org.eclipse.fordiac.ide.systemconfiguration.segment.properties;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.systemconfiguration.CommunicationConfigurationDetails;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Messages;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationFactory;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.DefaultConfiguration;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class DefaultCommDetails extends CommunicationConfigurationDetails {
	@Override
	public Composite createUi(final Composite parent, final CommunicationConfiguration config,
			final CommandExecutor executor, final TabbedPropertySheetWidgetFactory widgetFactory) {
		final Composite c = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(c);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(c);
		widgetFactory.createLabel(c, Messages.DefaultCommDetails_Default);
		return c;
	}

	@Override
	public CommunicationConfiguration createModel() {
		final DefaultConfiguration config = CommunicationFactory.eINSTANCE.createDefaultConfiguration();
		final CommunicationMappingTarget target = LibraryElementFactory.eINSTANCE.createCommunicationMappingTarget();
		config.setTarget(target);
		target.setName("CommunicationChannel"); //$NON-NLS-1$
		return config;
	}

	@Override
	public CommunicationConfiguration createModel(final List<VarDeclaration> parameters) {
		return createModel();
	}
}