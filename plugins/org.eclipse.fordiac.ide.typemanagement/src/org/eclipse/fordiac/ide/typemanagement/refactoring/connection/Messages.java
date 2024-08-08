/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	public static String ConnectionsToStructRefactoring_ChangeName;
	public static String ConnectionsToStructRefactoring_ChangeName;
	public static String ConnectionsToStructRefactoring_CheckPreconditions;
	public static String ConnectionsToStructRefactoring_CheckPreconditions;
	public static String ConnectionsToStructRefactoring_DestinationEventConflict;
	public static String ConnectionsToStructRefactoring_DestinationIsServiceFB;
	public static String ConnectionsToStructRefactoring_DestinationIsServiceFB;
	public static String ConnectionsToStructRefactoring_DestinationNoFB;
	public static String ConnectionsToStructRefactoring_DestinationNoFB;
	public static String ConnectionsToStructRefactoring_DestinationOutputsNoMatch;
	public static String ConnectionsToStructRefactoring_DestinationOutputsNoMatch;
	public static String ConnectionsToStructRefactoring_IncompatibleStructType;
	public static String ConnectionsToStructRefactoring_IncompatibleStructType;
	public static String ConnectionsToStructRefactoring_InNameExists;
	public static String ConnectionsToStructRefactoring_InNameExists;
	public static String ConnectionsToStructRefactoring_InvalidInName;
	public static String ConnectionsToStructRefactoring_InvalidInName;
	public static String ConnectionsToStructRefactoring_InvalidOutName;
	public static String ConnectionsToStructRefactoring_InvalidOutName;
	public static String ConnectionsToStructRefactoring_InvalidType;
	public static String ConnectionsToStructRefactoring_InvalidType;
	public static String ConnectionsToStructRefactoring_OutNameExists;
	public static String ConnectionsToStructRefactoring_OutNameExists;
	public static String ConnectionsToStructRefactoring_ProgressText;
	public static String ConnectionsToStructRefactoring_ProgressText;
	public static String ConnectionsToStructRefactoring_RefactoringTitle;
	public static String ConnectionsToStructRefactoring_RefactoringTitle;
	public static String ConnectionsToStructRefactoring_SameFBSameName;
	public static String ConnectionsToStructRefactoring_SameFBSameName;
	public static String ConnectionsToStructRefactoring_SelectionIsNoStruct;
	public static String ConnectionsToStructRefactoring_SelectionIsNoStruct;
	public static String ConnectionsToStructRefactoring_SourceEventConflict;
	public static String ConnectionsToStructRefactoring_SourceIsServiceFB;
	public static String ConnectionsToStructRefactoring_SourceIsServiceFB;
	public static String ConnectionsToStructRefactoring_SourceNoFB;
	public static String ConnectionsToStructRefactoring_SourceNoFB;
	public static String ConnectionsToStructRefactoring_SourceOutputsNoMatch;
	public static String ConnectionsToStructRefactoring_SourceOutputsNoMatch;
	public static String ConnectionsToStructRefactoring_StructExists;
	public static String ConnectionsToStructRefactoring_StructExists;
	public static String CreateStructChange_Name;
	public static String RepairBrokenConnectionChange_Name;
	public static String ReplaceVarsWithStructChange_Inputs;
	public static String ReplaceVarsWithStructChange_Outputs;
	public static String ReplaceVarsWithStructChange_Replace;
	public static String ReplaceVarsWithStructChange_Struct;
	public static String SystemConnectStructChange_Name;
	public static String SystemUpdateFBChange_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
