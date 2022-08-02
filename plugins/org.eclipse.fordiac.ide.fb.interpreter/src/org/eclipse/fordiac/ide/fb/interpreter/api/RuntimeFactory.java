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

import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class RuntimeFactory {

	public static FBRuntimeAbstract createFrom(final FBNetworkElement fb) {
		if (fb instanceof SubApp) {
			return (createFrom(fb.getFbNetwork()));
		}
		return createFrom(fb.getType());
	}

	public static FBRuntimeAbstract createFrom(final FBType fbType) {
		if (fbType instanceof BasicFBType) {
			return (createFrom((BasicFBType) fbType));
		}
		if (fbType instanceof SimpleFBType) {
			return (createFrom((SimpleFBType) fbType));
		}
		if (fbType instanceof CompositeFBType) {
			return (createFrom((CompositeFBType) fbType));
		}
		return null;
	}

	private static FBRuntimeAbstract createFrom(final BasicFBType fb, final ECState startState) {
		final BasicFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		basicFBTypeRT.setBasicfbtype(fb);
		basicFBTypeRT.setActiveState(startState);
		return basicFBTypeRT;
	}
	private static FBRuntimeAbstract createFrom(final BasicFBType fb) {
		// set the start state
		final EList<ECState> stateList = fb.getECC().getECState();
		final ECState startState = stateList.stream().filter(ECState::isStartState).collect(Collectors.toList()).get(0);
		return createFrom(fb, startState);
	}

	private static FBRuntimeAbstract createFrom(final SimpleFBType fb) {
		final SimpleFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createSimpleFBTypeRuntime();
		basicFBTypeRT.setSimpleFBType(fb);
		return basicFBTypeRT;
	}

	private static FBRuntimeAbstract createFrom(final CompositeFBType fb) {
		final FBNetworkRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime();
		basicFBTypeRT.setFbnetwork(fb.getFBNetwork());
		return basicFBTypeRT;
	}

	public static FBRuntimeAbstract createFrom(final FBNetwork app) {
		final FBNetworkRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime();
		basicFBTypeRT.setFbnetwork(app);
		return basicFBTypeRT;
	}

	private RuntimeFactory() {
		throw new UnsupportedOperationException("this class should not be instantiated"); //$NON-NLS-1$
	}

}
