/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * An EditorInput for opening IEC 61499 Editors with specified content. The
 * equals method is adapted that EditorInput is equal to another EditorInput if
 * the content is equal.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public abstract class UntypedEditorInput implements IContentEditorInput {

	private final EObject content;

	private String name;

	/**
	 * Constructor of UntypedEditorInput.
	 *
	 * @param content the content
	 * @param name    the name
	 * @param toolTip the tool tip
	 */
	protected UntypedEditorInput(final EObject content, final String name) {
		this.content = content;
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	@Override
	public boolean exists() {
		return true; // our models do always exist
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	@Override
	public String getName() {
		return name == null ? "" : name; //$NON-NLS-1$
	}

	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * In 4diac IDE we would like to have the tooltip the same as the name Tooltip
	 * is the thing shown in the title. Name mostly in the part tab.
	 */
	@Override
	public String getToolTipText() {
		return getName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return null;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	@Override
	public EObject getContent() {
		return content;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 *
	 * @return <code>true</code> if <code>content</code> is equal.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof final UntypedEditorInput input && content != null) {
			return content.equals(input.getContent());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return content.hashCode();
	}

	@Override
	public IPersistableElement getPersistable() {
		// per default our sub editors are not persistable, this is handled differently
		return null;
	}

}
