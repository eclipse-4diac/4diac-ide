/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class CreateCommentCommand extends AbstractCreateFBNetworkElementCommand {

	private final Rectangle posSizeRef;

	public CreateCommentCommand(final FBNetwork fbNetwork, final Rectangle posSizeRef) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createComment(), posSizeRef.x, posSizeRef.y);
		this.posSizeRef = posSizeRef;
	}

	@Override
	public boolean canExecute() {
		return getFBNetwork() != null;
	}

	@Override
	public void execute() {
		super.execute();
		getElement().setWidth(posSizeRef.width);
		getElement().setHeight(posSizeRef.height);
	}

	@Override
	public Comment getElement() {
		return (Comment) super.getElement();
	}

	@Override
	protected InterfaceList createInterfaceList() {
		// comments do not have an interface list
		return null;
	}

	@Override
	protected void checkName() {
		// comments don't have a name so no check needed
	}

}
