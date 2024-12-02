/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.editors;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

public class ApplicationViewerCellModifier implements ICellModifier {

	public static final String APP_NAME = "APP_NAME"; //$NON-NLS-1$
	public static final String APP_COMMENT = "APP_COMMENT"; //$NON-NLS-1$

	private final CommandStack commandStack;

	public ApplicationViewerCellModifier(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public boolean canModify(final Object element, final String property) {
		return true;
	}

	@Override
	public Object getValue(final Object element, final String property) {
		final Application selectedApp = (Application) element;
		return switch (property) {
		case APP_NAME -> selectedApp.getName();
		case APP_COMMENT -> selectedApp.getComment();
		default -> ""; //$NON-NLS-1$
		};
	}

	@Override
	public void modify(final Object element, final String property, final Object value) {
		final Application selectedApp = (Application) ((TableItem) element).getData();
		final Command cmd = switch (property) {
		case APP_NAME -> ChangeNameCommand.forName(selectedApp, value.toString());
		case APP_COMMENT -> new ChangeCommentCommand(selectedApp, value.toString());
		default -> null;
		};
		commandStack.execute(cmd);
	}

}
