/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lisa Sonnleithner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.metrics.messages"; //$NON-NLS-1$

	public static String SpiderChartBFBMeasuresOf;
	public static String SpiderChartBFBError;
	public static String Measure;
	public static String Metric;
	public static String Value;
	public static String CalculatedMetricsFor;
	public static String NumberOfStates;
	public static String NumberOfTransitions;
	public static String AlgorithmLOC;
	public static String NumberOfActions;
	public static String NumberOfInternalVars;
	public static String NumberOfIndependentPaths;
	public static String NumberOfInterfaceElements;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
