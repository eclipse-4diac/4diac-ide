/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;

public abstract class AbstractChangeAlgorithmTypeCommand extends Command {
	
	private final BaseFBType fbType;
	private final Algorithm oldAlgorithm;
	private Algorithm newAlgorithm;
	private final String algorithmType;

	public AbstractChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
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
		if (algorithmType.equalsIgnoreCase("ST")) { //$NON-NLS-1$
			newAlgorithm = createSTAlgorithm();
		} else {
			newAlgorithm = createOtherAlgorithm();
		}

		changeAlgorithm(getOldAlgorithm(), getNewAlgorithm());
	}
	
	@Override
	public void undo() {
		changeAlgorithm(getNewAlgorithm(), getOldAlgorithm());
	}

	@Override
	public void redo() {
		changeAlgorithm(getOldAlgorithm(), getNewAlgorithm());
	}
	
	protected abstract void changeAlgorithm(Algorithm oldAlg, Algorithm newAlg);

	public Algorithm getNewAlgorithm() {
		return newAlgorithm;
	}
	
	protected Algorithm getOldAlgorithm() {
		return oldAlgorithm;
	}
	
	protected BaseFBType getType() {
		return fbType;
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



}
