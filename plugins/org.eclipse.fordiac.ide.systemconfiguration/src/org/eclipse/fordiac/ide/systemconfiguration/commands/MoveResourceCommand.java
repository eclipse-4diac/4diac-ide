/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 fortiss GmbH, Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.commands.Command;

/**
 * The Class MoverResourceCommand.
 */
public class MoveResourceCommand extends Command {
	
	private final Resource child;

	private final Device device;
	
	private final int oldIndex;

	private int newIndex;

	/**
	 * Instantiates a new move input variable command.
	 * 
	 * @param child the child
	 * @param parent the parent
	 * @param oldIndex the old index
	 * @param newIndex the new index
	 */
	public MoveResourceCommand(final Resource child, final int newIndex) {
		super();
		this.child = child;
		this.device = child.getDevice();
		this.newIndex = newIndex;
		oldIndex = device.getResource().indexOf(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (newIndex > oldIndex) {
			newIndex--;
		}
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		device.getResource().move(oldIndex, child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		device.getResource().move(newIndex, child);
	}

}
