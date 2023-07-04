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
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBItemProvider;

public class FBItemProviderEmfCompare extends FBItemProvider {

	public FBItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		final EObject cont = ((FB) object).eContainer();
		if (cont != null) {
			return cont;
		}
		return super.getParent(object);
	}

	@Override
	public Collection<?> getChildren(final Object object) {

		return Collections.singletonList(((FB) object).getInterface());

		// final Collection<Object> children = new ArrayList<>();
		// children.addAll(((FB) object).getInterface());
		// return children;
		// return ((FB) object).getInterface().getAllInterfaceElements();
	}

	// EMF BOOK SEITE 343
	// @Override
	// protected Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
	// if (childrenFeatures == null) {
	// super.getChildrenFeatures(object);
	// childrenFeatures.add(LibraryElementPackage.Literals.FB_TYPE__INTERFACE_LIST);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__PLUGS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__SOCKETS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_INPUTS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_OUTPUTS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS);
	// childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS);
	// }
	// return childrenFeatures;
	// }
}
