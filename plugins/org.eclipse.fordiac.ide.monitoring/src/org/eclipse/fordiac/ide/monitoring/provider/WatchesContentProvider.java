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
 *   Michael Oberlehner - added struct tree nodes for watching variable values
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.AbstractStructTreeNode;
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

	public void updateWithList(final Collection<MonitoringBaseElement> elementsToMonitor) {
		Object[] expandedElements = null;
		if (root != null) {
			expandedElements = getTreeViewer().getExpandedElements().clone();
		}
		root = new WatchValueTreeNode(null);

		for (final MonitoringBaseElement element : elementsToMonitor) {
			if (element != null) {
				hasValueOnForte = !((MonitoringElement) element).getCurrentValue().equals("N/A"); //$NON-NLS-1$
				final WatchValueTreeNode node = root.addChild(element);

				if (expandedElements != null) {
					getTreeViewer().refresh(node);
					updateExpandedState(Arrays.asList(expandedElements), node);
				}

				if (!element.eAdapters().contains(adapter)) {
					element.eAdapters().add(adapter);
				}
			}
		}
		sortChildrenAsc();
	}

	private void init() {
		Object[] expandedElements = null;
		if (root != null) {
			expandedElements = getTreeViewer().getExpandedElements().clone();
		}
		root = new WatchValueTreeNode(null);

		final Collection<MonitoringBaseElement> elementsToMonitor = MonitoringManager.getInstance()
				.getAllElementsToMonitor();
		for (final MonitoringBaseElement element : elementsToMonitor) {
			if (element != null) {
				hasValueOnForte = !((MonitoringElement) element).getCurrentValue().equals("N/A"); //$NON-NLS-1$
				final WatchValueTreeNode node = root.addChild(element);

				if (expandedElements != null && node != null) {
					getTreeViewer().refresh(node);
					updateExpandedState(Arrays.asList(expandedElements), node);
				}

				if (!element.eAdapters().contains(adapter)) {
					element.eAdapters().add(adapter);
				}
			}
		}
		sortChildrenAsc();

	}

	public void sortChildrenAsc() {
		Collections.sort(root.getChildren(), (node1, node2) -> {
			final String s1 = ((WatchValueTreeNode) node1).getWatchedElementString();
			final String s2 = ((WatchValueTreeNode) node2).getWatchedElementString();
			return s1.compareToIgnoreCase(s2);
		});
	}

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification notification) {
			final int featureID = notification.getFeatureID(MonitoringElement.class);
			if (featureID == MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE
					&& notification.getNotifier() instanceof final MonitoringElement element) {
				Display.getDefault().asyncExec(() -> {
					if (!viewer.getControl().isDisposed()) {
						if (!hasValueOnForte) {
							init();
							hasValueOnForte = true;
						}
						final WatchValueTreeNode node = getNodeFromElement(element);
						if (node != null) {
							updateNodeElement(node, element);
						}
					}
				});

			}
		}

		private void updateNodeElement(final WatchValueTreeNode node, final MonitoringElement element) {
			final WatchValueTreeNode newNode = WatchValueTreeNode.createNode(element, root);
			root.getChildren().remove(newNode);
			udpateNodeValue(node, newNode);
			getTreeViewer().refresh();
		}

		private void udpateNodeValue(final WatchValueTreeNode node, final WatchValueTreeNode newNode) {
			node.setValue(newNode.getValue());
			udpateChildren(node, newNode);

		}

		private void udpateChildren(final WatchValueTreeNode node, final WatchValueTreeNode newNode) {
			if (node.getChildren().size() != newNode.getChildren().size()) {
				return;
			}
			for (int i = 0; i < node.getChildren().size(); i++) {
				final WatchValueTreeNode oldNode = (WatchValueTreeNode) node.getChildren().get(i);
				final WatchValueTreeNode newNodeChild = (WatchValueTreeNode) newNode.getChildren().get(i);
				oldNode.setValue(newNodeChild.getValue());
				udpateChildren(oldNode, newNodeChild);
			}
		}
	};

	private void updateExpandedState(final List<Object> expanded, final WatchValueTreeNode newNode) {
		for (final Object e : expanded) {
			if (hasBeenExpanded(newNode, e)) {
				getTreeViewer().setExpandedState(newNode, true);
			}
		}
		newNode.getChildren().forEach(c -> updateExpandedState(expanded, (WatchValueTreeNode) c));
	}

	private static boolean hasBeenExpanded(final WatchValueTreeNode newNode, final Object e) {
		return e instanceof final WatchValueTreeNode watchTreeNode
				&& watchTreeNode.getMonitoringBaseElement().equals(newNode.getMonitoringBaseElement())
				&& watchTreeNode.getWatchedElementString().equals(newNode.getWatchedElementString());
	}

	public WatchValueTreeNode getNodeFromElement(final MonitoringElement monitoringElement) {
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
		for (final AbstractStructTreeNode node : root.getChildren()) {
			final MonitoringBaseElement element = ((WatchValueTreeNode) node).getMonitoringBaseElement();
			if (element.eAdapters().contains(adapter)) {
				element.eAdapters().remove(adapter);
			}
		}
	}

	public TreeViewer getTreeViewer() {
		return (TreeViewer) viewer;
	}
}
