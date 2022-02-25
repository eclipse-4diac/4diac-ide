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
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;

public class CreateAlgorithmCommand extends Command implements CreationCommand {
	private static final String DEFAULT_ALGORITHM_NAME = "ALG1"; //$NON-NLS-1$

	private final BasicFBType fbType;
	private STAlgorithm newAlgorithm;
	private Algorithm oldAlgorithm;
	private ECAction action;
	private final int index;
	private final String name;

	public CreateAlgorithmCommand(final BasicFBType fbType) {
		this(fbType, fbType.getCallables().size() - 1, null);
	}

	public CreateAlgorithmCommand(final BasicFBType fbType, final ECAction action) {
		this(fbType);
		this.action = action;
	}

	public CreateAlgorithmCommand(final BasicFBType fbType, final int index, final String name) {
		this.fbType = fbType;
		this.index = index;
		this.name = (null != name) ? name : DEFAULT_ALGORITHM_NAME;
	}

	@Override
	public void execute() {
		if (null != action) {
			oldAlgorithm = action.getAlgorithm();
		}
		newAlgorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		newAlgorithm.setComment(""); //$NON-NLS-1$
		newAlgorithm.setText(""); // especially the xtext editor requires at least an empty //$NON-NLS-1$
		// algorithm text
		redo();
		newAlgorithm.setName(NameRepository.createUniqueName(newAlgorithm, name));
	}

	@Override
	public void undo() {
		if (null != action) {
			action.setAlgorithm(oldAlgorithm);
		}
		fbType.getCallables().remove(newAlgorithm);
	}

	@Override
	public void redo() {
		if (null != action) {
			action.setAlgorithm(newAlgorithm);
		}
		fbType.getCallables().add(index, newAlgorithm);
	}

	@Override
	public Algorithm getCreatedElement() {
		return newAlgorithm;
	}
}
