/*******************************************************************************
 * Copyright (c) 2013, 2016, 2017 fortiss GmbH, Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput;
import org.eclipse.ui.IMemento;

public class ApplicationEditorInput extends PersistableUntypedEditorInput {

	public ApplicationEditorInput(Application app) {
		super(app, app.getName());
	}
	
	@Override
	public void saveState(IMemento memento) {
		ApplicationEditorInputFactory.saveState(memento, this);
		
	}

	@Override
	public String getFactoryId() {
		return ApplicationEditorInputFactory.getFactoryId();
	}
	
	public AutomationSystem getAutomationSystem(){
		return getContent().getAutomationSystem();
	}

	@Override
	public Application getContent() {
		return (Application)super.getContent();
	}
}
