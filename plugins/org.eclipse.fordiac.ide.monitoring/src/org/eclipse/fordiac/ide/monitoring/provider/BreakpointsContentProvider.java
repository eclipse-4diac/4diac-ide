/*******************************************************************************
 * Copyright (c) 2012, 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Gerd Kainz, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;


public class BreakpointsContentProvider implements ITreeContentProvider {

	private Viewer viewer;

	EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(
				final org.eclipse.emf.common.notify.Notification notification) {

			int featureID = notification.getFeatureID(MonitoringElement.class);
			if (featureID == MonitoringPackage.MONITORING_ELEMENT__BREAKPOINT_ACTIVE) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						((TreeViewer) viewer).refresh();
						((TreeViewer) viewer).setSelection(new StructuredSelection(
								notification.getNotifier()));
					}
				});

			}
			if (featureID == MonitoringPackage.MONITORING_ELEMENT__BREAKPOINT) {
				Display.getDefault().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						((TreeViewer) viewer).refresh();
//						((TreeViewer) viewer).setSelection(new StructuredSelection(
//								notification.getNotifier()));
					}
				});
				
			}
		}
	};

	private final ArrayList<MonitoringElement> breakpoints = new ArrayList<MonitoringElement>();

	@Override
	public Object[] getChildren(Object parentElement) {

		if (parentElement instanceof MonitoringElement) {
			return new Object[0];
		}

		ArrayList<MonitoringElement> oldBreakpoints = new ArrayList<MonitoringElement>();
		oldBreakpoints.addAll(breakpoints);

		for (MonitoringBaseElement element : MonitoringManager.getInstance().getElementsToMonitor()) {
			if (element != null && element instanceof MonitoringElement) {
				MonitoringElement monitoringElement = (MonitoringElement)element;

				if (monitoringElement.isBreakpoint() && !breakpoints.contains(monitoringElement)) {
					breakpoints.add(monitoringElement);
					element.eAdapters().add(adapter);
				}
				else if (!monitoringElement.isBreakpoint() && breakpoints.contains(monitoringElement)) {
					breakpoints.remove(monitoringElement);
					if (monitoringElement.eAdapters().contains(adapter)) {
						monitoringElement.eAdapters().remove(adapter);
					}
				}
			}
		}

//		oldBreakpoints.removeAll(breakpoints);
//		for (MonitoringElement breakpoint : oldBreakpoints) {
//			if (breakpoint.eAdapters().contains(adapter)) {
//				breakpoint.eAdapters().remove(adapter);
//			}
//		}
//
//		breakpoints.removeAll(oldBreakpoints);
		return breakpoints.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

}
