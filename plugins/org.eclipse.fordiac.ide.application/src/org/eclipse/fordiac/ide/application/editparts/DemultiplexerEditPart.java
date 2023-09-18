/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;

public class DemultiplexerEditPart extends AbstractStructManipulatorEditPart {
	public DemultiplexerEditPart() {
		super();
	}

	@Override
	public Demultiplexer getModel() {
		return (Demultiplexer) super.getModel();
	}
}
