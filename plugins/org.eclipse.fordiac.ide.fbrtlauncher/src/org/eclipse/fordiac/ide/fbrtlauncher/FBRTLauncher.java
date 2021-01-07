/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - add access to path setting and configured runtime path
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbrtlauncher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.fbrtlauncher.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.runtime.IRuntimeLauncher;
import org.eclipse.fordiac.ide.runtime.LaunchParameter;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeException;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeUtils;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * The Class FBRTLauncher.
 */
public class FBRTLauncher implements IRuntimeLauncher {

	private static final String RMT_FRAME = "RMT_FRAME"; //$NON-NLS-1$
	private final List<LaunchParameter> params = new ArrayList<>();

	/**
	 * Instantiates a new fBRT launcher.
	 */
	public FBRTLauncher() {
		// define the initial parameters for the runtime
		setParam(Messages.FBRTLauncher_LABEL_PortParam, "61505"); //$NON-NLS-1$
		final LaunchParameter param = setParam(Messages.FBRTLauncher_LABEL_DeviceTypeParam, RMT_FRAME);
		param.setFixedValues(true);
		param.setValues(new String[] { RMT_FRAME, "RMT_DEV" }); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return "FBRT"; //$NON-NLS-1$
	}

	@Override
	public void launch() throws LaunchRuntimeException {
		checkPlatform();
		final String javaRte = getJavaRte();
		final String runtimePath = getRuntimePath();

		final String deviceType = params.get(1).getValue();
		String fbrtPath = "fb.rt."; //$NON-NLS-1$
		if (RMT_FRAME.equalsIgnoreCase(deviceType)) {
			fbrtPath += "hmi."; //$NON-NLS-1$
		}
		final String arguments = "-noverify -classpath ./lib" //$NON-NLS-1$
				+ File.pathSeparatorChar + "./" //$NON-NLS-1$
				+ new File(runtimePath).getName() + File.pathSeparatorChar + " " //$NON-NLS-1$
				+ fbrtPath + deviceType + " -n " //$NON-NLS-1$
				+ deviceType + " -s " //$NON-NLS-1$
				+ Integer.toString(getPortNumber()) + " -p " //$NON-NLS-1$
				+ Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_LIB);
		LaunchRuntimeUtils.startRuntime("FBRT " + deviceType + " on port " + Integer.toString(getPortNumber()), javaRte, //$NON-NLS-1$ //$NON-NLS-2$
				new File(runtimePath).getParentFile().getAbsolutePath(), arguments);

	}

	@Override
	public String getRuntimePath() {
		return Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH);
	}

	private static String getJavaRte() throws LaunchRuntimeException {
		String javaRte = System.getProperty("java.home"); //$NON-NLS-1$
		if (javaRte.isEmpty()) {
			throw new LaunchRuntimeException(Messages.FBRTLauncher_ERROR_MissingJavaVM);
		}
		javaRte = javaRte + File.separatorChar + "bin" + File.separatorChar + "java"; //$NON-NLS-1$ //$NON-NLS-2$
		if (isWin32Platform()) {
			javaRte += ".exe"; //$NON-NLS-1$
		}
		return javaRte;
	}

	private int getPortNumber() throws LaunchRuntimeException {
		final int port = Integer.parseInt(params.get(0).getValue());
		if ((port < 1024) || (port > 65535)) {
			throw new LaunchRuntimeException(Messages.FBRTLauncher_ERROR_WrongPort);
		}
		return port;
	}

	private static void checkPlatform() throws LaunchRuntimeException {
		final boolean isLinux = Platform.getOS().equalsIgnoreCase(Platform.OS_LINUX);
		final boolean isMacOS = Platform.getOS().equalsIgnoreCase(Platform.OS_MACOSX);
		if (!(isWin32Platform() || isLinux || isMacOS)) {
			throw new LaunchRuntimeException(Messages.FBRTLauncher_ERROR_MissingPlatform);
		}
	}

	private static boolean isWin32Platform() {
		return Platform.getOS().equalsIgnoreCase(Platform.OS_WIN32);
	}

	@Override
	public int getNumParameters() {
		return params.size();
	}

	@Override
	public List<LaunchParameter> getParams() {
		return params;
	}

	@Override
	public final LaunchParameter setParam(final String name, final String value) {
		for (final LaunchParameter param : params) {
			if (param.getName().equals(name)) {
				param.setValue(value);
				return param;
			}
		}
		final LaunchParameter param = new LaunchParameter();
		param.setName(name);
		param.setValue(value);
		params.add(param);
		return param;
	}

	@Override
	public String getPathPreferenceSettingPageID() {
		return "org.eclipse.fordiac.ide.fbrtlauncher.preferences.FBRTPreferencePage"; //$NON-NLS-1$
	}

	@Override
	public void addPathPreferenceChangeListener(final IPropertyChangeListener listener) {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(listener);
	}
}
