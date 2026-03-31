/*******************************************************************************
 * Copyright (c) 2026 Primetals Technology Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.listeners;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;

public class CommentDecoratorCommandStackListener implements CommandStackEventListener {

	private static final String DECORATOR_ID = "org.eclipse.fordiac.ide.hierarchymanager.ui.NodeCommentDecorator"; //$NON-NLS-1$

	@Override
	public void stackChanged(final CommandStackEvent event) {
		if (containsSubAppCommentChange(event.getCommand())) {
			Display.getDefault().asyncExec(() -> {
				final IDecoratorManager decoratorManager = PlatformUI.getWorkbench().getDecoratorManager();
				decoratorManager.update(DECORATOR_ID);
			});
		}
	}

	private static boolean containsSubAppCommentChange(final Command cmd) {
		if (cmd instanceof final CompoundCommand compoundCmd) {
			return compoundCmd.getCommands().stream()
					.anyMatch(CommentDecoratorCommandStackListener::containsSubAppCommentChange);
		}
		return cmd instanceof final ChangeCommentCommand commentCmd
				&& commentCmd.getNamedElement() instanceof UntypedSubApp;
	}
}
