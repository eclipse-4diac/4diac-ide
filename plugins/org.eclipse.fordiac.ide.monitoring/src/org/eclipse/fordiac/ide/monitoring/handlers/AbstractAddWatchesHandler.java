/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseFactory;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
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
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractAddWatchesHandler extends AbstractMonitoringHandler {

	private static boolean validatePort(final PortElement port, final IInterfaceElement ie) {
		if (port instanceof final SubAppPortElement subAppPort && subAppPort.getAnchor() == null) {
			ErrorMessenger.popUpErrorMessage(
					MessageFormat.format(Messages.MonitoringManagerUtils_NoSubappAnchor, ie.getName()));
			return false;
		}
		return true;
	}

	protected void createWatchForIE(final MonitoringManager manager, final IInterfaceElement ie) {
		final PortElement port = MonitoringManagerUtils.createPortElement(ie);
		if (validatePort(port, ie)) {
			createMonitoringElement(manager, port);
		}
	}

	public static MonitoringBaseElement createSubappElement(final PortElement port, final MonitoringManager manager) {
		final MonitoringBaseElement element = MonitoringFactory.eINSTANCE.createSubappMonitoringElement();
		final MonitoringBaseElement anchor = MonitoringFactory.eINSTANCE.createMonitoringElement();
		final PortElement anchorPort = MonitoringManagerUtils.createPortElement(((SubAppPortElement) port).getAnchor());
		anchor.setPort(anchorPort);
		((SubappMonitoringElement) element).setAnchor(anchor);
		final MonitoringBaseElement monitoringElement = manager.getMonitoringElement(anchorPort.getInterfaceElement());
		if (monitoringElement == null) {
			manager.addMonitoringElement(anchor);
		} else {
			((SubappMonitoringElement) element).setAnchor(monitoringElement);
		}
		return element;
	}

	private MonitoringBaseElement createMonitoringElement(final MonitoringManager manager, final PortElement port) {
		MonitoringBaseElement element;
		if (port instanceof AdapterPortElement) {
			element = MonitoringFactory.eINSTANCE.createMonitoringAdapterElement();
		} else if (port instanceof SubAppPortElement) {
			element = createSubappElement(port, manager);
		} else {
			element = MonitoringFactory.eINSTANCE.createMonitoringElement();
		}

		element.setPort(port);

		if (!manager.containsPort(port.getInterfaceElement())) {
			manager.addMonitoringElement(element);
		}
		if (port instanceof AdapterPortElement) {
			final MonitoringAdapterElement adpaterElement = (MonitoringAdapterElement) element;
			createMonitoringElementsForAdapterInterface(manager, adpaterElement);
		}
		return element;
	}

	private static void createMonitoredAdpaterFBView(final MonitoringAdapterElement adpaterElement) {
		final AdapterFB fb = LibraryElementFactory.eINSTANCE.createAdapterFB();
		final AdapterDeclaration interfaceElement = (AdapterDeclaration) adpaterElement.getPort().getInterfaceElement();
		final TypeEntry entry = interfaceElement.getType().getTypeEntry();
		fb.setTypeEntry(entry);
		fb.setAdapterDecl(interfaceElement);
		fb.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		createMonitoredAdapterInterface(fb);
		adpaterElement.setMonitoredAdapterFB(fb);
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
			newPort.setHierarchy(port.getHierarchy());
			ports.add(newPort);
			childElements.add((MonitoringElement) createMonitoringElement(manager, newPort));
		}
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

	protected static void handleMonitoringState(final Collection<? extends EObject> foundElements, final Shell shell) {
		if (!foundElements.isEmpty()) {
			final AutomationSystem system = getAutomationSystem(foundElements.iterator().next());
			if ((system != null) && !MonitoringManager.getInstance().isSystemMonitored(system)
					&& shouldEnableMonitoring(system, shell)) {
				enableMonitoring(system);
			}
		}
	}

	private static AutomationSystem getAutomationSystem(final EObject eObj) {
		return (EcoreUtil.getRootContainer(eObj) instanceof final AutomationSystem as) ? as : null;
	}

	private static boolean shouldEnableMonitoring(final AutomationSystem system, final Shell shell) {
		if (!Activator.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.P_MONITORING_STARTMONITORINGWITHOUTASKING)) {

			final MessageDialog dialog = new MessageDialog(shell, Messages.MonitoringDialog_EnableMonitoring, null,
					MessageFormat.format(Messages.MonitoringDialog_EnableMonitoringQuestion, system.getName()),
					MessageDialog.QUESTION,
					new String[] { Messages.MonitoringDialog_Enable, Messages.MonitoringDialog_No }, 0) {

				@Override
				protected Control createCustomArea(final Composite parent) {
					final Button checkBox = new Button(parent, SWT.CHECK);
					checkBox.setText(Messages.MonitoringPreferences_StartMonitoringWithoutAsking);
					checkBox.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(final SelectionEvent e) {
							Activator.getDefault().getPreferenceStore().setValue(
									PreferenceConstants.P_MONITORING_STARTMONITORINGWITHOUTASKING,
									checkBox.getSelection());
						}

						@Override
						public void widgetDefaultSelected(final SelectionEvent e) {
							// Nothing to do here
						}
					});
					return checkBox;
				}

			};

			return dialog.open() == 0;
		}
		return true;
	}

	private static void enableMonitoring(final AutomationSystem system) {
		MonitoringManager.getInstance().enableSystem(system);
	}

}