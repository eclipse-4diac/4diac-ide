/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener2;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class DeploymentStreamsProxy implements IStreamsProxy, IDeploymentListener2 {

	private static final TransformerFactory TRANSFORMER_FACTORY;
	static {
		TRANSFORMER_FACTORY = TransformerFactory.newInstance();
		TRANSFORMER_FACTORY.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); //$NON-NLS-1$
		TRANSFORMER_FACTORY.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); //$NON-NLS-1$
	}

	private final DeploymentStreamMonitor outputStreamMonitor = new DeploymentStreamMonitor();
	private final DeploymentStreamMonitor errorStreamMonitor = new DeploymentStreamMonitor();
	private final Transformer transformer;

	private String currentDest;
	private int count;

	public DeploymentStreamsProxy() throws DeploymentException {
		try {
			transformer = TRANSFORMER_FACTORY.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); //$NON-NLS-1$
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (final Exception e) {
			throw new DeploymentException(e.getMessage(), e);
		}
	}

	@Override
	public DeploymentStreamMonitor getErrorStreamMonitor() {
		return errorStreamMonitor;
	}

	@Override
	public DeploymentStreamMonitor getOutputStreamMonitor() {
		return outputStreamMonitor;
	}

	@Override
	public void write(final String input) throws IOException {
		// ignore input
	}

	@Override
	public void connectionOpened(final Device dev) {
		currentDest = null;
		outputStreamMonitor
				.message(MessageFormat.format(Messages.DeploymentStreamsProxy_ConnectedToDevice, dev.getName()));
	}

	@Override
	public void postCommandSent(final String info, final String destination, final String command) {
		if ((destination != null && !destination.isEmpty()) && !destination.isBlank()) {
			if (!destination.equals(currentDest)) {
				if (currentDest != null && !currentDest.isBlank()) {
					printDeployStatistics();
				}
				printDeployingResource(destination);
				currentDest = destination;
				count = 1;
			} else {
				count++;
			}
		}
	}

	private void printDeployStatistics() {
		outputStreamMonitor.message(MessageFormat.format(Messages.DeploymentStreamsProxy_DeployedElements, currentDest,
				Integer.valueOf(count)));
	}

	private void printDeployingResource(final String destination) {
		outputStreamMonitor.message(MessageFormat.format(Messages.DeploymentStreamsProxy_Deploying, destination));
	}

	@Override
	public void postResponseReceived(final String response, final String source) {
		// ignore
	}

	@Override
	public void postResponseReceived(final String info, final String request, final String response,
			final String peer) {
		if (response.contains("Reason")) { //$NON-NLS-1$
			final StringBuilder requestBuffer = new StringBuilder();
			requestBuffer.append("<!-- "); //$NON-NLS-1$
			requestBuffer.append(info);
			requestBuffer.append(" -->\n"); //$NON-NLS-1$
			requestBuffer.append(getFormattedXML(request));
			requestBuffer.append("\n"); //$NON-NLS-1$
			outputStreamMonitor.message(requestBuffer.toString());
			final StringBuilder responseBuffer = new StringBuilder();
			responseBuffer.append(getFormattedXML(response));
			responseBuffer.append("\n\n"); //$NON-NLS-1$
			errorStreamMonitor.message(responseBuffer.toString());
		}
	}

	@Override
	public void connectionClosed(final Device dev) {
		if (currentDest != null && !currentDest.isBlank()) {
			printDeployStatistics();
		}
		outputStreamMonitor
				.message(MessageFormat.format(Messages.DeploymentStreamsProxy_DisconnectedFromDevice, dev.getName()));
	}

	private String getFormattedXML(final String input) throws TransformerFactoryConfigurationError {
		try {
			final StringReader reader = new StringReader(input);
			final StringWriter writer = new StringWriter();
			transformer.transform(new StreamSource(reader), new StreamResult(writer));
			return writer.toString();
		} catch (final Exception e) {
			return input;
		}
	}
}