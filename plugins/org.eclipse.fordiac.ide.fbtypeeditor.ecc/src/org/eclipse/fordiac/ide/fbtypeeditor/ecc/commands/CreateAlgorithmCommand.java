/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - command returns now created element
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class CreateAlgorithmCommand extends AbstractCreationCommand {
	private final BasicFBType fbType;
	private STAlgorithm newAlgorithm;
	private Algorithm oldAlgorithm;
	private ECAction action;

	public CreateAlgorithmCommand(final BasicFBType fbType) {
		this.fbType = fbType;
	}

	public Algorithm getNewAlgorithm() {
		return newAlgorithm;
	}

	public CreateAlgorithmCommand(final BasicFBType fbType, ECAction action) {
		this(fbType);
		this.action = action;
	}

	@Override
	public void execute() {
		if (null != action) {
			oldAlgorithm = action.getAlgorithm();
		}
		newAlgorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		newAlgorithm.setComment("new algorithm"); //$NON-NLS-1$
		newAlgorithm.setText(""); // especially the xtext editor requires at least an empty //$NON-NLS-1$
									// algorithm text
		redo();
		newAlgorithm.setName(NameRepository.createUniqueName(newAlgorithm, "ALG")); //$NON-NLS-1$
	}

	@Override
	public void undo() {
		if (null != action) {
			action.setAlgorithm(oldAlgorithm);
		}
		fbType.getAlgorithm().remove(newAlgorithm);
	}

	@Override
	public void redo() {
		if (null != action) {
			action.setAlgorithm(newAlgorithm);
		}
		fbType.getAlgorithm().add(newAlgorithm);
	}

	@Override
	public Object getCreatedElement() {
		return newAlgorithm;
	}
}
