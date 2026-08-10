/*******************************************************************************
 * Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zijun Tang - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.ethercat.messages"; //$NON-NLS-1$

	public static String EsiFileImporter_PageName;
	public static String EsiFileImporter_WindowTitle;
	public static String EsiFileImporter_NoProjectSelected;

	public static String EsiFileImporterWizardPage_Description;
	public static String EsiFileImporterWizardPage_Title;
	public static String EsiFileImporterWizardPage_SelectEsiFile;
	public static String EsiFileImporterWizardPage_Browse;

	public static String EsiFileParser_ReadFileErrorPrefix;
	public static String EsiFileParser_ParseFileError;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
