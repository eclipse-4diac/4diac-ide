/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.util.FBTypeUtils;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

public class FBTypeDragAssistant extends CommonDragAdapterAssistant {

	@Override
	public Transfer[] getSupportedTransferTypes() {
		return new Transfer[] { TemplateTransfer.getInstance() };
	}

	@Override
	public void dragStart(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
		// TemplateTransfer.getInstance().setTemplate(null);
		if (aSelection.getFirstElement() instanceof IFile) {
			IFile fbTypeFile = (IFile) aSelection.getFirstElement();
			Palette fbPalette = FBTypeUtils
					.getPalletteForFBTypeFile(fbTypeFile);
			if (fbPalette != null) {
				PaletteEntry entry = TypeLibrary.getPaletteEntry(
						fbPalette, fbTypeFile);
				if (entry != null)
					TemplateTransfer.getInstance().setTemplate(entry);

			} else {
				anEvent.doit = false;
			}
		}
		super.dragStart(anEvent, aSelection);
	}

	@Override
	public boolean setDragData(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
		if (aSelection.getFirstElement() instanceof IFile) {
			IFile fbTypeFile = (IFile) aSelection.getFirstElement();
			Palette fbPalette = FBTypeUtils
					.getPalletteForFBTypeFile(fbTypeFile);
			if (fbPalette != null) {
				PaletteEntry entry = TypeLibrary.getPaletteEntry(
						fbPalette, fbTypeFile);

				if (null != entry) {
					anEvent.data = entry;
					return true;
				}
			}
		}

		return false;
	}

}
