/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - refactor evaluator API
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugThread;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.ui.view.figure.FBDebugViewFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor.NullEvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.eval.fb.SamplingFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;

public class FBDebugViewRootEditPart extends AbstractGraphicalEditPart {

	private static final long MIN_UPDATE_INTERVAL = 100; // the minimal update interval between two full refresh of
	// the shown values

	private final Map<Variable<?>, InterfaceValueEntity> interfaceValues = new HashMap<>();
	private final Map<Event, EventValueEntity> eventValues = new HashMap<>();
	private long lastUpdate;

	private final EvaluatorMonitor evalMonitor = new NullEvaluatorMonitor() {

		@Override
		public void update(final Collection<? extends Variable<?>> variables, final Evaluator evaluator) {
			if (evaluator == getFBEvaluator()) {
				// our evaluator sent an update
				updateValues(variables);
			}
		}

		@Override
		public void terminated(final EvaluatorThreadPoolExecutor executor) {
			updateAllValues();
		}

		private void updateValues(final Collection<? extends Variable<?>> variables) {
			final Map<Object, EditPart> editPartRegistry = getViewer().getEditPartRegistry();
			if (shouldUpdate()) {
				Display.getDefault().asyncExec(() -> {
					variables.forEach(variable -> updateVariable(editPartRegistry, variable));
					updateAllEvents(editPartRegistry);
				});
			}
		}

		private boolean shouldUpdate() {
			final long currentTime = System.currentTimeMillis();
			final long delta = currentTime - lastUpdate;
			if (delta > MIN_UPDATE_INTERVAL) {
				lastUpdate = currentTime;
				return true;
			}
			return false;
		}
	};

