/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;

/**
 * An EditorInput for opening IEC 61499 Editors with specified content. The
 * equals method is adapted that EditorInput is equal to another EditorInput if
 * the content is equal.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public abstract class UntypedEditorInput implements IEditorInput {

	private final Object content;

	private final String name;

	private final String tooltip;

	/**
	 * Constructor of UntypedEditorInput.
	 * 
	 * @param content
	 *          the content
	 * @param name
	 *          the name
	 * @param toolTip
	 *          the tool tip
	 */
	public UntypedEditorInput(final Object content, final String name,
			final String toolTip) {
		this.content = content;
		this.name = name;
		this.tooltip = toolTip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	@Override
	public boolean exists() {
		return true; //our models do always exist
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return tooltip == null ? "" : tooltip; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(final Class adapter) {
		return null;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * Equals.
	 * 
	 * @param obj
	 *          the obj
	 * 
	 * @return <code>true</code> if <code>content</code> is equal.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof UntypedEditorInput && content != null) {
			UntypedEditorInput input = (UntypedEditorInput) obj;
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

	
}
