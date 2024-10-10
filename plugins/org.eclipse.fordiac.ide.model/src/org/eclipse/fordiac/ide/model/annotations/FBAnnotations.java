/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add helper to find children by simple name
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations;

public final class FBAnnotations {

	public static boolean isResourceFB(final FB fb) {
		// A fB is a resource FB if the FB is in the fbnetwork of a resource and
		// the mapping is null or as preparation when we allow to map resource FBs
		// to applications when the mapping from is equal to the fb
		if (fb.getFbNetwork().eContainer() instanceof Resource) {
			return (null == fb.getMapping()) || (fb.equals(fb.getMapping().getFrom()));
		}
		return false;
	}

	public static FBNetwork loadCFBNetwork(final CFBInstance cfb) {
		FBNetwork fbNetwork = cfb.getCfbNetwork();
		if (null == fbNetwork) {
			fbNetwork = FBNetworkHelper.copyFBNetWork(cfb.getType().getFBNetwork(), cfb.getInterface());
			cfb.setCfbNetwork(fbNetwork);
		}
		return fbNetwork;
	}

	public static Stream<INamedElement> findBySimpleName(final CFBInstance root, final String name) {
		loadCFBNetwork(root); // ensure network is loaded
		return NamedElementAnnotations.findBySimpleName(root, name);
	}

	private FBAnnotations() {
		throw new UnsupportedOperationException("The utility class SubAppAnnotations should not be instatiated"); //$NON-NLS-1$
	}

}
