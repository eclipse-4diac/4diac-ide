/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl, Monika Wenger
 *               - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Michael Oberlehner - added subapp monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseFactory;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;
import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterInterfaceEditPart;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddWatchHandler extends AbstractMonitoringHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		super.execute(event);
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			final MonitoringManager manager = MonitoringManager.getInstance();
			final Set<IInterfaceElement> foundElements = getSelectedWatchedElements(manager,
					(StructuredSelection) selection);
			for (final IInterfaceElement ie : foundElements) {
				createElementFromPort(manager, ie);
			}
			refreshEditor();
			MonitoringManager.getInstance().notifyWatchesChanged();

		}
		return null;
	}

	public void createElementFromPort(final MonitoringManager manager, final IInterfaceElement ie) {
		final PortElement port = MonitoringManagerUtils.createPortElement(ie);
		if (validatePort(port, ie)) {
			createMonitoringElement(manager, port);
		}

	}

	private static boolean validatePort(final PortElement port, final IInterfaceElement ie) {
		if (port instanceof SubAppPortElement && ((SubAppPortElement) port).getAnchor() == null) {
			ErrorMessenger.popUpErrorMessage(
					MessageFormat.format(Messages.MonitoringManagerUtils_NoSubappAnchor, ie.getName()));
			return false;
		}

		return true;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		boolean needToAdd = false;
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			needToAdd = !getSelectedWatchedElements(MonitoringManager.getInstance(), (StructuredSelection) selection)
					.isEmpty();
		}
		setBaseEnabled(needToAdd);
	}

	private static Set<IInterfaceElement> getSelectedWatchedElements(final MonitoringManager manager,
			final StructuredSelection selection) {
		final Set<IInterfaceElement> foundElements = new HashSet<>();
		for (final Object selectedObject : selection) {
			if((selectedObject instanceof EditPart) && (!(selectedObject instanceof MonitoringAdapterInterfaceEditPart))) {
				final Object model = ((EditPart) selectedObject).getModel();

				if (model instanceof FBNetworkElement) {
					if (MonitoringManagerUtils.canBeMonitored((FBNetworkElement) model)) {
						foundElements.addAll(((FBNetworkElement) model).getInterface().getAllInterfaceElements());
					}
				} else if (model instanceof IInterfaceElement) {
					final IInterfaceElement ie = (IInterfaceElement) model;
					if (MonitoringManagerUtils.canBeMonitored(ie) && !manager.containsPort(ie)) {
						foundElements.add(ie);
					}
				}
			}
		}
		return foundElements;
	}

	protected MonitoringBaseElement createMonitoringElement(final MonitoringManager manager, final PortElement port) {
		MonitoringBaseElement element;
		if (port instanceof AdapterPortElement) {
			element = MonitoringFactory.eINSTANCE.createMonitoringAdapterElement();
		} else if (port instanceof SubAppPortElement) {
			element = createSubappElement(port);
		} else {
			element = MonitoringFactory.eINSTANCE.createMonitoringElement();
		}

		element.setPort(port);

		manager.addMonitoringElement(element);
		if (port instanceof AdapterPortElement) {
			final MonitoringAdapterElement adpaterElement = (MonitoringAdapterElement) element;
			createMonitoringElementsForAdapterInterface(manager, adpaterElement);
		}
		return element;
	}

	public static MonitoringBaseElement createSubappElement(final PortElement port) {
		MonitoringBaseElement element;
		element = MonitoringFactory.eINSTANCE.createSubappMonitoringElement();
		final MonitoringBaseElement anchor = MonitoringFactory.eINSTANCE.createMonitoringElement();
		final PortElement anchorPort = MonitoringManagerUtils
				.createPortElement(((SubAppPortElement) port).getAnchor());
		anchor.setPort(anchorPort);
		((SubappMonitoringElement) element).setAnchor(anchor);
		return element;
	}

	private void createMonitoringElementsForAdapterInterface(final MonitoringManager manager,
			final MonitoringAdapterElement adpaterElement) {
		createMonitoredAdpaterFBView(adpaterElement);
		refreshEditor();
		final PortElement port = adpaterElement.getPort();
		final List<MonitoringElement> childElements = adpaterElement.getElements();
		final InterfaceList interfaceList = adpaterElement.getMonitoredAdapterFB().getInterface();
		final List<PortElement> ports = ((AdapterPortElement) port).getPorts();
		final ArrayList<IInterfaceElement> ios = new ArrayList<>();
		ios.addAll(interfaceList.getEventInputs());
		ios.addAll(interfaceList.getEventOutputs());
		ios.addAll(interfaceList.getInputVars());
		ios.addAll(interfaceList.getOutputVars());
		for (final IInterfaceElement io : ios) {
			final PortElement newPort = MonitoringBaseFactory.eINSTANCE.createPortElement();
			newPort.setFb(port.getFb());
			newPort.setInterfaceElement(io);
			newPort.setResource(port.getResource());
			newPort.getHierarchy().addAll(port.getHierarchy());
			ports.add(newPort);
			childElements.add((MonitoringElement) createMonitoringElement(manager, newPort));
		}
	}

	private static void createMonitoredAdpaterFBView(final MonitoringAdapterElement adpaterElement) {
		final AdapterFB fb = LibraryElementFactory.eINSTANCE.createAdapterFB();
		final AdapterDeclaration interfaceElement = (AdapterDeclaration) adpaterElement.getPort().getInterfaceElement();
		final PaletteEntry entry = interfaceElement.getPaletteEntry();
		fb.setPaletteEntry(entry);
		fb.setAdapterDecl(interfaceElement);
		fb.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		createMonitoredAdapterInterface(fb);
		adpaterElement.setMonitoredAdapterFB(fb);
	}

	private static void createMonitoredAdapterInterface(final AdapterFB fb) {
		final InterfaceList interfaceList = fb.getInterface();
		for (final Event event : fb.getType().getInterfaceList().getEventInputs()) {
			interfaceList.getEventInputs().add(EcoreUtil.copy(event));
		}
		for (final Event event : fb.getType().getInterfaceList().getEventOutputs()) {
			interfaceList.getEventOutputs().add(EcoreUtil.copy(event));
		}
		for (final VarDeclaration inVar : fb.getType().getInterfaceList().getInputVars()) {
			interfaceList.getInputVars().add(EcoreUtil.copy(inVar));
		}
		for (final VarDeclaration outVar : fb.getType().getInterfaceList().getOutputVars()) {
			interfaceList.getOutputVars().add(EcoreUtil.copy(outVar));
		}
		// currently IEC 61499 does not allow adapters in adapters.
		// If this changes here also plugs and sockets need to be added
	}
}
