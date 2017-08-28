/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;

public class ChangeAlgorithmTypeCommand extends Command {

	private final BasicFBType fbType;	
	private Algorithm oldAlgorithm;	
	private Algorithm newAlgorithm;
	private String algorithmType;

	public ChangeAlgorithmTypeCommand(BasicFBType fbType,
			Algorithm oldAlgorithm, String algorithmType) {
		this.fbType = fbType;
		this.oldAlgorithm = oldAlgorithm;
		this.algorithmType = algorithmType;
	} 
		
	@Override
	public boolean canExecute() {
		if(algorithmType.equalsIgnoreCase("ST")){ 
			if(oldAlgorithm instanceof STAlgorithm) {
				return false;
			} else if (!(oldAlgorithm instanceof TextAlgorithm || oldAlgorithm instanceof OtherAlgorithm)){
				return false;
			}
		} else if ((!(oldAlgorithm instanceof STAlgorithm)) &&
				(oldAlgorithm instanceof TextAlgorithm || oldAlgorithm instanceof OtherAlgorithm)) {
			return false;
		}
		return true;
	}


	@Override
	public void execute() {		
		// FIXME this only works if there are no more other algorithms
		// supported!!!
		
		if (algorithmType.equalsIgnoreCase("ST")) {
			newAlgorithm = createSTAlgorithm();
		}else{
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
		OtherAlgorithm algorithm = LibraryElementFactory.eINSTANCE
				.createOtherAlgorithm();
		algorithm.setText(((TextAlgorithm) oldAlgorithm).getText());
		algorithm.setName(oldAlgorithm.getName());
		algorithm.setComment(oldAlgorithm.getComment());
		algorithm.setLanguage("AnyText");
		return algorithm;
	}
	

	@Override
	public void undo() {
		fbType.getAlgorithm().add(fbType.getAlgorithm().indexOf(newAlgorithm), oldAlgorithm);
		updateECActions(false);
		fbType.getAlgorithm().remove(newAlgorithm);
	}

	@Override
	public void redo() {
		fbType.getAlgorithm().add(fbType.getAlgorithm().indexOf(oldAlgorithm), newAlgorithm);
		updateECActions(true);
		fbType.getAlgorithm().remove(oldAlgorithm);
	}

	private void updateECActions(boolean redo){
		for(ECState state : fbType.getECC().getECState()){
			for(ECAction action : state.getECAction()){
				if(redo == true){
					if(action.getAlgorithm() == oldAlgorithm){
						action.setAlgorithm(newAlgorithm);
					}
				}else{
					if(action.getAlgorithm() == newAlgorithm){
						action.setAlgorithm(oldAlgorithm);
					}
				}
			}
		}
	}

	public Algorithm getNewAlgorithm() {
		return newAlgorithm;
	}
	
}
