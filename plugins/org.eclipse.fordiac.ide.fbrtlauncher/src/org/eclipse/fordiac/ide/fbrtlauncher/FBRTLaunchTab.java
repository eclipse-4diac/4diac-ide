/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - extracted and based on FBRTLauncher
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbrtlauncher;

import java.io.File;
import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.fbrtlauncher.preferences.FbrtPreferenceConstants;
import org.eclipse.fordiac.ide.runtime.RuntimeLaunchTab;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.FileChooserControl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class FBRTLaunchTab extends RuntimeLaunchTab {

	private static final String DEFAULT_FBRT_PORT = "61500"; //$NON-NLS-1$
	private static final String ATTR_FBRT_LOCATION = "org.eclipse.fordiac.ide.fbrtlauncher.location"; //$NON-NLS-1$
	private static final String ATTR_FBRT_DEV_TYPE = "org.eclipse.fordiac.ide.fbrtlauncher.deviceType"; //$NON-NLS-1$
	private static final String ATTR_FBRT_LIB = "org.eclipse.fordiac.ide.fbrtlauncher.lib"; //$NON-NLS-1$
	private static final String ATTR_FBRT_PORT = "org.eclipse.fordiac.ide.fbrtlauncher.port"; //$NON-NLS-1$

	private static final String[] FBRT_DEV_TYPES = { "RMT_FRAME", "RMT_DEV" }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String DEFAULT_FBRT_DEV_TYPE = FBRT_DEV_TYPES[0];

	private FileChooserControl fbrtChooser;
	private Text library;
	private Combo devType;

	@Override
	public void createControl(final Composite parent) {
		GridLayoutFactory.swtDefaults().applyTo(parent);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.BEGINNING).grab(true, false).applyTo(parent);
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.BEGINNING).grab(true, false).applyTo(comp);
		setControl(comp);

		fbrtChooser = new FileChooserControl(comp, SWT.NONE, Messages.FBRTPreferencePage_FBRTLocation, true);
		fbrtChooser.addChooserValueChangedListener(newVal -> scheduleUpdateJob());

		createLibrary(comp);
		createDevType(comp);
		createPortSelection(comp);

	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);

		configuration.setAttribute(ATTR_LOCATION, getJavaRte());
		final IPreferenceStore store = FbrtPreferenceConstants.STORE;

		final String fbrtPath = store.getString(FbrtPreferenceConstants.P_PATH);
		configuration.setAttribute(ATTR_FBRT_LOCATION, (fbrtPath != null && !fbrtPath.isBlank()) ? fbrtPath : ""); //$NON-NLS-1$

		configuration.setAttribute(ATTR_FBRT_LIB, store.getString(FbrtPreferenceConstants.P_LIB));
		configuration.setAttribute(ATTR_FBRT_DEV_TYPE, DEFAULT_FBRT_DEV_TYPE);
		configuration.setAttribute(ATTR_FBRT_PORT, DEFAULT_FBRT_PORT);

		updateLaunchArguments(configuration);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			fbrtChooser.setValue(configuration.getAttribute(ATTR_FBRT_LOCATION, "")); //$NON-NLS-1$
			library.setText(configuration.getAttribute(ATTR_FBRT_LIB, "")); //$NON-NLS-1$
			devType.select(
					getSelectedDevTypeIndex(configuration.getAttribute(ATTR_FBRT_DEV_TYPE, DEFAULT_FBRT_DEV_TYPE)));
			setPortValue(configuration.getAttribute(ATTR_FBRT_PORT, DEFAULT_FBRT_PORT));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(ATTR_FBRT_LOCATION, fbrtChooser.getValue());
		configuration.setAttribute(ATTR_FBRT_LIB, library.getText());
		configuration.setAttribute(ATTR_FBRT_DEV_TYPE, FBRT_DEV_TYPES[devType.getSelectionIndex()]);
		configuration.setAttribute(ATTR_FBRT_PORT, getPortValue());
		updateLaunchArguments(configuration);
	}

	@Override
	public String getName() {
		return Messages.FBRTLaunchTab_TabName;
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		if (!super.isValid(launchConfig)) {
			return false;
		}

		if (fbrtChooser.getValue().isBlank()) {
			setErrorMessage(Messages.FBRTLaunchTab_NoFBRTJarSet);
			return false;
		}
		return true;
	}

	private void createLibrary(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.FBRTPreferencePage_FBRTLibrary);
		library = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).applyTo(library);
		library.addModifyListener(ev -> scheduleUpdateJob());
	}

	private void createDevType(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.FBRTLaunchTab_DeviceType);

		devType = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
		Arrays.stream(FBRT_DEV_TYPES).forEach(devType::add);
		GridDataFactory.fillDefaults().align(GridData.BEGINNING, GridData.CENTER).applyTo(devType);
		devType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				scheduleUpdateJob();
			}
		});
	}

	private static String getJavaRte() {
		String javaRte = System.getProperty("java.home"); //$NON-NLS-1$
		if (javaRte.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		javaRte = javaRte + File.separatorChar + "bin" + File.separatorChar + "java"; //$NON-NLS-1$ //$NON-NLS-2$
		if (Platform.getOS().equalsIgnoreCase(Platform.OS_WIN32)) {
			javaRte += ".exe"; //$NON-NLS-1$
		}
		return javaRte;
	}

	private static void updateLaunchArguments(final ILaunchConfigurationWorkingCopy configuration) {
		try {
			// update java executable attribute, e.g., using it on a new machine.
			configuration.setAttribute(ATTR_LOCATION, getJavaRte());

			final File runtimeFile = getRuntimeFile(configuration);
			final String portNum = configuration.getAttribute(ATTR_FBRT_PORT, DEFAULT_FBRT_PORT);
			final String devType = configuration.getAttribute(ATTR_FBRT_DEV_TYPE, DEFAULT_FBRT_DEV_TYPE);

			String fbrtPath = "fb.rt."; //$NON-NLS-1$
			if (FBRT_DEV_TYPES[0].equalsIgnoreCase(devType)) {
				fbrtPath += "hmi."; //$NON-NLS-1$
			}

			final String arguments = "-noverify -classpath ./lib" //$NON-NLS-1$
					+ File.pathSeparatorChar + "./" //$NON-NLS-1$
					+ runtimeFile.getName() + File.pathSeparatorChar + " " //$NON-NLS-1$
					+ fbrtPath + devType + " -n " //$NON-NLS-1$
					+ devType + " -s " //$NON-NLS-1$
					+ portNum + " -p " //$NON-NLS-1$
					+ configuration.getAttribute(ATTR_FBRT_LIB, ""); //$NON-NLS-1$

			configuration.setAttribute(ATTR_TOOL_ARGUMENTS, arguments);
			final File parentFile = runtimeFile.getParentFile();
			if (parentFile != null) {
				configuration.setAttribute(ATTR_WORKING_DIRECTORY, parentFile.getPath());
			}

		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	private static int getSelectedDevTypeIndex(final String devType) {
		for (int i = 0; i < FBRT_DEV_TYPES.length; i++) {
			if (FBRT_DEV_TYPES[i].equals(devType)) {
				return i;
			}
		}
		return 0;
	}

	private static File getRuntimeFile(final ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		return new File(configuration.getAttribute(ATTR_FBRT_LOCATION, "")); //$NON-NLS-1$
	}

}
