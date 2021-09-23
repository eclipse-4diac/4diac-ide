/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
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
 *     - command now returns newly created element
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;

/**
 * The Class AddNewVersionInfoCommand.
 */
public class AddNewVersionInfoCommand extends Command implements CreationCommand {

	/** The format. */
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$

	/** The type. */
	private final LibraryElement type;

	/** The new version info. */
	private VersionInfo info;

	/**
	 * Instantiates a new change comment command.
	 *
	 * @param interfaceElement the interface element
	 * @param comment          the comment
	 */
	public AddNewVersionInfoCommand(final LibraryElement type) {
		super();
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		info = LibraryElementFactory.eINSTANCE.createVersionInfo();
		info.setDate(format.format(new Date(System.currentTimeMillis())));
		info.setAuthor(FordiacMessages.Unknown);
		info.setOrganization(FordiacMessages.Unknown);
		info.setVersion("1.0"); //$NON-NLS-1$
		info.setRemarks(""); //$NON-NLS-1$
		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		type.getVersionInfo().remove(info);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		type.getVersionInfo().add(info);
	}

	@Override
	public Object getCreatedElement() {
		return info;
	}

}
