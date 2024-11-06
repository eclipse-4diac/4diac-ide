/*******************************************************************************
 * Copyright (c) 2023, 2024 Paul Pavlicek and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *   Felix Schmid
 *     - removed unused messages
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.contracts.messages"; //$NON-NLS-1$

	public static String Contract_ErrorAssumption;

	public static String Contract_ErrorAssumptionsGuarantees;

	public static String Contract_ErrorElements;

	public static String Contract_ErrorGuarantee;

	public static String Contract_ErrorIncosistentAssumptions;

	public static String Contract_ErrorIncosistentGuarantees;

	public static String Contract_ErrorName;

	public static String DefineFbInterfaceConstraintHandler_Title;

	public static String DefineFbInterfaceConstraintHandler_Info;

	public static String DefineFBDecisionTwoPinDialog_Title;

	public static String DefineFBDecisionTwoPinDialog_Info;

	public static String DefineFBDecisionTwoPinDialog_CreateReaction;

	public static String DefineFBDecisionTwoPinDialog_CreateGuarantee;

	public static String ContractElementDialog_Title;

	public static String ContractElementDialog_Info;

	public static String ContractElementDialog_Ok;

	public static String ContractElementDialog_Define;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
