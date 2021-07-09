/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.ui.editors.AbstractCloseAbleFormEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;

public class EditorCloserAdapter extends AdapterImpl {

	private final IEditorPart part;

	public EditorCloserAdapter(final IEditorPart part) {
		this.part = part;
	}

	protected void closeEditor() {
		final IEditorSite siteToUse = part.getEditorSite();
		if (siteToUse instanceof MultiPageEditorSite) {
			final MultiPageEditorPart editor = ((MultiPageEditorSite) siteToUse).getMultiPageEditor();
			if (editor instanceof AbstractCloseAbleFormEditor) {
				((AbstractCloseAbleFormEditor) editor).closeChildEditor(part);
			}
		}
	}

}
