/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor.formatting;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.ui.editor.formatting.PreferenceStoreWhitespaceInformationProvider;

import com.google.inject.Inject;

public class STCoreWhitespaceInformationProvider extends PreferenceStoreWhitespaceInformationProvider {

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Override
	protected String getLineSeparatorPreference(final URI uri) {
		if (!fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(uri.fileExtension())) {
			return CommonElementExporter.LINE_END; // force fixed line ending for XML format
		}
		return super.getLineSeparatorPreference(uri);
	}
}
