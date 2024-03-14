/*******************************************************************************
 * Copyright (c) 2020, 2024 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.policies.SubAppContentLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;

public class UnfoldedSubappContentEditPart extends AbstractContainerContentEditPart {

	private final Adapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getPositionableElement_Position().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getConnection_RoutingData().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getFBNetwork_NetworkElements().equals(feature)) {
				Display.getDefault().asyncExec(() -> getParent().layoutExpandedInterface());
			}
			super.notifyChanged(notification);
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Notifier) getModel()).eAdapters().add(adapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((Notifier) getModel()).eAdapters().remove(adapter);
		}
	}

	@Override
	public SubAppForFBNetworkEditPart getParent() {
		return (SubAppForFBNetworkEditPart) super.getParent();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// Add policy to handle drag&drop of fbs
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new SubAppContentLayoutEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractCreateInstanceDirectEditPolicy() {
			@Override
			protected Command getElementCreateCommand(final TypeEntry value, final Point refPoint) {
				return new ResizeGroupOrSubappCommand(getHost(), AbstractCreateFBNetworkElementCommand
						.createCreateCommand(value, getModel(), refPoint.x, refPoint.y));
			}
		});
	}

	@Override
	public FBNetwork getModel() {
		return super.getModel();
	}

	@Override
	public SubApp getContainerElement() {
		return (SubApp) getModel().eContainer();
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == SubApp.class) {
			return key.cast(getContainerElement());
		}
		return super.getAdapter(key);
	}

}
