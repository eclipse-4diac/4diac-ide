/*******************************************************************************
 * Copyright (c) 2025, 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.ui.IEditorInput;

/**
 * An editor input for sub-elements of an editor input.
 */
public interface ISubEditorInput extends IEditorInput {

	/**
	 * Get the parent editor input
	 *
	 * @return the parent editor input
	 */
	IEditorInput getParent();

	/**
	 * Get the class of the sub-element
	 *
	 * @return the element class
	 */
	EClass getElementClass();

	/**
	 * Get the fragment for the sub-element
	 *
	 * @return the fragment
	 */
	String getFragment();
}
