/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RecordServiceSequenceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			final ServiceSequence seq = getSequence(selected);
			if (seq != null) {
				final List<String> events = new ArrayList<>();
				final RecordSequenceDialog dialog = new RecordSequenceDialog(HandlerUtil.getActiveShell(event),
						events);
				dialog.open();
				final BasicFBType fbType = (BasicFBType) seq.getService().getFBType();
				final EventManager eventManager = createEventManager(fbType,
						events);
				EventManagerUtils.process(eventManager);
				for (final Transaction transaction : eventManager.getTransactions()) {
					convertTransactionToServiceModel(seq, fbType, transaction);
				}
			}
		}
		return Status.OK_STATUS;
	}


	private static void convertTransactionToServiceModel(final ServiceSequence seq, final BasicFBType fbType,
			final Transaction transaction) {
		final ServiceTransaction serviceTransaction = LibraryElementFactory.eINSTANCE
				.createServiceTransaction();
		seq.getServiceTransaction().add(serviceTransaction);
		final InputPrimitive inputPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		inputPrimitive.setEvent(transaction.getInputEventOccurrence().getEvent().getName());
		inputPrimitive.setInterface(fbType.getService().getLeftInterface());
		serviceTransaction.setInputPrimitive(inputPrimitive);
		for (final EventOccurrence outputEvent : transaction.getOutputEventOccurences()) {
			final OutputPrimitive outputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
			outputPrimitive.setEvent(outputEvent.getEvent().getName());
			outputPrimitive.setInterface(fbType.getService().getLeftInterface());
			serviceTransaction.getOutputPrimitive().add(outputPrimitive);
		}
	}


	private static EventManager createEventManager(BasicFBType fb, List<String> events) {
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
		final ECState startState = stateList.stream().filter(ECState::isStartState)
				.collect(Collectors.toList()).get(0);
		basicFBTypeRT.setActiveState(startState);

		// create transactions
		for (final String inputEvent : events) {
			final EventOccurrence eventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
			final Event eventPin = (Event) fb.getInterfaceList().getInterfaceElement(inputEvent);
			if (eventPin == null) {
				throw new IllegalArgumentException("input primitive: event " + inputEvent + " does not exist");  //$NON-NLS-1$//$NON-NLS-2$
			}
			eventOccurrence.setEvent(eventPin);

			final Transaction transaction = OperationalSemanticsFactory.eINSTANCE.createTransaction();
			transaction.setInputEventOccurrence(eventOccurrence);
			eventManager.getTransactions().add(transaction);
		}

		// set initial runtime for first event
		eventManager.getTransactions().get(0).getInputEventOccurrence().setFbRuntime(basicFBTypeRT);

		return eventManager;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
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
		} else if (selected instanceof ServiceSequence) {
			return (ServiceSequence) selected;
		}
		return null;
	}

	public static class RecordSequenceDialog extends MessageDialog {
		private Text text;
		private final List<String> events;

		public RecordSequenceDialog(Shell parentShell, List<String> events) {
			super(parentShell, "Record Sequence (separated by ;)", null, "Input Events:", MessageDialog.INFORMATION, 0,
					"Run");
			this.events = events;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			final Composite container = new Composite(parent, SWT.NONE);
			final GridLayout layout = new GridLayout(2, false);
			container.setLayout(layout);
			text = new Text(container, SWT.NONE);
			text.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
			return container;
		}

		@Override
		protected void buttonPressed(int buttonId) {
			events.addAll(getEvents());
			super.buttonPressed(buttonId);
		}

		public List<String> getEvents() {
			return Arrays.asList(text.getText().split(";")); //$NON-NLS-1$
		}
	}
}
