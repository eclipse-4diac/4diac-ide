/*******************************************************************************
 * Copyright (c) 2013 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IPersistableElement;

/**
 * An EditorInput for opening IEC 61499 Editors with specified content. The
 * equals method is adapted that EditorInput is equal to another EditorInput if
 * the content is equal.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public abstract class PersistableUntypedEditorInput extends UntypedEditorInput implements IPersistableElement {

	protected PersistableUntypedEditorInput(final EObject content, final String name) {
		super(content, name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	@Override
	public IPersistableElement getPersistable() {
		return this;
	}

}
