/*******************************************************************************
 * Copyright (c) 2008 - 2014 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hofmann, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.util.dnd.TransferDataSelectionOfFb;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;

/**
 * This class can return a Factory for creating an instance for the specified
 * template.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ApplicationEditorTemplateTransferDropTargetListener extends
		FbTypeTemplateTransferDropTargetListener {

	/**
	 * Constructor.
	 * 
	 * @param viewer
	 *            the viewer
	 */
	public ApplicationEditorTemplateTransferDropTargetListener(
			final EditPartViewer viewer, final AutomationSystem system) {
		super(viewer, system);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.dnd.TemplateTransferDropTargetListener#getFactory(java
	 * .lang.Object)
	 */
	@Override
	protected CreationFactory getFactory(final Object template) {
		getCurrentEvent().detail = DND.DROP_COPY;

		// TODO: is FBType used, does FBType actually work? Is this transfer
		// used anywhere else?
		if (template instanceof FBType
				|| template instanceof FBTypePaletteEntry
				|| template instanceof FBTypePaletteEntry[]
				|| template instanceof TransferDataSelectionOfFb[]
						|| template instanceof SubApplicationTypePaletteEntry
						|| template instanceof SubApplicationTypePaletteEntry[]) {
			return new FBTypeTemplateCreationFactory(template);
		}
		return null;
	}

}
