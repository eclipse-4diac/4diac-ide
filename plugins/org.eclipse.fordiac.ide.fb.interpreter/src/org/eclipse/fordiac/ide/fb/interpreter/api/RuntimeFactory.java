/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class RuntimeFactory {

	public static FBRuntimeAbstract createFrom(final FBNetworkElement fb) {
		if (null == fb) {
			return null;
		}
		if (fb instanceof SubApp) {
			return (createFrom(fb.getFbNetwork()));
		}
		return createFrom(fb.getType());
	}

	public static FBRuntimeAbstract createFrom(final FBType fbType) {
		return switch (fbType) {
		case final BasicFBType basic -> createFrom(basic);
		case final SimpleFBType simple -> createFrom(simple);
		case final FunctionFBType function -> createFrom(function);
		case final CompositeFBType composite -> createFrom(composite);
		case final ServiceInterfaceFBType service -> createFrom(service);
		default -> null;
		};
	}

	public static FBRuntimeAbstract createFrom(final FBType fbType, final String startStateName) {
		final FBRuntimeAbstract rt = createFrom(fbType);
		setStartState(rt, startStateName);
		return rt;
	}

	private static BasicFBTypeRuntime createFrom(final BasicFBType fb, final ECState startState) {
		final BasicFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		basicFBTypeRT.setBasicfbtype(fb);
		basicFBTypeRT.setActiveState(startState.getName());
		return basicFBTypeRT;
	}

	private static BasicFBTypeRuntime createFrom(final BasicFBType fb) {
		// set the start state
		final EList<ECState> stateList = fb.getECC().getECState();
		final ECState startState = stateList.stream().filter(ECState::isStartState).findAny().orElse(null);
		if (startState == null) {
			throw new IllegalArgumentException("The FB has no StartState"); //$NON-NLS-1$
		}
		return createFrom(fb, startState);
	}

	private static SimpleFBTypeRuntime createFrom(final SimpleFBType fb) {
		final SimpleFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createSimpleFBTypeRuntime();
		basicFBTypeRT.setSimpleFBType(fb);
		return basicFBTypeRT;
	}

	private static FunctionFBTypeRuntime createFrom(final FunctionFBType fb) {
		final FunctionFBTypeRuntime funcFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createFunctionFBTypeRuntime();
		funcFBTypeRT.setFunctionFBType(fb);
		return funcFBTypeRT;
	}

	private static CompositeFBTypeRuntime createFrom(final CompositeFBType fb) {
		final CompositeFBTypeRuntime compositeRT = OperationalSemanticsFactory.eINSTANCE.createCompositeFBTypeRuntime();
		compositeRT.setCompositeFBType(fb);
		final var innerRT = RuntimeFactory.createFrom(fb.getFBNetwork());
		compositeRT.setNetworkRuntime(innerRT);
		return compositeRT;
	}

	private static ServiceInterfaceFBTypeRuntime createFrom(final ServiceInterfaceFBType fb) {
		final var serviceRT = OperationalSemanticsFactory.eINSTANCE.createServiceInterfaceFBTypeRuntime();
		serviceRT.setServiceFBType(fb);
		return serviceRT;
	}

	public static FBNetworkRuntime createFrom(final FBNetwork app) {
		final FBNetworkRuntime networkRT = OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime();
		networkRT.setFbnetwork(app);
		return networkRT;
	}

	public static FBNetworkRuntime createRecursiveFrom(final FBNetwork network) {
		final var networkRT = createFrom(network);
		createInternalRuntimes(networkRT);
		return networkRT;
	}

	private static void createInternalRuntimes(final FBNetworkRuntime containerRuntime) {
		containerRuntime.getFbnetwork().getBlockFBNetworkElements().forEach(networkElement -> {

			if (containerRuntime.getTypeRuntimes().get(networkElement) != null) {
				return; // runtime already created
			}

			// handle creation of untyped subapp runtime separately
			if (networkElement instanceof final UntypedSubApp originalSubApp) {
				var possibleNetwork = originalSubApp.loadSubAppNetwork();
				var subAppWithNetowrk = originalSubApp;
				if (possibleNetwork == null) {
					// subApps in resources don't contain a network. Only the opposite does
					subAppWithNetowrk = (UntypedSubApp) originalSubApp.getOpposite();
					possibleNetwork = subAppWithNetowrk.loadSubAppNetwork();

				}
				final FBNetworkRuntime networkRuntime = RuntimeFactory.createRecursiveFrom(possibleNetwork);
				networkRuntime.setOuterNetworkRuntime(containerRuntime);
				containerRuntime.getTypeRuntimes().put(originalSubApp, networkRuntime);

				// initialize transfer data from input vars (const values in inputs)
				initializeTransferData(subAppWithNetowrk.getInterface().getInputVars(), networkRuntime);

				createInternalRuntimes(networkRuntime);
				return;
			}

			final FBType copiedType = EcoreUtil.copy(networkElement.getType());
			VariableUtils.initializeFbType(copiedType);
			final var fbRuntime = RuntimeFactory.createFrom(copiedType);
			containerRuntime.getTypeRuntimes().put(networkElement, fbRuntime);

			// handle composite FBs
			if (fbRuntime instanceof final CompositeFBTypeRuntime compositeRuntime) {
				final var compositeNetworkRuntime = compositeRuntime.getNetworkRuntime();
				compositeNetworkRuntime.setOuterNetworkRuntime(containerRuntime);

				// initialize transfer data from input vars
				initializeTransferData(networkElement.getInterface().getInputVars(), compositeNetworkRuntime);

				createInternalRuntimes(compositeNetworkRuntime);
			}

		});
	}

	private static void initializeTransferData(final List<VarDeclaration> inputVars,
			final FBNetworkRuntime networkRuntime) {
		inputVars.forEach(inputVar -> {
			final var connections = inputVar.getOutputConnections();
			for (final var conn : connections) {
				final var value = inputVar.getValue();
				networkRuntime.getTransferData().put(conn, EcoreUtil.copy(value));
			}
		});
	}

	public static void setStartState(final FBRuntimeAbstract fbRT, final String startStateName) {
		if (fbRT instanceof final BasicFBTypeRuntime basicFBTypeRT) {
			if ((basicFBTypeRT.getBasicfbtype() == null) || (basicFBTypeRT.getBasicfbtype().getECC() == null)) {
				FordiacLogHelper.logWarning("RuntimeFactory could not set start state of FBType"); //$NON-NLS-1$
				return;
			}
			if (startStateName != null) {
				final EList<ECState> stateList = basicFBTypeRT.getBasicfbtype().getECC().getECState();
				final ECState startState = stateList.stream().filter(s -> s.getName().equals(startStateName))
						.findFirst().orElse(null);
				if (startState != null) {
					basicFBTypeRT.setActiveState(startState.getName());
					return;
				}
			}
			basicFBTypeRT.setActiveState(basicFBTypeRT.getBasicfbtype().getECC().getStart().getName());
		}
	}

	private RuntimeFactory() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}

}
