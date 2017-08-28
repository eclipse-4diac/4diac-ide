/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;

public class ChangeNameCommand extends Command {
	protected INamedElement element;
	private final EObject type;
	protected String name;
	private String oldName;

	public ChangeNameCommand(final INamedElement element, final String name) {
		super();
		this.element = element;
		if(element instanceof FB){
			this.type = element;
		}else{
			if(element.eContainer() instanceof FBType || element instanceof ECState || element instanceof Algorithm){
				this.type = element.eContainer();
			}else{
				if((null != element.eContainer()) && (element.eContainer().eContainer() instanceof FBType)){
					this.type = element.eContainer().eContainer();	
				}else{
					this.type = element;
				}
			}
		}
		this.name = name;
	}
	
	@Override
	public boolean canExecute() {
		return performNameCheck(name);
	}

	@Override
	public void execute() {
		oldName = element.getName();
		if(performNameCheck(name)){
			redo();
		}
	}

	@Override
	public void undo() {
		element.setName(oldName);
	}

	@Override
	public void redo() {
		element.setName(name);
	}

	public boolean performNameCheck(String nameToCheck) {		
		if(nameToCheck.equals(NameRepository.getUniqueElementName(element, type, nameToCheck))){
			Abstract4DIACUIPlugin.statusLineErrorMessage(null);	
			return true;
		}
		else{
			Abstract4DIACUIPlugin.statusLineErrorMessage("Element with Name: " + nameToCheck + " already exists!"); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
	}
}