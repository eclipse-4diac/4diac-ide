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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
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
		return compositeRT;
	}

	public static FBNetworkRuntime createFrom(final FBNetwork app) {
		final FBNetworkRuntime networkRT = OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime();
		networkRT.setFbnetwork(app);
		return networkRT;
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
