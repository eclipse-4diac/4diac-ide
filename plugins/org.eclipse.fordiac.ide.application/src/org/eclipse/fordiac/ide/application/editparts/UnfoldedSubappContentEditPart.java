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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.policies.AbstractContainerCreateInstanceDirectEditPolicy;
import org.eclipse.fordiac.ide.application.policies.SubAppContentLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;

public class UnfoldedSubappContentEditPart extends AbstractContainerContentEditPart {

	private final Adapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getPositionableElement_Position().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getConnection_RoutingData().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getFBNetwork_NetworkElements().equals(feature)) {
				getParent().layoutExpandedInterface();
			}
			super.notifyChanged(notification);
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(adapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(adapter);
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
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractContainerCreateInstanceDirectEditPolicy() {
			@Override
			protected Command getElementCreateCommand(final TypeEntry value, final Point refPoint) {
				return new ResizeGroupOrSubappCommand(getHost(), AbstractCreateFBNetworkElementCommand
						.createCreateCommand(value, getModel(), refPoint.x, refPoint.y));
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = new Figure() {
			Insets offsetInset = new Insets();

			@Override
			public void setBounds(final Rectangle rect) {
				final Rectangle copy = rect.getCopy();
				final Rectangle clientArea = getParent().getClientArea();
				final int maxAvailableHeight = clientArea.height - (rect.y - clientArea.y);
				// expand content to the available subapp size
				copy.height = Math.max(rect.height, maxAvailableHeight);

				final int lineHeight = (int) CoordinateConverter.INSTANCE.getLineHeight();
				// ensure that the content is aligned on the grid in x direction. We have to do
				// that here to compensate for different interface bar widths and also to
				// compensate changing interface bar widths.
				final int offset = lineHeight - copy.x % lineHeight;
				offsetInset.left = offset;
				offsetInset.right = offset;
				super.setBounds(copy);
			}

			@Override
			public Insets getInsets() {
				return offsetInset;
			}
		};

		figure.setOpaque(true);
		figure.setLayoutManager(new XYLayout());
		return figure;
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
