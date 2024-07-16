/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved adapter search code to palette
 *               - cleaned command stack handling for property sections
 *   Dunja Životin
 *     - extracted a part of the class into a separate widget
 ******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.gef.widgets.PinInfoBasicWidget;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.nat.AdapterTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.AdapterTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AdapterInterfaceElementSection extends AbstractDoubleColumnSection {

	protected PinInfoBasicWidget pinInfoBasicWidget;

	@Override
	protected IInterfaceElement getInputType(final Object input) {
		return InterfaceFilterSelection.getSelectableInterfaceElementOfType(input);
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		pinInfoBasicWidget = createPinInfoSection(getLeftComposite());
	}

	protected PinInfoBasicWidget createPinInfoSection(final Composite parent) {
		return new PinInfoBasicWidget(parent, getWidgetFactory());
	}

	@Override
	protected void setInputCode() {
		pinInfoBasicWidget.disableAllFields();
	}

	@Override
	protected void performRefresh() {
		if (pinInfoBasicWidget != null) {
			pinInfoBasicWidget.refresh();
		}
	}

	public boolean isEditable() {
		return !(EcoreUtil.getRootContainer(getType()) instanceof FunctionFBType);
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected void setInputInit() {
		setupPinInfoWidget(getType());

	}

	protected void setupPinInfoWidget(final IInterfaceElement ie) {
		if (pinInfoBasicWidget != null) {
			pinInfoBasicWidget.initialize(ie, this::executeCommand);
			pinInfoBasicWidget.getTypeSelectionWidget().initialize(ie, getTypeSelectionContentProvider(),
					getTypeSelectionTreeContentProvider());
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return AdapterTypeSelectionContentProvider.INSTANCE;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected ITreeContentProvider getTypeSelectionTreeContentProvider() {
		return AdapterTypeSelectionTreeContentProvider.INSTANCE;
	}
}
