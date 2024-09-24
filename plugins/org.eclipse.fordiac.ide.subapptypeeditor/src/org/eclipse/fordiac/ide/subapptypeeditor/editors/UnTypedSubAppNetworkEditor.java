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
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.jface.util.TransferDropTargetListener;

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
	protected DefaultEditDomain createEditDomain() {
		return new FBTypeEditDomain(this, commandStack);
	}

	@Override
	protected String getPaletteNavigatorID() {
		// for subapp types we want to show the same as for applications. If we wouldn't
		// provide this here
		// we would get the palette of the composite FB type editor
		return "org.eclipse.fordiac.ide.fbpaletteviewer"; //$NON-NLS-1$
	}

	@Override
	protected TypeLibrary getTypeLibrary() {
		return typeLib;
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
		return new FbTypeTemplateTransferDropTargetListener(getGraphicalViewer(), getTypeLibrary().getProject());
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// nothing needed to be done here, should be handled by the parent
		// SubAppNetworkBreadCrumbEditor
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return false;
	}

	@Override
	public void reloadType() {
		if (getType() instanceof final SubAppType subAppType) {
			final FBNetwork fbNetwork = subAppType.getFBNetwork();
			if (fbNetwork != null) {
				getGraphicalViewer().setContents(fbNetwork);
			} else {
				EditorUtils.CloseEditor.run(this);
			}
		}

	}

	@Override
	public Object getSelectableObject() {
		if (getGraphicalViewer() == null) {
			return null;
		}

		return getGraphicalViewer().getRootEditPart();
	}

}
