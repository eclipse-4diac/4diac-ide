/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class OutMappedInOutVarAdapter extends EContentAdapter {

	private VarDeclaration outMappedInOutVar;

	public static VarDeclaration adapt(final VarDeclaration varDeclaration) {
		OutMappedInOutVarAdapter adapter = findAdapter(varDeclaration);
		if (adapter == null) {
			adapter = new OutMappedInOutVarAdapter();
			varDeclaration.eAdapters().add(adapter);
		}
		VarDeclaration outMappedInOutVar = adapter.getOutMappedInOutVar();
		if (outMappedInOutVar == null) {
			outMappedInOutVar = createOutMappedInOutVar(varDeclaration);
			adapter.setOutMappedInOutVar(outMappedInOutVar);
		}
		return outMappedInOutVar;
	}

	public static VarDeclaration find(final VarDeclaration varDeclaration) {
		final OutMappedInOutVarAdapter adapter = findAdapter(varDeclaration);
		if (adapter != null) {
			return adapter.getOutMappedInOutVar();
		}
		return null;
	}

	public static OutMappedInOutVarAdapter findAdapter(final VarDeclaration varDeclaration) {
		return (OutMappedInOutVarAdapter) varDeclaration.eAdapters().stream()
				.filter(OutMappedInOutVarAdapter.class::isInstance).findFirst().orElse(null);
	}

	private static VarDeclaration createOutMappedInOutVar(final VarDeclaration varDeclaration) {
		final VarDeclaration outMappedInOutVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		outMappedInOutVar.setName(varDeclaration.getName());
		outMappedInOutVar.setComment(varDeclaration.getComment());
		outMappedInOutVar.setType(varDeclaration.getType());
		outMappedInOutVar.setArraySize(EcoreUtil.copy(varDeclaration.getArraySize()));
		return outMappedInOutVar;
	}

	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);
		if (notification.getNotifier() instanceof final VarDeclaration varDeclaration) {
			handleVarDeclarationNotifications(notification, varDeclaration);
		}
		if (notification.getNotifier() instanceof final ArraySize arraySize) {
			handleArraySizeNotifications(notification, arraySize);
		}
	}

	private void handleVarDeclarationNotifications(final Notification notification,
			final VarDeclaration varDeclaration) {
		switch (notification.getFeatureID(VarDeclaration.class)) {
		case LibraryElementPackage.VAR_DECLARATION__NAME:
			outMappedInOutVar.setName(varDeclaration.getName());
			break;
		case LibraryElementPackage.VAR_DECLARATION__COMMENT:
			outMappedInOutVar.setComment(varDeclaration.getComment());
			break;
		case LibraryElementPackage.VAR_DECLARATION__TYPE:
			outMappedInOutVar.setType(varDeclaration.getType());
			break;
		case LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE:
			// EcoreUtil copy correctly handles a null source value so we don't need to add
			// an own check.
			outMappedInOutVar.setArraySize(EcoreUtil.copy(varDeclaration.getArraySize()));
			break;
		default:
			// we don't want to copy anything for the other features
			break;
		}
	}

	private void handleArraySizeNotifications(final Notification notification, final ArraySize arraySize) {
		if (notification.getFeatureID(ArraySize.class) == LibraryElementPackage.ARRAY_SIZE__VALUE) {
			outMappedInOutVar.getArraySize().setValue(arraySize.getValue());
		}
	}

	VarDeclaration getOutMappedInOutVar() {
		return outMappedInOutVar;
	}

	void setOutMappedInOutVar(final VarDeclaration outMappedInOutVar) {
		this.outMappedInOutVar = outMappedInOutVar;
	}
}
