/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin
 *   - initial API and implementation and/or initial documentation
 *   - removed boilerplate code
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class InternalConnectionsViewer extends ConnectionDisplayWidget {

	public InternalConnectionsViewer(final TabbedPropertySheetWidgetFactory widgetFactory, final Composite parent,
			final AbstractSection section) {
		super(widgetFactory, parent, section);
	}

	@Override
	public void refreshConnectionsViewer(final IInterfaceElement type) {
		this.type = type;
		isInputViewer = type.isIsInput();
		refreshConnectionsViewerManual(!isInputViewer); // Negation for the title
	}

	@Override
	protected TableViewer createConnectionsViewer(final Composite parent) {
		final TableViewer internalViewer = super.createConnectionsViewer(parent);
		internalViewer.setContentProvider(new InternalConnectionContentProvider());
		return internalViewer;
	}

	private static class InternalConnectionContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof IInterfaceElement) {
				final IInterfaceElement element = ((IInterfaceElement) inputElement);
				if ((element.isIsInput() && (null != element.getFBNetworkElement()))
						|| (!element.isIsInput() && (null == element.getFBNetworkElement()))) {
					return element.getOutputConnections().toArray();
				}
				return element.getInputConnections().toArray();
			}
			return new Object[] {};
		}
	}
}
