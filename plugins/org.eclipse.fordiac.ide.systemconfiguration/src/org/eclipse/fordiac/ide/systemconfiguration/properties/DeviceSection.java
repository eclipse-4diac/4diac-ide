/*******************************************************************************
 * Copyright (c) 2017 -2019 fortiss GmbH, Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.gef.commands.ChangeProfileCommand;
import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public class DeviceSection extends AbstractInterfaceSection {
	private static String[] profileNames;
	private CCombo profile;

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		Device helper = getInputType(input);
		if (null != helper) {
			return helper.getAutomationSystem().getCommandStack();
		}
		return null;
	}

	@Override
	public void refresh() {
		super.refresh();
		if (null != type) {
			setProfile();
		}
	}

	private void setProfile() {
		int i = 0;
		for (String p : profile.getItems()) {
			if (p.equals(((Device) getType()).getProfile())) {
				profile.select(i);
				break;
			}
			i++;
		}
	}

	@Override
	protected Device getInputType(Object input) {
		if (input instanceof EditPart) {
			input = ((EditPart) input).getModel();
		}
		if (input instanceof Device) {
			return ((Device) input);
		}
		return null;
	}

	@Override
	protected void createFBInfoGroup(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Instance Name:");
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(ChangeNameCommand.forName(getType(), nameText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, "Instance Comment:");
		commentText = createGroupText(composite, true);
		GridData gridData = new GridData(SWT.FILL, 0, true, false);
		commentText.setLayoutData(gridData);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, "Profile:");
		profile = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), composite);
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
			List<String> newProfileNames = DeviceManagementInteractorFactory.INSTANCE.getAvailableProfileNames();
			profileNames = newProfileNames.toArray(new String[newProfileNames.size()]);
		}
		return profileNames;
	}
}
