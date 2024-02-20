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
package org.eclipse.fordiac.ide.model.systemconfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class CommunicationConfigurationDetails {

	public static final String COMM_EXT_ATT_ID = "id"; //$NON-NLS-1$
	public static final String COMM_EXT_ATT_LABEL = "label"; //$NON-NLS-1$

	private static final String COMM_CONFIG_ID = "org.eclipse.fordiac.ide.systemconfiguration.communication"; //$NON-NLS-1$

	protected CommunicationConfigurationDetails() {
	}

	public abstract Composite createUi(final Composite parent, final CommunicationConfiguration config,
			final CommandExecutor executor, final TabbedPropertySheetWidgetFactory widgetFactory);

	public abstract CommunicationConfiguration createModel();

	public abstract CommunicationConfiguration createModel(List<VarDeclaration> parameters);

	public static CommunicationConfigurationDetails getCommConfigUiFromExtensionPoint(final String name,
			final String attribute) {
		final IConfigurationElement selected = getCommConfigExtensionPoint(attribute, name);
		if (selected != null) {
			try {
				final Object o = selected.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof CommunicationConfigurationDetails) {
					return (CommunicationConfigurationDetails) o;
				}
			} catch (final CoreException ex) {
				FordiacLogHelper.logError(ex.getMessage());
			}
		}
		return null;
	}

	private static IConfigurationElement[] getCommConfigExtensionPoint() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		return registry.getConfigurationElementsFor(COMM_CONFIG_ID);
	}

	private static IConfigurationElement getCommConfigExtensionPoint(final String attribute, final String value) {
		if (attribute != null && value != null) {
			for (final IConfigurationElement e : getCommConfigExtensionPoint()) {
				if (value.equalsIgnoreCase(e.getAttribute(attribute))) {
					return e;
				}
			}
		}
		return null;
	}

	public static String[] getCommConfigNamesFromExtensionPoint() {
		final IConfigurationElement[] config = getCommConfigExtensionPoint();
		final List<String> names = new ArrayList<>();
		names.add(""); //$NON-NLS-1$
		for (final IConfigurationElement e : config) {
			names.add(e.getAttribute(COMM_EXT_ATT_LABEL));
		}
		return names.toArray(new String[0]);
	}

	public static int getIndexOfCommunication(final String[] names, final String id) {
		final IConfigurationElement selected = CommunicationConfigurationDetails.getCommConfigExtensionPoint("id", id); //$NON-NLS-1$
		if ((selected == null) || (selected.getAttribute(COMM_EXT_ATT_LABEL) == null)) {
			return 0;
		}
		return Arrays.asList(names).indexOf(selected.getAttribute(COMM_EXT_ATT_LABEL));
	}
}
