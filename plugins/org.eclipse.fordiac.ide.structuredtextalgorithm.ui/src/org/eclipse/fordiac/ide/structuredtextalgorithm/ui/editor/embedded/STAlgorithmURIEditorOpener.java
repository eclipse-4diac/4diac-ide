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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded;

import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

public class STAlgorithmURIEditorOpener extends LanguageSpecificURIEditorOpener {

	@Override
	protected String getEditorId() {
		return "org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor"; //$NON-NLS-1$
	}
}
