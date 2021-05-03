/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Jaeger, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.properties;

import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructViewingComposite;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.TypeInfoWidget;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class DataTypeInfoSection extends AbstractSection {

	private TypeInfoWidget typeInfoWidget;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		typeInfoWidget = new TypeInfoWidget(getWidgetFactory());
		typeInfoWidget.createControls(composite);
	}

	@Override
	protected StructuredType getType() {
		return (StructuredType) type;
	}

	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof StructViewingComposite) {
			return ((StructViewingComposite) input).getStruct();
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}

	@Override
	public void refresh() {
		if (null != getType()) {
			typeInfoWidget.refresh();
		}
	}

	@Override
	protected void setInputInit() {
		typeInfoWidget.initialize(getType(), this::executeCommand);
	}
}
