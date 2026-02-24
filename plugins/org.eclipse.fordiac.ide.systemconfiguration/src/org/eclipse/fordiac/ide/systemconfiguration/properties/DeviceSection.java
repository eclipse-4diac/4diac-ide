/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Monika Wenger, Alois Zoitl
 *      - initial API and implementation and/or initial documentation
 *    Bianca Wiesmayr
 *      - merged two DeviceInterfaceSection plus Abstract Class into DeviceSection
 *   Alois Zoitl - fixed layout, reduced code duplication
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.gef.commands.ChangeProfileCommand;
import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

public class DeviceSection extends AbstractInterfaceSection {
	private static String[] profileNames;
	private CCombo profile;

	@Override
	protected void performRefresh() {
		super.performRefresh();
		setProfile();
	}

	private void setProfile() {
		int i = 0;
		for (final String p : profile.getItems()) {
			if (p.equals(((Device) getType()).getProfile())) {
				profile.select(i);
				break;
			}
			i++;
		}
	}

	@Override
	protected Device getInputType(Object input) {
		if (input instanceof final EditPart ep) {
			input = ep.getModel();
		}
		if (input instanceof final Device dev) {
			return dev;
		}
		return null;
	}

	@Override
	protected void createInfoGroup(final Composite container) {
		super.createInfoGroup(container);
		getWidgetFactory().createCLabel(container, Messages.DeviceSection_Profile + ":"); //$NON-NLS-1$
		profile = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), container);
		profile.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			executeCommand(new ChangeProfileCommand((Device) getType(), profile.getText()));
			refresh();
			addContentAdapter();
		});
		profile.setItems(getAvailableProfileNames());
	}

	protected static String[] getAvailableProfileNames() {
		if (null == profileNames) {
			final List<String> newProfileNames = DeviceManagementInteractorFactory.INSTANCE.getAvailableProfileNames();
			profileNames = newProfileNames.toArray(new String[newProfileNames.size()]);
		}
		return profileNames;
	}
}
