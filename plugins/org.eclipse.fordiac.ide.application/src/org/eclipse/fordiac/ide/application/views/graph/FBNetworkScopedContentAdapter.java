/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.application.views.graph;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

public class FBNetworkScopedContentAdapter extends EContentAdapter {
	private final FBNetwork root;

	public FBNetworkScopedContentAdapter(final FBNetwork root) {
		this.root = root;
		root.eAdapters().add(this);
	}

	public void dispose() {
		root.eAdapters().remove(this);
	}

	@Override
	protected void addAdapter(final Notifier notifier) {
		if (notifier == root || notifier instanceof final BlockFBNetworkElement block && block.eContainer() == root) {
			super.addAdapter(notifier);
		}
	}
}
