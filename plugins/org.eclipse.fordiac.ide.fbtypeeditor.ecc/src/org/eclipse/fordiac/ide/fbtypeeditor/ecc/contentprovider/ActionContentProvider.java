/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider;

import org.eclipse.fordiac.ide.model.libraryElement.BaseECState;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class ActionContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof final BaseECState baseState) {
			return baseState.getECActions().toArray();
		}
		if (inputElement instanceof final ECAction action) {
			return action.getECState().getECC().getBasicFBType().getAlgorithm().toArray();
		}
		if (inputElement instanceof final SimpleECAction simpleAction) {
			return simpleAction.getSimpleECState().getSimpleFBType().getAlgorithm().toArray();
		}
		return new Object[] {};
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final BaseECState state && null != state.getECActions()) {
			return state.getECActions().toArray();
		}
		if (parentElement instanceof final BaseFBType baseType) {
			return baseType.getAlgorithm().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final ECState state) {
			return state.getECC();
		}
		if (element instanceof final ECAction action && null != action.getECState()) {
			return action.getECState().getBaseFBType();
		}
		if (element instanceof final SimpleECAction simpleAction && null != simpleAction.getSimpleECState()) {
			return simpleAction.getSimpleECState().getBaseFBType();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final BaseECState state) {
			return !state.getECActions().isEmpty();
		}
		if (element instanceof final ECAction action) {
			return !action.getECState().getBaseFBType().getAlgorithm().isEmpty();
		}
		if (element instanceof final SimpleECAction simpleAction) {
			return !simpleAction.getSimpleECState().getBaseFBType().getAlgorithm().isEmpty();
		}
		return false;
	}
}
