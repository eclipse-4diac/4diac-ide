/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 			Johannes Kepler University,
 * 							Primetals Technologies Austria GmbH
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
 *               - added containers for in out vars, cleaned container code
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.figures.FBTypeFigure;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.ControlListener;

public class FBTypeEditPart extends AbstractConnectableEditPart {

	private ControlListener controlListener;
	final List<AbstractContainerElement> containerChildren = new ArrayList<>(6);
	private DiagramFontChangeListener fontChangeListener;

	private final Adapter versionInfoAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			final Object feature = notification.getFeature();
			if (!notification.isTouch() && (LibraryElementPackage.eINSTANCE.getVersionInfo_Version().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getLibraryElement_VersionInfo().equals(feature))) {
				getFigure().updateVersionInfoLabel();
			}
		}

		@Override
		protected void addAdapter(final Notifier notifier) {
			// in addition to the root FBType we only want to get VersionInfo updates,
			// therefore we limit the addAdapter to VersionInfos
			if (notifier instanceof VersionInfo) {
				super.addAdapter(notifier);
			}
		}
	};

	@Override
	public void activate() {
		super.activate();
		getModel().eAdapters().add(versionInfoAdapter);
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
		getModel().eAdapters().remove(versionInfoAdapter);
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

	@Override
	protected IFigure createFigure() {
		return new FBTypeFigure(getModel());
	}

	@Override
	public void setSelected(final int value) {
		// nothing to be done here
	}

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		createChildrenContainer();
	}

	@Override
	protected List<AbstractContainerElement> getModelChildren() {
		return containerChildren;
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
		if (childEditPart.getModel() instanceof VarInOutInputContainer) {
			return getFigure().getVarInOutInputs();
		}
		if (childEditPart.getModel() instanceof VariableOutputContainer) {
			return getFigure().getDataOutputs();
		}
		if (childEditPart.getModel() instanceof VarInOutOutputContainer) {
			return getFigure().getVarInOutOutputs();
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

	private void createChildrenContainer() {
		containerChildren.add(new EventInputContainer(getModel()));
		containerChildren.add(new EventOutputContainer(getModel()));
		containerChildren.add(new VariableInputContainer(getModel()));
		containerChildren.add(new VarInOutInputContainer(getModel()));
		containerChildren.add(new VariableOutputContainer(getModel()));
		containerChildren.add(new VarInOutOutputContainer(getModel()));

		// adapter types cannot have plugs or sockets
		if (!(getModel() instanceof AdapterType)) {
			containerChildren.add(new SocketContainer(getModel()));
			containerChildren.add(new PlugContainer(getModel()));
		}
	}

}
