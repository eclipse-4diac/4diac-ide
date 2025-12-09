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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.ConcurrentModificationException;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

class InOutVarsEList extends EObjectContainmentEList<VarDeclaration> {

	private static final long serialVersionUID = 1L;

	InOutVarsEList(final InterfaceListImpl owner, final int featureID) {
		super(VarDeclaration.class, owner, featureID);
	}

	@Override
	protected NotificationChain shadowSet(final VarDeclaration oldObject, final VarDeclaration newObject,
			final NotificationChain notifications) {
		final int index = indexOf(newObject);
		checkConcurrentModification(index, oldObject);
		getInterfaceList().getOutMappedInOutVars().set(index, OutMappedInOutVarAdapter.adapt(newObject));
		return notifications;
	}

	@Override
	protected NotificationChain shadowAdd(final VarDeclaration newObject, final NotificationChain notifications) {
		final int index = indexOf(newObject);
		getInterfaceList().getOutMappedInOutVars().add(index, OutMappedInOutVarAdapter.adapt(newObject));
		return notifications;
	}

	@Override
	protected void didRemove(final int index, final VarDeclaration oldObject) {
		checkConcurrentModification(index, oldObject);
		getInterfaceList().getOutMappedInOutVars().remove(index);
	}

	@Override
	protected void didClear(final int size, final Object[] oldObjects) {
		if (oldObjects != null) {
			for (int i = 0; i < size; ++i) {
				checkConcurrentModification(i, (VarDeclaration) oldObjects[i]);
			}
		}
		getInterfaceList().getOutMappedInOutVars().clear();
	}

	@Override
	protected void didMove(final int index, final VarDeclaration movedObject, final int oldIndex) {
		checkConcurrentModification(oldIndex, movedObject);
		getInterfaceList().getOutMappedInOutVars().move(index, oldIndex);
	}

	@Override
	protected boolean hasShadow() {
		return true;
	}

	@Override
	protected boolean isNotificationRequired() {
		return true;
	}

	protected void checkConcurrentModification(final int index, final VarDeclaration varDeclaration) {
		if (index >= getInterfaceList().getOutMappedInOutVars().size() || OutMappedInOutVarAdapter
				.find(varDeclaration) != getInterfaceList().getOutMappedInOutVars().get(index)) {
			throw new ConcurrentModificationException("Direct change to outMappedInOutVars"); //$NON-NLS-1$
		}
	}

	InterfaceListImpl getInterfaceList() {
		return (InterfaceListImpl) getNotifier();
	}
}
