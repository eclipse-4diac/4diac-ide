/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	public static String ExtractCallableRefactoring_CallableNotFound;
	public static String ExtractCallableRefactoring_InvalidName;
	public static String ExtractCallableRefactoring_InvalidReturnType;
	public static String ExtractCallableRefactoring_InvalidReturnVariableAccess;
	public static String ExtractCallableRefactoring_InvalidSelection;
	public static String ExtractCallableRefactoring_Name;
	public static String ExtractCallableRefactoring_NameNotProvided;
	public static String ExtractCallableRefactoring_NameNotUnique;
	public static String ExtractCallableRefactoring_SourceNotFound;
	public static String ExtractCallableRefactoring_ControlFlowError;
	public static String ExtractCallableRefactoring_RegionNotFound;
	public static String ExtractCallableRefactoring_ReturnTypeNotPresent;
	public static String ExtractCallableRefactoring_ReturnVariableAccessMayChangeSemantics;
	public static String ExtractCallableRefactoring_TextNotFound;
	public static String ExtractCallableWizardPage_NameField;
	public static String MoveElementHandler_RefactoringDialogTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
