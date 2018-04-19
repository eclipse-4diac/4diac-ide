/*******************************************************************************
 * Copyright (c) 2008 - 2017  Profactor GbmH, TU Wien ACIN, AIT, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.draw2d.ITransparencyFigure;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.ColorManager;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.RGB;

public abstract class AbstractViewEditPart extends AbstractConnectableEditPart{
	private static final String ERROR_IN_CREATE_FIGURE = Messages.AbstractViewEditPart_ERROR_createFigure;
	protected DirectEditManager manager;
	
	private EContentAdapter adapter;

	private EContentAdapter getContentAdapter() {
		if(null == adapter) {
			adapter = createContentAdapter();
			Assert.isNotNull(adapter);
		}
		return adapter;
	}
	
	private final EContentAdapter iNamedElementContentAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			if(notification.getNotifier().equals(getINamedElement())){
				Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(
						feature)) {
					refreshName();
				}
				if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment()
						.equals(feature)) {
					refreshComment();
				}
	
				super.notifyChanged(notification);
			}
		}
	};

	protected void backgroundColorChanged(IFigure figure) {
		Color fordiacColor = getBackgroundColor();
		setColor(figure, fordiacColor);
	}
	
	protected Color getBackgroundColor(){
		return null;
	}

	protected void setColor(IFigure figure, Color fordiacColor) {
		org.eclipse.swt.graphics.Color newColor;
		if (fordiacColor != null) {
			newColor = ColorManager.getColor(new RGB(fordiacColor.getRed(),
					fordiacColor.getGreen(), fordiacColor.getBlue()));
		}
 		else {
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
		// TODO __gebenh implement refreshComment if necessary
	}

	/**
	 * Needs to return an EContentAdapter which will be registered on the model
	 * objects and gets informed if something change.
	 * 
	 * @return EContentAdapter the EContentAdapter of the derived class (must
	 *         not be null).
	 */
	protected abstract EContentAdapter createContentAdapter();

	/**
	 * If an View needs to be informed for changes in the PreferencePage (e.g.
	 * change of a Color) the derived class have to return an
	 * IPropertyChangeListener with implemented <code>propertyChange()</code>
	 * method if notification on changes are required otherwise it can return
	 * <code>null</code>.
	 * 
	 * @return IPropertyChangeListener the IPropertyChangeListener of the
	 *         derived class or <code>null</code> if derived class should not be
	 *         added to the listeners.
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
				Activator
						.getDefault()
						.getPreferenceStore()
						.addPropertyChangeListener(
								getPreferenceChangeListener());
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			if (getINamedElement() != null) {
				getINamedElement().eAdapters().remove(
						iNamedElementContentAdapter);
			}
			((Notifier) getModel()).eAdapters().remove(getContentAdapter());
			if (getPreferenceChangeListener() != null) {
				Activator
						.getDefault()
						.getPreferenceStore()
						.removePropertyChangeListener(
								getPreferenceChangeListener());
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
		} catch (IllegalArgumentException e) {
			Activator.getDefault().logError(ERROR_IN_CREATE_FIGURE, e);
		}
		return f;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
		// EditPolicy which allows the direct edit of the Instance Name
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new AbstractViewRenameEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT
				|| request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getNameLabel();
			manager = new LabelDirectEditManager(this, TextCellEditor.class,
					new NameCellEditorLocator(l), l);
		}
		return manager;
	}

	public void performDirectEdit() {
		getManager().show();
	}

	/**
	 * Performs direct edit setting the initial text to be the initialCharacter.
	 * 
	 * @param initialCharacter
	 *            the initial character
	 */
	public void performDirectEdit(final char initialCharacter) {
		if (getManager() instanceof LabelDirectEditManager) {
			((LabelDirectEditManager) getManager()).show(initialCharacter);
		} else {
			getManager().show();
		}
	}

	public void setTransparency(int value) {
		if (getFigure() instanceof ITransparencyFigure) {
			((ITransparencyFigure) getFigure()).setTransparency(value);
		}
	}
	
	
	
}
