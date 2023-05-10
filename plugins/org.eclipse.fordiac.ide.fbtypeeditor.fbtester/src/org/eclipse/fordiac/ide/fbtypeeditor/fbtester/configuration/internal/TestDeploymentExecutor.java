/*******************************************************************************
 * Copyright (c) 2012 - 2021 Profactor GmbH, fortiss GmbH, TU Wien ACIN
 * 			2021 Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *    Christoph Binder - using Deployment Plugin and changes for reset and reload buttons
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration.internal;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.executors.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.iec61499.handlers.EthernetDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.TestingManager;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TestDeploymentExecutor {
	private static final String RESET = "<Request ID=\"{0}\" Action=\"RESET\"><FB Name=\"{1}\" Type=\"*\"/></Request>"; //$NON-NLS-1$
	private static final String KILL_FB = "<Request ID=\"{0}\" Action=\"KILL\"><FB Name=\"{1}\" Type=\"*\"/></Request>"; //$NON-NLS-1$
	private static final String RESOURCE_MARKER = "_RES"; //$NON-NLS-1$
	private static final String RESOURCE_TYPE = "EMB" + RESOURCE_MARKER; //$NON-NLS-1$
	private Resource res;
	private EthernetDeviceManagementCommunicationHandler handler;
	private DeploymentExecutor executor;
	private FBNetworkElement fb;
	private FBDeploymentData fbdata;
	private String address;
	private Map<String, TestElement> testElements;
	private Map<String, TestElement> triggerElements;

	/** setup deployment to be applicable for FBTester
	 *
	 * @param type        the FBType under test
	 * @param ipAddress   IP address
	 * @param runtimePort the wanted runtimePort
	 * @param listener    the DeploymentListener to get request responses
	 * @param elements    the TestElements */
	public TestDeploymentExecutor(final FBType type, final String ipAddress, final int runtimePort,
			final IDeploymentListener listener, final Map<String, TestElement> elements) {
		setup(type, ipAddress, runtimePort, listener, elements);
	}

	// helper class
	private class TestResource extends ResourceImpl {

		@Override
		public String getTypeName() {
			return RESOURCE_TYPE;
		}
	}

	// helper class
	private class TestFB extends FBImpl {
		private final String typename;

		TestFB(final String type) {
			super();
			this.typename = type;
		}

		@Override
		public String getTypeName() {
			return typename;
		}
	}

	private void setup(final FBType type, final String ipAddress, final int runtimePort,
			final IDeploymentListener listener, final Map<String, TestElement> elements) {
		res = new TestResource();
		res.setName("_" + type.getName() + RESOURCE_MARKER); //$NON-NLS-1$
		handler = new EthernetDeviceManagementCommunicationHandler();
		executor = new DeploymentExecutor(null, handler);
		executor.addDeploymentListener(listener);

		fb = new TestFB(type.getName());
		fb.setName(type.getName());
		fbdata = new FBDeploymentData("_", fb); //$NON-NLS-1$
		address = ipAddress + ":" + Integer.toString(runtimePort); //$NON-NLS-1$
		testElements = elements;
		triggerElements = TestingManager.getInstance().getTriggerElements(type);
	}

	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		return executor.queryResources();
	}

	public void addWatch(final MonitoringBaseElement element) throws DeploymentException {
		executor.addWatch(element);
	}

	public Response readWatches() throws DeploymentException {
		return executor.readWatches();
	}

	public void setValue(final MonitoringBaseElement element, final String value) throws DeploymentException {
		executor.forceValue(element, value);
	}

	public void sendEvent(final MonitoringBaseElement element) throws DeploymentException {
		executor.triggerEvent(element);
	}

	public void resetFB() throws IOException {
		final String kill = MessageFormat.format(KILL_FB, Integer.toString(0), "_" + fb.getName()); //$NON-NLS-1$
		final String reset = MessageFormat.format(RESET, Integer.toString(1), "_" + fb.getName()); //$NON-NLS-1$
		handler.sendREQ(res.getName(), kill);
		handler.sendREQ(res.getName(), reset);
	}

	public void startFB() throws DeploymentException {
		executor.startFB(res, fbdata);
	}

	public String cleanNetwork() {
		try {
			executor.deleteResource(res.getName());
			handler.disconnect();
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	/** Deploy the network required for testing a function block.
	 *
	 * @return the string */
	public String deployNetwork() {
		try {
			handler.connect(address);
			executor.createResource(res);
			executor.createFBInstance(fbdata, res);
			executor.startResource(res);
			checkInterface();
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	private void checkInterface() throws DeploymentException {
		final Collection<TestElement> allElements = new ArrayList<>();
		allElements.addAll(testElements.values());
		allElements.addAll(triggerElements.values());

		for (final TestElement element : allElements) {
			if (element.getFBString().equals("_" + fb.getName())) { //$NON-NLS-1$
				executor.addWatch(element);
				executor.removeWatch(element);
			} else {
				FordiacLogHelper.logInfo(
						MessageFormat.format(Messages.FORTERemoteTester_ElementSkipped, element.getFBString()));
			}
		}
	}

	public void addWatches() {
		try {
			for (final TestElement element : testElements.values()) {
				if (element.getFBString().equals("_" + fb.getName())) { //$NON-NLS-1$
					if (element.getInterfaceElement() instanceof VarDeclaration) {
						executor.addWatch(element);
					}
					if (element.getInterfaceElement() instanceof Event && !element.getInterfaceElement().isIsInput()) {
						executor.addWatch(element);
					}
				} else {
					FordiacLogHelper.logInfo(
							MessageFormat.format(Messages.FORTERemoteTester_ElementSkipped, element.getFBString()));
				}
			}
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}
}
