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
 ******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.TypeSelectionWidget;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AdapterInterfaceElementSection extends AbstractSection {
	protected TypeSelectionWidget typeSelectionWidget;
	private Text nameText;
	private Text commentText;

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
		createTypeAndCommentSection(getLeftComposite());
	}

	protected void createTypeAndCommentSection(final Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(parent, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(parent, true);
		nameText.addVerifyListener(new IdentifierVerifyListener());
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});
		getWidgetFactory().createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(parent, true);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
		getWidgetFactory().createCLabel(parent, FordiacMessages.Type + ":"); //$NON-NLS-1$
		typeSelectionWidget = new TypeSelectionWidget(getWidgetFactory());
		typeSelectionWidget.createControls(parent);
	}

	@Override
	protected void setInputCode() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			typeSelectionWidget.refresh();
		}
		commandStack = commandStackBuffer;
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		typeSelectionWidget.initialize(getType(), getTypeSelectionContentProvider(),
				this::handleDataSelectionChanged);
	}

	protected void handleDataSelectionChanged(final String dataName) {
		final AdapterTypePaletteEntry adapterEntry = getTypeLibrary().getBlockTypeLib().getAdapterTypeEntry(dataName);
		final DataType newType = adapterEntry == null ? null : adapterEntry.getType();
		if (newType != null) {
			commandStack.execute(new ChangeDataTypeCommand((VarDeclaration) getType(), newType));
		}
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected void setInputInit() {
		// nothing to be done here
	}

	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return new AdapterTypeSelectionContentProvider();
	}

	private class AdapterTypeSelectionContentProvider implements ITypeSelectionContentProvider {
		@Override
		public List<DataType> getTypes() {
			return getTypeLibrary().getBlockTypeLib().getAdapterTypesSorted().stream()
					.map(AdapterTypePaletteEntry::getType)
					.collect(Collectors.toList());
		}
	}
}
