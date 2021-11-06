/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2014 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fortelauncher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fortelauncher.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.runtime.IRuntimeLauncher;
import org.eclipse.fordiac.ide.runtime.LaunchParameter;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeException;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeUtils;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * The Class ForteLauncher.
 */
public class ForteLauncher implements IRuntimeLauncher {

	private final List<LaunchParameter> params = new ArrayList<>();

	/**
	 * Instantiates a new forte launcher.
	 */
	public ForteLauncher() {
		// Define the initial parameters for the runtime
		setParam(Messages.ForteLauncher_LABEL_PortParam, "61499"); //$NON-NLS-1$

	}

	@Override
	public String getName() {
		return "Local FORTE"; //$NON-NLS-1$
	}

	@Override
	public void launch() throws LaunchRuntimeException {
		try {
			final int port = Integer.parseInt(params.get(0).getValue());
			if ((port < 1024) || (port > 65535)) {
				throw new NumberFormatException();
			}
			final String runtimePath = getRuntimePath();
			LaunchRuntimeUtils.startRuntime("FORTE on port " + params.get(0).getValue(), runtimePath, //$NON-NLS-1$
					new File(runtimePath)
					.getParentFile().getAbsolutePath(),
					"-c " //$NON-NLS-1$
					+ "localhost:" //$NON-NLS-1$
					+ params.get(0).getValue());
		} catch (final NumberFormatException num) {
			throw new LaunchRuntimeException(Messages.ForteLauncher_ERROR_WrongPort);
		} catch (final Exception ex) {
			FordiacLogHelper.logError(Messages.ForteLauncher_ERROR_CouldNotLaunchFORTE, ex);
		}
	}

	@Override
	public String getRuntimePath() {
		return Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH);
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
		return "org.eclipse.fordiac.ide.fortelauncher.preferences.FortePreferencePage"; //$NON-NLS-1$
	}

	@Override
	public void addPathPreferenceChangeListener(final IPropertyChangeListener listener) {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(listener);
	}
}
