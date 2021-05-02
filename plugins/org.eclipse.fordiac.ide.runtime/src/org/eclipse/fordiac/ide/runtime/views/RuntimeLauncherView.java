/*******************************************************************************
 * Copyright (c) 2008, 2020 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Thomas Strasser, Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - add currently configured runtime path plus link to settings
 *   Alois Zoitl - reworked into an E4 view part
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.views;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.fordiac.ide.runtime.Activator;
import org.eclipse.fordiac.ide.runtime.IRuntimeLauncher;
import org.eclipse.fordiac.ide.runtime.LaunchParameter;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeException;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class RuntimeLauncherView {

	private FormToolkit toolkit;

	private final List<IRuntimeLauncher> launchers;

	private final Map<String, Combo> comboTable = new HashMap<>();

	@Inject
	public RuntimeLauncherView() {
		launchers = loadRuntimeLaunchers();
	}

	@PostConstruct
	public void postConstruct(final Composite parent, final MPart part) {
		toolkit = new FormToolkit(parent.getDisplay());

		final Form form = toolkit.createForm(parent);
		form.getBody().setLayout(new GridLayout(1, true));
		form.setText("Launch Simulated Devices");

		launchers.forEach(launcher -> createRuntimeLauncherGroupUI(form.getBody(), launcher));

		initFields(part);
	}

	@Persist
	void save(final MPart part) {
		final Map<String, String> persistedState = part.getPersistedState();
		for (final IRuntimeLauncher launcher : launchers) {
			for (final LaunchParameter param : launcher.getParams()) {
				if (!param.isFixedValues()) {
					final String launchParamID = getLaunchParamID(launcher, param);
					persistedState.put(launchParamID,
							Arrays.deepToString(comboTable.get(getLaunchParamID(launcher, param)).getItems()));
				}
			}
		}
	}

	private void initFields(final MPart part) {
		final Map<String, String> persistedState = part.getPersistedState();

		for (final IRuntimeLauncher launcher : launchers) {
			for (final LaunchParameter param : launcher.getParams()) {
				if (!param.isFixedValues()) {
					configureDynamicParam(persistedState, launcher, param);
				}
				comboTable.get(getLaunchParamID(launcher, param)).select(0);
			}
		}
	}

	private void configureDynamicParam(final Map<String, String> persistedState, final IRuntimeLauncher launcher,
			final LaunchParameter param) {
		final String launchParamID = getLaunchParamID(launcher, param);
		String text = persistedState.get(launchParamID);
		if (null != text) {
			final Combo combo = comboTable.get(launchParamID);
			text = text.substring(1, text.length() - 1);
			final String[] data = text.split(","); //$NON-NLS-1$
			for (final String element : data) {
				final String temp = element.trim();
				if (!Arrays.asList(combo.getItems()).contains(temp)) {
					combo.add(temp);
				}
			}
		}
	}

	private void createRuntimeLauncherGroupUI(final Composite parent, final IRuntimeLauncher launcher) {
		final Group launcherGroup = new Group(parent, SWT.FILL);
		toolkit.adapt(launcherGroup);
		launcherGroup.setText(launcher.getName());
		launcherGroup.setLayout(new GridLayout(3, false));
		launcherGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
		final List<LaunchParameter> params = launcher.getParams();

		boolean launchButton = true;
		for (int j = 0; j < launcher.getNumParameters(); j++) {
			final Label paramName = toolkit.createLabel(launcherGroup, params.get(j).getName(), SWT.WRAP);

			int style = SWT.BORDER;
			if (params.get(j).isFixedValues()) {
				style |= SWT.READ_ONLY;
			}

			final Combo paramValue = new Combo(launcherGroup, style);
			toolkit.adapt(paramValue, true, true);
			if (params.get(j).isFixedValues()) {
				for (int k = 0; k < params.get(j).getValues().length; k++) {
					final String deviceString = params.get(j).getValues()[k];
					paramValue.add(deviceString);
				}
			} else {
				paramValue.add(params.get(j).getValue());
			}
			final GridData textAreaData = new GridData();
			textAreaData.horizontalAlignment = GridData.FILL;
			textAreaData.grabExcessHorizontalSpace = true;
			paramValue.setLayoutData(textAreaData);

			paramValue.addModifyListener(new RuntimeModifyListener(launcher, paramName.getText()) {
				@Override
				public void modifyText(final ModifyEvent e) {
					getLauncher().setParam(getName(), ((Combo) e.getSource()).getText());
				}
			});
			comboTable.put(launcher.getName() + "." //$NON-NLS-1$
					+ paramName.getText(), paramValue);

			if (launchButton) { // first line does launch,
				createLaunchButton(launcher, launcherGroup);
				launchButton = false;
			}
		}
		createConfiguredRuntimeUI(launcher, launcherGroup);
	}

	private void createConfiguredRuntimeUI(final IRuntimeLauncher launcher, final Composite parent) {
		final Composite configuredRuntimeComposite = toolkit.createComposite(parent);
		configuredRuntimeComposite.setLayout(new RowLayout());
		configuredRuntimeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		final Label configuredRuntimeTextLabel = toolkit.createLabel(configuredRuntimeComposite,
				"Currently configured runtime: ", SWT.NONE);
		final Link configuredRuntimePathLabel = new Link(configuredRuntimeComposite, SWT.NONE);
		toolkit.adapt(configuredRuntimePathLabel, false, false);
		setRuntimePathLink(launcher, configuredRuntimePathLabel);
		configuredRuntimePathLabel.addListener(SWT.Selection, ev -> {
			final PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
					// String defines active preference page, String[] defines all available
					// preference pages
					parent.getShell(), launcher.getPathPreferenceSettingPageID(),
					new String[] { launcher.getPathPreferenceSettingPageID(),
					"org.eclipse.fordiac.ide.preferences.FordiacPreferencePage" }, //$NON-NLS-1$
					null);
			dialog.open();

			// needed to update label text and size:
		});

		launcher.addPathPreferenceChangeListener(ev -> Display.getDefault().asyncExec(() -> {
			if (!configuredRuntimeComposite.isDisposed()) {
				setRuntimePathLink(launcher, configuredRuntimePathLabel);
				configuredRuntimeComposite.getParent().layout();
			}
		}));
	}

	private static void setRuntimePathLink(final IRuntimeLauncher launcher, final Link configuredRuntimePathLabel) {
		configuredRuntimePathLabel.setText("<a>" + getRuntimePath(launcher) + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String getRuntimePath(final IRuntimeLauncher launcher) {
		String path = launcher.getRuntimePath();
		if (path.length() == 0) {
			path = MessageFormat.format("No runtime configured for {0}. Click here for setting it up.",
					launcher.getName());
		}
		return path;
	}

	private void createLaunchButton(final IRuntimeLauncher launcher, final Group launcherGroup) {
		final Button launchButton = new Button(launcherGroup, SWT.WRAP);
		launchButton.setText("Launch " + launcher.getName());
		final GridData launchButtonData = new GridData();
		launchButtonData.verticalSpan = launcher.getNumParameters();
		launchButtonData.verticalAlignment = GridData.FILL;
		launchButtonData.horizontalAlignment = GridData.FILL;
		launchButton.setLayoutData(launchButtonData);
		launchButton.addListener(SWT.Selection, event -> {
			try {
				launcher.launch();

				for (final LaunchParameter param : launcher.getParams()) {
					if (!Arrays.asList(comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
							.getItems()).contains(comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
									.getText())) {
						comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
						.add(comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
								.getText(), 0);
					}
				}
			} catch (final LaunchRuntimeException e1) {
				Activator.getDefault().logError(e1.getMessage(), e1);
				// TODO added error message pop-up
			}
		});
	}

	private static String getLaunchParamID(final IRuntimeLauncher launcher, final LaunchParameter param) {
		return launcher.getName() + "." + param.getName(); //$NON-NLS-1$
	}

	private static List<IRuntimeLauncher> loadRuntimeLaunchers() {
		final IConfigurationElement[] elems = getRuntimeLaunchers();
		final List<IRuntimeLauncher> retVal = new ArrayList<>(elems.length);
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IRuntimeLauncher) {
					final IRuntimeLauncher launcher = (IRuntimeLauncher) object;
					retVal.add(launcher);

				}
			} catch (final CoreException corex) {
				Activator.getDefault().logError("Error loading run-time launcher", corex); //$NON-NLS-1$
			}
		}
		return retVal;
	}

	private static IConfigurationElement[] getRuntimeLaunchers() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(Activator.PLUGIN_ID,
				"RuntimeLauncher"); //$NON-NLS-1$
		Arrays.sort(elems, (element1, element2) -> {
			final String order1 = element1.getAttribute("order"); //$NON-NLS-1$
			final String order2 = element2.getAttribute("order"); //$NON-NLS-1$

			if (null == order1) {
				return (null == order2) ? 0 : 1;
			} else if (null == order2) {
				return -1;
			} else {
				return order1.compareTo(order2);
			}
		});
		return elems;
	}
}
