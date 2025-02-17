/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.globalconstantseditor.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.globalconstantseditor.ui.messages"; //$NON-NLS-1$

	public static String GlobalConstantsOutlineTreeProvider_VarGlobalDeclarationBlockConstantText;

	public static String GlobalConstantsOutlineTreeProvider_VarGlobalDeclarationBlockText;

	public static String GlobalConstQuickFix_AddConstantKeyword;
	public static String GlobalConstQuickFix_RenameGlobalConstants;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
