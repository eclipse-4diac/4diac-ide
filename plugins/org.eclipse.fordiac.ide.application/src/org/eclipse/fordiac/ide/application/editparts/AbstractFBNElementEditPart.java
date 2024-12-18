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

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editors.NewInstanceDirectEditManager;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.application.policies.DeleteFBNElementEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.policies.DragHighlightEditPolicy;
import org.eclipse.fordiac.ide.gef.tools.ScrollingDragEditPartsTracker;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
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
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

/** This class implements an EditPart for a FunctionBlock. */
public abstract class AbstractFBNElementEditPart extends AbstractPositionableElementEditPart
		implements AnnotableGraphicalEditPart {

	protected static final class TypeDirectEditPolicy extends DirectEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			final Object value = request.getCellEditor().getValue();
			if (value instanceof final TypeEntry te) {
				return new UpdateFBTypeCommand(getHost().getModel(), te);
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			// as we want to change the type we will not show the new type
		}

		@Override
		public AbstractFBNElementEditPart getHost() {
			return (AbstractFBNElementEditPart) super.getHost();
		}
	}

	private ColorizableElement referencedDevice;

	private DiagramFontChangeListener fontChangeListener;

	protected AbstractFBNElementEditPart() {
	}

	private Adapter interfaceAdapter;

	private Adapter getInterfaceAdapter() {
		if (null == interfaceAdapter) {
			interfaceAdapter = createInterfaceAdapter();
			Assert.isNotNull(interfaceAdapter);
		}
		return interfaceAdapter;
	}

	protected Adapter createInterfaceAdapter() {
		return new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				if (!notification.isTouch()) {
					Display.getDefault().execute(() -> {
						refreshChildren();
						// this ensure that parameters are correctly updated when pins are added or
						// removed (e.g., errormarkerpins are deleted)
						getParent().refresh();
					});
				}
			}
		};
	}

	@Override
	public FBNetworkElementFigure getFigure() {
		return (FBNetworkElementFigure) super.getFigure();
	}

	private final Adapter colorChangeListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
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
		final ColorizableElement device = findDevice();
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
		if ((null != getModel()) && !getModel().getInterface().eAdapters().contains(getInterfaceAdapter())) {
			getModel().getInterface().eAdapters().add(getInterfaceAdapter());
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (referencedDevice != null) {
			referencedDevice.eAdapters().remove(colorChangeListener);
		}
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());

		if (null != getModel()) {
			getModel().getInterface().eAdapters().remove(getInterfaceAdapter());
		}
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	public boolean isOnlyThisOrNothingSelected() {
		final List<? extends EditPart> selection = getViewer().getSelectedEditParts();
		if (selection.size() > 1) {
			return false;
		}
		if (selection.size() == 1) {
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
	public <T> T getAdapter(final Class<T> key) {
		if (key == FBNetworkElement.class) {
			return key.cast(getModel());
		}
		return super.getAdapter(key);
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

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TypeDirectEditPolicy());

		// show cross mouse cursor on hover to indicate that an FB can be draged around
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new DragHighlightEditPolicy());
	}

	/** The listener. */
	private IPropertyChangeListener listener;

	private InstanceName instanceName;

	private HiddenPinIndicator inputPinIndicator;
	private HiddenPinIndicator outputPinIndicator;

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
				if (event.getProperty().equals(UIPreferenceConstants.P_EVENT_CONNECTOR_COLOR)
						|| UIPreferenceConstants.isDataConnectorProperty(event.getProperty())
						|| event.getProperty().equals(UIPreferenceConstants.P_ADAPTER_CONNECTOR_COLOR)) {
					getFigure().repaint();
				}
			};
		}
		return listener;
	}

	@Override
	protected void backgroundColorChanged(final IFigure figure) {
		Color color = null;
		if (getModel() != null) {
			final ColorizableElement dev = findDevice();
			if (dev != null) {
				color = LibraryElementFactory.eINSTANCE.createColor();
				color.setRed(dev.getColor().getRed());
				color.setGreen(dev.getColor().getGreen());
				color.setBlue(dev.getColor().getBlue());
			}
		}
		setColor(figure, color);
	}

	protected ColorizableElement findDevice() {
		Resource res = null;
		if ((null != getModel()) && getModel().isMapped()) {
			res = getModel().getResource();
		}
		return (null != res) ? res.getDevice() : null;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof final InterfaceEditPart interfaceEditPart) {
			getTargetFigure(interfaceEditPart).add(child, getInterfaceElementIndex(interfaceEditPart));
		} else if (childEditPart instanceof final HiddenPinIndicatorEditPart hiddenPinIndicatorEditPart) {
			addPinIndicatorFigure(hiddenPinIndicatorEditPart, child);
		} else {
			getFigure().add(child, new GridData(GridData.HORIZONTAL_ALIGN_CENTER), index);
		}
	}

	private void addPinIndicatorFigure(final HiddenPinIndicatorEditPart indicatorEditPart,
			final IFigure indicatorFigure) {
		if (((HiddenPinIndicator) indicatorEditPart.getModel()).isInput()) {
			getFigure().getPinIndicatorInput().add(indicatorFigure);
		} else {
			getFigure().getPinIndicatorOutput().add(indicatorFigure);
		}
	}

	private IFigure getTargetFigure(final InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isInput()) {
			return getTargetInputFigure(interfaceEditPart);
		}
		return getTargetOutputFigure(interfaceEditPart);
	}

	private IFigure getTargetInputFigure(final InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isEvent()) {
			return getFigure().getEventInputs();
		}
		if (interfaceEditPart.isAdapter()) {
			return getFigure().getSockets();
		}
		if (interfaceEditPart.isVariable()) {
			if (((VarDeclaration) interfaceEditPart.getModel()).isInOutVar()) {
				return getFigure().getVarInOutInputs();
			}
			return getFigure().getDataInputs();
		}
		if (interfaceEditPart instanceof ErrorMarkerInterfaceEditPart
				|| interfaceEditPart.getModel() instanceof ErrorMarkerInterface) {
			return getFigure().getErrorMarkerInput();
		}
		return getFigure();
	}

	private IFigure getTargetOutputFigure(final InterfaceEditPart interfaceEditPart) {
		if (interfaceEditPart.isEvent()) {
			return getFigure().getEventOutputs();
		}
		if (interfaceEditPart.isAdapter()) {
			return getFigure().getPlugs();
		}
		if (interfaceEditPart.isVariable()) {
			if (((VarDeclaration) interfaceEditPart.getModel()).isInOutVar()) {
				return getFigure().getVarInOutOutputs();
			}
			return getFigure().getDataOutputs();
		}
		if (interfaceEditPart instanceof ErrorMarkerInterfaceEditPart
				|| interfaceEditPart.getModel() instanceof ErrorMarkerInterface) {
			return getFigure().getErrorMarkerOutput();
		}
		return getFigure();
	}

	private int getInterfaceElementIndex(final InterfaceEditPart interfaceEditPart) {
		final InterfaceList interfaceList = getModel().getInterface();
		if (interfaceEditPart.isInput()) {
			return getInterfaceInputElementIndex(interfaceEditPart, interfaceList);
		}
		return getInterfaceOutputElementIndex(interfaceEditPart, interfaceList);

	}

	private static int getInterfaceInputElementIndex(final InterfaceEditPart interfaceEditPart,
			final InterfaceList interfaceList) {
		if (interfaceEditPart.isEvent()) {
			return interfaceList.getEventInputs().indexOf(interfaceEditPart.getModel());
		}
		if (interfaceEditPart.isAdapter()) {
			return interfaceList.getSockets().indexOf(interfaceEditPart.getModel());
		}
		if (interfaceEditPart.isVariable()) {
			final VarDeclaration varDecl = (VarDeclaration) interfaceEditPart.getModel();
			if (varDecl.isInOutVar()) {
				return interfaceList.getInOutVars().stream().filter(VarDeclaration::isVisible).toList()
						.indexOf(varDecl);
			}
			return interfaceList.getVisibleInputVars().indexOf(varDecl);
		}
		if (interfaceEditPart instanceof ErrorMarkerInterfaceEditPart) {
			return calcErrorMarkerINdex(interfaceEditPart, interfaceList);
		}
		return -1;

	}

	private static int getInterfaceOutputElementIndex(final InterfaceEditPart interfaceEditPart,
			final InterfaceList interfaceList) {
		if (interfaceEditPart.isEvent()) {
			return interfaceList.getEventOutputs().indexOf(interfaceEditPart.getModel());
		}
		if (interfaceEditPart.isAdapter()) {
			return interfaceList.getPlugs().indexOf(interfaceEditPart.getModel());
		}
		if (interfaceEditPart.isVariable()) {
			final VarDeclaration varDecl = (VarDeclaration) interfaceEditPart.getModel();
			if (varDecl.isInOutVar()) {
				return interfaceList.getOutMappedInOutVars().stream().filter(VarDeclaration::isVisible).toList()
						.indexOf(varDecl);
			}
			return interfaceList.getVisibleOutputVars().indexOf(varDecl);
		}
		if (interfaceEditPart instanceof ErrorMarkerInterfaceEditPart) {
			return calcErrorMarkerINdex(interfaceEditPart, interfaceList);
		}
		return -1;
	}

	private static int calcErrorMarkerINdex(final InterfaceEditPart interfaceEditPart,
			final InterfaceList interfaceList) {
		final int indexOf = interfaceList.getErrorMarker().indexOf(interfaceEditPart.getModel());
		return indexOf - (int) interfaceList.getErrorMarker().subList(0, indexOf).stream()
				.filter(e -> interfaceEditPart.isInput() ? !e.isIsInput() : e.isIsInput()).count();
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof final InterfaceEditPart interfaceEditPart) {
			if (getTargetFigure(interfaceEditPart).getChildren().contains(child)) {
				getTargetFigure(interfaceEditPart).remove(child);
			} else {
				child.getParent().remove(child);
			}
		} else if (childEditPart instanceof final HiddenPinIndicatorEditPart hiddenPinIndicatorEditPart) {
			removePinIndicatorFigure(hiddenPinIndicatorEditPart, child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private void removePinIndicatorFigure(final HiddenPinIndicatorEditPart indicatorEditPart,
			final IFigure indicatorFigure) {
		if (((HiddenPinIndicator) indicatorEditPart.getModel()).isInput()) {
			getFigure().getPinIndicatorInput().remove(indicatorFigure);
		} else {
			getFigure().getPinIndicatorOutput().remove(indicatorFigure);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> elements = new ArrayList<>();

		addBasicElements(elements);
		removeInvisibleInOutVars(elements);
		removeInvisibleInputOutputVars(elements);
		addPinIndicators(elements);

		return elements;
	}

	private void addBasicElements(final List<Object> elements) {
		elements.add(getInstanceName());
		elements.addAll(getModel().getInterface().getAllInterfaceElements());
	}

	private void removeInvisibleInOutVars(final List<Object> elements) {
		final List<VarDeclaration> inoutInRemovalList = getModel().getInterface().getInOutVars().stream()
				.filter(it -> !it.isVisible()).toList();
		final List<VarDeclaration> inoutOutRemovalList = getModel().getInterface().getOutMappedInOutVars().stream()
				.filter(it -> !it.isVisible()).toList();

		elements.removeAll(inoutInRemovalList);
		elements.removeAll(inoutOutRemovalList);
	}

	private void removeInvisibleInputOutputVars(final List<Object> elements) {
		final List<VarDeclaration> inputRemovalList = getModel().getInterface().getInputVars().stream()
				.filter(it -> !it.isVisible()).toList();
		final List<VarDeclaration> outputRemovalList = getModel().getInterface().getOutputVars().stream()
				.filter(it -> !it.isVisible()).toList();

		elements.removeAll(inputRemovalList);
		elements.removeAll(outputRemovalList);
	}

	private void addPinIndicators(final List<Object> elements) {
		final boolean hasInvisibleInputs = !getModel().getInterface().getInputVars().stream()
				.filter(it -> !it.isVisible()).toList().isEmpty()
				|| !getModel().getInterface().getInOutVars().stream().filter(it -> !it.isVisible()).toList().isEmpty();
		final boolean hasInvisibleOutputs = !getModel().getInterface().getOutputVars().stream()
				.filter(it -> !it.isVisible()).toList().isEmpty()
				|| !getModel().getInterface().getOutMappedInOutVars().stream().filter(it -> !it.isVisible()).toList()
						.isEmpty();

		elements.addAll(getPinIndicators(hasInvisibleInputs, hasInvisibleOutputs));
	}

	protected List<Object> getPinIndicators(final boolean input, final boolean output) {
		final List<Object> indicators = new ArrayList<>(2);
		if (input) {
			if (inputPinIndicator == null) {
				inputPinIndicator = new HiddenPinIndicator(getModel(), true);
			}
			indicators.add(inputPinIndicator);
		}
		if (output) {
			if (outputPinIndicator == null) {
				outputPinIndicator = new HiddenPinIndicator(getModel(), false);
			}
			indicators.add(outputPinIndicator);
		}
		return indicators;
	}

	protected InstanceName getInstanceName() {
		if (instanceName == null) {
			instanceName = new InstanceName(getModel());
		}
		return instanceName;
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

			if (request.getType() == RequestConstants.REQ_OPEN) {
				selectPropertySheet();
			}
			// forward direct edit request to instance name
			getChildren().stream().filter(InstanceNameEditPart.class::isInstance)
					.forEach(e -> ((InstanceNameEditPart) e).performRequest(request));
			return;
		}
		super.performRequest(request);
	}

	public void selectPropertySheet() {
		if (null != getViewer()) {
			getViewer().flush();
			EditorUtils.refreshPropertySheetWithSelection(EditorUtils.getCurrentActiveEditor(), getViewer(), this);
		}
	}

	@Override
	public void performDirectEdit() {
		final NewInstanceDirectEditManager directEditManager = createDirectEditManager();
		directEditManager.updateRefPosition(getRefPoint());
		directEditManager.show(getModel().getTypeName());
	}

	private Point getRefPoint() {
		final org.eclipse.draw2d.geometry.Point typeLabelTopLeft = getFigure().getTypeLabel().getBounds().getTopLeft()
				.scale(getZoomManager().getZoom());
		final FigureCanvas viewerControl = (FigureCanvas) getViewer().getControl();
		final org.eclipse.draw2d.geometry.Point location = viewerControl.getViewport().getViewLocation();
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
	protected NewInstanceDirectEditManager createDirectEditManager() {
		return new NewInstanceDirectEditManager(this, getTypeLibrary(), true);
	}

	private TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(getModel());
	}

	@Override
	public void setTransparency(final int value) {
		for (final GraphicalEditPart ep : getChildren()) {
			if (ep instanceof final AbstractViewEditPart avep) {
				avep.setTransparency(value);
			}
		}
		super.setTransparency(value);
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		return new ScrollingDragEditPartsTracker(this);
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure(), getModel(), event,
				FordiacAnnotationUtil::showOnTarget);
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure().getTypeLabel(), getModel(), event,
				FordiacAnnotationUtil::showOnTargetType);
		getChildren().stream().filter(InstanceNameEditPart.class::isInstance).map(InstanceNameEditPart.class::cast)
				.forEach(child -> child.updateAnnotations(event));
	}
}