/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *      - Initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;

public abstract class AbstractChangeListElementOrderCommand<T> extends Command {
	private final T selection;
	private final EList<T> list;
	private final int oldIndex;
	private int newIndex;

	private AbstractChangeListElementOrderCommand(final T selection, final EList<T> list) {
		this.selection = selection;
		this.list = list;
		this.oldIndex = list.indexOf(selection);
		Assert.isTrue(oldIndex >= 0);
	}

	protected AbstractChangeListElementOrderCommand(final T selection, final boolean moveUp, final EList<T> list) {
		this(selection, list);
		this.newIndex = moveUp ? oldIndex - 1 : oldIndex + 1;

		if (newIndex < 0) {
			newIndex = 0;
		}
		if (newIndex >= list.size()) {
			newIndex = list.size() - 1;
		}
	}

	protected AbstractChangeListElementOrderCommand(final T selection, final int newIndex, final EList<T> list) {
		this(selection, list);
		this.newIndex = newIndex;
	}

	protected AbstractChangeListElementOrderCommand(final T selection, final T refElement, final boolean insertAfter,
			final EList<T> list) {
		this(selection, list);
		int index = list.indexOf(refElement);
		Assert.isTrue(index >= 0);
		if (insertAfter) {
			index++;
		}
		this.newIndex = index;
	}

	@Override
	public boolean canExecute() {
		return (null != selection) && (list.size() > 1) && (list.size() > newIndex);
	}

	@Override
	public void execute() {
		moveTo(newIndex);
	}

	@Override
	public void redo() {
		moveTo(newIndex);
	}

	@Override
	public void undo() {
		moveTo(oldIndex);
	}

	private void moveTo(final int index) {
		list.move(index, selection);
	}

	public T getSelection() {
		return selection;
	}
}
