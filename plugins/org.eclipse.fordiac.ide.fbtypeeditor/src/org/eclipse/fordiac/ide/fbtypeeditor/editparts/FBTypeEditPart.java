/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added diagram font preference
 *               - extracted common FB shape for interface and fbn editors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.figures.FBTypeFigure;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Display;

public class FBTypeEditPart extends AbstractConnectableEditPart {

	private ControlListener controlListener;
	private EventInputContainer eic;
	private EventOutputContainer eoc;
	private VariableInputContainer vic;
	private SocketContainer socketcont;
	private VariableOutputContainer voc;
	private PlugContainer plugcont;
	private DiagramFontChangeListener fontChangeListener;

	private final Adapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (Notification.REMOVING_ADAPTER != notification.getEventType()) {
				final Object feature = notification.getFeature();
				if ((LibraryElementPackage.eINSTANCE.getVersionInfo().equals(feature))
						|| (LibraryElementPackage.eINSTANCE.getVersionInfo_Version().equals(feature))) {
					getFigure().updateVersionInfoLabel();
				}

				Display.getDefault().syncExec(() -> {
					if ((null != getParent()) && (null != getFigure()) && (getFigure().isShowing())) {
						refresh();
					}
				});
			}
		}
	};

	@Override
	public void activate() {
		super.activate();
		getModel().eAdapters().add(adapter);
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
		// position the FB at 0,0
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), new Rectangle(0, 0, -1, -1));
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (controlListener != null) {
			getParent().getViewer().getControl().removeControlListener(controlListener);
		}
		getModel().eAdapters().remove(adapter);
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());

	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	@Override
	public FBType getModel() {
		return (FBType) super.getModel();
	}

	FBTypeEditPart() {
		super();
	}

	@Override
	protected IFigure createFigure() {
		return new FBTypeFigure(getModel());
	}

	@Override
	public void setSelected(final int value) {
		// nothing to be done here
	}

	@Override
	protected List<Object> getModelChildren() {
		if (null == eic) {
			eic = new EventInputContainer(getModel());
		}
		if (null == eoc) {
			eoc = new EventOutputContainer(getModel());
		}
		if (null == vic) {
			vic = new VariableInputContainer(getModel());
		}
		if (null == socketcont) {
			socketcont = new SocketContainer(getModel());
		}
		if (null == voc) {
			voc = new VariableOutputContainer(getModel());
		}
		if (null == plugcont) {
			plugcont = new PlugContainer(getModel());
		}
		final ArrayList<Object> temp = new ArrayList<>(6);
		temp.add(eic);
		temp.add(eoc);
		temp.add(vic);
		if (!(getModel() instanceof AdapterFBType)) {
			// adaptertypes cannot have sockets
			temp.add(socketcont);
		}
		temp.add(voc);
		if (!(getModel() instanceof AdapterFBType)) {
			// adaptertypes cannot have plugs
			temp.add(plugcont);
		}
		return temp;
	}

	@Override
	public FBTypeFigure getFigure() {
		return (FBTypeFigure) super.getFigure();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceContainerEditPart) {
			final Figure cont = getContainer(childEditPart);
			if (null != cont) {
				cont.add(child);
			}

		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	private Figure getContainer(final EditPart childEditPart) {
		if (childEditPart.getModel() instanceof EventInputContainer) {
			return getFigure().getEventInputs();
		}
		if (childEditPart.getModel() instanceof EventOutputContainer) {
			return getFigure().getEventOutputs();
		}
		if (childEditPart.getModel() instanceof VariableInputContainer) {
			return getFigure().getDataInputs();
		}
		if (childEditPart.getModel() instanceof VariableOutputContainer) {
			return getFigure().getDataOutputs();
		}
		if (childEditPart.getModel() instanceof SocketContainer) {
			return getFigure().getSockets();
		}
		if (childEditPart.getModel() instanceof PlugContainer) {
			return getFigure().getPlugs();
		}
		return null;
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceContainerEditPart) {
			final Figure cont = getContainer(childEditPart);
			if (null != cont) {
				cont.remove(child);
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		getFigure().getTypeLabel().setText(getModel().getName());
	}

}
