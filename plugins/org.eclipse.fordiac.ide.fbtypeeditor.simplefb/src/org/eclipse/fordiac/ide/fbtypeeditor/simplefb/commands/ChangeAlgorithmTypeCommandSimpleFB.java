/*******************************************************************************
 * Copyright (c) 22018 TU Wien/ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Peter Gsellmann
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;

public class ChangeAlgorithmTypeCommandSimpleFB extends Command {

	protected final BaseFBType fbType;
	protected Algorithm oldAlgorithm;
	protected Algorithm newAlgorithm;
	private String algorithmType;

	public ChangeAlgorithmTypeCommandSimpleFB(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
		this.fbType = fbType;
		this.oldAlgorithm = oldAlgorithm;
		this.algorithmType = algorithmType;
	}

	@Override
	public boolean canExecute() {
		if (algorithmType.equalsIgnoreCase("ST")) { //$NON-NLS-1$
			if (oldAlgorithm instanceof STAlgorithm) {
				return false;
			} else if (!(oldAlgorithm instanceof TextAlgorithm || oldAlgorithm instanceof OtherAlgorithm)) {
				return false;
			}
		} else if ((!(oldAlgorithm instanceof STAlgorithm))
				&& (oldAlgorithm instanceof TextAlgorithm || oldAlgorithm instanceof OtherAlgorithm)) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		// FIXME this only works if there are no more other algorithms
		// supported!!!

		if (algorithmType.equalsIgnoreCase("ST")) { //$NON-NLS-1$
			newAlgorithm = createSTAlgorithm();
		} else {
			newAlgorithm = createOtherAlgorithm();
		}

		redo();
	}

	private Algorithm createSTAlgorithm() {
		STAlgorithm algorithm = LibraryElementFactory.eINSTANCE.createSTAlgorithm();
		algorithm.setText(((TextAlgorithm) oldAlgorithm).getText());
		algorithm.setName(oldAlgorithm.getName());
		algorithm.setComment(oldAlgorithm.getComment());
		return algorithm;
	}

	private Algorithm createOtherAlgorithm() {
		OtherAlgorithm algorithm = LibraryElementFactory.eINSTANCE.createOtherAlgorithm();
		algorithm.setText(((TextAlgorithm) oldAlgorithm).getText());
		algorithm.setName(oldAlgorithm.getName());
		algorithm.setComment(oldAlgorithm.getComment());
		algorithm.setLanguage("AnyText"); //$NON-NLS-1$
		return algorithm;
	}

	@Override
	public void undo() {
		((SimpleFBType) fbType).setAlgorithm(oldAlgorithm);
	}

	@Override
	public void redo() {
		((SimpleFBType) fbType).setAlgorithm(newAlgorithm);
	}

	public Algorithm getNewAlgorithm() {
		return newAlgorithm;
	}

}
