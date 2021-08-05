/*******************************************************************************
 * Copyright (c) 2012, 2014, 2015 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Gerd Kainz, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.StructTreeNode;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

public class WatchesContentProvider implements ITreeContentProvider {

	private Viewer viewer;
	private WatchValueTreeNode root;
	private volatile boolean hasValueOnForte = false;

	public WatchesContentProvider() {
		init();
	}

	public void update() {
		init();
	}

	private synchronized void init() {
		root = new WatchValueTreeNode(null);
		final Collection<MonitoringBaseElement> elementsToMonitor = MonitoringManager.getInstance().getElementsToMonitor();
		for (final MonitoringBaseElement element : elementsToMonitor) {
			if (element != null) {
				hasValueOnForte = !((MonitoringElement) element).getCurrentValue().equals("N/A"); //$NON-NLS-1$
				root.addChild(element);
				if (!element.eAdapters().contains(adapter)) {
					element.eAdapters().add(adapter);
				}
			}
			Collections.sort(root.getChildren(), (node1, node2) -> {

				final String s1 = ((WatchValueTreeNode) node1).getWatchedElementString();
				final String s2 = ((WatchValueTreeNode) node2).getWatchedElementString();
				return s1.compareToIgnoreCase(s2);
			});
		}

	}

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification notification) {
			final int featureID = notification.getFeatureID(MonitoringElement.class);
			if (featureID == MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE
					&& notification.getNotifier() instanceof MonitoringElement) {
				Display.getDefault().asyncExec(() -> {
					if (!viewer.getControl().isDisposed()) {
						final MonitoringElement element = (MonitoringElement) notification.getNotifier();
						if (!hasValueOnForte) {
							init();
							hasValueOnForte = true;
						}
						final WatchValueTreeNode containsElement = containsElement(element);
						if (containsElement != null) {
							((TreeViewer) viewer).refresh();
						}
					}
				});

			}
		}
	};


	public WatchValueTreeNode containsElement(final MonitoringElement monitoringElement) {
		return (WatchValueTreeNode) root.getChildren().stream()
				.filter(node -> ((WatchValueTreeNode) node).getMonitoringBaseElement().equals(monitoringElement))
				.findAny().orElse(null);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		Assert.isTrue(parentElement instanceof WatchValueTreeNode);
		return ((WatchValueTreeNode) parentElement).getChildrenAsArray();
	}

	@Override
	public Object getParent(final Object element) {
		return ((WatchValueTreeNode) element).getParent();
	}

	@Override
	public boolean hasChildren(final Object element) {

		if (element instanceof String) {
			return false;
		}
		Assert.isTrue(element instanceof WatchValueTreeNode);
		return ((WatchValueTreeNode) element).hasChildren();
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return root.getChildrenAsArray();
	}

	@Override
	public void dispose() {
		removeAdapterFromChildrenList();
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		this.viewer = viewer;
	}

	private void removeAdapterFromChildrenList() {
		for (final StructTreeNode node : root.getChildren()) {
			final MonitoringBaseElement element = ((WatchValueTreeNode) node).getMonitoringBaseElement();
			if (element.eAdapters().contains(adapter)) {
				element.eAdapters().remove(adapter);
			}
		}
	}

}
