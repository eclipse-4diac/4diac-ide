/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.structuredtextalgorithm.ui.messages"; //$NON-NLS-1$
	public static String STAlgorithmCustomValidatorConfigurationBlock_ShadowingFunction;
	public static String STAlgorithmCustomValidatorConfigurationBlock_UnusedAlgorithm;
	public static String STAlgorithmLabelProvider_AlgorithmText;
	public static String STAlgorithmLabelProvider_MethodText;
	public static String STAlgorithmQuickfixProvider_Add_missing_algorithm;
	public static String STAlgorithmQuickfixProvider_CreateMissingInternalVariable;
	public static String STAlgorithmQuickfixProvider_Remove_all_unused_algorithms;
	public static String STAlgorithmQuickfixProvider_Remove_unused_algorithm;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
