/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2019 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr
 *     - add currently configured runtime path plus link to settings
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.views;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.part.ViewPart;

/**
 * The Class RuntimeLauncherView.
 */
public class RuntimeLauncherView extends ViewPart {

	private static final String VALUES = "values"; //$NON-NLS-1$

	private IMemento memento;

	private final List<IRuntimeLauncher> launchers = new ArrayList<>();

	private final Map<String, Combo> comboTable = new HashMap<>();

	/**
	 * Instantiates a new runtime launcher view.
	 */
	public RuntimeLauncherView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout parentLayout = new GridLayout(1, false);
		parent.setLayout(parentLayout);

		IConfigurationElement[] elems = getRuntimeLaunchers();
		for (IConfigurationElement element : elems) {
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IRuntimeLauncher) {
					IRuntimeLauncher launcher = (IRuntimeLauncher) object;
					launchers.add(launcher);
					createRuntimeLauncherGroupUI(parent, launcher);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading Export Filter", corex); //$NON-NLS-1$
			}
		}
		initFields();
	}

	private void createRuntimeLauncherGroupUI(Composite parent, IRuntimeLauncher launcher) {
		Group launcherGroup = new Group(parent, SWT.FILL);
		launcherGroup.setText(launcher.getName());
		launcherGroup.setLayout(new GridLayout(3, false));
		launcherGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
		List<LaunchParameter> params = launcher.getParams();

		boolean launchButton = true;
		for (int j = 0; j < launcher.getNumParameters(); j++) {
			Label paramName = new Label(launcherGroup, SWT.WRAP);
			paramName.setText(params.get(j).getName());

			int style = SWT.BORDER;
			if (params.get(j).isFixedValues()) {
				style |= SWT.READ_ONLY;
			}

			Combo paramValue = new Combo(launcherGroup, style);
			if (params.get(j).isFixedValues()) {
				for (int k = 0; k < params.get(j).getValues().length; k++) {
					String deviceString = params.get(j).getValues()[k];
					paramValue.add(deviceString);
				}
			} else {
				paramValue.add(params.get(j).getValue());
			}
			GridData textAreaData = new GridData();
			textAreaData.horizontalAlignment = GridData.FILL;
			textAreaData.grabExcessHorizontalSpace = true;
			paramValue.setLayoutData(textAreaData);

			paramValue.addModifyListener(new RuntimeModifyListener(launcher, paramName.getText()) {
				@Override
				public void modifyText(ModifyEvent e) {
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

	private void createConfiguredRuntimeUI(IRuntimeLauncher launcher, Group launcherGroup) {
		Composite configuredRuntimeComposite = new Composite(launcherGroup, 0);
		configuredRuntimeComposite.setLayout(new RowLayout());
		configuredRuntimeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		Label configuredRuntimeTextLabel = new Label(configuredRuntimeComposite, 0);
		configuredRuntimeTextLabel.setText("Currently configured runtime: ");
		Link configuredRuntimePathLabel = new Link(configuredRuntimeComposite, SWT.NONE);
		setRuntimePathLink(launcher, configuredRuntimePathLabel);
		configuredRuntimePathLabel.addListener(SWT.Selection, ev -> {
			PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
					// String defines active preference page, String[] defines all available
					// preference pages
					this.getSite().getWorkbenchWindow().getShell(), launcher.getPathPreferenceSettingPageID(),
					new String[] { launcher.getPathPreferenceSettingPageID(),
							"org.eclipse.fordiac.ide.preferences.FordiacPreferencePage" }, //$NON-NLS-1$
					null);
			dialog.open();

			// needed to update label text and size:
		});

		launcher.addPathPreferenceChangeListener(ev -> {
			Display.getDefault().asyncExec(() -> {
				if (!configuredRuntimeComposite.isDisposed()) {
					setRuntimePathLink(launcher, configuredRuntimePathLabel);
					configuredRuntimeComposite.getParent().layout();
				}
			});
		});
	}

	private static void setRuntimePathLink(IRuntimeLauncher launcher, Link configuredRuntimePathLabel) {
		configuredRuntimePathLabel.setText("<a>" + getRuntimePath(launcher) + "</a>");
	}

	private static String getRuntimePath(IRuntimeLauncher launcher) {
		String path = launcher.getRuntimePath();
		if (path.length() == 0) {
			path = MessageFormat.format("No runtime configured for {0}. Click here for setting it up.",
					launcher.getName());
		}
		return path;
	}

	private static IConfigurationElement[] getRuntimeLaunchers() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(Activator.PLUGIN_ID, "RuntimeLauncher"); //$NON-NLS-1$
		Arrays.sort(elems, (element1, element2) -> {
			String order1 = element1.getAttribute("order"); //$NON-NLS-1$
			String order2 = element2.getAttribute("order"); //$NON-NLS-1$

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

	private void createLaunchButton(IRuntimeLauncher launcher, Group launcherGroup) {
		Button launchButton = new Button(launcherGroup, SWT.WRAP);
		launchButton.setText("Launch " + launcher.getName());
		GridData launchButtonData = new GridData();
		launchButtonData.verticalSpan = launcher.getNumParameters();
		launchButtonData.verticalAlignment = GridData.FILL;
		launchButtonData.horizontalAlignment = GridData.FILL;
		launchButton.setLayoutData(launchButtonData);
		launchButton.addListener(SWT.Selection, event -> {
			try {
				launcher.launch();
				getViewSite().getActionBars().getStatusLineManager().setErrorMessage(null);

				for (LaunchParameter param : launcher.getParams()) {
					if (!Arrays.asList(comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
							.getItems()).contains(comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
									.getText())) {
						comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
								.add(comboTable.get(getLaunchParamID(launcher, param)) // $NON-NLS-1$
										.getText(), 0);
					}
				}
			} catch (LaunchRuntimeException e1) {
				Activator.getDefault().logError(e1.getMessage(), e1);
				getViewSite().getActionBars().getStatusLineManager().setErrorMessage(e1.getMessage());
			}
		});
	}

	@Override
	public void setFocus() {
		// set focus to my widget. For a label, this doesn't
		// make much sense, but for more complex sets of widgets
		// you would decide which one gets the focus.
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		this.memento = memento;
	}

	private void initFields() {
		if (null == memento) {
			return;
		}
		IMemento[] values = memento.getChildren(VALUES);
		for (IRuntimeLauncher launcher : launchers) {
			for (LaunchParameter param : launcher.getParams()) {
				if (!param.isFixedValues()) {
					configureDynamicParam(values, launcher, param);
				}
				comboTable.get(getLaunchParamID(launcher, param)).select(0);
			}
		}
	}

	private void configureDynamicParam(IMemento[] values, IRuntimeLauncher launcher, LaunchParameter param) {
		for (IMemento mem : values) {
			String text = mem.getTextData();
			if ((null != text) && mem.getID().equals(getLaunchParamID(launcher, param))) {
				text = text.substring(1, text.length() - 1);
				String[] data = text.split(","); //$NON-NLS-1$
				for (String element : data) {
					String temp = element.trim();
					if (!Arrays.asList(comboTable.get(getLaunchParamID(launcher, param)).getItems()).contains(temp)) {
						comboTable.get(getLaunchParamID(launcher, param)).add(temp);
					}
				}
			}
		}
	}

	private static String getLaunchParamID(IRuntimeLauncher launcher, LaunchParameter param) {
		return launcher.getName() + "." + param.getName(); //$NON-NLS-1$
	}

	@Override
	public void saveState(IMemento memento) {
		for (IRuntimeLauncher launcher : launchers) {
			for (LaunchParameter param : launcher.getParams()) {
				if (!param.isFixedValues()) {
					IMemento mem = memento.createChild(VALUES, getLaunchParamID(launcher, param));
					mem.putTextData(Arrays.deepToString(comboTable.get(getLaunchParamID(launcher, param)).getItems()));
				}
			}
		}
	}
}
