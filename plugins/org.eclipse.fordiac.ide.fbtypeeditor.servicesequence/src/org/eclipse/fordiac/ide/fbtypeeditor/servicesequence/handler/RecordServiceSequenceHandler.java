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
 *  Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RecordServiceSequenceHandler extends AbstractHandler {

	private static final int CANCEL = -1;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			final ServiceSequence seq = getSequence(selected);
			if (seq != null) {
				final List<String> events = new ArrayList<>();
				final List<String> parameters = new ArrayList<>();
				final RecordSequenceDialog dialog = new RecordSequenceDialog(HandlerUtil.getActiveShell(event), events,
						parameters, seq);
				final int returnCode = dialog.open();
				if (returnCode != CANCEL) {
					try {
						final BasicFBType fbType = (BasicFBType) seq.getService().getFBType();
						setParameters(fbType, parameters);
						final EventManager eventManager = createEventManager(fbType, events);
						EventManagerUtils.process(eventManager);
						if (!dialog.isAppend()) {
							seq.getServiceTransaction().clear();
						}
						for (final Transaction transaction : eventManager.getTransactions()) {
							convertTransactionToServiceModel(seq, fbType, (FBTransaction) transaction);
						}
					} catch (final Exception e) {
						MessageDialog.openError(HandlerUtil.getActiveShell(event), "Problem",
								"The input data specification was incorrect. Please try again and check the variable names");
					}
				}
			}
		}
		return Status.OK_STATUS;
	}

	private static void setParameters(final BasicFBType fbType, final List<String> parameters) {
		// parameter: format "VarName:=Value"
		for (final String param : parameters) {
			final String[] paramValues = param.split(":="); //$NON-NLS-1$
			if (paramValues.length == 2) {
				AbstractInterpreterTest.setVariable(fbType, paramValues[0], paramValues[1]);
			}
		}
	}

	private static void convertTransactionToServiceModel(final ServiceSequence seq, final BasicFBType fbType,
			final FBTransaction transaction) {
		final ServiceTransaction serviceTransaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		seq.getServiceTransaction().add(serviceTransaction);
		final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		inputPrimitive.setEvent(transaction.getInputEventOccurrence().getEvent().getName());
		inputPrimitive.setInterface(fbType.getService().getLeftInterface());
		serviceTransaction.setInputPrimitive(inputPrimitive);
		for (final EventOccurrence outputEvent : transaction.getOutputEventOccurrences()) {
			final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
			outputPrimitive.setEvent(outputEvent.getEvent().getName());
			outputPrimitive.setInterface(fbType.getService().getLeftInterface());
			serviceTransaction.getOutputPrimitive().add(outputPrimitive);
		}
	}

	private static EventManager createEventManager(final BasicFBType fb, final List<String> events) {
		if (fb.getService() == null) {
			fb.setService(ServiceSequenceUtils.createEmptyServiceModel());
		}
		final ResourceSet reset = new ResourceSetImpl();
		final Resource resource = reset.createResource(URI.createURI("platform:/resource/" + fb.getName() + ".xmi")); //$NON-NLS-1$ //$NON-NLS-2$
		final EventManager eventManager = OperationalSemanticsFactory.eINSTANCE.createEventManager();
		resource.getContents().add(eventManager);
		final BasicFBTypeRuntime basicFBTypeRT = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		basicFBTypeRT.setBasicfbtype(fb);
		// set the start state
		final EList<ECState> stateList = basicFBTypeRT.getBasicfbtype().getECC().getECState();
		final ECState startState = stateList.stream().filter(ECState::isStartState).collect(Collectors.toList()).get(0);
		basicFBTypeRT.setActiveState(startState);

		// create transactions
		for (final String inputEvent : events) {
			final EventOccurrence eventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
			final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
			if (eventPin == null) {
				throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
			}
			eventOccurrence.setEvent(eventPin);

			final Transaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
			transaction.setInputEventOccurrence(eventOccurrence);
			eventManager.getTransactions().add(transaction);
		}

		// set initial runtime for first event
		eventManager.getTransactions().get(0).getInputEventOccurrence().setFbRuntime(basicFBTypeRT);

		return eventManager;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			Object selected = ((StructuredSelection) selection).getFirstElement();
			if (selected instanceof EditPart) {
				selected = ((EditPart) selected).getModel();
			}
			if (selected instanceof ServiceSequence) {
				setBaseEnabled(((ServiceSequence) selected).getService().getFBType() instanceof BasicFBType);
			}
		}
	}

	private static ServiceSequence getSequence(final Object selected) {
		if (selected instanceof ServiceSequenceEditPart) {
			return ((ServiceSequenceEditPart) selected).getModel();
		}
		if (selected instanceof ServiceSequence) {
			return (ServiceSequence) selected;
		}
		return null;
	}

	private static class RecordSequenceDialog extends MessageDialog {
		private Text inputEventText;
		private Text inputParameterText;
		private CCombo inputStartStateCombo;
		private Button appendCheckbox;
		private final List<String> events;
		private final List<String> parameters;
		private boolean append;
		private final ServiceSequence serviceSequence;

		public RecordSequenceDialog(final Shell parentShell, final List<String> events, final List<String> parameters,
				final ServiceSequence serviceSequence) {
			super(parentShell, "Record Sequence (separated by ;)", null, "Configuration", MessageDialog.INFORMATION, 0,
					"Run");
			this.events = events;
			this.parameters = parameters;
			this.serviceSequence = serviceSequence;
		}

		@Override
		protected Control createCustomArea(final Composite parent) {
			parent.setLayout(new FillLayout());
			final Composite dialogArea = new Composite(parent, SWT.NONE);
			final GridLayout layout = new GridLayout(2, false);
			dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
			dialogArea.setLayout(layout);

			final Group group = new Group(dialogArea, SWT.NONE);
			group.setText("Input Data");
			group.setLayout(new GridLayout(2, false));
			group.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

			Label label = new Label(group, SWT.None);
			label.setText("Input Event(s)");

			inputEventText = new Text(group, SWT.NONE);
			inputEventText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

			label = new Label(group, SWT.None);
			label.setText("Parameters");

			inputParameterText = new Text(group, SWT.NONE);
			inputParameterText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

			label = new Label(group, SWT.None);
			label.setText("Start State");

			inputStartStateCombo = ComboBoxWidgetFactory.createCombo(group);
			inputStartStateCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
			final FBType fbtype = serviceSequence.getService().getFBType();
			if (fbtype instanceof BasicFBType) {
				final String[] startnames = Stream
						.concat(((BasicFBType) fbtype).getECC().getECState().stream().map(ECState::getName),
								Stream.of("")) //$NON-NLS-1$
						.toArray(String[]::new);
				inputStartStateCombo.setItems(startnames);
				inputStartStateCombo.select(Arrays.asList(startnames).indexOf("")); //$NON-NLS-1$
				inputStartStateCombo.select(CANCEL);
			} else {
				inputStartStateCombo.setEnabled(false);
			}

			appendCheckbox = new Button(group, SWT.CHECK);
			appendCheckbox.setText("Append");
			appendCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
			return dialogArea;
		}

		@Override
		protected void buttonPressed(final int buttonId) {
			events.addAll(getEvents());
			parameters.addAll(getParameters());
			append = appendCheckbox.getSelection();
			super.buttonPressed(buttonId);
		}

		private List<String> getEvents() {
			return Arrays.asList(inputEventText.getText().split(";")); //$NON-NLS-1$
		}

		private List<String> getParameters() {
			return Arrays.asList(inputParameterText.getText().split(";")); //$NON-NLS-1$
		}

		public boolean isAppend() {
			return append;
		}
	}
}
