/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.application.editors.FBTypePaletteViewerProvider;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.utilities.FbTypeTemplateTransferDropTargetListener;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;

public class UnTypedSubAppNetworkEditor extends SubAppNetworkEditor implements IFBTEditorPart {

	private CommandStack commandStack;
	private TypeLibrary typeLib;

	public void setTypeLib(final TypeLibrary typeLib) {
		this.typeLib = typeLib;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	protected void setModel(final IEditorInput input) {
		super.setModel(input);

		setEditDomain(new FBTypeEditDomain(this, commandStack));
	}

	@Override
	protected String getPaletteNavigatorID() {
		// for subapp types we want to show the same as for applications. If we wouldn't
		// provide this here
		// we would get the palette of the composite FB type editor
		return "org.eclipse.fordiac.ide.fbpaletteviewer"; //$NON-NLS-1$
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		super.selectionChanged(part, selection);
		updateActions(getSelectionActions());
	}

	@Override
	protected Palette getPalette() {
		return typeLib.getBlockTypeLib();
	}

	@Override
	public AutomationSystem getSystem() {
		return null;
	}

	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new FBTypePaletteViewerProvider(typeLib.getProject(), getEditDomain(), getPaletteNavigatorID());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		return new FbTypeTemplateTransferDropTargetListener(getGraphicalViewer(), getPalette().getProject());
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// TODO Auto-generated method stub
		return false;
	}

}
