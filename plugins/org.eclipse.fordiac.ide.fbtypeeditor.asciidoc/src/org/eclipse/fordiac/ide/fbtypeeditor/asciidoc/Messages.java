/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "fbtasciidoc"; //$NON-NLS-1$

	public static String AsciiDoc_Description;
	public static String Toolbar_UploadSelectImage;
	public static String Toolbar_FileAlreadyExists;
	public static String Toolbar_ErrorMessage;
	public static String Toolbar_ButtonOverwrite;
	public static String Toolbar_UseExistingImage;
	public static String Toolbar_ButtonCancel;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}

}
