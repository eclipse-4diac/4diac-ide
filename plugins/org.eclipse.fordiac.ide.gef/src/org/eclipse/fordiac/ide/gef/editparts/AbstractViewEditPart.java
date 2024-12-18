/*******************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GbmH, TU Wien ACIN, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.draw2d.ITransparencyFigure;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.RGB;

public abstract class AbstractViewEditPart extends AbstractConnectableEditPart {
	private static final String ERROR_IN_CREATE_FIGURE = Messages.AbstractViewEditPart_ERROR_createFigure;

	private Adapter adapter;

	private Adapter getContentAdapter() {
		if (null == adapter) {
			adapter = createContentAdapter();
			Assert.isNotNull(adapter);
		}
		return adapter;
	}

	private final Adapter iNamedElementContentAdapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			if (notification.getNotifier().equals(getINamedElement())) {
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) {
					refreshName();
				}
				if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
					refreshComment();
				}

				super.notifyChanged(notification);
			}
		}
	};

	protected void backgroundColorChanged(final IFigure figure) {
		setColor(figure, null);
	}

	@SuppressWarnings("static-method")
	protected void setColor(final IFigure figure, final Color fordiacColor) {
		org.eclipse.swt.graphics.Color newColor;
		if (fordiacColor != null) {
			newColor = ColorManager
					.getColor(new RGB(fordiacColor.getRed(), fordiacColor.getGreen(), fordiacColor.getBlue()));
		} else {
			newColor = null;
		}
		figure.setBackgroundColor(newColor);
	}

	public abstract INamedElement getINamedElement();

	public abstract Label getNameLabel();

	protected void refreshName() {
		getNameLabel().setText(getINamedElement().getName());
	}

	protected void refreshComment() {
		// The default edit part does not show any comment, override in subclasses if
		// comments are shown and require updates
	}

	/**
	 * Needs to return an Adapter which will be registered on the model objects and
	 * gets informed if something change.
	 *
	 * @return Adapter the Adapter of the derived class (must not be null).
	 */
	protected abstract Adapter createContentAdapter();

	/**
	 * If an View needs to be informed for changes in the PreferencePage (e.g.
	 * change of a Color) the derived class have to return an
	 * IPropertyChangeListener with implemented <code>propertyChange()</code> method
	 * if notification on changes are required otherwise it can return
	 * <code>null</code>.
	 *
	 * @return IPropertyChangeListener the IPropertyChangeListener of the derived
	 *         class or <code>null</code> if derived class should not be added to
	 *         the listeners.
	 */
	protected abstract IPropertyChangeListener getPreferenceChangeListener();

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			if (getINamedElement() != null) {
				getINamedElement().eAdapters().add(iNamedElementContentAdapter);
			}
			((Notifier) getModel()).eAdapters().add(getContentAdapter());
			if (getPreferenceChangeListener() != null) {
				GefPreferenceConstants.STORE.addPropertyChangeListener(getPreferenceChangeListener());
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			if (getINamedElement() != null) {
				getINamedElement().eAdapters().remove(iNamedElementContentAdapter);
			}
			((Notifier) getModel()).eAdapters().remove(getContentAdapter());
			if (getPreferenceChangeListener() != null) {
				GefPreferenceConstants.STORE.removePropertyChangeListener(getPreferenceChangeListener());
			}
		}
	}

	protected abstract IFigure createFigureForModel();

	/**
	 * Creates the <code>Figure</code> to be used as this part's <i>visuals</i>.
	 *
	 * @return a figure
	 *
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 * @see #createFigureForModel()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure f = null;
		try {
			f = createFigureForModel();
			if (f != null) {
				backgroundColorChanged(f);
			}
		} catch (final IllegalArgumentException e) {
			FordiacLogHelper.logError(ERROR_IN_CREATE_FIGURE, e);
		}
		return f;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
		// EditPolicy which allows the direct edit of the Instance Name
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractViewRenameEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	protected DirectEditManager createDirectEditManager() {
		return new LabelDirectEditManager(this, getNameLabel());
	}

	protected void performDirectEdit() {
		createDirectEditManager().show();
	}

	public void setTransparency(final int value) {
		if (getFigure() instanceof final ITransparencyFigure transparancyFigure) {
			transparancyFigure.setTransparency(value);
		}
	}

}
