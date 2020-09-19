/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 		 2019 Johannes Kepler University Linz
 *               2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added diagram font preference
 *   			 - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - added separate colors for different data types
 *   Bianca Wiesmayr, Alois Zoitl - forward direct editing request to instance name
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.editors.NewInstanceDirectEditManager;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.application.policies.DeleteFBNElementEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.policies.DragHighlightEditPolicy;
import org.eclipse.fordiac.ide.gef.tools.ScrollingDragEditPartsTracker;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Point;

/**
 * This class implements an EditPart for a FunctionBlock.
 */
public abstract class AbstractFBNElementEditPart extends AbstractPositionableElementEditPart {

	private Device referencedDevice;

	private DiagramFontChangeListener fontChangeListener;

	public AbstractFBNElementEditPart() {
		super();
	}

	@Override
	public FBNetworkElementFigure getFigure() {
		return (FBNetworkElementFigure) super.getFigure();
	}

	private Adapter colorChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getColorizableElement_Color()) {
				backgroundColorChanged(getFigure());
			}
		}
	};

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				refreshToolTip(); // TODO add here checks that better define when the tooltip should be refreshed
				if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getFBNetworkElement_Mapping()) {
					updateDeviceListener();
				}
			}

		};
	}

	protected void updateDeviceListener() {
		Device device = findDevice();
		if (device != referencedDevice) {
			if (referencedDevice != null) {
				referencedDevice.eAdapters().remove(colorChangeListener);
			}
			referencedDevice = device;
			if (referencedDevice != null) {
				referencedDevice.eAdapters().add(colorChangeListener);
			}
			backgroundColorChanged(getFigure());
		}
	}

	@Override
	public void activate() {
		super.activate();
		updateDeviceListener();
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (referencedDevice != null) {
			referencedDevice.eAdapters().remove(colorChangeListener);
		}
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	public boolean isOnlyThisOrNothingSelected() {
		@SuppressWarnings("unchecked")
		List<EditPart> selection = getViewer().getSelectedEditParts();
		if (selection.size() > 1) {
			return false;
		} else if (selection.size() == 1) {
			return selection.get(0) == this;
		}
		return true;
	}

	protected void refreshToolTip() {
		getFigure().refreshToolTips();
	}

	@Override
	protected void refreshComment() {
		refreshToolTip();
	}

	@Override
	public FBNetworkElement getModel() {
		return (FBNetworkElement) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a FB
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteFBNElementEditPolicy());

		// Highlight In and Outconnections of the selected fb, allow alignment of FBs
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new FBNElementSelectionPolicy());

		// FBNetwork elements renaming is done in a dedicated editpart
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {

			@Override
			protected Command getDirectEditCommand(DirectEditRequest request) {
				Object value = request.getCellEditor().getValue();
				if (value instanceof PaletteEntry) {
					return new UpdateFBTypeCommand(getModel(), (PaletteEntry) value);
				}
				return null;
			}

			@Override
			protected void showCurrentEditValue(DirectEditRequest request) {
				// as we want to change the type we will not show the new type
			}

		});

		// show cross mouse cursor on hover to indicate that an FB can be draged around
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new DragHighlightEditPolicy());
	}

	/** The listener. */
	private IPropertyChangeListener listener;

	/**
	 * Returns an <code>IPropertyChangeListener</code> with implemented
	 * <code>propertyChange()</code>. e.g. a color change event repaints the
	 * FunctionBlock.
	 *
	 * @return the preference change listener
	 */
	@Override
	public org.eclipse.jface.util.IPropertyChangeListener getPreferenceChangeListener() {
		if (null == listener) {
			listener = event -> {
				if (event.getProperty().equals(PreferenceConstants.P_EVENT_CONNECTOR_COLOR)
						|| PreferenceConstants.isDataConnectorProperty(event.getProperty())
						|| event.getProperty().equals(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR)) {
					getFigure().repaint();
				}
			};
		}
		return listener;
	}

	@Override
	protected void backgroundColorChanged(IFigure figure) {
		Color color = null;
		if (getModel() != null) {
			Device dev = findDevice();
			if (dev != null) {
				color = LibraryElementFactory.eINSTANCE.createColor();
				color.setRed(dev.getColor().getRed());
				color.setGreen(dev.getColor().getGreen());
				color.setBlue(dev.getColor().getBlue());
			}
		}
		setColor(figure, color);
	}

	private Device findDevice() {
		Resource res = null;
		if ((null != getModel()) && getModel().isMapped()) {
			res = getModel().getResource();
		}
		return (null != res) ? res.getDevice() : null;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			getTargetFigure(interfaceEditPart).add(child, getInterfaceElementIndex(interfaceEditPart));
		} else {
			getFigure().add(child, new GridData(GridData.HORIZONTAL_ALIGN_CENTER), index);
		}
	}

	private IFigure getTargetFigure(InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isInput()) {
			if (interfaceEditPart.isEvent()) {
				return getFigure().getEventInputs();
			}
			if (interfaceEditPart.isAdapter()) {
				return getFigure().getSockets();
			}
			if (interfaceEditPart.isVariable()) {
				return getFigure().getDataInputs();
			}
		} else {
			if (interfaceEditPart.isEvent()) {
				return getFigure().getEventOutputs();
			}
			if (interfaceEditPart.isAdapter()) {
				return getFigure().getPlugs();
			}
			if (interfaceEditPart.isVariable()) {
				return getFigure().getDataOutputs();
			}
		}
		return getFigure();
	}

	private int getInterfaceElementIndex(InterfaceEditPart interfaceEditPart) {
		InterfaceList interfaceList = getModel().getInterface();
		if (interfaceEditPart.isInput()) {
			if (interfaceEditPart.isEvent()) {
				return interfaceList.getEventInputs().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isAdapter()) {
				return interfaceList.getSockets().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isVariable()) {
				return interfaceList.getInputVars().indexOf(interfaceEditPart.getModel());
			}
		} else {
			if (interfaceEditPart.isEvent()) {
				return interfaceList.getEventOutputs().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isAdapter()) {
				return interfaceList.getPlugs().indexOf(interfaceEditPart.getModel());
			}
			if (interfaceEditPart.isVariable()) {
				return interfaceList.getOutputVars().indexOf(interfaceEditPart.getModel());
			}
		}
		return -1;
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			getTargetFigure(interfaceEditPart).remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		List<Object> elements = new ArrayList<>();
		elements.add(new InstanceName(getModel()));
		elements.addAll(getModel().getInterface().getAllInterfaceElements());
		return elements;
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	protected PositionableElement getPositionableElement() {
		return getModel();
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) {
			// forward direct edit request to instance name
			@SuppressWarnings("unchecked")
			List<EditPart> children = getChildren();
			children.stream().filter(e -> e instanceof InstanceNameEditPart)
					.forEach(e -> ((InstanceNameEditPart) e).performRequest(request));
			return;
		}
		super.performRequest(request);
	}

	@Override
	public void performDirectEdit() {
		NewInstanceDirectEditManager directEditManager = getManager();
		directEditManager.updateRefPosition(getRefPoint());
		directEditManager.show(getModel().getTypeName());
	}

	private Point getRefPoint() {
		org.eclipse.draw2d.geometry.Point typeLabelTopLeft = getFigure().getTypeLabel().getBounds().getTopLeft()
				.scale(getZoomManager().getZoom());
		FigureCanvas viewerControl = (FigureCanvas) getViewer().getControl();
		org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
		return new Point(typeLabelTopLeft.x - location.x, typeLabelTopLeft.y - location.y);
	}

	private ZoomManager getZoomManager() {
		return ((ZoomScalableFreeformRootEditPart) getRoot()).getZoomManager();
	}

	@Override
	public Label getNameLabel() {
		return null;
	}

	@Override
	protected void refreshName() {
		// don't do anyting here
	}

	@Override
	protected NewInstanceDirectEditManager getManager() {
		return (NewInstanceDirectEditManager) super.getManager();
	}

	@Override
	protected DirectEditManager createDirectEditManager() {
		return new NewInstanceDirectEditManager(this, getPalette(), true);
	}

	private Palette getPalette() {
		if (getModel().eContainer().eContainer() instanceof FBType) {
			return ((FBType) getModel().eContainer().eContainer()).getPaletteEntry().getPalette();
		}
		// we are in an app or supp
		return getModel().getFbNetwork().getAutomationSystem().getPalette();
	}

	@Override
	public void setTransparency(int value) {
		for (Object ep : getChildren()) {
			if (ep instanceof AbstractViewEditPart) {
				((AbstractViewEditPart) ep).setTransparency(value);
			}
		}
		super.setTransparency(value);
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		return new ScrollingDragEditPartsTracker(this);
	}

}
