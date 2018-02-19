/*******************************************************************************
 * Copyright (c) 2014, 2015 fortiss GmbH
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

import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;

public class ChangeAlgorithmTextCommand extends Command {

	private TextAlgorithm algorithm;
	
	private String newAlgorithmText;
	
	private String oldAlgorithmText;
	
	public ChangeAlgorithmTextCommand(TextAlgorithm algorithm, String algorithmText) {
		this.algorithm = algorithm;
		this.newAlgorithmText = algorithmText;
	}
	
	@Override
	public void execute() {		
		oldAlgorithmText = algorithm.getText();		
		redo();
	}
	
	@Override
	public void undo() {
		algorithm.setText(oldAlgorithmText);
	}

	@Override
	public void redo() {
		algorithm.setText(newAlgorithmText);
	}

}
