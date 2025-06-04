/*******************************************************************************
 * Copyright (c) 2023, 2025 Paul Pavlicek and others
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
 *     - add messages for new contract check system
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.contracts.messages"; //$NON-NLS-1$

	public static String NoPinSelectedErrorDialog_Title;

	public static String NoPinSelectedErrorDialog_Info;

	public static String EvaluateSelectionErrorDialog_Title;

	public static String EvaluateSelectionErrorDialog_Info;

	public static String DefineContractDecisionDialog_Title;

	public static String DefineContractDecisionDialog_Info;

	public static String DefineContractDecisionDialog_Create;

	public static String ContractRuleSingleEvent;

	public static String ContractRuleRepetition;

	public static String ContractRuleReaction;

	public static String ContractRuleAge;

	public static String ContractRuleCausalReaction;

	public static String ContractRuleCausalAge;

	public static String ContractRuleEmpty;

	public static String ContractModelLoadError;

	public static String ContractUnkownRuleWarning;

	public static String ContractSingleEventMatchError;

	public static String ContractRepetitionOffsetMatchError;

	public static String ContractRepetitionIntervalMatchError;

	public static String ContractRuleTypeError;

	public static String ContractUnresolvedReactionInfo;

	public static String ContractMultipleFulfillError;

	public static String ContractMultipleResolveError;

	public static String ContractConflictingAssumptionsError;

	public static String ContractConflictingGuaranteesError;

	public static String ContractCheckSuccess_Title;

	public static String ContractCheckIssue_Title;

	public static String ContractCheckNetworkSize;

	public static String ContractCheckSelectionSize;

	public static String ContractCheckNoIssues;

	public static String ContractCheckOneIssue;

	public static String ContractCheckNIssues;

	public static String ContractCheck_OK;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
