/*******************************************************************************
 * Copyright (c) 2026 Huzaifa Abdul Rehman
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 AND CC0-1.0
 *
 * AI Disclosure: This file was largely generated with OpenAI Codex.
 * The AI-generated portions are made available under CC0-1.0.
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class AdapterConnectionValidationTest {

	private final FBNetwork network = LibraryElementFactory.eINSTANCE.createFBNetwork();
	private final AdapterType adapterType = LibraryElementFactory.eINSTANCE.createAdapterType();

	AdapterConnectionValidationTest() {
		adapterType.setName("TEST_ADAPTER"); //$NON-NLS-1$
	}

	@Test
	void reportsFanOutThroughModelValidation() {
		final AdapterDeclaration source = adapter("OUT1"); //$NON-NLS-1$
		final AdapterConnection first = (AdapterConnection) connect(
				LibraryElementFactory.eINSTANCE.createAdapterConnection(), source, inputAdapter("IN1")); //$NON-NLS-1$
		connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source, inputAdapter("IN2")); //$NON-NLS-1$
		final BasicDiagnostic diagnostics = new BasicDiagnostic();

		LibraryElementValidator.INSTANCE.validateAdapterConnection(first, diagnostics, new HashMap<>());

		assertTrue(diagnostics.getChildren().stream()
				.anyMatch(diagnostic -> LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(diagnostic.getSource())
						&& diagnostic.getCode() == LibraryElementValidator.CONNECTION__VALIDATE_DUPLICATE
						&& diagnostic.getSeverity() == Diagnostic.ERROR
						&& diagnostic.getData().getFirst() == first));
	}

	@Test
	void reportsAdapterFanOutOnBothConnections() {
		final AdapterDeclaration source = adapter("OUT1"); //$NON-NLS-1$
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source,
				inputAdapter("IN1")); //$NON-NLS-1$
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source,
				inputAdapter("IN2")); //$NON-NLS-1$

		assertEquals(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection,
				source.getQualifiedName()), assertConnectionError(first).getMessage());
		assertConnectionError(second);
		assertFalse(first.validateDuplicate(null, Map.of()));
	}

	@Test
	void reportsAdapterFanInOnBothConnections() {
		final AdapterDeclaration destination = inputAdapter("IN1"); //$NON-NLS-1$
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(),
				adapter("OUT1"), destination); //$NON-NLS-1$
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(),
				adapter("OUT2"), destination); //$NON-NLS-1$

		assertEquals(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection,
				destination.getQualifiedName()), assertConnectionError(first).getMessage());
		assertConnectionError(second);
		assertFalse(first.validateDuplicate(null, Map.of()));
	}

	@Test
	void acceptsSingleAdapterConnection() {
		final Connection connection = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(),
				adapter("OUT1"), inputAdapter("IN1")); //$NON-NLS-1$ //$NON-NLS-2$

		assertNoConnectionError(connection);
	}

	@Test
	void clearsErrorAfterRemovingExtraAdapterConnection() {
		final AdapterDeclaration source = adapter("OUT1"); //$NON-NLS-1$
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source,
				inputAdapter("IN1")); //$NON-NLS-1$
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source,
				inputAdapter("IN2")); //$NON-NLS-1$
		assertConnectionError(first);

		EcoreUtil.delete(second);

		assertNoConnectionError(first);
	}

	@Test
	void acceptsAdapterBoundaryConnectionInEachDirection() {
		final var subApp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		subApp.setName("SUBAPP"); //$NON-NLS-1$
		subApp.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		subApp.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		network.getNetworkElements().add(subApp);
		final AdapterDeclaration boundary = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		boundary.setName("BOUNDARY"); //$NON-NLS-1$
		boundary.setType(adapterType);
		boundary.setIsInput(true);
		subApp.getInterface().getSockets().add(boundary);
		final AdapterDeclaration internalDestination = inputAdapter("IN1"); //$NON-NLS-1$
		subApp.getSubAppNetwork().getNetworkElements().add(internalDestination.getBlockFBNetworkElement());
		final Connection incoming = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(),
				adapter("OUT1"), boundary); //$NON-NLS-1$
		final Connection outgoing = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), boundary,
				internalDestination);
		subApp.getSubAppNetwork().getAdapterConnections().add((AdapterConnection) outgoing);

		assertNoConnectionError(incoming);
		assertNoConnectionError(outgoing);
	}

	@Test
	void preservesEventFanOut() {
		final var source = LibraryElementFactory.eINSTANCE.createEvent();
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createEventConnection(), source,
				LibraryElementFactory.eINSTANCE.createEvent());
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createEventConnection(), source,
				LibraryElementFactory.eINSTANCE.createEvent());

		assertNoConnectionError(first);
		assertNoConnectionError(second);
	}

	@Test
	void preservesDataFanOut() {
		final var source = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createDataConnection(), source,
				LibraryElementFactory.eINSTANCE.createVarDeclaration());
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createDataConnection(), source,
				LibraryElementFactory.eINSTANCE.createVarDeclaration());

		assertNoConnectionError(first);
		assertNoConnectionError(second);
	}

	@Test
	void preservesEventFanIn() {
		final var destination = LibraryElementFactory.eINSTANCE.createEvent();
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createEventConnection(),
				LibraryElementFactory.eINSTANCE.createEvent(), destination);
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createEventConnection(),
				LibraryElementFactory.eINSTANCE.createEvent(), destination);

		assertNoConnectionError(first);
		assertNoConnectionError(second);
	}

	@Test
	void preservesDuplicateConnectionError() {
		final AdapterDeclaration source = adapter("OUT1"); //$NON-NLS-1$
		final AdapterDeclaration destination = inputAdapter("IN1"); //$NON-NLS-1$
		final Connection first = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source, destination);
		final Connection second = connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source, destination);

		assertEquals(MessageFormat.format(Messages.ConnectionAnnotations_DuplicateConnection,
				source.getQualifiedName(), destination.getQualifiedName()), assertConnectionError(first).getMessage());
		assertConnectionError(second);
	}

	@Test
	void toleratesMissingAdapterEndpoints() {
		final AdapterDeclaration source = adapter("OUT1"); //$NON-NLS-1$
		final AdapterDeclaration destination = inputAdapter("IN1"); //$NON-NLS-1$
		connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source, destination);
		assertNoConnectionError(LibraryElementFactory.eINSTANCE.createAdapterConnection());
		assertNoConnectionError(connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), source, null));
		assertNoConnectionError(connect(LibraryElementFactory.eINSTANCE.createAdapterConnection(), null,
				destination));
	}

	private Connection connect(final Connection connection, final IInterfaceElement source,
			final IInterfaceElement destination) {
		connection.setSource(source);
		connection.setDestination(destination);
		if (connection instanceof final AdapterConnection adapterConnection) {
			network.getAdapterConnections().add(adapterConnection);
		} else if (connection instanceof final EventConnection eventConnection) {
			network.getEventConnections().add(eventConnection);
		} else if (connection instanceof final DataConnection dataConnection) {
			network.getDataConnections().add(dataConnection);
		}
		return connection;
	}

	private AdapterDeclaration adapter(final String name) {
		final var block = LibraryElementFactory.eINSTANCE.createFB();
		block.setName(name);
		block.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		network.getNetworkElements().add(block);
		final AdapterDeclaration adapter = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		adapter.setName(name);
		adapter.setType(adapterType);
		block.getInterface().getPlugs().add(adapter);
		return adapter;
	}

	private AdapterDeclaration inputAdapter(final String name) {
		final AdapterDeclaration adapter = adapter(name);
		adapter.setIsInput(true);
		adapter.getBlockFBNetworkElement().getInterface().getSockets().add(adapter);
		return adapter;
	}

	private static Diagnostic assertConnectionError(final Connection connection) {
		final BasicDiagnostic diagnostics = new BasicDiagnostic();
		assertFalse(LibraryElementValidator.INSTANCE.validateConnection_validateDuplicate(connection, diagnostics,
				Map.of()));
		assertEquals(Diagnostic.ERROR, diagnostics.getSeverity());
		assertEquals(1, diagnostics.getChildren().size());
		final Diagnostic diagnostic = diagnostics.getChildren().getFirst();
		assertEquals(LibraryElementValidator.CONNECTION__VALIDATE_DUPLICATE, diagnostic.getCode());
		assertSame(connection, diagnostic.getData().getFirst());
		return diagnostic;
	}

	private static void assertNoConnectionError(final Connection connection) {
		final BasicDiagnostic diagnostics = new BasicDiagnostic();
		assertTrue(LibraryElementValidator.INSTANCE.validateConnection_validateDuplicate(connection, diagnostics,
				Map.of()));
		assertEquals(Diagnostic.OK, diagnostics.getSeverity());
	}
}
