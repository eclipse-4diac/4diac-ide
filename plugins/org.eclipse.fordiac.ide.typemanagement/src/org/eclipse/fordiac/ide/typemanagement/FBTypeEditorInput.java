/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * An EditorInput for opening FBType files with specified content. The equals
 * method is adapted that EditorInput is equal to another EditorInput if the
 * content is equal.
 */
public class FBTypeEditorInput implements IEditorInput {
	private FBType fbType;
	private final PaletteEntry entry;

	public FBTypeEditorInput(final FBType fbType, final PaletteEntry entry) {
		this.fbType = fbType;
		this.entry = entry;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		return fbType.getName() == null ? "" : fbType.getName(); //$NON-NLS-1$
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return fbType.getComment() == null ? "" //$NON-NLS-1$
				: fbType.getComment() + " (" + entry.getFile().getProjectRelativePath().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(final Class adapter) {
		return null;
	}

	public FBType getContent() {
		return fbType;
	}

	public PaletteEntry getPaletteEntry() {
		return entry;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof FBTypeEditorInput) {
			final FBTypeEditorInput input = (FBTypeEditorInput) obj;
			return fbType.equals(input.getContent()) && entry.getFile().equals(input.getPaletteEntry().getFile());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return fbType.hashCode();
	}

	public void setFbType(final FBType fbType) {
		this.fbType = fbType;
	}
}
