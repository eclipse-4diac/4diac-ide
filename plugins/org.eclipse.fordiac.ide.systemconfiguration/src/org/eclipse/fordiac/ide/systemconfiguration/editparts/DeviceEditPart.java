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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteDeviceEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeviceViewLayoutEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.SegmentNodeEditPolicy;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Color;

public class DeviceEditPart extends AbstractPositionableElementEditPart implements NodeEditPart {
	private ResourceContainer resContainer;
	private DiagramFontChangeListener fontChangeListener;

	DeviceEditPart() {
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
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();
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
	public void refresh() {
		super.refresh();
		getFigure().getName().setText(getModel().getName());
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
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
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof DeviceInterfaceEditPart) {
			getFigure().getDataInputs().remove(child);
		} else if (childEditPart instanceof ResourceContainerEditPart) {
			getFigure().getContentPane().remove(child);
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		resContainer = new ResourceContainer((Device) model);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelChildren() {
		final ArrayList elements = new ArrayList();
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
	protected void backgroundColorChanged(final IFigure figure) {
		// TODO model refactoring - default value for colors if not persisted
		org.eclipse.fordiac.ide.model.libraryElement.Color fordiacColor = getModel().getColor();
		if (fordiacColor == null) {
			fordiacColor = ColorHelper.createRandomColor();
			getModel().setColor(fordiacColor);
		}
		setColor(figure, fordiacColor);
	}

	@Override
	public boolean isConnectable() {
		return true;
	}

	private final class DeviceConnectionAnchor extends ChopboxAnchor {
		DeviceConnectionAnchor(final IFigure owner) {
			super(owner);
		}

		@Override
		protected Rectangle getBox() {
			// calculate a bounding box which consist of device main part and
			// name label
			final Rectangle main = getFigure().getConnectionReferenceFigure().getBounds().getCopy();
			final Rectangle top = getFigure().getName().getBounds();

			main.setHeight(main.height + top.height());
			main.y = top.y();

			return main;
		}
	}

	private class DeviceFigure extends Shape implements InteractionStyleFigure, IFontUpdateListener {
		private static final boolean DEVICE_HAS_OUTER_BORDER = false;

		private final Label instanceNameLabel = new Label();
		private Label typeLabel;
		private final Figure dataInputs = new Figure();
		private Figure contentPane;
		private AdvancedLineBorder upperSeparator;
		private final AdvancedLineBorder lowerSeparator;

		@Override
		public int getIntersectionStyle(final Point location) {
			if (instanceNameLabel.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_DRAG; // move/drag
			}
			return InteractionStyleFigure.REGION_CONNECTION; // connection
		}

		private final RoundedRectangle deviceRectangle = new BorderedRoundedRectangle();

		public DeviceFigure() {
			setLayoutManager(new ToolbarLayout());
			createInstanceNameLabel(this);

			deviceRectangle.setCornerDimensions(
					new Dimension(GefPreferenceConstants.CORNER_DIM, GefPreferenceConstants.CORNER_DIM));
			final ToolbarLayout bottomLayout = new ToolbarLayout();
			bottomLayout.setStretchMinorAxis(true);
			deviceRectangle.setLayoutManager(bottomLayout);
			deviceRectangle.setOutline(DEVICE_HAS_OUTER_BORDER);
			deviceRectangle.setBorder(new RoundedRectangleShadowBorder());
			add(deviceRectangle);

			createDeviceInfoSection(deviceRectangle);

			final ToolbarLayout bottomInputsLayout = new ToolbarLayout(false);
			bottomInputsLayout.setStretchMinorAxis(true);

			dataInputs.setLayoutManager(bottomInputsLayout);
			dataInputs.setOpaque(false);
			lowerSeparator = new AdvancedLineBorder(PositionConstants.SOUTH);
			dataInputs.setBorder(lowerSeparator);
			deviceRectangle.add(dataInputs);

			createContentPane(deviceRectangle);
			setInstanceAndTypeLabelFonts();
		}

		private void createInstanceNameLabel(final Figure parent) {
			instanceNameLabel.setText(getINamedElement().getName());
			instanceNameLabel.setTextAlignment(PositionConstants.CENTER);
			parent.add(instanceNameLabel);
		}

		@Override
		public void setBackgroundColor(final Color bg) {
			final Color deviceColor = ColorHelper.darker(bg);
			upperSeparator.setColor(deviceColor);
			lowerSeparator.setColor(deviceColor);
			super.setBackgroundColor(bg);
		}

		private void createDeviceInfoSection(final Figure parent) {
			final ToolbarLayout deviceInfoLayout = new ToolbarLayout();
			deviceInfoLayout.setStretchMinorAxis(true);

			final Figure deviceInfo = new Figure();
			deviceInfo.setLayoutManager(deviceInfoLayout);
			final LibraryElement type = getModel().getType();
			final String typeName = (null != type) ? type.getName() : "Type not set!";
			typeLabel = new Label(typeName);
			deviceInfo.add(typeLabel);
			typeLabel.setTextAlignment(PositionConstants.CENTER);
			typeLabel.setBorder(new MarginBorder(0, 0, 10, 0));

			parent.add(deviceInfo);
			upperSeparator = new AdvancedLineBorder(PositionConstants.SOUTH);
			deviceInfo.setBorder(upperSeparator);
		}

		private void createContentPane(final RoundedRectangle container) {
			contentPane = new Figure();
			contentPane.setLayoutManager(new ToolbarLayout());
			container.add(contentPane);
		}

		public Label getName() {
			return instanceNameLabel;
		}

		public Figure getConnectionReferenceFigure() {
			return deviceRectangle;
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
			instanceNameLabel.setFont(JFaceResources.getFontRegistry().getBold(UIPreferenceConstants.DIAGRAM_FONT));
			typeLabel.setFont(JFaceResources.getFontRegistry().getItalic(UIPreferenceConstants.DIAGRAM_FONT));
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

	@Override
	protected List<Link> getModelTargetConnections() {
		return getModel().getInConnections();
	}
}
