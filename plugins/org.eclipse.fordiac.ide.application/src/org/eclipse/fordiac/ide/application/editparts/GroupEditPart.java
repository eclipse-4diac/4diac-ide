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
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.jface.util.IPropertyChangeListener;

public class GroupEditPart extends AbstractPositionableElementEditPart {
	private InstanceComment groupComment;
	private GroupContentNetwork groupContents;

	@Override
	public Group getModel() {
		return (Group) super.getModel();
	}

	@Override
	protected PositionableElement getPositionableElement() {
		return getModel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		// we don't have a name label in groups
		return null;
	}

	@Override
	protected void refreshName() {
		// as we don't have a name label we don't want to do anything here
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getGroup_GroupElements().equals(feature)) {
					// group elements changed tell the parent that fbs may now be at different places
					getParent().refresh();
				}
			}
		};
	}

	@Override
	protected IPropertyChangeListener getPreferenceChangeListener() {
		// for now we don't need a preference change listener
		return null;
	}

	@Override
	protected IFigure createFigureForModel() {
		final RoundedRectangle mainFigure = new RoundedRectangle() {
			@Override
			public void paintFigure(final Graphics graphics) {
				// paint figure of shape does not check for background borders, needed for drop shadow
				if (getBorder() instanceof AbstractBackground) {
					((AbstractBackground) getBorder()).paintBackground(this, graphics, NO_INSETS);
				}
				super.paintFigure(graphics);
			}
		};
		mainFigure.setOutline(false);
		mainFigure.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		mainFigure.setFillXOR(false);
		mainFigure.setOpaque(false);
		mainFigure.setBorder(new RoundedRectangleShadowBorder());
		mainFigure.setLayoutManager(new ToolbarLayout(false));
		return mainFigure;
	}

	@Override
	protected List getModelChildren() {
		final List<Object> children = new ArrayList<>(2);
		children.add(getInstanceComment());
		children.add(getSubappContents());
		return children;
	}

	private InstanceComment getInstanceComment() {
		if (null == groupComment) {
			groupComment = new InstanceComment(getModel());
		}
		return groupComment;
	}

	private GroupContentNetwork getSubappContents() {
		if (null == groupContents) {
			groupContents = new GroupContentNetwork(getModel());
		}
		return groupContents;
	}

}
