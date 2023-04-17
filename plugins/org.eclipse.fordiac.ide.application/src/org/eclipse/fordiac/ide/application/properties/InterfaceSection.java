/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Bianca Wiesmayr - make struct settable for Multiplexers
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public class InterfaceSection extends AbstractInterfaceSection {


	@Override
	protected FBNetworkElement getInputType(final Object input) {
		return InstanceSectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		if (getType().isContainedInTypedInstance()) {
			disableAllFields();
		}
	}

	@Override
	protected FBNetworkElement getType() {
		return (FBNetworkElement) super.getType();
	}

	private final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	private final Adapter fbnElementAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected void addContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().add(fbnElementAdapter);
			getType().getInterface().eAdapters().add(interfaceAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().remove(fbnElementAdapter);
			getType().getInterface().eAdapters().remove(interfaceAdapter);
		}
	}

}
