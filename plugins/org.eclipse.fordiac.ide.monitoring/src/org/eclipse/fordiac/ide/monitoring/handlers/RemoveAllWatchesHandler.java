/*******************************************************************************
 * Copyright (c) 2015, 2028 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Removes all selected watches
 *
 */
public class RemoveAllWatchesHandler extends AbstractMonitoringHandler {

	@Override
	protected void doExecute(final ExecutionEvent event, final StructuredSelection structSel) {
		final MonitoringManager manager = MonitoringManager.getInstance();
		final Set<IInterfaceElement> foundElements = getSelectedWatchedelements(manager, structSel);
		for (final IInterfaceElement ifElement : foundElements) {
			removeMonitoringElement(manager, ifElement);
		}
		MonitoringManager.getInstance().notifyWatchesChanged();
		refreshEditor();
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(selection instanceof final StructuredSelection sSel
				&& !getSelectedWatchedelements(MonitoringManager.getInstance(), sSel).isEmpty());
	}

	private static Set<IInterfaceElement> getSelectedWatchedelements(final MonitoringManager manager,
			final StructuredSelection selection) {
		final Set<IInterfaceElement> foundElements = new HashSet<>();
		for (final Object selectedObject : selection) {
			if (selectedObject instanceof final EditPart ep) {
				if (selectedObject instanceof final MonitoringEditPart mEP) {
					final IInterfaceElement ie = mEP.getModel().getPort().getInterfaceElement();
					if (manager.containsPort(ie)) {
						foundElements.add(ie);
					}
				} else if (ep.getModel() instanceof final EObject eObject) {
					foundElements.addAll(getWatchedelementsForLibrayElement(manager, eObject));
				}
			} else if (selectedObject instanceof final EObject eObject) {
				foundElements.addAll(getWatchedelementsForLibrayElement(manager, eObject));
			} else if (selectedObject instanceof final WatchValueTreeNode watchNode && !watchNode.hasChildren()) {
				foundElements.add(watchNode.getMonitoringBaseElement().getPort().getInterfaceElement());
			}
		}
		return foundElements;
	}

	private static Set<IInterfaceElement> getWatchedelementsForLibrayElement(final MonitoringManager manager,
			final EObject element) {

		final Set<IInterfaceElement> foundElements = new HashSet<>();
		if (element instanceof final SubApp subapp) {
			foundElements.addAll(getWatchedIfElementsForFB(manager, subapp));
			foundElements.addAll(getWatchedElementsFromFBNetwork(manager, subapp.getSubAppNetwork()));
		} else if (element instanceof final FBNetworkElement fbe) {
			foundElements.addAll(getWatchedElementsForFB(manager, fbe));
		} else if (element instanceof final FBNetwork fbn) {
			foundElements.addAll(getWatchedElementsFromFBNetwork(manager, fbn));
		} else if (element instanceof final IInterfaceElement ie) {
			if (manager.containsPort(ie)) {
				foundElements.add(ie);
			}
		} else if (element instanceof final AutomationSystem as) {
			foundElements.addAll(getWatchedElementsFromSystem(manager, as));
		} else if (element instanceof final Application app) {
			foundElements.addAll(getWatchedElementsFromFBNetwork(manager, app.getFBNetwork()));
		}
		return foundElements;
	}

	private static Set<IInterfaceElement> getWatchedElementsFromFBNetwork(final MonitoringManager manager,
			final FBNetwork fbNetwork) {
		final Set<IInterfaceElement> foundElements = new HashSet<>();
		// fbnetwork can be null when we checking a cfb or typed subapp instance where
		// the network was not loaded sofar
		if (fbNetwork != null) {
			for (final FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
				foundElements.addAll(getWatchedElementsForFB(manager, fbnElement));

			}
		}
		return foundElements;
	}

	private static Set<IInterfaceElement> getWatchedElementsForFB(final MonitoringManager manager,
			final FBNetworkElement fbnElement) {
		final Set<IInterfaceElement> foundElements = new HashSet<>();
		foundElements.addAll(getWatchedIfElementsForFB(manager, fbnElement));
		if (fbnElement.getType() instanceof BaseFBType) {
			foundElements.addAll(getWatchedInternalVars(manager, (FB) fbnElement));
		} else if (fbnElement instanceof final SubApp subapp && subapp.getSubAppNetwork() != null) {
			foundElements.addAll(getWatchedElementsFromFBNetwork(manager, subapp.getSubAppNetwork()));
		}
		return foundElements;
	}

	private static Collection<InternalVarInstance> getWatchedInternalVars(final MonitoringManager manager,
			final FB fb) {
		final BaseFBType baseFBType = (BaseFBType) fb.getType();
		return baseFBType.getInternalVars().stream(). //
				map(intVar -> WatchInternalVarsHandler.createInternalVarInstance(fb, intVar)). //
				filter(manager::containsPort). //
				toList();
	}

	public static Collection<IInterfaceElement> getWatchedIfElementsForFB(final MonitoringManager manager,
			final FBNetworkElement model) {
		return model.getInterface().getAllInterfaceElements().stream(). //
				filter(manager::containsPort). //
				toList();
	}

	private static Collection<? extends IInterfaceElement> getWatchedElementsFromSystem(final MonitoringManager manager,
			final AutomationSystem system) {
		final Set<IInterfaceElement> foundElements = new HashSet<>();
		for (final Application application : system.getApplication()) {
			foundElements.addAll(getWatchedElementsFromFBNetwork(manager, application.getFBNetwork()));
		}
		return foundElements;
	}

	private static void removeMonitoringElement(final MonitoringManager manager, final IInterfaceElement port) {
		final MonitoringBaseElement element = manager.getMonitoringElement(port);

		if (element instanceof final MonitoringAdapterElement monAE) {
			for (final MonitoringElement child : monAE.getElements()) {
				manager.removeMonitoringElement(child);
			}
		}
		manager.removeMonitoringElement(element);
	}

}
