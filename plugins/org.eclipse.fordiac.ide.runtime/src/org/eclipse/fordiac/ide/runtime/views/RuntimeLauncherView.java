/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Thomas Strasser, Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {

		GridLayout parentLayout = new GridLayout(3, false);
		parent.setLayout(parentLayout);

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "RuntimeLauncher"); //$NON-NLS-1$
		Arrays.sort(elems, new Comparator<IConfigurationElement>() {
			@Override
			public int compare(IConfigurationElement element1, IConfigurationElement element2) {
				String order1 = element1.getAttribute("order"); //$NON-NLS-1$
				String order2 = element2.getAttribute("order"); //$NON-NLS-1$
				
				if (null == order1) {
					return (null == order2) ?  0 : 1;
				} else if (null == order2) {
					return -1;
				} else {
					return order1.compareTo(order2);
				}
			}
		});
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IRuntimeLauncher) {
					IRuntimeLauncher launcher = (IRuntimeLauncher) object;
					launchers.add(launcher);

					Group launcherGroup = new Group(parent, SWT.NONE);
					launcherGroup.setText(launcher.getName());
					launcherGroup.setLayout(new GridLayout(3, false));

					GridData launcherGroupData = new GridData();
					launcherGroupData.horizontalAlignment = GridData.FILL;
					launcherGroupData.grabExcessHorizontalSpace = true;
					launcherGroupData.horizontalSpan = 3;
					launcherGroup.setLayoutData(launcherGroupData);

					// Get the parameters for the launcher
					List<LaunchParameter> params = launcher.getParams();

					boolean firstButton = true;
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
						paramValue.addModifyListener(new RuntimeModifyListener(
								launcher, paramName.getText()) {

							@Override
							public void modifyText(ModifyEvent e) {
								launcher.setParam(name, ((Combo) e.getSource())
										.getText());
							}

						});
						comboTable.put(launcher.getName() + "." //$NON-NLS-1$
								+ paramName.getText(), paramValue);
						if (firstButton) {
							// create the launch button
							Button launchButton = new Button(launcherGroup,
									SWT.WRAP);
							launchButton.setText("Launch " + launcher.getName());
							GridData launchButtonData = new GridData();
							launchButtonData.verticalSpan = launcher.getNumParameters();
							launchButtonData.verticalAlignment = GridData.FILL;
							launchButtonData.horizontalAlignment = GridData.FILL;
							launchButton.setLayoutData(launchButtonData);
							launchButton.addListener( SWT.Selection, event -> {
											try {
												launcher.launch();
												getViewSite().getActionBars().getStatusLineManager().setErrorMessage(null);
												
												for (LaunchParameter param : launcher.getParams()) {
													if (!Arrays.asList(comboTable.get(launcher.getName() + "." + param.getName()).getItems()) //$NON-NLS-1$
															.contains(comboTable.get(launcher.getName() + "." + param.getName()).getText())) { //$NON-NLS-1$
														comboTable.get(launcher.getName() + "." + param.getName()) //$NON-NLS-1$
																.add(comboTable.get(launcher.getName() + "." + param.getName()).getText(), 0); //$NON-NLS-1$
													}
												}
											} catch (LaunchRuntimeException e1) {
												Activator.getDefault().logError(e1.getMessage(), e1);
												getViewSite().getActionBars().getStatusLineManager().setErrorMessage(e1.getMessage());
											}
										});
							firstButton = false;
						}
					}

				}
				
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading Export Filter", corex); //$NON-NLS-1$
			}
		}
		initFields();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// set focus to my widget. For a label, this doesn't
		// make much sense, but for more complex sets of widgets
		// you would decide which one gets the focus.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite,
	 * org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		this.memento = memento;
	}

	private void initFields() {
		if (memento != null) {
			IMemento[] values = memento.getChildren(VALUES);
			for (IRuntimeLauncher launcher : launchers) {
				for (LaunchParameter param : launcher.getParams()) {
					if (!param.isFixedValues()) {
						configureDynamicParam(values, launcher, param);
					}
					comboTable.get(launcher.getName() + "." + param.getName()).select(0); //$NON-NLS-1$
				}
			}
		}
	}

	private void configureDynamicParam(IMemento[] values,
			IRuntimeLauncher launcher, LaunchParameter param) {
		for (int i = 0; i < values.length; i++) {
			IMemento mem = values[i];
			if (mem.getID().equals(launcher.getName() + "." + param.getName())) { //$NON-NLS-1$
				String text = mem.getTextData();
				if (text != null) {
					text = text.substring(1, text.length() - 1);
					String[] data = text.split(","); //$NON-NLS-1$
					for (int j = 0; j < data.length; j++) {
						String temp = data[j].trim();
						if (!Arrays.asList(
								comboTable.get(
										launcher.getName() + "." //$NON-NLS-1$
												+ param.getName()).getItems())
								.contains(temp)) {
							comboTable.get(
									launcher.getName() + "." + param.getName()) //$NON-NLS-1$
									.add(temp);
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void saveState(IMemento memento) {
		for (IRuntimeLauncher launcher : launchers) {
			for (LaunchParameter param : launcher.getParams()) {
				if (!param.isFixedValues()) {
					IMemento mem = memento.createChild(VALUES, launcher
							.getName()
							+ "." + param.getName()); //$NON-NLS-1$
					mem.putTextData(Arrays.deepToString(comboTable.get(
							launcher.getName() + "." + param.getName()) //$NON-NLS-1$
							.getItems()));
				}
			}
		}
	}
}
