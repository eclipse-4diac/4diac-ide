/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.datatypeeditor.properties;

import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructEditingComposite;
import org.eclipse.fordiac.ide.gef.properties.AttributeSection;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StructAttributeSection extends AttributeSection {

	private Label label;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		label.setBackground(GUIHelper.COLOR_WHITE);
		super.createControls(parent, tabbedPropertySheetPage);
	}

	@Override
	protected void setType(final Object input) {
		super.setType(input);
		refresh(); // refresh to update input of viewer
		if (getType() instanceof final INamedElement namedElement) {
			label.setText(namedElement.getName() + ":"); //$NON-NLS-1$
		}
	}

	@Override
	protected ConfigurableObject getInputType(Object input) {
		if (input instanceof final StructEditingComposite structViewingComposite) {
			input = structViewingComposite.setConfigurablObjectListener(this::setType);
		}
		if (input instanceof final StructuredType structuredType) {
			if (structuredType.eContainer() instanceof final AttributeDeclaration attributeDeclaration) {
				return attributeDeclaration;
			}
			return structuredType;
		}
		if (input instanceof final DirectlyDerivedType directlyDerivedType) {
			if (directlyDerivedType.eContainer() instanceof final AttributeDeclaration attributeDeclaration) {
				return attributeDeclaration;
			}
			return directlyDerivedType;
		}
		if (input instanceof final ConfigurableObject configurableObject) {
			return configurableObject;
		}
		return null;
	}
}
