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
import org.eclipse.fordiac.ide.model.commands.change.ChangeAdapterTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Display;

public class TypeEditPart extends AbstractInterfaceElementEditPart {

	private final TypeLibrary typeLib;

	private DiagramFontChangeListener fontChangeListener;

	public TypeEditPart(final TypeLibrary typeLib) {
		super();
		this.typeLib = typeLib;
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

	private static class TypeFigure extends Label implements IFontUpdateListener {
		public TypeFigure() {
			super();
			setTypeLabelFonts();
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
		return getModel().getReferencedElement();
	}

	@Override
	public TypeField getModel() {
		return (TypeField) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		final TypeFigure fig = new TypeFigure();
		updateFigure(fig);
		return fig;
	}

	@Override
	public TypeFigure getFigure() {
		return (TypeFigure) super.getFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateFigure(getFigure());
	}

	private void updateFigure(final TypeFigure typeFigure) {
		typeFigure.setText(getTypeName());

		final Display display = Display.getCurrent();
		if (null != display) {
			if (getINamedElement() instanceof final VarDeclaration varDecl
					&& varDecl.getType() instanceof ErrorMarkerDataType) {
				typeFigure.setOpaque(true);
				typeFigure.setBackgroundColor(display.getSystemColor(SWT.COLOR_RED));
			} else {
				typeFigure.setOpaque(false);
			}
		}
	}

	private String getTypeName() {
		return getModel().getLabel();
	}

	@Override
	protected void createEditPolicies() {

		final ModifiedNonResizeableEditPolicy handle = new ModifiedNonResizeableEditPolicy();
		handle.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, handle);

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected void showCurrentEditValue(final DirectEditRequest request) {
				// nothing to do
			}

			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					final int index = ((Integer) request.getCellEditor().getValue()).intValue();
					final CCombo combo = (CCombo) request.getCellEditor().getControl();
					if (index >= 0 && index < combo.getItemCount()) {
						final String typeName = combo.getItem(index);
						ChangeDataTypeCommand cmd;
						if (getCastedModel() instanceof final AdapterDeclaration adp) {
							cmd = new ChangeAdapterTypeCommand(adp,
									typeLib.getAdapterTypeEntry(typeName).getType());
						} else {
							cmd = new ChangeDataTypeCommand(getCastedModel(),
									typeLib.getDataTypeLibrary().getType(typeName));
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
			// allow direct edit only for VarDeclarations and AdapterDeclarations
			if ((getCastedModel() instanceof VarDeclaration) || (getCastedModel() instanceof AdapterDeclaration)) {
				super.performRequest(request);
			}
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected ComboDirectEditManager createDirectEditManager() {
		return new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(getFigure()),
				getFigure());
	}

	@Override
	public void performDirectEdit() {
		// First update the list of available types
		final ArrayList<String> dataTypeNames = new ArrayList<>();
		if (getCastedModel() instanceof AdapterDeclaration) {
			typeLib.getAdapterTypesSorted().forEach(adapterType -> dataTypeNames.add(adapterType.getTypeName()));
		} else {
			for (final DataType dataType : typeLib.getDataTypeLibrary().getDataTypesSorted()) {
				dataTypeNames.add(dataType.getName());
			}
		}
		final ComboDirectEditManager editManager = createDirectEditManager();
		editManager.updateComboData(dataTypeNames);
		editManager.setSelectedItem(dataTypeNames.indexOf(getTypeName()));
		editManager.show();
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