	private final IDebugEventSetListener debugEventListener = events -> {
		for (final DebugEvent ev : events) {
			switch (ev.getKind()) {
			case DebugEvent.SUSPEND:
				if (isCorrectSource(ev.getSource())) {
					updateAllValues();
				}
				break;
			case DebugEvent.CHANGE:
				if (ev.getSource() instanceof final EvaluatorDebugVariable edVar) {
					final Map<Object, EditPart> editPartRegistry = getViewer().getEditPartRegistry();
					Display.getDefault().asyncExec(() -> updateVariable(editPartRegistry, edVar.getInternalVariable()));
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	public FBDebugViewFigure getFigure() {
		return (FBDebugViewFigure) super.getFigure();
	}

	@Override
	protected IFigure createFigure() {
		final IFigure newFigure = new FBDebugViewFigure(getNumWithedEventInputs(), getNumWithedEventOutputs());
		newFigure.setBorder(new MarginBorder(10));
		newFigure.setOpaque(false);
		return newFigure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy() {
			@Override
			protected EditPolicy createChildEditPolicy(final EditPart child) {
				if (child instanceof AbstractDebugInterfaceValueEditPart) {
					// we only want to provide selection feedback for debug values
					return new ModifiedNonResizeableEditPolicy(GefPreferenceConstants.CORNER_DIM_HALF, new Insets(1));
				}
				return null;
			}
		});
	}

	@Override
	public EvaluatorProcess getModel() {
		return (EvaluatorProcess) super.getModel();
	}

	@Override
	public void activate() {
		super.activate();
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), new Rectangle(0, 0, -1, -1));
		getModel().getExecutor().addMonitor(evalMonitor);
		DebugPlugin.getDefault().addDebugEventListener(debugEventListener);
		lastUpdate = System.currentTimeMillis();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getExecutor().removeMonitor(evalMonitor);
		DebugPlugin.getDefault().removeDebugEventListener(debugEventListener);
	}

	public FBEvaluator<?> getFBEvaluator() {
		return (FBEvaluator<?>) getModel().getEvaluator();
	}

	private FBType getFBType() {
		return getFBEvaluator().getType();
	}

	@Override
	protected List<?> getModelChildren() {
		final ArrayList<Object> children = new ArrayList<>();
		children.add(getFBType());
		children.addAll(getEventValues());
		children.addAll(getInterfaceValues());
		return children;
	}

	private Collection<EventValueEntity> getEventValues() {
		if (eventValues.isEmpty()) {
			fillEventValues();
		}
		return eventValues.values();
	}

	private void fillEventValues() {
		final var eventQueue = getFBEvaluator().getEventQueue();
		if (eventQueue instanceof final FBEvaluatorCountingEventQueue countingEventQueue) {
			getFBType().getInterfaceList().getEventInputs().forEach(ev -> addEventEntry(countingEventQueue, ev));
			getFBType().getInterfaceList().getEventOutputs().forEach(ev -> addEventEntry(countingEventQueue, ev));
		}
	}

	private EventValueEntity addEventEntry(final FBEvaluatorCountingEventQueue queue, final Event ev) {
		return eventValues.put(ev, new EventValueEntity(ev, queue.getCount(ev)));
	}

	private Collection<InterfaceValueEntity> getInterfaceValues() {
		if (interfaceValues.isEmpty()) {
			fillInterfaceValues();
		}
		return interfaceValues.values();
	}

	private void fillInterfaceValues() {
		final EvaluatorDebugTarget debugTarget = (EvaluatorDebugTarget) getModel().getAdapter(IDebugTarget.class);
		getFBEvaluator().getContext().getMembers().entrySet().forEach(entry -> {
			final IInterfaceElement interfaceElement = getFBType().getInterfaceList()
					.getInterfaceElement(entry.getKey());
			if (interfaceElement != null) {
				interfaceValues.put(entry.getValue(),
						new InterfaceValueEntity(interfaceElement, entry.getValue(), debugTarget));
			}
		});
		if (getFBEvaluator() instanceof final SamplingFBEvaluator samplingEvaluator) {
			samplingEvaluator.getDelegate().getContext().getMembers().entrySet().forEach(entry -> {
				final IInterfaceElement interfaceElement = getFBType().getInterfaceList()
						.getInterfaceElement(entry.getKey());
				if (interfaceElement != null) {
					interfaceValues.put(entry.getValue(),
							new InnerValueEntity(interfaceElement, entry.getValue(), debugTarget));
				}
			});
		}
	}

	private void updateVariable(final Map<Object, EditPart> editPartRegistry, final Variable<?> variable) {
		final InterfaceValueEntity interfaceValueEntity = interfaceValues.get(variable);
		if (interfaceValueEntity != null) {
			final Object ep = editPartRegistry.get(interfaceValueEntity);
			if (ep instanceof final InterfaceValueEditPart ivEP) {
				ivEP.updateValue();
				refreshVisuals();
			}
		}
	}

	private void updateAllValues() {
		final Map<Object, EditPart> editPartRegistry = getViewer().getEditPartRegistry();
		Display.getDefault().asyncExec(() -> {
			interfaceValues.entrySet().forEach(entry -> updateVariable(editPartRegistry, entry.getKey()));
			updateAllEvents(editPartRegistry);
		});
	}

	private void updateAllEvents(final Map<Object, EditPart> editPartRegistry) {
		eventValues.entrySet().stream().map(entry -> editPartRegistry.get(entry.getValue())). //
				filter(EventValueEditPart.class::isInstance).map(EventValueEditPart.class::cast). //
				forEach(EventValueEditPart::update);
	}

	private boolean isCorrectSource(final Object source) {
		return ((source instanceof final EvaluatorDebugThread eDT) && eDT.getDebugTarget().getProcess() == getModel());
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure targetFigure = getTargetFigure(childEditPart);
		if (targetFigure != null) {
			targetFigure.add(((GraphicalEditPart) childEditPart).getFigure());
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final IFigure targetFigure = getTargetFigure(childEditPart);
		if (targetFigure != null) {
			targetFigure.remove(((GraphicalEditPart) childEditPart).getFigure());
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private IFigure getTargetFigure(final EditPart childEditPart) {
		return switch (childEditPart) {
		case final FBTypeEditPart fbtEP -> getFigure().getFBFigureContainer();
		case final InnerValueEditPart innerEP ->
			(innerEP.isInput()) ? getFigure().getInnerInputValues() : getFigure().getInnerOutputValues();
		case final AbstractDebugInterfaceValueEditPart ivEP ->
			(ivEP.isInput()) ? getFigure().getOuterInputValues() : getFigure().getOuterOutputValues();
		default -> null;
		};
	}

	private int getNumWithedEventInputs() {
		return (int) getFBType().getInterfaceList().getEventInputs().stream().filter(ev -> !ev.getWith().isEmpty())
				.count();
	}

	private int getNumWithedEventOutputs() {
		return (int) getFBType().getInterfaceList().getEventOutputs().stream().filter(ev -> !ev.getWith().isEmpty())
				.count();
	}
}
