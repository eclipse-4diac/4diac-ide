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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

class OutMappedInOutVarsEList extends EObjectContainmentEList<VarDeclaration> {

	private static final long serialVersionUID = 1L;

	OutMappedInOutVarsEList(final InterfaceListImpl owner, final int featureID) {
		super(VarDeclaration.class, owner, featureID);
	}

	@Override
	protected void didSet(final int index, final VarDeclaration newObject, final VarDeclaration oldObject) {
		updateInOutVarReference(index, newObject);
	}

	@Override
	protected void didAdd(final int index, final VarDeclaration newObject) {
		updateInOutVarReference(index, newObject);
	}

	@Override
	protected void didRemove(final int index, final VarDeclaration oldObject) {
		removeInOutVarReference(index, oldObject);
	}

	@Override
	protected void didClear(final int size, final Object[] oldObjects) {
		if (oldObjects != null) {
			for (int i = 0; i < size; ++i) {
				removeInOutVarReference(i, (VarDeclaration) oldObjects[i]);
			}
		}
	}

	@Override
	protected void didMove(final int index, final VarDeclaration movedObject, final int oldIndex) {
		updateInOutVarReference(index, movedObject);
	}

	protected void updateInOutVarReference(final int index, final VarDeclaration varDeclaration) {
		// get corresponding inOutVar (must be present already)
		if (index >= getInterfaceList().getInOutVars().size()) {
			throw new IllegalStateException("No corresponding inOutVar found for outMappedInOutVar"); //$NON-NLS-1$
		}
		final VarDeclaration inOutVar = getInterfaceList().getInOutVars().get(index);
		// get adapter (must be present if inOutVar is in an interface list)
		final OutMappedInOutVarAdapter adapter = OutMappedInOutVarAdapter.findAdapter(inOutVar);
		if (adapter == null) {
			throw new IllegalStateException("No OutMappedInOutVarAdapter found on corresponding inOutVar"); //$NON-NLS-1$
		}
		// update reference (should point to varDeclaration already, unless this is a direct change)
		if (adapter.getOutMappedInOutVar() == null) {
			adapter.setOutMappedInOutVar(varDeclaration);
		} else if (adapter.getOutMappedInOutVar() != varDeclaration) {
			throw new IllegalStateException("Different outMappedInOutVar referenced in corresponding inOutVar"); //$NON-NLS-1$
		}
	}

	protected void removeInOutVarReference(final int index, final VarDeclaration varDeclaration) {
		// get corresponding inOutVar (may be already gone or a different one if moved)
		if (index >= getInterfaceList().getInOutVars().size()) {
			return; // already gone
		}
		final VarDeclaration inOutVar = getInterfaceList().getInOutVars().get(index);
		// get adapter (must be present if inOutVar is in an interface list)
		final OutMappedInOutVarAdapter adapter = OutMappedInOutVarAdapter.findAdapter(inOutVar);
		if (adapter == null) {
			throw new IllegalStateException("No OutMappedInOutVarAdapter found on corresponding inOutVar"); //$NON-NLS-1$
		}
		// remove reference if still pointing to the old outMappedInOutVar
		// (should only happen if this is a direct change)
		if (adapter.getOutMappedInOutVar() == varDeclaration) {
			adapter.setOutMappedInOutVar(null);
		}
	}

	InterfaceListImpl getInterfaceList() {
		return (InterfaceListImpl) getNotifier();
	}
}
