/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.util.EList;

public class CopyOnWriteEList<E> extends CopyOnWriteArrayList<E> implements EList<E> {

	private static final long serialVersionUID = 7978649951724533310L;

	@Override
	public void move(final int newPosition, final E object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E move(final int newPosition, final int oldPosition) {
		throw new UnsupportedOperationException();
	}
}
