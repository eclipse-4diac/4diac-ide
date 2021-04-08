/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.annotations;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

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

	public static boolean isResourceTypeFB(final FB fb) {
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

	private FBAnnotations() {
		throw new UnsupportedOperationException("The utility class SubAppAnnotations should not be instatiated"); //$NON-NLS-1$
	}

}
