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
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.library.ui.messages"; //$NON-NLS-1$

	public static String DirsWithArchives;
	public static String DirsWithUnzippedTypeLibs;

	public static String ExtractedLibraryImportWizard;

	public static String ImportExtractedFiles;

	public static String LibraryPage_Comment;
	public static String LibraryPage_DeselectAll;
	public static String LibraryPage_Name;
	public static String LibraryPage_SelectAll;
	public static String LibraryPage_SymbolicName;
	public static String LibraryPage_Version;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
