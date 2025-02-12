/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.messages"; //$NON-NLS-1$
	public static String STFunctionLabelProvider_FunctionText;
	public static String STFunctionQuickfixProvider_RenameFunction;
	public static String STFunctionRenameFunctionParameterRefactoringParticpant_Name;
	public static String STFunctionRenameInterfaceParameterRefactoringParticipant_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
