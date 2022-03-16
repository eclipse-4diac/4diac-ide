/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
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

import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.PinInfoBasicWidget;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AdapterInterfaceElementSection extends AbstractSection {

	protected PinInfoBasicWidget pinInfoBasicWidget;

	@Override
	protected IInterfaceElement getInputType(final Object input) {
		if (input instanceof InterfaceEditPart) {
			return ((InterfaceEditPart) input).getCastedModel();
		}
		if (input instanceof TypeEditPart) {
			return ((TypeEditPart) input).getCastedModel();
		}
		if (input instanceof CommentEditPart) {
			return ((CommentEditPart) input).getCastedModel();
		}
		if (input instanceof Event) {
			return (Event) input;
		}
		if (input instanceof VarDeclaration) {
			return (VarDeclaration) input;
		}
		return null;
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
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type && pinInfoBasicWidget != null) {
			pinInfoBasicWidget.refresh();
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected void setInputInit() {
		if (pinInfoBasicWidget != null) {
			pinInfoBasicWidget.initialize(getType(), this::executeCommand);
			pinInfoBasicWidget.getTypeSelectionWidget().initialize(getType(), getTypeSelectionContentProvider());
		}

	}

	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return () -> getTypeLibrary().getBlockTypeLib().getAdapterTypesSorted().stream()
				.map(AdapterTypePaletteEntry::getType).collect(Collectors.toList());
	}

}