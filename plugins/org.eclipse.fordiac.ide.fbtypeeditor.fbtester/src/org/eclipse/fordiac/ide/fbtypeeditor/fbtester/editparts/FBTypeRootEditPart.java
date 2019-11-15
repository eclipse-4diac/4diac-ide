/*******************************************************************************
 * Copyright (c) 2012, 2014, 2016 Profactor GmbH, TU Wien ACIN fortiss GmbH,
 * 				 2018 Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDiagramEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;

public class FBTypeRootEditPart extends AbstractDiagramEditPart {
	private EContentAdapter adapter;

	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		// Create the static router for the connection layer
		ConnectionLayer connLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		connLayer.setConnectionRouter(new ShortestPathConnectionRouter(figure));
		return figure;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((Notifier) getCastedFBTypeModel()).eAdapters().add(getContentAdapter());

		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((Notifier) getCastedFBTypeModel()).eAdapters().remove(getContentAdapter());
		}
	}

	public EContentAdapter getContentAdapter() {
		if (adapter == null) {
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(final Notification notification) {
					int type = notification.getEventType();
					switch (type) {
					case Notification.ADD:
					case Notification.ADD_MANY:
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								refreshChildren();
							}
						});
						break;
					case Notification.SET:
						break;
					}
				}
			};
		}
		return adapter;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());

	}

	public FBType getCastedFBTypeModel() {
		return getEditorInput().getContent();
	}

	private FBTypeEditorInput getEditorInput() {
		return (FBTypeEditorInput) getModel();
	}

	private PaletteEntry getPaletteEntry() {
		return getEditorInput().getPaletteEntry();
	}

	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<>();
		FB fB = LibraryElementFactory.eINSTANCE.createFB();
		fB.setPaletteEntry(getPaletteEntry());
		fB.setInterface(EcoreUtil.copy(getCastedFBTypeModel().getInterfaceList()));
		fB.setName(getCastedFBTypeModel().getName());
		fB.setX(10);
		fB.setY(10);

		createValues(fB);
		children.add(fB);

		for (IInterfaceElement elem : fB.getInterface().getAllInterfaceElements()) {
			children.add(createTestElement(fB, elem));

		}

		return children;
	}

	private static TestElement createTestElement(FB fb, IInterfaceElement interfaceElement) {

		TestElement element = new TestElement();
		element.setFb(fb);

		if (interfaceElement == null) {
			// TODO ExceptionHandling
		} else {
			element.setElement(interfaceElement);
		}
		return element;
	}

	protected static void createValues(FB fB) {
		fB.getInterface().getInputVars().forEach(var -> var.setValue(LibraryElementFactory.eINSTANCE.createValue()));
	}
}
