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
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class TypeDeclarationEditorConfiguration extends AbstractRegistryConfiguration {
	public static final String TYPE_DECLARATION_CELL = "TYPE_DECLARATION_CELL"; //$NON-NLS-1$

	final IRowDataProvider<VarDeclaration> dataProvider;

	public TypeDeclarationEditorConfiguration(final IRowDataProvider<VarDeclaration> dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
				new TypeDeclarationCellEditor(dataProvider), DisplayMode.EDIT, TYPE_DECLARATION_CELL);
	}
}
