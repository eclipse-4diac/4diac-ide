/*******************************************************************************
 * Copyright (c) 2012, 2024 Profactor GmbH, fortiss GmbH,
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
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Lukas Wais - Implemented a max size for monitoring values
 *   Michael Oberlehner - Added deletion of monitorign elements
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.editparts;

import static org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants.DIAGRAM_FONT;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.handlers.AbstractMonitoringHandler;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.FontMetrics;

public abstract class AbstractMonitoringBaseEditPart extends AbstractViewEditPart implements SpecificLayerEditPart {

	public static class DeleteInterfaceAdapter extends AdapterImpl {

		final AbstractMonitoringBaseEditPart host;

		public DeleteInterfaceAdapter(final AbstractMonitoringBaseEditPart host) {
			this.host = host;
		}

		@Override
		public void notifyChanged(final Notification notification) {
			switch (notification.getEventType()) {
			case Notification.REMOVE, Notification.REMOVE_MANY:
				if (isElementOrParentDeleted(notification.getOldValue())) {
					deleteMonitoringElement();
				}
				break;
			default:
			}
		}

		public void deleteMonitoringElement() {
			MonitoringManager.getInstance().removeMonitoringElement(host.getModel());
			MonitoringManager.getInstance().notifyWatchesChanged();
			if (host.getViewer() != null) {
				final RootEditPart rootEditPart = host.getViewer().getRootEditPart();
				if (rootEditPart != null) {
					AbstractMonitoringHandler.refresh(rootEditPart);
				}
			}
		}

		public boolean isElementOrParentDeleted(final Object deletedElement) {
			if (deletedElement == null) {
				return false;
			}

			if (deletedElement.equals(host.getInterfaceElement())) {
				// our interface element was deleted
				return true;
			}

			if (deletedElement instanceof FBNetworkElement) {
				if (deletedElement.equals(host.getInterfaceElement().getFBNetworkElement())) {
					return true;
				}

				if (deletedElement instanceof final SubApp subApp) {
					return host.getFBNetworks().contains(subApp.getSubAppNetwork());
				}

				if (deletedElement instanceof final Group group) {
					return isParentGroupDeleted(group);
				}
			}
			return false;
		}

		private boolean isParentGroupDeleted(final Group deletedGroup) {
			final FBNetworkElement parent = host.getInterfaceElement().getFBNetworkElement();
			for (final FBNetworkElement element : deletedGroup.getGroupElements()) {
				if (element.equals(parent)) {
					return true;
				}
				if (element instanceof final SubApp subApp) {
					return host.getFBNetworks().contains(subApp.getSubAppNetwork());
				}
			}
			return false;
		}

	}

	protected InterfaceEditPart parentPart;
	private final List<FBNetwork> fBnetworks = new ArrayList<>();
	private EObject parentModel;
	public static final int MONITORING_VALUE_LR_MARGIN = 5;

	private IPropertyChangeListener listener;
	private final Adapter deleteInterfaceAdapter = new DeleteInterfaceAdapter(this);

	@Override
	public void deactivate() {
		fBnetworks.forEach(n -> n.eAdapters().remove(deleteInterfaceAdapter));
		if (null != parentModel) {
			parentModel.eAdapters().remove(deleteInterfaceAdapter);
		}
		super.deactivate();
	}

	@Override
	public void activate() {
		super.activate();
		for (final Object object : getViewer().getEditPartRegistry().keySet()) {
			if (object instanceof final IInterfaceElement interfaceElement) {
				if (interfaceElement.equals(getInterfaceElement())) {
					final EditPart part = getViewer().getEditPartForModel(object);
					if (part instanceof final InterfaceEditPart iep) {
						parentPart = iep;
						final IFigure f = parentPart.getFigure();
						f.addAncestorListener(new AncestorListener() {

							@Override
							public void ancestorRemoved(final IFigure ancestor) {
								// nothing to do
							}

							@Override
							public void ancestorMoved(final IFigure ancestor) {
								refreshVisuals();
							}

							@Override
							public void ancestorAdded(final IFigure ancestor) {
								// nothing to do
							}
						});
					}
				} else if (interfaceElement instanceof final AdapterDeclaration adp) {
					IInterfaceElement subInterfaceElement = null;
					final InterfaceList interfaceList = adp.getType().getInterfaceList();
					final List<IInterfaceElement> list = new ArrayList<>();
					list.addAll(interfaceList.getEventInputs());
					list.addAll(interfaceList.getEventOutputs());
					list.addAll(interfaceList.getInputVars());
					list.addAll(interfaceList.getOutputVars());
					for (final IInterfaceElement element : list) {
						if (element.equals(getInterfaceElement())
								&& (interfaceElement.eContainer().eContainer() == getModel().getPort().getFb())) {
							subInterfaceElement = element;
							break;
						}
					}

					if (subInterfaceElement != null) {
						Object subObject = null;
						for (final Object obj : getViewer().getEditPartRegistry().values()) {
							if ((obj instanceof final MonitoringAdapterEditPart part)
									&& (part.getModel().getPort().getInterfaceElement() == interfaceElement)) {
								for (final Object subView : part.getModelChildren()) {
									if (((IInterfaceElement) subView).getName().equals(subInterfaceElement.getName())) {
										subObject = subView;
										break;
									}
								}
								if (subObject != null) {
									break;
								}
							}
						}

						if (subObject != null) {
							final EditPart part = getViewer().getEditPartRegistry().get(subObject);
							if (part instanceof final InterfaceEditPart iep) {
								parentPart = iep;
								final IFigure f = parentPart.getFigure();
								f.addAncestorListener(new AncestorListener() {

									@Override
									public void ancestorRemoved(final IFigure ancestor) {
										// nothing to do
									}

									@Override
									public void ancestorMoved(final IFigure ancestor) {
										// calculatePos()
										refreshVisuals();

									}

									@Override
									public void ancestorAdded(final IFigure ancestor) {
										// nothing to do
									}
								});
							}
						}
					}
				}
			}
		}

		parentModel = getInterfaceElement().eContainer();
		if (null != parentModel) {
			parentModel.eAdapters().add(deleteInterfaceAdapter);
		}
		addDeleteAdapterToNetworks();
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(getPreferenceChangeListener());
		refreshVisuals();
	}

	public void addDeleteAdapterToNetworks() {
		FBNetworkElement fbNetworkElement = getInterfaceElement().getFBNetworkElement();
		addNetwork(fbNetworkElement);
		while (fbNetworkElement != null && fbNetworkElement.isNestedInSubApp()) {
			fbNetworkElement = fbNetworkElement.getOuterFBNetworkElement();
			addNetwork(fbNetworkElement);
		}
	}

	public void addNetwork(final FBNetworkElement fbNetworkElement) {
		if (fbNetworkElement != null) {
			final FBNetwork fbNetwork = fbNetworkElement.getFbNetwork();
			if (fbNetwork != null) {
				fBnetworks.add(fbNetwork);
				fbNetwork.eAdapters().add(deleteInterfaceAdapter);
			}
		}
	}

	@Override
	public INamedElement getINamedElement() {
		return getInterfaceElement();
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		if (listener == null) {
			listener = event -> {
				if (event.getProperty().equals(PreferenceConstants.P_WATCH_COLOR)
						|| event.getProperty().equals(PreferenceConstants.P_FORCE_COLOR)) {
					setBackgroundColor(getFigure());
				}
			};
		}
		return listener;

	}

	@Override
	public String getSpecificLayer() {
		return ZoomScalableFreeformRootEditPart.TOP_LAYER;
	}

	@Override
	public MonitoringBaseElement getModel() {
		return (MonitoringBaseElement) super.getModel();
	}

	@Override
	public boolean understandsRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_MOVE) {
			return false;
		}
		return super.understandsRequest(request);
	}

	@Override
	public void removeNotify() {
		if (isGrandParentDeletion()) {
			// if a grandparent is removed or a subapp collapsed our figure is not removed
			// as it is in a specific layer.
			// Therefore we have to do it here separatly.
			final IFigure layerFig = getLayer(getSpecificLayer());
			if (layerFig != null && layerFig.equals(getFigure().getParent())) {
				layerFig.remove(getFigure());
				return;
			}
		}
		super.removeNotify();
	}

	private boolean isGrandParentDeletion() {
		// if the interface element has a fbnetworkelement and this fbnetworkelement a
		// network a grandparent was deleted
		// or an expanded subapp folded
		return (getInterfaceElement().getFBNetworkElement() != null
				&& getInterfaceElement().getFBNetworkElement().getFbNetwork() != null);
	}

	private Point calculatePos() {
		if (parentPart != null) {
			final Rectangle bounds = parentPart.getFigure().getBounds();
			int x = 0;
			if (isInput()) {
				final int width = calculateWidth();
				x = bounds.x - 2 - width;
			} else {
				x = bounds.x + bounds.width + 2;
			}
			final int y = bounds.y;
			return new Point(x, y);
		}
		return new Point(0, 0);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
		setBackgroundColor(getFigure());
		getFigure().setEnabled(!getModel().isOffline());
	}

	protected void refreshPosition() {
		if (getParent() != null) {
			Rectangle bounds = null;
			final Point p = calculatePos();
			final int width = calculateWidth();
			final int height = getHeight();
			bounds = new Rectangle(p.x, p.y, width, height);
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private int getHeight() {
		return FigureUtilities.getFontMetrics(JFaceResources.getFontRegistry().get(DIAGRAM_FONT)).getHeight();
	}

	protected void setBackgroundColor(final IFigure l) {
		l.setBackgroundColor(PreferenceGetter.getColor(Activator.getDefault().getPreferenceStore(),
				PreferenceConstants.P_WATCH_COLOR));
	}

	@Override
	protected void backgroundColorChanged(final IFigure figure) {
		setBackgroundColor(figure);
	}

	protected boolean isInput() {
		return getInterfaceElement().isIsInput();
	}

	protected IInterfaceElement getInterfaceElement() {
		return getModel().getPort().getInterfaceElement();
	}

	private int calculateWidth() {
		int width = getFigure().getPreferredSize().width;
		width = Math.max(40, width);
		width = Math.min(width, getMaxWidth());
		return width;
	}

	private static int maxLabelWidth = -1;

	private static int getMaxWidth() {
		if (maxLabelWidth == -1) {
			final IPreferenceStore preferenceStore = org.eclipse.fordiac.ide.gef.Activator.getDefault()
					.getPreferenceStore();
			final int maxLabelSize = preferenceStore.getInt(DiagramPreferences.MAX_VALUE_LABEL_SIZE);
			final FontMetrics fm = FigureUtilities.getFontMetrics(JFaceResources.getFontRegistry().get(DIAGRAM_FONT));
			maxLabelWidth = (int) ((maxLabelSize + 2) * fm.getAverageCharacterWidth()) + 2 * MONITORING_VALUE_LR_MARGIN;
		}
		return maxLabelWidth;
	}

	public List<FBNetwork> getFBNetworks() {
		return fBnetworks;
	}

}
