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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

public final class DocumentationAssetHelper {

	public static String getDocumentationAssetFolderName(final String typeFileName) {
		return "." + typeFileName + TypeLibraryTags.DOCUMENTATION_ASSET_FOLDER_SUFFIX; //$NON-NLS-1$
	}

	private DocumentationAssetHelper() {
		throw new UnsupportedOperationException();
	}
}
