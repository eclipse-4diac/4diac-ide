/*******************************************************************************
 * Copyright (c) 2008, 2011 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 - 2020 Johannes Kepler University
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
 *   Bianca Wiesmayr - removed gradient
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteSegmentEditPolicy;
import org.eclipse.fordiac.ide.systemconfiguration.policies.SegmentNodeEditPolicy;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;

public class SegmentEditPart extends AbstractViewEditPart implements NodeEditPart {
	/** necessary that the gradient pattern can be scaled accordingly */
	private DiagramFontChangeListener fontChangeListener;

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
	protected IFigure createFigureForModel() {
		return new SegmentFigure();
	}

	@Override
	public SegmentFigure getFigure() {
		return (SegmentFigure) super.getFigure();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getColorizableElement_Color().equals(feature)) {
					backgroundColorChanged(getFigure());
				}
				if (LibraryElementPackage.eINSTANCE.getPositionableElement_Position().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getSegment_Width().equals(feature)) {
					refreshVisuals();
				}
				super.notifyChanged(notification);
				refreshSourceConnections();
			}

		};
	}

	@Override
	public Segment getModel() {
		return (Segment) super.getModel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	private SegmentFigure getCastedFigure() {
		return getFigure();
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
	public Label getNameLabel() {
		return getCastedFigure().getName();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
			@Override
			public Command getCommand(final Request request) {
				final Object type = request.getType();
				if (REQ_ALIGN.equals(type) && (request instanceof final AlignmentRequest aReq)) {
					return getAlignCommand(aReq);
				}
				return null;
			}

			protected Command getAlignCommand(final AlignmentRequest request) {
				final AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
				req.setEditParts(getHost());
				req.setAlignment(request.getAlignment());
				req.setAlignmentRectangle(request.getAlignmentRectangle());
				return getHost().getParent().getCommand(req);
			}

			@Override
			protected Command getCreateCommand(final CreateRequest request) {
				return null;
			}
		});
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new SegmentNodeEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSegmentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	@Override
	public boolean isConnectable() {
		return true;
	}

	protected void refreshPosition() {
		final Point position = getModel().getPosition().toScreenPoint();
		final Rectangle bounds = new Rectangle(position.x, position.y,
				CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getWidth()), -1);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
	}

	public class SegmentFigure extends Shape implements InteractionStyleFigure, IFontUpdateListener {
		private final Label instanceNameLabel;
		private final Label typeLabel;
		private final Figure main = new Figure();

		private final RoundedRectangle rect = new BorderedRoundedRectangle() {
			@Override
			public void setBounds(final Rectangle rect) {
				super.setBounds(rect);
				setCornerDimensions(new Dimension((rect.height * 2) / 3, rect.height));
			}

		};

		public SegmentFigure() {
			this.setFillXOR(true);
			setFill(true);
			final GridData rectLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
			final GridData instanceNameLayout = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
			instanceNameLabel = new Label();
			instanceNameLabel.setText(getINamedElement().getName());
			instanceNameLabel.setTextAlignment(PositionConstants.RIGHT);
			instanceNameLabel.setLabelAlignment(PositionConstants.RIGHT);

			final GridLayout gridLayout = new GridLayout(1, true);
			gridLayout.verticalSpacing = 2;
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;
			setLayoutManager(gridLayout);

			final GridLayout mainLayout = new GridLayout(3, false);
			mainLayout.marginHeight = 0;
			mainLayout.marginWidth = 0;
			mainLayout.horizontalSpacing = 0;
			mainLayout.verticalSpacing = -1;
			final GridData mainLayoutData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);

			main.setLayoutManager(mainLayout);
			add(main);
			setConstraint(main, mainLayoutData);

			main.add(rect);
			main.setConstraint(rect, rectLayoutData);
			rect.setBorder(new RoundedRectangleShadowBorder());
			rect.add(instanceNameLabel);
			rect.setOutline(false);
			instanceNameLabel.setBorder(new MarginBorder(4, 0, 4, 0));

			final GridLayout rectLayout = new GridLayout(3, false);
			rectLayout.marginHeight = 2;
			rectLayout.marginWidth = 0;
			rect.setLayoutManager(rectLayout);
			rect.setConstraint(instanceNameLabel, instanceNameLayout);
			rect.add(new Label(":")); //$NON-NLS-1$
			final LibraryElement type = getModel().getType();
			final String typeName = (null != type) ? type.getName() : "Type not set!";
			typeLabel = new Label(typeName);
			rect.add(typeLabel);

			rect.setConstraint(typeLabel, new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.GRAB_HORIZONTAL));
			typeLabel.setTextAlignment(PositionConstants.LEFT);
			typeLabel.setLabelAlignment(PositionConstants.LEFT);
			typeLabel.setBackgroundColor(ColorConstants.blue);
			typeLabel.setOpaque(false);

			setInstanceAndTypeLabelFonts();
		}

		public Label getName() {
			return instanceNameLabel;
		}

		@Override
		protected void fillShape(final Graphics graphics) {
			// nothing to do here right now
		}

		@Override
		protected void outlineShape(final Graphics graphics) {
			// nothing to do here right now
		}

		@Override
		public int getIntersectionStyle(final Point location) {
			if (instanceNameLabel.intersects(new Rectangle(location, new Dimension(1, 1)))) {
				return InteractionStyleFigure.REGION_DRAG; // move/drag
			}
			return InteractionStyleFigure.REGION_CONNECTION; // connection
		}

		@Override
		public void updateFonts() {
			setInstanceAndTypeLabelFonts();
			invalidateTree();
			revalidate();
		}

		private void setInstanceAndTypeLabelFonts() {
			instanceNameLabel.setFont(JFaceResources.getFontRegistry().getBold(UIPreferenceConstants.DIAGRAM_FONT));
			typeLabel.setFont(JFaceResources.getFontRegistry().getItalic(UIPreferenceConstants.DIAGRAM_FONT));
		}
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	protected List<?> getModelSourceConnections() {
		return getModel().getOutConnections();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		return Collections.emptyList();
	}
}
