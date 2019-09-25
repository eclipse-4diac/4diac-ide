/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University Linz
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
 *     - changed section border to a simple line removing issues on Linux and
 *       modernizing the look
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteDeviceEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeviceViewLayoutEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.SegmentNodeEditPolicy;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

public class DeviceEditPart extends AbstractPositionableElementEditPart implements NodeEditPart {
	/** necessary that the gradient pattern can be scaled accordingly */
	private ZoomManager zoomManager;
	private ResourceContainer resContainer;
	private DiagramFontChangeListener fontChangeListener;

	DeviceEditPart(ZoomManager zoomManager) {
		super();
		setConnectable(true);
		this.zoomManager = zoomManager;
	}

	@Override
	public Device getModel() {
		return (Device) super.getModel();
	}

	@Override
	public void activate() {
		super.activate();
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	@Override
	protected EContentAdapter createContentAdapter() {
		return new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getColorizableElement_Color().equals(feature)) {
					backgroundColorChanged(getFigure());
				} else {
					super.notifyChanged(notification);
					refreshChildren();
					refreshTargetConnections();
				}
			}
		};
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
	public DeviceFigure getFigure() {
		return (DeviceFigure) super.getFigure();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof DeviceInterfaceEditPart) {
			getFigure().getDataInputs().add(child);
		} else if (childEditPart instanceof ResourceContainerEditPart) {
			getFigure().getContentPane().add(child);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof DeviceInterfaceEditPart) {
			getFigure().getDataInputs().remove(child);
		} else if (childEditPart instanceof ResourceContainerEditPart) {
			getFigure().getContentPane().remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	public void setModel(Object model) {
		super.setModel(model);
		resContainer = new ResourceContainer((Device) model);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelChildren() {
		ArrayList elements = new ArrayList();
		elements.addAll(getModel().getVarDeclarations());
		elements.add(resContainer);
		return elements;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DeviceViewLayoutEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteDeviceEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new SegmentNodeEditPolicy());
	}

	@Override
	public Label getNameLabel() {
		return getFigure().getName();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected IFigure createFigureForModel() {
		return new DeviceFigure();
	}

	@Override
	protected void backgroundColorChanged(IFigure figure) {
		// TODO model refactoring - default value for colors if not persisted
		org.eclipse.fordiac.ide.model.libraryElement.Color fordiacColor = getModel().getColor();
		if (fordiacColor == null) {
			fordiacColor = ColorHelper.createRandomColor();
			getModel().setColor(fordiacColor);
		}
		setColor(figure, fordiacColor);

	}

	private final class DeviceConnectionAnchor extends ChopboxAnchor {
		DeviceConnectionAnchor(IFigure owner) {
			super(owner);
		}

		@Override
		protected Rectangle getBox() {
			// calculate a bounding box which consist of device main part and
			// name label
			Rectangle main = getFigure().getConnectionReferenceFigure().getBounds().getCopy();
			Rectangle top = getFigure().getName().getBounds();

			main.setHeight(main.height + top.height());
			main.y = top.y();

			return main;
		}
	}

	private class DeviceFigure extends Shape implements InteractionStyleFigure, IFontUpdateListener {
		private final Label instanceNameLabel = new Label();
		private Label typeLabel;
		private Figure dataInputs = new Figure();
		private Figure contentPane;

		@Override
		public int getIntersectionStyle(Point location) {
			if (instanceNameLabel.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_DRAG; // move/drag
			}
			return InteractionStyleFigure.REGION_CONNECTION; // connection
		}

		private RoundedRectangle bottom = new RoundedRectangle() {
			@Override
			protected void fillShape(Graphics graphics) {
				Display display = Display.getCurrent();
				Rectangle boundingRect = getBounds().getCopy();
				boundingRect.scale(zoomManager.getZoom());
				Point topLeft = boundingRect.getTopLeft();
				Point bottomRight = boundingRect.getBottomRight();
				Color first = ColorHelper.lighter(getBackgroundColor());
				Pattern pattern = new Pattern(display, topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, first,
						getBackgroundColor());
				graphics.setBackgroundPattern(pattern);
				graphics.fillRoundRectangle(getBounds(), getCornerDimensions().width, getCornerDimensions().height);
				graphics.setBackgroundPattern(null);
				pattern.dispose();
				first.dispose();
			}
		};

		public DeviceFigure() {
			setBackgroundColor(ColorConstants.white);
			this.setFillXOR(true);

			setLayoutManager(new ToolbarLayout());

			createInstanceNameLabel(this);

			IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
			int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
			bottom.setCornerDimensions(new Dimension(cornerDim, cornerDim));
			ToolbarLayout bottomLayout = new ToolbarLayout();
			bottomLayout.setStretchMinorAxis(true);
			bottom.setLayoutManager(bottomLayout);
			bottom.setOutline(false);
			add(bottom);

			createDeviceInfoSection(bottom);

			ToolbarLayout bottomInputsLayout = new ToolbarLayout(false);
			bottomInputsLayout.setStretchMinorAxis(true);
			dataInputs.setLayoutManager(bottomInputsLayout);
			dataInputs.setOpaque(false);
			dataInputs.setBorder(new AdvancedLineBorder(PositionConstants.SOUTH));
			bottom.add(dataInputs);
			createContentPane(bottom);
			setInstanceAndTypeLabelFonts();
		}

		private void createInstanceNameLabel(Figure parent) {
			instanceNameLabel.setText(getINamedElement().getName());
			instanceNameLabel.setTextAlignment(PositionConstants.CENTER);
			parent.add(instanceNameLabel);
		}

		private void createDeviceInfoSection(Figure parent) {
			ToolbarLayout deviceInfoLayout = new ToolbarLayout();
			deviceInfoLayout.setStretchMinorAxis(true);

			Figure deviceInfo = new Figure();
			deviceInfo.setLayoutManager(deviceInfoLayout);
			LibraryElement type = getModel().getType();
			String typeName = (null != type) ? type.getName() : "Type not set!";
			typeLabel = new Label(typeName);
			deviceInfo.add(typeLabel);
			typeLabel.setTextAlignment(PositionConstants.CENTER);
			typeLabel.setBorder(new MarginBorder(0, 0, 10, 0));

			parent.add(deviceInfo);

			deviceInfo.setBorder(new AdvancedLineBorder(PositionConstants.SOUTH));
		}

		private void createContentPane(RoundedRectangle container) {
			contentPane = new Figure();
			contentPane.setLayoutManager(new ToolbarLayout());
			container.add(contentPane);
		}

		public Label getName() {
			return instanceNameLabel;
		}

		public Figure getConnectionReferenceFigure() {
			return bottom;
		}

		@Override
		protected void fillShape(final Graphics graphics) {
			// Nothing to do here right now
		}

		@Override
		protected void outlineShape(final Graphics graphics) {
			// Nothing to do here right now
		}

		public Figure getDataInputs() {
			return dataInputs;
		}

		public Figure getContentPane() {
			return contentPane;
		}

		@Override
		public void updateFonts() {
			setInstanceAndTypeLabelFonts();
			invalidateTree();
			revalidate();
		}

		public void setInstanceAndTypeLabelFonts() {
			instanceNameLabel.setFont(JFaceResources.getFontRegistry().getBold(PreferenceConstants.DIAGRAM_FONT));
			typeLabel.setFont(JFaceResources.getFontRegistry().getItalic(PreferenceConstants.DIAGRAM_FONT));
		}
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new DeviceConnectionAnchor(getFigure().getConnectionReferenceFigure());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelTargetConnections() {
		List<Object> connections = new ArrayList<>();
		connections.addAll(getModel().getInConnections());
		connections.addAll(super.getModelTargetConnections());
		return connections;
	}
}
