/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.Command;

/**
 * Stable input for adding a struct member through a connection gesture.
 *
 * @implNote This context deliberately stores URIs and interface paths rather
 *           than model objects. A configurable block may be replaced while the
 *           refactoring is running.
 */
public final class AddStructMemberContext {
	private static final String DEFAULT_MEMBER_NAME = "member"; //$NON-NLS-1$

	private final URI structTypeURI;
	private final URI targetModelURI;
	private final String structTypeName;
	private final String connectionTypeName;
	private final String connectionArraySize;
	private final PinLocator connectionPin;
	private final TargetLocator target;
	private final AddStructMemberConfiguration initialConfiguration;
	private final boolean typeSelectionRequired;
	private final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory;

	private AddStructMemberContext(final StructuredType structType) {
		structTypeURI = EcoreUtil.getURI(structType);
		targetModelURI = null;
		structTypeName = PackageNameHelper.getFullTypeName(structType);
		connectionTypeName = ""; //$NON-NLS-1$
		connectionArraySize = ""; //$NON-NLS-1$
		connectionPin = null;
		target = null;
		borderCrossingCommandFactory = null;
		typeSelectionRequired = true;
		initialConfiguration = new AddStructMemberConfiguration(
				createMemberName(structType, DEFAULT_MEMBER_NAME), "", "", "", null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private AddStructMemberContext(final StructuredType structType, final LibraryElement targetModel,
			final VarDeclaration connectionPin, final TargetLocator target,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		structTypeURI = EcoreUtil.getURI(structType);
		targetModelURI = EcoreUtil.getURI(targetModel).trimFragment();
		structTypeName = PackageNameHelper.getFullTypeName(structType);
		connectionTypeName = PackageNameHelper.getFullTypeName(connectionPin.getType());
		connectionArraySize = getArraySize(connectionPin);
		this.connectionPin = PinLocator.of(connectionPin);
		this.target = target;
		this.borderCrossingCommandFactory = borderCrossingCommandFactory;
		typeSelectionRequired = GenericTypes.isAnyType(connectionPin.getType());
		initialConfiguration = new AddStructMemberConfiguration(createMemberName(structType, connectionPin.getName()),
				connectionPin.getComment(), typeSelectionRequired ? "" : connectionTypeName, connectionArraySize, //$NON-NLS-1$
				null);
	}

	public static Optional<AddStructMemberContext> forTarget(final VarDeclaration connectionPin,
			final EObject target) {
		return forTarget(connectionPin, target, null);
	}

	public static Optional<AddStructMemberContext> forTarget(final VarDeclaration connectionPin, final EObject target,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		if (target instanceof final StructManipulator manipulator) {
			return forStructManipulator(connectionPin, manipulator, borderCrossingCommandFactory);
		}
		if (target instanceof final VarDeclaration structPin) {
			return forStructPin(connectionPin, structPin, borderCrossingCommandFactory);
		}
		return Optional.empty();
	}

	/**
	 * Create a context for adding a member to the type of a selected struct pin.
	 *
	 * @return a context, or an empty optional if the pin does not reference a
	 *         concrete structured type
	 */
	public static Optional<AddStructMemberContext> forStructPin(final VarDeclaration structPin) {
		if (structPin == null || structPin.isArray()
				|| !(structPin.getType() instanceof final StructuredType structType)
				|| structType instanceof ErrorDataType || structType == GenericTypes.ANY_STRUCT
				|| structType.getTypeEntry() == null) {
			return Optional.empty();
		}
		return Optional.of(new AddStructMemberContext(structType));
	}

	/**
	 * Create a context for a drop on a structured data pin.
	 *
	 * @return a context, or an empty optional if the pins do not describe a
	 *         supported direct-network connection
	 */
	public static Optional<AddStructMemberContext> forStructPin(final VarDeclaration connectionPin,
			final VarDeclaration structPin) {
		return forStructPin(connectionPin, structPin, null);
	}

	private static Optional<AddStructMemberContext> forStructPin(final VarDeclaration connectionPin,
			final VarDeclaration structPin,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		if (connectionPin == null || structPin == null || structPin.isArray()
				|| !(structPin.getType() instanceof final StructuredType structType)
				|| structType == GenericTypes.ANY_STRUCT || structType.getTypeEntry() == null
				|| !(structPin instanceof ContainerVarDeclaration) || !LinkConstraints.isWithConstraintOK(structPin)
				|| !isSupportedStructPinConnection(connectionPin, structPin, borderCrossingCommandFactory)
				|| connectionPin.getBlockFBNetworkElement() == structPin.getBlockFBNetworkElement()) {
			return Optional.empty();
		}
		return getRootLibraryElement(connectionPin)
				.map(root -> new AddStructMemberContext(structType, root, connectionPin,
						TargetLocator.forStructPin(PinLocator.of(structPin)), borderCrossingCommandFactory));
	}

	/**
	 * Create a context for a drop on a configured struct multiplexer or
	 * demultiplexer.
	 *
	 * @return a context, or an empty optional if the elements do not describe a
	 *         supported direct-network connection
	 */
	public static Optional<AddStructMemberContext> forStructManipulator(final VarDeclaration connectionPin,
			final StructManipulator manipulator) {
		return forStructManipulator(connectionPin, manipulator, null);
	}

	private static Optional<AddStructMemberContext> forStructManipulator(final VarDeclaration connectionPin,
			final StructManipulator manipulator,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		if (connectionPin == null || manipulator == null
				|| !(manipulator.getDataType() instanceof final StructuredType structType)
				|| structType instanceof ErrorDataType || structType == GenericTypes.ANY_STRUCT
				|| structType.getTypeEntry() == null) {
			return Optional.empty();
		}

		final StructMemberTargetKind kind;
		final boolean memberIsInput;
		if (manipulator instanceof Multiplexer) {
			kind = StructMemberTargetKind.MULTIPLEXER;
			memberIsInput = true;
		} else if (manipulator instanceof Demultiplexer) {
			kind = StructMemberTargetKind.DEMULTIPLEXER;
			memberIsInput = false;
		} else {
			return Optional.empty();
		}

		if (!isSupportedManipulatorConnection(connectionPin, manipulator, memberIsInput,
				borderCrossingCommandFactory)
				|| connectionPin.getBlockFBNetworkElement() == manipulator) {
			return Optional.empty();
		}
		return getRootLibraryElement(connectionPin)
				.map(root -> new AddStructMemberContext(structType, root, connectionPin,
						TargetLocator.forManipulator(EcoreUtil.getURI(manipulator), kind), borderCrossingCommandFactory));
	}

	public AddStructMemberConfiguration getInitialConfiguration() {
		return initialConfiguration;
	}

	public boolean isTypeSelectionRequired() {
		return typeSelectionRequired;
	}

	boolean hasConnection() {
		return connectionPin != null;
	}

	StructMemberTargetKind getTargetKind() {
		return target.kind();
	}

	public IProject getProject() {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE
				.getTypeEntryForURI(hasConnection() ? targetModelURI : structTypeURI);
		return typeEntry != null && typeEntry.getFile() != null ? typeEntry.getFile().getProject() : null;
	}

	public TypeLibrary getTypeLibrary() {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(structTypeURI);
		return typeEntry != null ? typeEntry.getTypeLibrary() : null;
	}

	URI getStructTypeURI() {
		return structTypeURI;
	}

	URI getTargetModelURI() {
		return targetModelURI;
	}

	String getStructTypeName() {
		return structTypeName;
	}

	String getStructTypeFileName() {
		return structTypeURI.trimFragment().lastSegment();
	}

	String getConnectionTypeName() {
		return connectionTypeName;
	}

	String getConnectionArraySize() {
		return connectionArraySize;
	}

	PinLocator getConnectionPin() {
		return connectionPin;
	}

	TargetLocator getTarget() {
		return target;
	}

	VarDeclaration resolveConnectionPin(final LibraryElement root, final boolean demandCreate) {
		return resolvePin(root, connectionPin, demandCreate);
	}

	VarDeclaration resolveStructPin(final LibraryElement root, final boolean demandCreate) {
		if (target.kind() != StructMemberTargetKind.STRUCT_PIN) {
			return null;
		}
		return resolvePin(root, new PinLocator(target.blockURI(), target.pinPath(), target.input()), demandCreate);
	}

	StructManipulator resolveStructManipulator(final LibraryElement root) {
		if (target.kind() == StructMemberTargetKind.STRUCT_PIN) {
			return null;
		}
		return resolveBlock(root, target.blockURI()) instanceof final StructManipulator manipulator ? manipulator : null;
	}

	Command createBorderCrossingConnectionCommand(final IInterfaceElement source, final IInterfaceElement destination) {
		return borderCrossingCommandFactory != null ? borderCrossingCommandFactory.apply(source, destination) : null;
	}

	boolean matches(final LibraryElement root) {
		return matches(root, false);
	}

	boolean matches(final LibraryElement root, final boolean demandCreateConnectionPin) {
		final VarDeclaration resolvedConnectionPin = resolveConnectionPin(root, demandCreateConnectionPin);
		if (resolvedConnectionPin == null
				|| !connectionTypeName.equals(PackageNameHelper.getFullTypeName(resolvedConnectionPin.getType()))
				|| !connectionArraySize.equals(getArraySize(resolvedConnectionPin))
				|| !LinkConstraints.hasValidWithConstraint(resolvedConnectionPin)) {
			return false;
		}

		if (target.kind() == StructMemberTargetKind.STRUCT_PIN) {
			final VarDeclaration structPin = resolveStructPin(root, false);
			return structPin instanceof ContainerVarDeclaration && !structPin.isArray()
					&& LinkConstraints.hasValidWithConstraint(structPin)
					&& structPin.getType() instanceof final StructuredType structuredType
					&& structTypeName.equals(PackageNameHelper.getFullTypeName(structuredType))
					&& isSupportedStructPinConnection(resolvedConnectionPin, structPin,
							borderCrossingCommandFactory)
					&& resolvedConnectionPin.getBlockFBNetworkElement() != structPin.getBlockFBNetworkElement();
		}

		final StructManipulator manipulator = resolveStructManipulator(root);
		return manipulator != null && target.kind().matches(manipulator)
				&& manipulator.getDataType() instanceof final StructuredType structuredType
				&& structTypeName.equals(PackageNameHelper.getFullTypeName(structuredType))
				&& isSupportedManipulatorConnection(resolvedConnectionPin, manipulator, target.input(),
						borderCrossingCommandFactory)
				&& resolvedConnectionPin.getBlockFBNetworkElement() != manipulator;
	}

	private VarDeclaration resolvePin(final LibraryElement root, final PinLocator locator,
			final boolean demandCreate) {
		final BlockFBNetworkElement block = resolveBlock(root, locator.blockURI());
		if (block == null) {
			return null;
		}
		return block.getInterface().getInterfaceElement(locator.path(), demandCreate) instanceof final VarDeclaration pin
				&& pin.isIsInput() == locator.input() ? pin : null;
	}

	private BlockFBNetworkElement resolveBlock(final LibraryElement root, final URI blockURI) {
		if (root == null || root.eResource() == null || !blockURI.hasFragment()
				|| !Objects.equals(targetModelURI, blockURI.trimFragment())) {
			return null;
		}
		return root.eResource().getEObject(blockURI.fragment()) instanceof final BlockFBNetworkElement block ? block : null;
	}

	private static Optional<LibraryElement> getRootLibraryElement(final EObject element) {
		final EObject root = EcoreUtil.getRootContainer(element);
		return root instanceof final LibraryElement libraryElement && root.eResource() != null
				? Optional.of(libraryElement)
				: Optional.empty();
	}

	private static boolean isSupportedStructPinConnection(final VarDeclaration connectionPin,
			final VarDeclaration structPin,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		if (!hasValidConnectionPin(connectionPin) || structPin.getBlockFBNetworkElement() == null) {
			return false;
		}
		final ConnectionEndpoints endpoints = getDirectConnectionEndpoints(connectionPin, structPin);
		if (endpoints != null) {
			return endpoints.destination().getInputConnections().isEmpty();
		}
		return borderCrossingCommandFactory != null
				&& borderCrossingCommandFactory.apply(connectionPin, structPin) != null;
	}

	private static boolean isSupportedManipulatorConnection(final VarDeclaration connectionPin,
			final StructManipulator manipulator, final boolean memberIsInput,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		if (!hasValidConnectionPin(connectionPin) || manipulator.getFbNetwork() == null) {
			return false;
		}
		final VarDeclaration representativeMember = getRepresentativeMember(manipulator, memberIsInput);
		if (representativeMember != null) {
			final ConnectionEndpoints endpoints = getDirectConnectionEndpoints(connectionPin, representativeMember);
			if (endpoints != null) {
				return endpoints.destination() != connectionPin || connectionPin.getInputConnections().isEmpty();
			}
			return borderCrossingCommandFactory != null
					&& borderCrossingCommandFactory.apply(connectionPin, representativeMember) != null;
		}
		final FBNetwork network = manipulator.getFbNetwork();
		final boolean directionValid = memberIsInput ? LinkConstraints.isValidConnSource(connectionPin, network)
				: LinkConstraints.isValidConnDestination(connectionPin, network);
		if (directionValid) {
			return true;
		}
		final VarDeclaration structPin = memberIsInput ? manipulator.getInterface().getOutputVars().getFirst()
				: manipulator.getInterface().getInputVars().getFirst();
		return borderCrossingCommandFactory != null && borderCrossingCommandFactory.apply(
				memberIsInput ? connectionPin : structPin, memberIsInput ? structPin : connectionPin) != null;
	}

	private static boolean hasValidConnectionPin(final VarDeclaration connectionPin) {
		return connectionPin.getType() != null && !(connectionPin.getType() instanceof ErrorDataType)
				&& connectionPin.getBlockFBNetworkElement() != null;
	}

	private static VarDeclaration getRepresentativeMember(final StructManipulator manipulator,
			final boolean memberIsInput) {
		return manipulator.getInterface().getAllInterfaceElements().filter(VarDeclaration.class::isInstance)
				.map(VarDeclaration.class::cast).filter(pin -> pin.isIsInput() == memberIsInput).findFirst().orElse(null);
	}

	static ConnectionEndpoints getDirectConnectionEndpoints(final IInterfaceElement first,
			final IInterfaceElement second) {
		final BlockFBNetworkElement firstBlock = first.getBlockFBNetworkElement();
		final BlockFBNetworkElement secondBlock = second.getBlockFBNetworkElement();
		if (firstBlock == null || secondBlock == null) {
			return null;
		}
		final FBNetwork firstNetwork = firstBlock.getFbNetwork();
		final ConnectionEndpoints firstResult = getConnectionEndpoints(first, second, firstNetwork);
		if (firstResult != null) {
			return firstResult;
		}
		final FBNetwork secondNetwork = secondBlock.getFbNetwork();
		return secondNetwork != firstNetwork ? getConnectionEndpoints(first, second, secondNetwork) : null;
	}

	private static ConnectionEndpoints getConnectionEndpoints(final IInterfaceElement first,
			final IInterfaceElement second, final FBNetwork network) {
		if (network != null && LinkConstraints.isValidConnSource(first, network)
				&& LinkConstraints.isValidConnDestination(second, network)) {
			return new ConnectionEndpoints(network, first, second);
		}
		if (network != null && LinkConstraints.isValidConnSource(second, network)
				&& LinkConstraints.isValidConnDestination(first, network)) {
			return new ConnectionEndpoints(network, second, first);
		}
		return null;
	}

	private static String createMemberName(final StructuredType structType, final String proposal) {
		String result = proposal;
		while (StructMemberRefactoringSupport.containsMember(structType, result)) {
			result = NameRepository.createUniqueName(proposal, result);
		}
		return result;
	}

	record ConnectionEndpoints(FBNetwork network, IInterfaceElement source, IInterfaceElement destination) {
		ConnectionEndpoints {
			Objects.requireNonNull(network);
			Objects.requireNonNull(source);
			Objects.requireNonNull(destination);
		}
	}

	record PinLocator(URI blockURI, List<String> path, boolean input) {
		PinLocator {
			blockURI = Objects.requireNonNull(blockURI);
			path = List.copyOf(path);
		}

		static PinLocator of(final VarDeclaration pin) {
			return new PinLocator(EcoreUtil.getURI(Objects.requireNonNull(pin.getBlockFBNetworkElement())),
					pin.getBlockRelativePath(), pin.isIsInput());
		}
	}

	record TargetLocator(URI blockURI, List<String> pinPath, StructMemberTargetKind kind, boolean input) {
		TargetLocator {
			blockURI = Objects.requireNonNull(blockURI);
			pinPath = List.copyOf(pinPath);
			kind = Objects.requireNonNull(kind);
		}

		static TargetLocator forStructPin(final PinLocator pin) {
			return new TargetLocator(pin.blockURI(), pin.path(), StructMemberTargetKind.STRUCT_PIN, pin.input());
		}

		static TargetLocator forManipulator(final URI blockURI, final StructMemberTargetKind kind) {
			return new TargetLocator(blockURI, List.of(), kind, kind == StructMemberTargetKind.MULTIPLEXER);
		}
	}
}
