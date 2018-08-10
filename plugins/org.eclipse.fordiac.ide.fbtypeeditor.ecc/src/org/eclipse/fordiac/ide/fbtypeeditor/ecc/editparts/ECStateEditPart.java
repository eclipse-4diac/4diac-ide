/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECStateLayoutEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.ECStateSelectionPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies.TransitionNodeEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.figures.HorizontalLineFigure;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

/**
 * The Class ECStateEditPart.
 */
public class ECStateEditPart extends AbstractDirectEditableEditPart implements NodeEditPart {

	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			
			if(Notification.REMOVING_ADAPTER != notification.getEventType()){
				Object feature = notification.getFeature();
				
				if (!((LibraryElementPackage.eINSTANCE.getECAction_Algorithm().equals(feature))|| 
						(LibraryElementPackage.eINSTANCE.getECAction_Output().equals(feature))||
						(LibraryElementPackage.eINSTANCE.getECState().equals(feature)))) {
					refresh();
				}
			}
		}
	};

	/** The ecc adapter. */
	private final EContentAdapter eccAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			updateBorder();
		}

	};
	
	public ECStateEditPart() {
		setConnectable(true);
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getCastedModel().eAdapters().add(adapter);
			((ECC) getCastedModel().eContainer()).eAdapters().add(eccAdapter);
			Activator.getDefault().getPreferenceStore()
					.addPropertyChangeListener(propertyChangeListener);
		}
	}
		

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getCastedModel().eAdapters().remove(adapter);

			if (getCastedModel().eContainer() != null) {
				((ECC) getCastedModel().eContainer()).eAdapters().remove(
						eccAdapter);
			}
			Activator.getDefault().getPreferenceStore()
					.removePropertyChangeListener(propertyChangeListener);
		}
	}

	public class StateBorder extends LineBorder {
		private boolean initialState;
		private Rectangle tempRect2;

		public boolean isInitialState() {
			return initialState;
		}

		public void setInitialState(boolean initialState) {
			this.initialState = initialState;
		}

		public StateBorder() {
			super();
			initialState = false;
		}

		public StateBorder(boolean isInitialState) {
			this();
			initialState = isInitialState;
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			if (initialState) {
				return new Insets(6, 9, 6, 9);
			} else {
				return new Insets(3, 6, 3, 6);
			}
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics,
				final Insets insets) {

			super.paint(figure, graphics, insets);

			graphics.setBackgroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_ECC_STATE_COLOR));
			graphics.setForegroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			figure.setForegroundColor(PreferenceGetter
					.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			
			graphics.drawRectangle(tempRect);
			if (initialState) {
				tempRect2 = new Rectangle(tempRect);
				tempRect2.shrink(3, 3);
				graphics.drawRectangle(tempRect2);
			}
		}

	}

	/**
	 * The Class ECStateFigure.
	 */
	public class ECStateFigure extends Figure implements InteractionStyleFigure {

		private StateBorder stateBorder = new StateBorder();

		/** The name label. */
		private Label nameLabel;

		/** The action container. */
		private final Figure actionContainer = new Figure(){
			@Override
			public void add(IFigure figure, Object constraint, int index) {
				super.add(figure, constraint, index);
				setConstraint(figure, new GridData(SWT.FILL, SWT.BEGINNING, true,
						false));
			}
		};

		/** The line. */
		private final Figure line = new HorizontalLineFigure(15);

		/**
		 * Instantiates a new eC state figure.
		 */
		public ECStateFigure() {			
			ToolbarLayout tbLayout = new ToolbarLayout();
			tbLayout.setStretchMinorAxis(false);
			tbLayout.setHorizontal(true);
			setLayoutManager(tbLayout);
			

			Figure stateLabel = new Figure();
			
			add(stateLabel);
			FlowLayout layout = new FlowLayout();
			layout.setStretchMinorAxis(true);
			layout.setMajorSpacing(0);
			layout.setMinorSpacing(0);
			layout.setHorizontal(true);
			layout.setMinorAlignment(OrderedLayout.ALIGN_CENTER);
			stateLabel.setLayoutManager(layout);
			
			
			stateLabel.add(nameLabel = new Label() {
				
			@Override
			protected void paintFigure(Graphics graphics) {	
					Display display = Display.getCurrent();	
					Rectangle boundingRect = getBounds();
	
					Point topLeft = boundingRect.getTopLeft();	
					Point bottomRight = boundingRect.getBottomRight();
					
					Color first = FigureUtilities.lighter(nameLabel.getBackgroundColor());
					Pattern pattern = new Pattern(display, topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, 
							first, nameLabel.getBackgroundColor());	
					graphics.setBackgroundPattern(pattern);	
					graphics.fillRectangle(boundingRect);	
					graphics.setBackgroundPattern(null);	
					pattern.dispose();
					first.dispose();
					graphics.translate(bounds.x, bounds.y);
					graphics.drawText(getSubStringText(), getTextLocation());
					graphics.translate(-bounds.x, -bounds.y);
				}
			}
			);
			
			nameLabel.setText(getCastedModel().getName());
			nameLabel.setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_COLOR));
			nameLabel.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			nameLabel.setOpaque(true);

			nameLabel.setBorder(new StateBorder(isInitialState()));

			stateLabel.add(line);
			line.setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			

			add(actionContainer);
			
			GridLayout gl = new GridLayout(2, false);
			gl.horizontalSpacing = -1;
			gl.verticalSpacing = 0;
			gl.marginWidth = 0;
			gl.marginHeight = 0;
			actionContainer.setLayoutManager(gl);
		}

		/**
		 * Sets the checks for action.
		 * 
		 * @param hasAction
		 *            the new checks for action
		 */
		public void setHasAction(final boolean hasAction) {
			line.setVisible(hasAction);
		}

		/**
		 * Gets the content pane.
		 * 
		 * @return the content pane
		 */
		public Figure getContentPane() {
			return actionContainer;
		}

		/**
		 * Gets the name label.
		 * 
		 * @return the name label
		 */
		public Label getNameLabel() {
			return nameLabel;
		}

		public Figure getLine() {
			return line;
		}

		public StateBorder getStateBorder() {
			return stateBorder;
		}

		@Override
		public int getIntersectionStyle(Point location) {
			Rectangle bounds = nameLabel.getBounds().getCopy();
			bounds.x = bounds.x+3;
			bounds.y = bounds.y + 3;
			bounds.width = bounds.width - 6;
			bounds.height = bounds.height - 6;
			if (bounds.intersects(new Rectangle(location, new Dimension(1,1)))) {
				return InteractionStyleFigure.REGION_CONNECTION; // connection 
			} 
			return InteractionStyleFigure.REGION_DRAG; // move/drag
		}

	}

	/**
	 * Update border.
	 */
	private void updateBorder() {
		getNameLabel().setBorder(new StateBorder(isInitialState()));
	}

	/**
	 * Checks if is initial state.
	 * 
	 * @return true, if is initial state
	 */
	private boolean isInitialState() {
		return getCastedModel().isStartState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getContentPane()
	 */
	@Override
	public IFigure getContentPane() {
		return ((ECStateFigure) getFigure()).getContentPane();
	}
	
	List<Object> stateChildren; 

	public List<Object> getCurrentChildren(){
		return stateChildren;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		if(null == stateChildren){
			stateChildren = new ArrayList<>();
		}
		else{
			stateChildren.clear();
		}
		
		for (ECAction ecAction : getCastedModel().getECAction()) {
			stateChildren.add(new ECActionAlgorithm(ecAction));
			stateChildren.add(new ECActionOutputEvent(ecAction));			
		}
		
		return stateChildren;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		ECStateFigure figure = new ECStateFigure();
		// updateBorder();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new ECStateLayoutEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new TransitionNodeEditPolicy());

		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {

			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteECStateCommand(getCastedModel());
			}

		});

		// Highlight In and Out-Transitions of the selected State
		installEditPolicy("Highlight_IN_OUTS", //$NON-NLS-1$
				new ECStateSelectionPolicy());

	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		
		if (request.getType() == RequestConstants.REQ_OPEN) {
			// transform doubleclick to direct edit
			request.setType(RequestConstants.REQ_DIRECT_EDIT);
		}

		super.performRequest(request);
	}

	/**
	 * Gets the casted model.
	 * 
	 * @return the casted model
	 */
	public ECState getCastedModel() {
		return (ECState) getModel();
	}

	@Override
	protected void refreshVisuals() {
		Rectangle rect = new Rectangle(getCastedModel().getX(), getCastedModel().getY(), -1, -1);

		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rect);

		((ECStateFigure) getFigure()).setHasAction(!getCastedModel().getECAction().isEmpty());
		super.refreshVisuals();
	}

	@Override
	protected List<ECTransition> getModelSourceConnections() {
		return getCastedModel().getOutTransitions();
	}

	@Override
	protected List<ECTransition> getModelTargetConnections() {
		return getCastedModel().getInTransitions();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			final ConnectionEditPart connection) {
		if (connection.getTarget() != null
				&& connection.getTarget().equals(connection.getSource())) {
			return new FixedAnchor(
					((ECStateFigure) getFigure()).getNameLabel(), false);
		}
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			final ConnectionEditPart connection) {
		if (connection.getSource() != null
				&& connection.getSource().equals(connection.getTarget())) {
			return new FixedAnchor(
					((ECStateFigure) getFigure()).getNameLabel(), true);
		}
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return new ChopboxAnchor(((ECStateFigure) getFigure()).getNameLabel());
	}

	@Override
	public Label getNameLabel() {
		return ((ECStateFigure) getFigure()).getNameLabel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getCastedModel();
	}

	/** The property change listener. */
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals( PreferenceConstants.P_ECC_STATE_COLOR)) {
			getNameLabel().setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_COLOR));
		}
		if (event.getProperty().equals(PreferenceConstants.P_ECC_STATE_BORDER_COLOR)) {
			getNameLabel().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
			((ECStateFigure) getFigure()).getLine().setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_STATE_BORDER_COLOR));
		}
	};

	public void highlightTransitions(boolean highlight) {
		for (Object obj : getSourceConnections()) {
			if (obj instanceof ECTransitionEditPart) {
				((ECTransitionEditPart) obj).highlight(highlight);
			}
		}
		for (Object obj : getTargetConnections()) {
			if (obj instanceof ECTransitionEditPart) {
				((ECTransitionEditPart) obj).highlight(highlight);
			}
		}
	}

	/**
	 * Gets the manager.
	 * 
	 * @return the manager
	 */
	@Override
	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getNameLabel();
			manager = new LabelDirectEditManager(this, TextCellEditor.class,
					new NameCellEditorLocator(l), l,
					new IdentifierVerifyListener());
		}

		return manager;
	}

}
