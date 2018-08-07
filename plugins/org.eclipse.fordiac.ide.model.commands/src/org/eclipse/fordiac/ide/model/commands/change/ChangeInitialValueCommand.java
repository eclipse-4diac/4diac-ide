/*******************************************************************************
 * Copyright (c) 2012 - 2014 TU Wien ACIN
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.VarInitialization;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeInitialValueCommand extends Command {	
	private VarDeclaration variable;
	private String newInitialValue;
	private String oldInitialValue;
	
	public ChangeInitialValueCommand(final VarDeclaration variable, final String newInitialValue){
		super();
		this.variable = variable;
		this.newInitialValue = newInitialValue;
	}
	
	@Override
	public boolean canExecute() {
		return (null != variable)&&(null != newInitialValue);
	}
	
	@Override
	public void execute() {
		
		if (variable.getVarInitialization() != null) {
			oldInitialValue = variable.getVarInitialization().getInitialValue();
		} else {
			VarInitialization varInitialization = DataFactory.eINSTANCE
					.createVarInitialization();
			variable.setVarInitialization(varInitialization);
		}
		variable.getVarInitialization().setInitialValue(newInitialValue);
	}
	
	@Override
	public void undo() {
		variable.getVarInitialization().setInitialValue(oldInitialValue);
	}

	@Override
	public void redo() {
		variable.getVarInitialization().setInitialValue(newInitialValue);
	}
}
