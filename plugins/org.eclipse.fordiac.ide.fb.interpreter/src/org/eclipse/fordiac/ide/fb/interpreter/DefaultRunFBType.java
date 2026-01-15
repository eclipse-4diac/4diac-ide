/*******************************************************************************
 * Copyright (c) 2021, 2025 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek - cleanup
 *   Felix Schmid - implemented functions and subapps
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.IRunFBTypeVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.model.eval.Evaluator;

public class DefaultRunFBType implements IRunFBTypeVisitor {

	private final EventOccurrence eventOccurrence;
	private static final Map<String, Evaluator> evaluatorCache = new HashMap<>();

	public static void clearCaches() {
		evaluatorCache.clear();
	}

	private DefaultRunFBType(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public static Function<Object, Object> of(final IRunFBTypeVisitor runTypeVisitor) {
		return new LambdaVisitor<>() //
				.on(BasicFBTypeRuntime.class).then(runTypeVisitor::runBasicFBType) //
				.on(SimpleFBTypeRuntime.class).then(runTypeVisitor::runSimpleFBType) //
				.on(FunctionFBTypeRuntime.class).then(runTypeVisitor::runFunctionFBType) //
				.on(CompositeFBTypeRuntime.class).then(runTypeVisitor::runCompositeFBType) //
				.on(FBNetworkRuntime.class).then(runTypeVisitor::runFBNetwork) //
				.on(ServiceInterfaceFBTypeRuntime.class).then(runTypeVisitor::runServiceInterfaceFBType);
	}

	@SuppressWarnings("unchecked")
	public static EList<EventOccurrence> runFBType(final FBRuntimeAbstract fbTypeRuntime,
			final EventOccurrence eventOccurrence) {
		final var defaultRun = new DefaultRunFBType(eventOccurrence);
		return (EList<EventOccurrence>) of(defaultRun).apply(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runBasicFBType(final BasicFBTypeRuntime fBTypeRuntime) {
		return new BasicFBTypeDefaultInterpreter(eventOccurrence, evaluatorCache).run(fBTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runSimpleFBType(final SimpleFBTypeRuntime fBTypeRuntime) {
		return new SimpleFBTypeDefaultInterpreter(eventOccurrence, evaluatorCache).run(fBTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runFunctionFBType(final FunctionFBTypeRuntime fBTypeRuntime) {
		return new FunctionFBTypeDefaultInterpreter(eventOccurrence, evaluatorCache).run(fBTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runCompositeFBType(final CompositeFBTypeRuntime fbTypeRuntime) {
		return new CompositeFBTypeDefaultInterpreter(eventOccurrence).run(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runServiceInterfaceFBType(final ServiceInterfaceFBTypeRuntime fbTypeRuntime) {
		return new ServiceInterfaceFBTypeDefaultInterpreter(eventOccurrence).run(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runFBNetwork(final FBNetworkRuntime fBNetworkRuntime) {
		return new FBNetworkDefaultInterpreter(eventOccurrence).run(fBNetworkRuntime);
	}

}
