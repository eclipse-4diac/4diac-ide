/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved adapter search code to palette
 *   Alois Zoitl - added diagram font preference
 *               - added selection feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ComboBoxCellEditor;

public class TypeEditPart extends AbstractInterfaceElementEditPart {

	private Palette systemPalette;
	private Label comment;

	private DiagramFontChangeListener fontChangeListener;

	public TypeEditPart(Palette systemPalette) {
		super();
		this.systemPalette = systemPalette;
	}

	@Override
	public void activate() {
		super.activate();
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	public class TypeFigure extends Label implements IFontUpdateListener {
		public TypeFigure() {
			super();
			setTypeLabelFonts();
		}

		@Override
		public void setText(String s) {
			if (getCastedModel() instanceof VarDeclaration) {
				// if is array append array size
				VarDeclaration varDec = (VarDeclaration) getCastedModel();
				if (varDec.isArray()) {
					s = s + "[" + varDec.getArraySize() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			super.setText(s);
		}

		@Override
		public void updateFonts() {
			setTypeLabelFonts();
			invalidateTree();
			revalidate();
		}

		private void setTypeLabelFonts() {
			setFont(JFaceResources.getFontRegistry().getItalic(PreferenceConstants.DIAGRAM_FONT));
		}
	}

	@Override
	public IInterfaceElement getCastedModel() {
		return ((TypeField) getModel()).getReferencedElement();
	}

	@Override
	protected IFigure createFigure() {
		comment = new TypeFigure();
		update();
		return comment;
	}

	@Override
	public TypeFigure getFigure() {
		return (TypeFigure) super.getFigure();
	}

	@Override
	protected void update() {
		comment.setText(getTypeName());
	}

	private String getTypeName() {
		return ((TypeField) getModel()).getLabel();
	}

	@Override
	protected void createEditPolicies() {

		ModifiedNonResizeableEditPolicy handle = new ModifiedNonResizeableEditPolicy();
		handle.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, handle);

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
							// TODO change to own command in order to update cfb internals
							cmd = new ChangeTypeCommand((VarDeclaration) getCastedModel(),
									systemPalette.getAdapterTypeEntry(typeName).getType());
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

	@Override
	protected DirectEditManager createDirectEditManager() {
		return new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(comment), comment);
	}

	@Override
	public void performDirectEdit() {
		// First update the list of available types
		ArrayList<String> dataTypeNames = new ArrayList<>();
		if (getCastedModel() instanceof AdapterDeclaration) {
			systemPalette.getAdapterTypesSorted().forEach(adapterType -> dataTypeNames.add(adapterType.getLabel()));
		} else {
			for (DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()) {
				dataTypeNames.add(dataType.getName());
			}
		}
		((ComboDirectEditManager) getManager()).updateComboData(dataTypeNames);
		((ComboDirectEditManager) getManager()).setSelectedItem(dataTypeNames.indexOf(getTypeName()));
		getManager().show();
	}

	@Override
	public Label getNameLabel() {
		return getFigure();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}
}
