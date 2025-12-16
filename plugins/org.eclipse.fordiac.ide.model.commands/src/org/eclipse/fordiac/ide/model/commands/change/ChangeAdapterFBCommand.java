/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted from ChangeDataTypeCommand
 *   Martin Jobst - refactored additional commands
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;

/**
 * This class is only used by the {@link ChangeDataTypeCommand}
 */
final class ChangeAdapterFBCommand extends UpdateFBTypeCommand {

	public ChangeAdapterFBCommand(final AdapterDeclaration adpDecl) {
		super(adpDecl.getAdapterFB(), null);
	}

	@Override
	public void execute() {
		// use the type of the adapterDecl for changing the adapterfbs type
		setEntry(getOldElement().getAdapterDecl().getType().getTypeEntry());
		super.execute();
		setAdapterFB((AdapterFB) newElement);
	}

	@Override
	public void undo() {
		super.undo();
		setAdapterFB(getOldElement());
	}

	@Override
	public void redo() {
		super.redo();
		setAdapterFB((AdapterFB) newElement);
	}

	private static void setAdapterFB(final AdapterFB fb) {
		fb.getAdapterDecl().setAdapterFB(fb);
	}

	@Override
	public AdapterFB getOldElement() {
		return (AdapterFB) super.getOldElement();
	}
}
