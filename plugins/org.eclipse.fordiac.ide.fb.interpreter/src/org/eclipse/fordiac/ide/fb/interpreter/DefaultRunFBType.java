/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.function.Function;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.IRunFBTypeVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.impl.EvalStatementImpl;
import org.eclipse.fordiac.ide.fb.interpreter.impl.EvaluateExpressionImpl;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils;
import org.eclipse.fordiac.ide.fb.interpreter.parser.AlgorithmStXMI;
import org.eclipse.fordiac.ide.fb.interpreter.parser.ConditionExpressionXMI;
import org.eclipse.fordiac.ide.fb.interpreter.parser.DefaultParserXMI;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;

public class DefaultRunFBType implements IRunFBTypeVisitor{

	private final EventOccurrence eventOccurrence;

	public DefaultRunFBType(EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public static Function<Object,Object> of(IRunFBTypeVisitor runTypeVisitor) {
		return new LambdaVisitor<>()
				.on(BasicFBTypeRuntime.class).then(runTypeVisitor::runFBType)
				.on(SimpleFBTypeRuntime.class).then(runTypeVisitor::runFBType)
				.on(FBNetworkRuntime.class).then(runTypeVisitor::runFBType)
				;
	}

	@SuppressWarnings("unchecked")
	public static EList<EventOccurrence> runFBType(FBRuntimeAbstract fbTypeRuntime, EventOccurrence eventOccurrence) {
		final var defaultRun = new DefaultRunFBType(eventOccurrence);
		return (EList<EventOccurrence>) of(defaultRun).apply(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runFBType(BasicFBTypeRuntime basicFBTypeRuntime) {
		// Initialization of variables
		VariableUtils.fBVariableInitialization(basicFBTypeRuntime.getBasicfbtype());
		final var outputEvents = new BasicEList<EventOccurrence>();
		// First Step: evaluate the ECC
		final var eCC = basicFBTypeRuntime.getBasicfbtype().getECC();
		//Create a resource if the BasicFBType does not have one
		final var fBTypeResource = new DefaultParserXMI().createFBResource(basicFBTypeRuntime.getBasicfbtype());
		// Active State
		final var eCState = basicFBTypeRuntime.getActiveState();
		if (eCState == null) {
			basicFBTypeRuntime.setActiveState(eCC.getStart());
		}
		var firedTransition = evaluateOutTransitions(basicFBTypeRuntime, fBTypeResource);
		while (firedTransition != null) {
			isConsumed();
			basicFBTypeRuntime.setActiveState(firedTransition.getDestination());// fire transition
			outputEvents.addAll(performEntryAction(basicFBTypeRuntime, fBTypeResource));
			firedTransition = evaluateOutTransitions(basicFBTypeRuntime, fBTypeResource);
		}
		basicFBTypeRuntime.setBasicfbtype((BasicFBType)fBTypeResource.getContents().get(0));
		// TODO can probably be improved by copying better
		for (final EventOccurrence eo : outputEvents) {
			((BasicFBTypeRuntime) eo.getFbRuntime()).setActiveState(basicFBTypeRuntime.getActiveState());
		}
		return outputEvents;
	}

	private static EList<EventOccurrence> performEntryAction(BasicFBTypeRuntime basicFBTypeRuntime,
			Resource fBTypeResource) {
		final var outputEvents = new BasicEList<EventOccurrence>();
		for (final ECAction action : basicFBTypeRuntime.getActiveState().getECAction()) {
			if (action.getAlgorithm() != null) {
				processAlgorithm(action, fBTypeResource);
			}
			if (action.getOutput() != null) {
				processOutputEvent(basicFBTypeRuntime, action, outputEvents, fBTypeResource);
			}
		}
		return outputEvents;
	}

	private static void processAlgorithm(ECAction action, Resource fBTypeResource) {
		final var textAlgorithm = (TextAlgorithm) action.getAlgorithm();
		final var resource = new AlgorithmStXMI(fBTypeResource.getResourceSet()).
				createXtextResourceFromAlgorithmSt(textAlgorithm.getText());
		final var eObjectStructuredText = resource.getContents().get(0);
		if (!(eObjectStructuredText instanceof StructuredTextAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		final var structuredText = (StructuredTextAlgorithm) eObjectStructuredText;
		final var listOfStatements = structuredText.getStatements().getStatements();
		new EvalStatementImpl().evaluateAllStatements(listOfStatements);
	}

	private static void processOutputEvent(BasicFBTypeRuntime basicFBTypeRuntime, ECAction action,
			BasicEList<EventOccurrence> outputEvents, Resource fBTypeResource) {
		final var eventOcurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		// Copy FBTypeRuntime
		final var copyRuntimeFBType = new Copier();
		final var newBasicFBTypeRT = (BasicFBTypeRuntime) copyRuntimeFBType.copy(basicFBTypeRuntime);
		copyRuntimeFBType.copyReferences();
		// Copy FBType
		final var copyFBType = new Copier();
		final var copyBasicfbtype = (BasicFBType) copyFBType.copy(fBTypeResource.getContents().get(0));
		copyFBType.copyReferences();
		//Add copy FBType to the RuntimeFBType
		newBasicFBTypeRT.setBasicfbtype(copyBasicfbtype);
		eventOcurrence.setFbRuntime(newBasicFBTypeRT);
		// Event
		eventOcurrence.setEvent(action.getOutput());
		outputEvents.add(eventOcurrence);
	}

	private ECTransition evaluateOutTransitions(BasicFBTypeRuntime basicFBTypeRuntime, Resource fBTypeResource) {
		final var outTransitions = basicFBTypeRuntime.getActiveState().getOutTransitions();
		for (final ECTransition outTransition : outTransitions) {
			if (transitionCanFire(outTransition, fBTypeResource)) {
				return outTransition;
			}
		}
		return null;
	}

	private boolean transitionCanFire(ECTransition outTransition, Resource fBTypeResource) {
		final var event = outTransition.getConditionEvent();
		if (transitionHoldsFor(event)) {
			final var condExpression = outTransition.getConditionExpression();
			if (condExpression.isEmpty() || "1".equals(condExpression)) { //$NON-NLS-1$
				return true;
			}
			final var resource = new ConditionExpressionXMI(fBTypeResource.getResourceSet())
					.createXtextResourceFromConditionExp(condExpression);
			final var rootEObject = resource.getContents().get(0);
			if (rootEObject instanceof Expression) {
				final var evaluation = (Boolean) EvaluateExpressionImpl.of().apply(rootEObject);
				if (Boolean.TRUE.equals(evaluation)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean transitionHoldsFor(final Event event) {
		return (event == null) ||
				(event.getName().equals(this.eventOccurrence.getEvent().getName()) && this.eventOccurrence.isActive());
	}

	private void isConsumed() {
		this.eventOccurrence.setActive(false);
		// The event was consumed, so it was not ignored
		this.eventOccurrence.setIgnored(false);
	}

	@Override
	public EList<EventOccurrence> runFBType(SimpleFBTypeRuntime fBTypeRuntime) {
		throw new UnsupportedOperationException("Not supported operation runFBType(FBTypeRuntime fBTypeRuntime)"); //$NON-NLS-1$
	}

	@Override
	public EList<EventOccurrence> runFBType(FBNetworkRuntime fBNetworkRuntime) {
		throw new UnsupportedOperationException("Not supported operation runFBType(FBTypeRuntime fBTypeRuntime)"); //$NON-NLS-1$
	}
}
