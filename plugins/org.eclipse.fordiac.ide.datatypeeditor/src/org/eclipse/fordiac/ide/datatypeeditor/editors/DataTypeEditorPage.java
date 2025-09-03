/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University, Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Muttenthaler Benjamin
 *     - fixed reload of view if file on file system did change
 *     - use new saveType method of AbstractTypeExporter
 *     - replaced DataTypeListener by AdapterImpl
 *     - keep a copy of the datatype object in the view, otherwise the content of the file is changed even the save button was not pressed
 *   Lukas Wais
 *     - enabled Save As
 *******************************************************************************/

package org.eclipse.fordiac.ide.datatypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.datatypeeditor.Messages;
import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructEditingComposite;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditorPage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorSite;

public class DataTypeEditorPage extends AbstractTypeEditorPage {

	private GraphicalAnnotationModel annotationModel;
	private StructEditingComposite structComposite;

	@Override
	public void dispose() {
		if (structComposite != null) {
			structComposite.dispose();
		}
		super.dispose();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// nothing to do for save
	}

	@Override
	public void doSaveAs() {
		// nothing to do for saveAs
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(Messages.DataTypeEditorPage_DataType);
		setTitleImage(FordiacImage.ICON_DATA_TYPE.getImage());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(final Composite parent) {
		if (getType() instanceof final StructuredType structType) {
			structComposite = new StructEditingComposite(parent, getCommandStack(), structType, annotationModel,
					getSite());
			getSite().setSelectionProvider(structComposite.getSelectionProvider());
		}
	}

	@Override
	public void setFocus() {
		structComposite.setFocus();
	}

	@Override
	public AnyDerivedType getType() {
		return (AnyDerivedType) super.getType();
	}

	@Override
	public void setInput(final IEditorInput input) {
		checkEditorInput(input);
		if (getSite() instanceof final MultiPageEditorSite mpes) {
			annotationModel = mpes.getMultiPageEditor().getAdapter(GraphicalAnnotationModel.class);
		}
		if (structComposite != null) {
			structComposite.setAnnotationModel(annotationModel);
		}
		super.setInputWithNotify(input);
	}

	@Override
	public void reloadType() {
		if (getType() instanceof final StructuredType structType) {
			structComposite.setStructType(structType);
		}
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// currently we don't support markers
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// currently we don't have outline for data types so we don't need to do
		// anything
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return false;
	}

	@Override
	public Object getSelectableObject() {
		return null;
	}

}
