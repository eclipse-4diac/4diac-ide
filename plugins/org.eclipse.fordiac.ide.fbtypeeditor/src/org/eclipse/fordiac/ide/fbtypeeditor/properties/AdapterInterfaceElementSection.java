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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.CommentEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.TypeEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AdapterInterfaceElementSection extends AbstractSection {
	private Text nameText;
	private Text commentText;
	protected CCombo typeCombo;

	@Override
	protected IInterfaceElement getInputType(Object input) {
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

	protected void createTypeAndCommentSection(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(parent, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(parent, true);
		nameText.addVerifyListener(new IdentifierVerifyListener());
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(parent, true);
		commentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
		getWidgetFactory().createCLabel(parent, FordiacMessages.Type + ":"); //$NON-NLS-1$
		Composite typeComp = getWidgetFactory().createComposite(parent);
		typeComp.setLayout(new GridLayout(2, false));
		typeComp.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		typeCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), typeComp);
		GridData languageComboGridData = new GridData(SWT.FILL, 0, true, false);
		typeCombo.setLayoutData(languageComboGridData);
		typeCombo.addListener(SWT.Selection, event -> {
			DataType newType = getTypeForSelection(typeCombo.getText());
			if (null != newType) {
				executeCommand(new ChangeTypeCommand((VarDeclaration) type, newType));
				refresh();
			}
		});
	}

	@Override
	protected void setInputCode() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
		typeCombo.removeAll();
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			setTypeDropdown();
		}
		commandStack = commandStackBuffer;
	}

	Collection<DataType> typeList;

	protected void setTypeDropdown() {
		typeCombo.removeAll();
		typeList = getTypes();
		if (null != typeList) {
			List<String> typeNames = new ArrayList<>();
			for (DataType type : typeList) {
				typeNames.add(type.getName());
			}
			Collections.sort(typeNames);
			String currTypeName = (null != ((VarDeclaration) type).getType())
					? ((VarDeclaration) type).getType().getName()
					: ""; // this handles gracefully the case when the adpater type could //$NON-NLS-1$
							// not be loaded
			for (int i = 0; i < typeNames.size(); i++) {
				typeCombo.add(typeNames.get(i));
				if (typeNames.get(i).equals(currTypeName)) {
					typeCombo.select(i);
				}
			}
		}
	}

	protected Collection<DataType> getTypes() {
		List<DataType> types = new ArrayList<>();
		FBType fbType = (FBType) getType().eContainer().eContainer();
		PaletteEntry entry = fbType.getPaletteEntry();

		entry.getPalette().getAdapterTypesSorted().forEach(adaptertype -> types.add(adaptertype.getType()));
		return types;
	}

	protected DataType getTypeForSelection(String text) {
		if (null != typeList) {
			for (DataType dataType : typeList) {
				if (dataType.getName().equals(text)) {
					return dataType;
				}
			}
		}
		return null;
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected void setInputInit() {
	}
}
