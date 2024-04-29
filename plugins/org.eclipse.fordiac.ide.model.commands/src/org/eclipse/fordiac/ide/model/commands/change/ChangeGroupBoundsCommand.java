/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;

public class ChangeGroupBoundsCommand extends AbstractChangeContainerBoundsCommand {

	public ChangeGroupBoundsCommand(final Group group, final int dx, final int dy, final int dw, final int dh) {
		super(group, dx, dy, dw, dh, group.getWidth(), group.getHeight());
	}

	@Override
	protected void updateSize(final double width, final double height) {
		getTarget().setWidth(width);
		getTarget().setHeight(height);
	}

	@Override
	protected List<FBNetworkElement> getChildren() {
		return getTarget().getGroupElements();
	}

	@Override
	public Group getTarget() {
		return (Group) super.getTarget();
	}

}
