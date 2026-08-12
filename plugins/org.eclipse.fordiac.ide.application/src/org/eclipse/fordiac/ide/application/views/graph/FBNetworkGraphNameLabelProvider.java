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

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.graph.FBNetworkGraph;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class FBNetworkGraphNameLabelProvider extends ColumnLabelProvider {

	private final AdapterFactoryLabelProvider delegate = new AdapterFactoryLabelProvider(
			new LibraryElementItemProviderAdapterFactory());

	@Override
	public String getText(final Object object) {
		return switch (object) {
		case final FBNetworkGraph<?>.Node node -> delegate.getText(node.getElement());
		case final FBNetwork network when network.eContainer() != null -> delegate.getText(network.eContainer());
		case null, default -> delegate.getText(object);
		};
	}

	@Override
	public Image getImage(final Object object) {
		return switch (object) {
		case final FBNetworkGraph<?>.Node node -> delegate.getImage(node.getElement());
		case final FBNetwork network when network.eContainer() != null -> delegate.getImage(network.eContainer());
		case null, default -> delegate.getImage(object);
		};
	}

	@Override
	public void dispose() {
		delegate.dispose();
		super.dispose();
	}
}
