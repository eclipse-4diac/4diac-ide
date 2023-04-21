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
 *   Sebastian Hollersbacher
 *     - initial API and implementation and/or initial documentation
 ******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeDropdown;

/**
 * An interface to content providers for TypeSelectionWidget and DataTypeDropdown.
 *
 * @see org.eclipse.fordiac.ide.gef.widgets.TypeSelectionWidget
 * @see DataTypeDropdown
 */
public interface ITypeSelectionContentProvider {
	/**
	 * Returns the elements to display in the DataTypeDropdown
	 * and to select in TypeSelectionWidget
	 * The result is not modified by the viewer.
	 *
	 * @return a list of DataTypes to display in the viewer
	 */
	List<DataType> getTypes();
}
