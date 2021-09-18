/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *          2019, 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *   Melanie Winter - add insertion after refelement/index
 *   Melanie Winter, Bianca Wiesmayr
 *    - extracted code from CreateTransactionSection for better reuse
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;

public abstract class AbstractCreateElementCommand<T> extends Command implements CreationCommand {
	private T newElement;
	private final EList<T> list;
	private int index;


	protected AbstractCreateElementCommand(final EList<T> list) {
		this.list = list;
		calculateInsertionIndex(list, null);
	}

	protected AbstractCreateElementCommand(final EList<T> list, final int index) {
		this(list);
		this.index = index;
	}

	protected AbstractCreateElementCommand(final EList<T> list, final T refElement) {
		this(list);
		calculateInsertionIndex(list, refElement);
	}

	private void calculateInsertionIndex(final EList<T> list, final T refElement) {
		if (refElement != null) {
			index = list.indexOf(refElement) + 1;
		} else {
			index = list.size();
		}
	}

	@Override
	public boolean canExecute() {
		return list != null;
	}
	@Override
	public void execute() {
		newElement = createNewElement();
		list.add(index, newElement);
	}

	protected abstract T createNewElement();

	@Override
	public void undo() {
		list.remove(newElement);
	}

	@Override
	public void redo() {
		list.add(index, newElement);
	}

	@Override
	public Object getCreatedElement() {
		return newElement;
	}
}
