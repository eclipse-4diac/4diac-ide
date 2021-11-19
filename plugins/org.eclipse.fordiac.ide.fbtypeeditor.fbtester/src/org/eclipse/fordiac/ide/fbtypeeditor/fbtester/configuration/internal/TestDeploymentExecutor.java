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
 *    Christoph Binder - using Deployment Plugin
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration.internal;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.iec61499.EthernetDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TestDeploymentExecutor {
	private static final String RESOURCE_MARKER = "_RES"; //$NON-NLS-1$
	private static final String RESOURCE_TYPE = "EMB" + RESOURCE_MARKER; //$NON-NLS-1$
	private Resource res;
	private EthernetDeviceManagementCommunicationHandler handler;
	private DeploymentExecutor executor;
	private FBNetworkElement fb;
	private FBDeploymentData fbdata;
	private String address;

	public TestDeploymentExecutor(final FBType type, final String ipAddress, final int runtimePort) {
		setup(type, ipAddress, runtimePort);
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

	/** setup deployment to be applicable for FBTester
	 *
	 * @param type
	 * @param ipAddress
	 * @param runtimePort */
	private void setup(final FBType type, final String ipAddress, final int runtimePort) {
		res = new TestResource();
		res.setName("_" + type.getName() + RESOURCE_MARKER); //$NON-NLS-1$
		handler = new EthernetDeviceManagementCommunicationHandler();
		executor = new DeploymentExecutor(null, handler);

		fb = new TestFB(type.getName());
		fb.setName(type.getName());
		// arbitrary PaletteEntry, necessary to use the overridden getTypeName()
		fb.setPaletteEntry(PaletteFactory.eINSTANCE.createFBTypePaletteEntry());
		fbdata = new FBDeploymentData("_", fb); //$NON-NLS-1$

		address = ipAddress + ":" + Integer.toString(runtimePort); //$NON-NLS-1$
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

	public String clean() {
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
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	public void addWatches(final Map<String, TestElement> elements) {
		try {
			for (final TestElement element : elements.values()) {
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
