/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
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

	public static String DefineFBDecisionTwoPinDialog_CreateGuarantee;

	public static String DefineFBDecisionTwoPinDialog_CreateReaction;

	public static String DefineFBDecisionTwoPinDialog_Error;

	public static String DefineFBDecisionTwoPinDialog_ErrorInfo;

	public static String DefineFBDecisionTwoPinDialog_Info;

	public static String DefineFBReactionOnePinDialog_SpecifyOffset;

	public static String DefineFbInterfaceConstraintHandler_ErrorMessage;

	public static String DefineFbInterfaceConstraintHandler_Error;

	public static String DefineFbInterfaceConstraintHandler_ErrorText;

	public static String DefineFbInterfaceConstraintHandler_ErrorTitle;

	public static String DefineFbInterfaceConstraintHandler_Info;

	public static String DefineFbInterfaceConstraintHandler_InfoErrorGuarantee;

	public static String DefineFbInterfaceConstraintHandler_ThreePinErrorMessage;

	public static String DefineFbInterfaceConstraintHandler_Title;

	public static String DefineFBReactionOnePinDialog_Button;

	public static String DefineFBReactionOnePinDialog_DefineAssumption;

	public static String DefineFBReactionOnePinDialog_DefineConstraint;

	public static String DefineFBReactionOnePinDialog_Error;

	public static String DefineFBReactionOnePinDialog_Event;

	public static String DefineFBReactionOnePinDialog_Every;

	public static String DefineFBReactionOnePinDialog_Info;

	public static String DefineFBReactionOnePinDialog_Occurs;

	public static String DefineFBReactionOnePinDialog_Offset;

	public static String DefineFBReactionOnePinDialog_PleaseFill;

	public static String DefineFBReactionOnePinDialog_Reaction;

	public static String DefineFBReactionOnePinDialog_OffsetDialog;

	public static String DefineFBReactionOnePinDialog_Title;

	public static String DefineFBReactionOnePinDialog_Within;

	public static String DefineFBReactionThreePinDialog_DefineGuarantee;

	public static String DefineFBReactionTwoPinDialog_Button;

	public static String DefineFBReactionTwoPinDialog_Info;

	public static String DefineFBReactionTwoPinDialog_OccursWithin;

	public static String DefineFBReactionTwoPinDialog_Reaction;

	public static String DefineFBReactionTwoPinDialog_Title;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
