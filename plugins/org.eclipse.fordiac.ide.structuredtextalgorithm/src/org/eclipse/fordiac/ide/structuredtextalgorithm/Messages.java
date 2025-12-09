/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.structuredtextalgorithm;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.structuredtextalgorithm.messages"; //$NON-NLS-1$

	public static String STAlgorithmValidator_DuplicateMethodOrAlgorithmName;
	public static String STAlgorithmValidator_MissingAlgorithm;
	public static String STAlgorithmValidator_UnqualifiedMethodOrAlgorithmShadowingFunction;
	public static String STAlgorithmValidator_UnusedAlgorithm;
	public static String STAlgorithmValidator_NameUsedAsEventInput;
	public static String STAlgorithmValidator_NameUsedAsDataInput;
	public static String STAlgorithmValidator_NameUsedAsEventOutput;
	public static String STAlgorithmValidator_NameUsedAsDataOutput;
	public static String STAlgorithmValidator_NameUsedAsVariable;
	public static String STAlgorithmValidator_NameUsedAsConstant;
	public static String STAlgorithmValidator_NameUsedAsFunctionBlockVariable;
	public static String STAlgorithmValidator_NameUsedAsAlgorithm;
	public static String STAlgorithmValidator_NameUsedAsMethod;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
