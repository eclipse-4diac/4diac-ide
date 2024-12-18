/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - allowed resource drop on on whole interfaces
 *   Alois Zoitl - extracted interface selection policy and added connection
 *   			   creation feedback
 *   Daniel Lindhuber - added source comment
 *   Alois Zoitl - added update handling on source comment
 *   Fabio Gandolfi - added resizing of pin labels by property settings
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.gef.figures.ToolTipFigure;
import org.eclipse.fordiac.ide.gef.policies.DataInterfaceLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.InterfaceElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.policies.ValueEditPartChangeEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.widgets.Display;

public abstract class InterfaceEditPart extends AbstractConnectableEditPart
		implements NodeEditPart, IDeactivatableConnectionHandleRoleEditPart, AnnotableGraphicalEditPart {

	private int mouseState;
	private static int minWidth = -1;
	private static int maxWidth = -1;

	private Adapter contentAdapter = null;
	private IInterfaceElement sourcePin = null;
	private Adapter sourcePinAdapter = null;

	private String pinLabelStyle = GefPreferenceConstants.STORE.getString(GefPreferenceConstants.PIN_LABEL_STYLE);
	private final IPropertyChangeListener preferenceListener = event -> {
		if (event.getProperty().equals(GefPreferenceConstants.PIN_LABEL_STYLE)) {
			pinLabelStyle = ((String) event.getNewValue());
			refreshLabelText();
		}
	};

	protected InterfaceEditPart() {
		addPreferenceListener();
	}

	private static int getMinWidth() {
		if (-1 == minWidth) {
			minWidth = GefPreferenceConstants.STORE.getInt(GefPreferenceConstants.MIN_PIN_LABEL_SIZE);
		}
		return minWidth;
	}

	@SuppressWarnings("static-method") // allow subclasses to overload and provide different max widths
	protected int getMaxWidth() {
		if (-1 == maxWidth) {
			loadMaxWidth();
		}
		return maxWidth;
	}

	private static synchronized void loadMaxWidth() {
		maxWidth = GefPreferenceConstants.STORE.getInt(GefPreferenceConstants.MAX_PIN_LABEL_SIZE);
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		return new ConnCreateDirectEditDragTrackerProxy(this);
	}

	public int getMouseState() {
		return mouseState;
	}

	@Override
	protected void refreshTargetConnections() {
		updateSourcePinAdapter();
		super.refreshTargetConnections();
		if (getReferencedValueEditPart() != null) {
			getReferencedValueEditPart().refreshValue();
		}
	}

	@Override
	public boolean isConnectable() {
		return true;
	}

	protected String getLabelText() {
		final String altText = getAlternativePinLabelText();
		return (!altText.isBlank()) ? altText : getPinName(getModel());
	}

	private String getAlternativePinLabelText() {
		switch (pinLabelStyle) {
		case GefPreferenceConstants.PIN_LABEL_STYLE_PIN_COMMENT:
			if (getModel().getFBNetworkElement() != null) {
				// only return the comment for instances and not for type editors
				return getModel().getComment();
			}
			break;
		case GefPreferenceConstants.PIN_LABEL_STYLE_SRC_PIN_NAME:
			if (isShowInput()) {
				return getSourcePinInstanceName();
			}
			break;
		default:
			// in the default case we don't nothing and fall back to the default value below
			break;
		}
		return ""; //$NON-NLS-1$
	}

	private void refreshLabelText() {
		((InterfaceFigure) getFigure()).setText(getLabelText());
	}

	private boolean isShowInput() {
		return GefPreferenceConstants.PIN_LABEL_STYLE_SRC_PIN_NAME.equals(pinLabelStyle) && isInput()
				&& !getModel().getInputConnections().isEmpty();
	}

	private void addPreferenceListener() {
		GefPreferenceConstants.STORE.addPropertyChangeListener(preferenceListener);
	}

	private String getSourcePinInstanceName() {
		final IInterfaceElement refPin = getSourcePin();
		if (refPin != null) {
			final FBNetworkElement source = refPin.getFBNetworkElement();
			final String pinName = getPinName(refPin);
			if (source != null && !isSubAppPin(source)) {
				final String elementName = source.getName();
				return elementName + "." + pinName; //$NON-NLS-1$
			}
			return pinName;
		}
		return ""; //$NON-NLS-1$
	}

	private static String getPinName(final IInterfaceElement pin) {
		if (pin instanceof final MemberVarDeclaration memberVarDecl) {
			return memberVarDecl.getName();
		}
		return pin.getName();
	}

	private IInterfaceElement getSourcePin() {
		final EList<Connection> inputConnections = getModel().getInputConnections();
		if (!inputConnections.isEmpty()) {
			return inputConnections.get(0).getSource();
		}
		return null;
	}

	private boolean isSubAppPin(final FBNetworkElement connSource) {
		if (connSource instanceof final SubApp subapp && getModel().getFBNetworkElement() != null) {
			return subapp.getSubAppNetwork() == getModel().getFBNetworkElement().getFbNetwork();
		}
		return false;
	}

	public class InterfaceFigure extends SetableAlphaLabel {

		public InterfaceFigure() {
			setOpaque(false);
			setText(getLabelText());
			setBorder(new ConnectorBorder(getModel()));
			if (isInput()) {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}

			if (isAdapter()) {
				// this mouse listener acquires the current mouse state including the modifier
				// keys so that we can use it in
				// case we are clicking on an adapter with the ctrl key pressed.
				addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseDragged(final MouseEvent me) {
						mouseState = me.getState();
					}

					@Override
					public void mouseEntered(final MouseEvent me) {
						mouseState = me.getState();

					}

					@Override
					public void mouseExited(final MouseEvent me) {
						mouseState = me.getState();
					}

					@Override
					public void mouseHover(final MouseEvent me) {
						mouseState = me.getState();
					}

					@Override
					public void mouseMoved(final MouseEvent me) {
						mouseState = me.getState();
					}

				});
			}
		}

		@Override
		public String getSubStringText() {
			if (getLabelText().length() < getMinWidth()) {
				final int diff = getMinWidth() - getLabelText().length();
				return isInput() ? getLabelText() + " ".repeat(diff) : " ".repeat(diff) + getLabelText(); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (getLabelText().length() > getMaxWidth()) {
				return getLabelText().substring(0, getMaxWidth()) + getTruncationString();
			}
			return getLabelText();
		}

		@Override
		// Copied code from Label class, changed size calculation via subStringTextSize
		// rather than TextSize.
		public Dimension getPreferredSize(final int wHint, final int hHint) {
			if (prefSize == null) {
				prefSize = calculateLabelSize(getSubStringTextSize());
				final Insets insets = getInsets();
				prefSize.expand(insets.getWidth(), insets.getHeight());
				if (getLayoutManager() != null) {
					prefSize.union(getLayoutManager().getPreferredSize(this, wHint, hHint));
				}
			}
			if (wHint >= 0 && wHint < prefSize.width) {
				final Dimension minSize = getMinimumSize(wHint, hHint);
				final Dimension result = prefSize.getCopy();
				result.width = Math.min(result.width, wHint);
				result.width = Math.max(minSize.width, result.width);
				return result;
			}
			return prefSize;
		}

		@Override
		public Dimension getMinimumSize(final int wHint, final int hHint) {
			return getPreferredSize(-1, -1);
		}

		@Override
		public Dimension getTextSize() {
			// call super class to set TextSize
			super.getTextSize();
			return getSubStringTextSize();
		}
	}

	@Override
	protected IFigure createFigure() {
		final InterfaceFigure figure = new InterfaceFigure();
		figure.setToolTip(new ToolTipFigure(getModel(), FordiacAnnotationUtil.getAnnotationModel(this)));
		return figure;
	}

	@Override
	protected void refreshVisuals() {
		refreshLabelText();
	}

	protected abstract GraphicalNodeEditPolicy getNodeEditPolicy();

	public void setInOutConnectionsWidth(final int width) {
		getSourceConnections().forEach(cep -> checkConnection(width, cep));
		getTargetConnections().forEach(cep -> checkConnection(width, cep));
	}

	private static void checkConnection(final int width, final ConnectionEditPart cep) {
		if (cep.getFigure() instanceof final PolylineConnection plc) {
			plc.setLineWidth(width);
		}
	}

	@Override
	protected void createEditPolicies() {
		final GraphicalNodeEditPolicy nodeEditPolicy = getNodeEditPolicy();
		if (null != nodeEditPolicy) {
			installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, nodeEditPolicy);
		}
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new InterfaceElementSelectionPolicy(this));

		if (isVariable()) {
			// layoutrole that allows to drop "strings" to an Input Variable
			// which is than used as Parameter
			installEditPolicy(EditPolicy.LAYOUT_ROLE, getLayoutPolicy());

			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ValueEditPartChangeEditPolicy() {
				@Override
				protected ValueEditPart getValueEditPart() {
					return getReferencedValueEditPart();
				}
			});
		}
	}

	@SuppressWarnings("static-method") // we want to allow subclasses to provide different layout policies
	protected LayoutEditPolicy getLayoutPolicy() {
		return new DataInterfaceLayoutEditPolicy();
	}

	@Override
	public void setConnectionHandleRoleEnabled(final boolean enabled) {
		// nothing to do
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == IInterfaceElement.class) {
			return key.cast(getModel());
		}
		return super.getAdapter(key);
	}

	@Override
	public IInterfaceElement getModel() {
		return (IInterfaceElement) super.getModel();
	}

	public boolean isInput() {
		return getModel().isIsInput();
	}

	public boolean isEvent() {
		return getModel() instanceof Event;
	}

	public boolean isAdapter() {
		return getModel() instanceof AdapterDeclaration;
	}

	public boolean isVariable() {
		return getModel() instanceof VarDeclaration;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		// don't show any children
	}

	protected Adapter getContentAdapter() {
		if (null == contentAdapter) {
			contentAdapter = createContentAdapter();
		}
		return contentAdapter;
	}

	// Allows child classes to provide their own content adapters
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)) {
					Display.getDefault().execute(() -> refresh());
				}
				super.notifyChanged(notification);
			}
		};
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getModel().eAdapters().add(getContentAdapter());
			addSourcePinAdapter();
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getModel().eAdapters().remove(getContentAdapter());
			GefPreferenceConstants.STORE.removePropertyChangeListener(preferenceListener);
			removeSourcePinAdapter();
		}
	}

	private ConnectionAnchor sourceConAnchor = null;
	private ConnectionAnchor destinationConAnchor = null;

	@Override
	public final ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		if (sourceConAnchor == null) {
			sourceConAnchor = createSourceConAnchor();
		}
		return sourceConAnchor;
	}

	@Override
	public final ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		if (sourceConAnchor == null) {
			sourceConAnchor = createSourceConAnchor();
		}
		return sourceConAnchor;
	}

	@Override
	public final ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		if (destinationConAnchor == null) {
			destinationConAnchor = createTargetConAnchor();
		}
		return destinationConAnchor;
	}

	@Override
	public final ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		if (destinationConAnchor == null) {
			destinationConAnchor = createTargetConAnchor();
		}
		return destinationConAnchor;
	}

	protected FixedAnchor createSourceConAnchor() {
		return new FixedAnchor(getFigure(), isInput());
	}

	protected FixedAnchor createTargetConAnchor() {
		return new FixedAnchor(getFigure(), isInput());
	}

	@Override
	protected List<?> getModelSourceConnections() {
		if (!isInput()) {
			// only outputs have source connections
			return getModel().getOutputConnections();
		}
		return Collections.emptyList();
	}

	@Override
	protected List<?> getModelTargetConnections() {
		if (isInput()) {
			// only outputs have source connections
			return getModel().getInputConnections();
		}
		return Collections.emptyList();
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick
		if (request.getType() == RequestConstants.REQ_MOVE) {
			// TODO: move parent editpart??
		}
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN)) {
			final ValueEditPart part = getReferencedValueEditPart();
			if ((part != null) && (isVariable())) {
				part.performRequest(request);
			}
		} else {
			super.performRequest(request);
		}
	}

	public ValueEditPart getReferencedValueEditPart() {
		final Value value = getValue();
		if ((value != null) && (getViewer().getEditPartForModel(value) instanceof final ValueEditPart vep)) {
			return vep;
		}
		return null;
	}

	private Value getValue() {
		if (getModel() instanceof final VarDeclaration varDecl) {
			return varDecl.getValue();
		}
		if (getModel() instanceof final ErrorMarkerInterface emi) {
			return emi.getValue();
		}
		return null;
	}

	private void addSourcePinAdapter() {
		if (isShowInput()) {
			sourcePin = getSourcePin();
			if (sourcePin != null) {
				sourcePin.eAdapters().add(getSourcePinAdapter());
				final FBNetworkElement sourceElement = sourcePin.getFBNetworkElement();
				if (sourceElement != null) {
					sourceElement.eAdapters().add(getSourcePinAdapter());
				}
			}
		}
	}

	private void removeSourcePinAdapter() {
		if (sourcePin != null) {
			sourcePin.eAdapters().remove(getSourcePinAdapter());
			final FBNetworkElement sourceElement = sourcePin.getFBNetworkElement();
			if (sourceElement != null) {
				sourceElement.eAdapters().remove(getSourcePinAdapter());
			}
			sourcePin = null;
		}
	}

	private void updateSourcePinAdapter() {
		if (sourcePin != getSourcePin()) {
			// the source pin has changed remove the adapters from the old source ping and
			// add to the new
			removeSourcePinAdapter();
			addSourcePinAdapter();
		}
	}

	private Adapter getSourcePinAdapter() {
		if (sourcePinAdapter == null) {
			sourcePinAdapter = createSourcePinAdapter();
		}
		return sourcePinAdapter;
	}

	private Adapter createSourcePinAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) {
					refreshLabelText();
				}
			}
		};
	}

	protected void updatePadding(final int yPosition) {
		final IFigure paddingFigure = getFigure().getParent().getChildren().get(0);
		final int textHeight = ((InterfaceFigure) getFigure()).getTextBounds().height();
		paddingFigure.setMinimumSize(new Dimension(-1, yPosition - textHeight));
	}

	protected static int getYPositionFromAttribute(final IInterfaceElement ie) {
		final Attribute attribute = ie.getAttribute(FordiacKeywords.INTERFACE_Y_POSITION);
		if (attribute != null) {
			return Integer.parseInt(attribute.getValue());
		}
		return 0;
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		GraphicalAnnotationStyles.updateAnnotationFeedback(getFigure(), getModel(), event);
		getFigure().setToolTip(new ToolTipFigure(getModel(), event.getModel()));
	}

}
