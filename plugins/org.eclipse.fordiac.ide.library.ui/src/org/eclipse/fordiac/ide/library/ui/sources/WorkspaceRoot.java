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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.sources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibGroupNode;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public final class WorkspaceRoot {
	final List<LibGroupNode> extractedLibs = new ArrayList<>();
	final List<LibGroupNode> standardLibs = new ArrayList<>();

	final SectionNode standardSection = new SectionNode(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME, standardLibs);
	final SectionNode extractedSection = new SectionNode(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME, extractedLibs);

	public Object[] getChildren() {
		return new Object[] { standardSection, extractedSection };
	}
}