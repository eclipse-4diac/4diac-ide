/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ComboBoxCellEditor;

public class TypeEditPart extends AbstractInterfaceElementEditPart implements EditPart {

	private Palette systemPalette;
	private Label comment;

	public TypeEditPart(Palette systemPalette) {
		super();
		this.systemPalette = systemPalette;
	}

	public class TypeFigure extends Label {
		public TypeFigure() {
			super();
			this.setFont(JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
		}

		@Override
		public void setText(String s) {
			if (getCastedModel() instanceof VarDeclaration) {
				// if is array append array size
				VarDeclaration varDec = (VarDeclaration) getCastedModel();
				if (varDec.isArray()) {
					s = s + "[" + varDec.getArraySize() + "]";
				}
			}
			super.setText(s);
		}
	}

	public IInterfaceElement getCastedModel() {
		return ((TypeField) getModel()).getReferencedElement();
	}

	@Override
	protected IFigure createFigure() {
		comment = new TypeFigure();
		update();
		// comment.setSize(-1, -1);
		// comment.set
		return comment;
	}

	protected void update() {
		comment.setText(getTypeName());
	}

	private String getTypeName() {
		return ((TypeField) getModel()).getLabel();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected void showCurrentEditValue(DirectEditRequest request) {
				// nothing to do
			}

			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					int index = (Integer) request.getCellEditor().getValue();
					if (index > 0 && index < ((ComboDirectEditManager) getManager()).getComboBox().getItemCount()) {
						String typeName = ((ComboDirectEditManager) getManager()).getComboBox().getItem(index);
						ChangeTypeCommand cmd;
						if (getCastedModel() instanceof AdapterDeclaration) {
							// TODO change to own command in order to update cfb
							// internals
							cmd = new ChangeTypeCommand((VarDeclaration) getCastedModel(),
									getAdapterTypeEntry(systemPalette, typeName).getAdapterType());
						} else {
							cmd = new ChangeTypeCommand((VarDeclaration) getCastedModel(),
									DataTypeLibrary.getInstance().getType(typeName));
						}
						return cmd;
					}
				}
				return null;
			}
		});
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// transform doubleclick to direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			// allow direct edit only for VarDeclarations
			if (getCastedModel() instanceof VarDeclaration) {
				super.performRequest(request);
			}
		} else {
			super.performRequest(request);
		}
	}

	public DirectEditManager getManager() {
		if (manager == null) {
			manager = new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(comment),
					comment);
		}
		return manager;
	}

	public void performDirectEdit() {
		// CCombo combo = getManager().getComboBox();
		// First update the list of available types
		ArrayList<String> dataTypeNames = new ArrayList<String>();
		if (getCastedModel() instanceof AdapterDeclaration) {
			for (AdapterTypePaletteEntry adapterType : getAdapterTypes(systemPalette)) {
				dataTypeNames.add(adapterType.getLabel());
			}
			Collections.sort(dataTypeNames);
		} else {
			for (DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()) {
				dataTypeNames.add(dataType.getName());
			}
		}
		((ComboDirectEditManager) getManager()).updateComboData(dataTypeNames);
		((ComboDirectEditManager) getManager()).setSelectedItem(dataTypeNames.indexOf(getTypeName()));
		getManager().show();
	}

	public static AdapterTypePaletteEntry getAdapterTypeEntry(final Palette systemPalette, final String typeName) {
		PaletteEntry entry = systemPalette.getTypeEntry(typeName);
		return (entry instanceof AdapterTypePaletteEntry) ? (AdapterTypePaletteEntry) entry : null;
	}

	public static ArrayList<AdapterTypePaletteEntry> getAdapterTypes(final Palette systemPalette) {
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();
		Palette pal = systemPalette;
		if (null == pal) {
			pal = TypeLibrary.getInstance().getPalette();
		}
		retVal.addAll(getAdapterGroup(pal.getRootGroup()));
		return retVal;
	}

	private static ArrayList<AdapterTypePaletteEntry> getAdapterGroup(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();
		for (Iterator<PaletteGroup> iterator = group.getSubGroups().iterator(); iterator.hasNext();) {
			PaletteGroup paletteGroup = iterator.next();
			retVal.addAll(getAdapterGroup(paletteGroup));
		}
		retVal.addAll(getAdapterGroupEntries(group));
		return retVal;
	}

	private static ArrayList<AdapterTypePaletteEntry> getAdapterGroupEntries(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();
		for (PaletteEntry entry : group.getEntries()) {
			if (entry instanceof AdapterTypePaletteEntry) {
				retVal.add((AdapterTypePaletteEntry) entry);
			}
		}
		return retVal;
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}
}
