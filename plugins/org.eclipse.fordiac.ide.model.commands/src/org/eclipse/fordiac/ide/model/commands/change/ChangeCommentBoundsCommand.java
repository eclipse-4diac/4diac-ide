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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public class ChangeCommentBoundsCommand extends AbstractChangeContainerBoundsCommand {

	public ChangeCommentBoundsCommand(final Comment comment, final int dx, final int dy, final int dw, final int dh) {
		super(comment, dx, dy, dw, dh, comment.getWidth(), comment.getHeight());
	}

	@Override
	protected void updateSize(final double width, final double height) {
		getTarget().setWidth(width);
		getTarget().setHeight(height);
	}

	@Override
	protected List<FBNetworkElement> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public Comment getTarget() {
		return (Comment) super.getTarget();
	}

}
