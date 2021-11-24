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
@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
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

	public static String CognitiveComplexity;
	public static String CyclomaticNumber;

	public static String HalsteadDifficulty;
	public static String HalsteadDisctinctOperatorsN1;
	public static String HalsteadDisctinctOperatorsN2;
	public static String HalsteadEstimatedLength;
	public static String HalsteadNumberOfActions;
	public static String HalsteadProgramEffort;
	public static String HalsteadProgramLength;
	public static String HalsteadProgramVocabulary;
	public static String HalsteadProgramVolume;
	public static String HalsteadPurityRatio;
	public static String HalsteadTotalNumberOfOperatorsN1;
	public static String HalsteadTotalNumberOfOperatorsN2;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
