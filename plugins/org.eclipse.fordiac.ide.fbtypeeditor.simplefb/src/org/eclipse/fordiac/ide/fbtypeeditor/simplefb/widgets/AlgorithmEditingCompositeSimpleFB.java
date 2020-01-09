/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN, Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann 
 *   - initial implementation and/or initial documentation
 *   Martin Melik-Merkumians
 *   - changes access modifiers for OO encapsulation
 *   Alois Zoitl - moved more code into AlgorithmEditingComposite
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.widgets;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.AbstractChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.AlgorithmEditingComposite;
import org.eclipse.fordiac.ide.fbtypeeditor.simplefb.commands.ChangeAlgorithmTypeCommandSimpleFB;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AlgorithmEditingCompositeSimpleFB extends AlgorithmEditingComposite {

	@Override
	public void createControls(final Composite parent, final FormToolkit toolkit) {
		Composite container = toolkit.createComposite(parent);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);

		super.createControls(container, toolkit);
	}

	@Override
	protected AbstractChangeAlgorithmTypeCommand getChangeAlgorithmTypeCommand(BaseFBType fbType,
			Algorithm oldAlgorithm, String algorithmType) {
		return new ChangeAlgorithmTypeCommandSimpleFB(fbType, oldAlgorithm, algorithmType);
	}

}
