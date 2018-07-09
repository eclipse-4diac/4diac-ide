/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public abstract class AbstractChangeElementNameWithOppositeCommand extends ChangeNameCommand {

	/* the command for updating the name of the opposite element when necessary
	 */
	private ChangeNameCommand oppositeChange = null;

	public AbstractChangeElementNameWithOppositeCommand(final INamedElement element, String name) {
		super(element, name);
		
		INamedElement oppositeElement = getOppositeElement(element);
		if(null != oppositeElement) {
			oppositeChange = new ChangeNameCommand(oppositeElement, name);
		}
	}

	@Override
	public void execute() {
		super.execute();
		if(null != oppositeChange) {
			oppositeChange.execute();
		}
	}

	@Override
	public void redo() {
		super.redo();
		if(null != oppositeChange) {
			oppositeChange.redo();
		}
	}

	@Override
	public void undo() {
		super.undo();
		if(null != oppositeChange) {
			oppositeChange.undo();
		}
	}

	protected abstract INamedElement getOppositeElement(INamedElement element);
}
