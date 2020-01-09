/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 *               2019 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Thomas Strasser
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - add access to path setting and configured runtime path
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime;

import java.util.List;

/**
 * The Interface IRuntimeLauncher.
 */
public interface IRuntimeLauncher {
	// launch the specific runtime
	/**
	 * Launch.
	 *
	 * @throws LaunchRuntimeException the launch runtime exception
	 */
	void launch() throws LaunchRuntimeException;

	// get the name of the runtime being launched
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	// get the amount of parameters required to launch the runtime
	/**
	 * Gets the num parameters.
	 *
	 * @return the num parameters
	 */
	int getNumParameters();

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	List<LaunchParameter> getParams();

	/**
	 * Sets the param.
	 *
	 * @param name  the name
	 * @param value the value
	 */
	LaunchParameter setParam(String name, String value);

	/**
	 * get setting for the configured runtime path
	 *
	 * @return path the configured path
	 */
	String getRuntimePath();

	/**
	 * get id of the Settings page where path is configured, as defined in
	 * corresponding plugin.xml
	 *
	 * @return id the page id
	 */
	String getPathPreferenceSettingPageID();

}
