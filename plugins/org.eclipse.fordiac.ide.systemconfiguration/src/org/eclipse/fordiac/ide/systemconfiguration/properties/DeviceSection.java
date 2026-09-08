/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH, Johannes Kepler University, Aimirim STI
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
 *    Alois Zoitl 
 *      - fixed layout, reduced code duplication
 *    Pedro Ricardo
 *      - Added available profiles filter
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.gef.commands.ChangeProfileCommand;
import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.helpers.DeviceProfileHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

public class DeviceSection extends AbstractInterfaceSection {
	private static List<String> availableProfileNames;
	private CCombo profile;

	@Override
	protected void performRefresh() {
		super.performRefresh();
		final Device device = (Device) getType();
		if (device == null) {
			return;
		}
		profile.setItems(getSelectableProfiles(device));
		setProfile(device);
	}

	private void setProfile(final Device device) {
		final String currentProfile = device.getProfile() != null ? device.getProfile() : ""; //$NON-NLS-1$
		final int index = profile.indexOf(currentProfile);
		if (index >= 0) {
			profile.select(index);
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
	}

	/** Filter the globally available profiles with the list of allowed profiles 
     * for the given device via 'SupportedProfiles' attribute. Returns all profiles if
     * the attribute is not there. */
	private static String[] getSelectableProfiles(final Device device) {
		final List<String> available = getAvailableProfileNames();
		final List<String> supported = DeviceProfileHelper.getSupportedProfiles(device.getType());
		final List<String> selectable = new ArrayList<>();
		if (supported.isEmpty()) {
			selectable.addAll(available);
		} else {
			supported.stream().filter(available::contains).forEach(selectable::add);
		}
		final String currentProfile = device.getProfile();
		if (currentProfile != null && !currentProfile.isEmpty() && !selectable.contains(currentProfile)) {
			selectable.add(currentProfile);
		}
		return selectable.toArray(new String[0]);
	}

	private static List<String> getAvailableProfileNames() {
		if (availableProfileNames == null) {
			availableProfileNames = DeviceManagementInteractorFactory.INSTANCE.getAvailableProfileNames();
		}
		return availableProfileNames;
	}
}
