/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.VarInOutHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class DeploymentDebugWatchUtils {

	public record SubAppConnectionEndpoint<T extends IInterfaceElement>(T element, boolean negate) {
		SubAppConnectionEndpoint(final T element) {
			this(element, false);
		}
	}

	public static Resource getResource(final INamedElement element) {
		return switch (element) {
		case final IInterfaceElement interfaceElement -> getResource(interfaceElement.getFBNetworkElement());
		case final AdapterFB adapterFB when adapterFB
				.eContainer() instanceof final AdapterDeclaration adapterDeclaration ->
			getResource(adapterDeclaration);
		case final FBNetworkElement networkElement -> networkElement.getResource();
		case null, default -> null;
		};
	}

	public static Device getDevice(final INamedElement element) {
		final Resource resource = getResource(element);
		return resource != null ? resource.getDevice() : null;
	}

	public static String getResourceRelativeName(final INamedElement element, final Resource resource) {
		if (element == null) {
			return ""; //$NON-NLS-1$
		}
		final String qualifiedName = element.getQualifiedName();
		if (resource != null) {
			final String resourceName = resource.getQualifiedName();
			if (qualifiedName.startsWith(resourceName)) {
				return qualifiedName.substring(resourceName.length() + 1);
			}
		}
		return qualifiedName;
	}

	public static boolean isSubAppInterfaceElement(final IInterfaceElement interfaceElement) {
		final FBNetworkElement fbNetworkElement = interfaceElement.getFBNetworkElement();
		return fbNetworkElement instanceof SubApp || fbNetworkElement instanceof final AdapterFB adapterFB
				&& isSubAppInterfaceElement(adapterFB.getAdapterDecl());
	}

	@SuppressWarnings("unchecked")
	public static <T extends IInterfaceElement> Stream<T> resolveSubappInterfaceConnections(final T element) {
		if (element == null) {
			return Stream.empty();
		}
		return switch (element.getFBNetworkElement()) {
		case null -> Stream.empty();
		case final SubApp subapp -> {
			subapp.loadSubAppNetwork(); // ensure network is loaded
			if (element.isIsInput()) {
				yield (Stream<T>) element.getOutputConnections().stream().map(Connection::getDestination)
						.filter(IInterfaceElement::isIsInput) // skip inner connections to output
						.flatMap(DeploymentDebugWatchUtils::resolveSubappInterfaceConnections);
			}
			yield (Stream<T>) element.getInputConnections().stream().map(Connection::getSource)
					.filter(Predicate.not(IInterfaceElement::isIsInput)) // skip inner connections to input
					.flatMap(DeploymentDebugWatchUtils::resolveSubappInterfaceConnections);
		}
		case final AdapterFB adapterFB -> (Stream<T>) resolveSubappInterfaceConnections(adapterFB.getAdapterDecl())
				.map(AdapterDeclaration::getAdapterFB).flatMap(resolved -> resolved.findBySimpleName(element.getName()))
				.filter(IInterfaceElement.class::isInstance);
		default -> Stream.of(element);
		};
	}

	public static <T extends IInterfaceElement> Stream<SubAppConnectionEndpoint<T>> resolveSubappInterfaceEndpoints(
			final T element) {
		return resolveSubappInterfaceEndpoints(element, false);
	}

	@SuppressWarnings("unchecked")
	private static <T extends IInterfaceElement> Stream<SubAppConnectionEndpoint<T>> resolveSubappInterfaceEndpoints(
			final T element, final boolean negate) {
		if (element == null) {
			return Stream.empty();
		}
		return switch (element.getFBNetworkElement()) {
		case null -> Stream.empty();
		case final SubApp subapp -> {
			subapp.loadSubAppNetwork(); // ensure network is loaded
			yield element.isIsInput()
					// follow inner output connection to destination for input
					? element.getOutputConnections().stream()
							// skip inner connections to output
							.filter(conn -> conn.getDestination().isIsInput())
							.flatMap(conn -> resolveSubappInterfaceEndpoints((T) conn.getDestination(),
									negate ^ conn.isNegated()))
					// follow inner input connection to source for output
					: element.getInputConnections().stream()
							// skip inner connections to input
							.filter(conn -> !conn.getSource().isIsInput())
							.flatMap(conn -> resolveSubappInterfaceEndpoints((T) conn.getSource(),
									negate ^ conn.isNegated()));
		}
		case final AdapterFB adapterFB -> resolveSubappInterfaceConnections(adapterFB.getAdapterDecl())
				.map(AdapterDeclaration::getAdapterFB).flatMap(resolved -> resolved.findBySimpleName(element.getName()))
				.filter(IInterfaceElement.class::isInstance)
				.map(resolved -> new SubAppConnectionEndpoint<>((T) resolved));
		default -> Stream.of(new SubAppConnectionEndpoint<>(element, negate));
		};
	}

	public static LibraryElement evaluateWatchType(final VarDeclaration varDeclaration) {
		if (varDeclaration.isInOutVar() && varDeclaration.isArray()
				&& TypeDeclarationParser.isVariableArrayBounds(varDeclaration.getArraySize().getValue())) {
			// use type of defining InOut declaration for arrays with variable length
			return VariableOperations.evaluateResultType(VarInOutHelper.getDefiningVarInOutDeclaration(varDeclaration));
		}
		return VariableOperations.evaluateResultType(varDeclaration);
	}

	private DeploymentDebugWatchUtils() {
		throw new UnsupportedOperationException();
	}
}
