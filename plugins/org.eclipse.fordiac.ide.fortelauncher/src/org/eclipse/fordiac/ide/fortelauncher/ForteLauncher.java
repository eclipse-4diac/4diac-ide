/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2014 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
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
			int port = Integer.parseInt(params.get(0).getValue());
			if ((port < 1024) || (port > 65535)) {
				throw new NumberFormatException();
			}
			String runtime = Activator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.P_PATH);
			LaunchRuntimeUtils
					.startRuntime("FORTE", runtime, new File(runtime) //$NON-NLS-1$
							.getParentFile().getAbsolutePath(), "-c " //$NON-NLS-1$
							+  "localhost:" //$NON-NLS-1$
							+ params.get(0).getValue());
		} catch (NumberFormatException num) {
			throw new LaunchRuntimeException(
					Messages.ForteLauncher_ERROR_WrongPort);
		} catch (Exception ex) {
			Activator.getDefault().logError("Could not launch FORTE", ex);
		}
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
	public LaunchParameter setParam(final String name, final String value) {
		boolean found = false;
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).getName().equals(name)) {
				params.get(i).setValue(value);
				found = true;
				if (found) {
					return params.get(i);
				}
			}
		}
		LaunchParameter param = new LaunchParameter();
		param.setName(name);
		param.setValue(value);
		params.add(param);
		return param;
	}
}
