/*******************************************************************************
 * Copyright (c) 2021, 2023 Johannes Kepler University Linz and others
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
 *  Paul Pavlicek
 *     - cleanup and extracting code, added random generation
 *  Felix Roithmayr
 *     - added extra support for context menu entry
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.InputGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers.CoverageCalculator;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers.EventManagerSaveAndLoadHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	private String startState;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			ServiceSequence seq = getSequence(selected);
			if ((seq == null) && (selected instanceof SequenceRootEditPart) && (selection.toList().size() == 1)) {
				final CreateServiceSequenceCommand cmd = new CreateServiceSequenceCommand(
						((FBType) ((EditPart) selected).getModel()).getService());
				if (cmd.canExecute()) {
					cmd.execute();
				}
				seq = getSequence(cmd.getCreatedElement());
			}

			if (seq != null) {
				final List<String> events = new ArrayList<>();
				final List<String> parameters = new ArrayList<>();
				final RecordSequenceDialog dialog = new RecordSequenceDialog(HandlerUtil.getActiveShell(event), events,
						parameters, seq);
				final int returnCode = dialog.open();
				final int count = dialog.getCount();
				if (returnCode != CANCEL) {
					try {
						final FBType fbType = seq.getService().getFBType();
						setParameters(fbType, parameters);
						runInterpreter(seq, events, dialog.isAppend(), dialog.isRandom(), fbType, count, startState);
						seq.setStartState(startState);
					} catch (final Exception e) {
						FordiacLogHelper.logError(e.getMessage(), e);
						MessageDialog.openError(HandlerUtil.getActiveShell(event),
								Messages.RecordServiceSequenceHandler_PROBLEM,
								Messages.RecordServiceSequenceHandler_CHECK_VARIABLE_NAMES);
					}
				}
			}
		}
		return Status.OK_STATUS;
	}

	private static void runInterpreter(final ServiceSequence seq, final List<String> eventNames, final boolean isAppend,
			final boolean isRandom, final FBType fbType, final int count, final String startState) {
		List<Event> events;
		final FBType typeCopy = EcoreUtil.copy(fbType);
		events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
				.filter(Objects::nonNull).collect(Collectors.toList());
		if (isRandom && (count > 0)) {
			events.addAll(InputGenerator.getRandomEventsSequence(typeCopy, count));
		}
		final EventManager eventManager = EventManagerFactory.createEventManager(typeCopy, events, isRandom,
				startState);
		TransactionFactory.addTraceInfoTo(eventManager.getTransactions());
		EventManagerUtils.process(eventManager);
		if (!isAppend) {
			seq.getServiceTransaction().clear();
		}
		for (final Transaction transaction : eventManager.getTransactions()) {
			ServiceSequenceUtils.convertTransactionToServiceModel(seq, fbType, (FBTransaction) transaction);
		}
		seq.setComment("Coverage: " + CoverageCalculator.calculateCoverageOfSequence(eventManager.getTransactions()));
		seq.setEventManager(eventManager);
		EventManagerSaveAndLoadHelper.saveEventManagerToFile(fbType, eventManager);
	}

	static void setParameters(final FBType fbType, final List<String> parameters) {
		// parameter: format "VarName:=Value"
		for (final String param : parameters) {
			final String[] paramValues = param.split(":=", 2); //$NON-NLS-1$
			if (paramValues.length == 2) {
				VariableUtils.setVariable(fbType, paramValues[0], paramValues[1]);
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			setBaseEnabled(structuredSelection.size() <= 1);
		}
	}

	private static Event findEvent(final FBType fbType, final String eventName) {
		final Event event = (Event) fbType.getInterfaceList().getInterfaceElement(eventName);
		if ((event == null) || !event.isIsInput()) {
			throw new IllegalArgumentException("input primitive: event " + eventName + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return event;
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

	private class RecordSequenceDialog extends MessageDialog {
		private static final int DEFAULT_RANDOMCOUNT = 10;
		private Text inputEventText;
		private Text inputParameterText;
		private Text inputCount;
		private Button appendCheckbox;
		private Button randomCheckbox;
		private CCombo inputStartStateCombo;
		private final List<String> events;
		private final List<String> parameters;
		private boolean append;
		private boolean random;
		private int count;

		private final ServiceSequence serviceSequence;

		public RecordSequenceDialog(final Shell parentShell, final List<String> events, final List<String> parameters,
				final ServiceSequence serviceSequence) {
			super(parentShell, "Record Sequence ", null, //$NON-NLS-1$
					"Configuration \nSeparate input events by ; \nSeparate parameters by ; (overwritten when random is true) \nCount specifies number of random elements\nAppend appends the sequence to the current record \nRandom adds count random events to the sequence", //$NON-NLS-1$
					MessageDialog.INFORMATION, 0, "Run"); //$NON-NLS-1$
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
			group.setText(Messages.RecordServiceSequenceHandler_INPUT_DATA);
			group.setLayout(new GridLayout(2, false));
			group.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

			Label label = new Label(group, SWT.None);
			label.setText(Messages.RecordServiceSequenceHandler_INPUT_EVENTS);

			inputEventText = new Text(group, SWT.NONE);
			inputEventText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

			label = new Label(group, SWT.None);
			label.setText(Messages.RecordServiceSequenceHandler_PARAMETERS);

			inputParameterText = new Text(group, SWT.NONE);
			inputParameterText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

			label = new Label(group, SWT.None);
			label.setText(Messages.RecordServiceSequenceHandler_COUNT);

			inputCount = new Text(group, SWT.NONE);
			inputCount.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

			label = new Label(group, SWT.None);
			label.setText("Start State");//$NON-NLS-1$

			inputStartStateCombo = ComboBoxWidgetFactory.createCombo(group);
			inputStartStateCombo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
			final FBType fbtype = serviceSequence.getService().getFBType();
			if (fbtype instanceof BasicFBType) {
				final String[] startnames = Stream
						.concat(((BasicFBType) fbtype).getECC().getECState().stream().map(ECState::getName),
								Stream.of("")) //$NON-NLS-1$
						.toArray(String[]::new);
				inputStartStateCombo.setItems(startnames);
				inputStartStateCombo.select(0);
			} else {
				inputStartStateCombo.setEnabled(false);
			}

			appendCheckbox = new Button(group, SWT.CHECK);
			appendCheckbox.setText(Messages.RecordServiceSequenceHandler_APPEND);
			appendCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));

			randomCheckbox = new Button(group, SWT.CHECK);
			randomCheckbox.setText(Messages.RecordServiceSequenceHandler_RANDOM);
			randomCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
			randomCheckbox.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(final SelectionEvent e) {
					inputEventText.setEnabled(!randomCheckbox.getSelection());
					inputParameterText.setEnabled(!randomCheckbox.getSelection());
				}

				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					// is never called
				}
			});
			return dialogArea;
		}

		@Override
		protected void buttonPressed(final int buttonId) {
			events.addAll(getEvents());
			parameters.addAll(getParameters());
			append = appendCheckbox.getSelection();
			random = randomCheckbox.getSelection();
			if (!getCountText().isBlank()) {
				count = Integer.parseInt(getCountText());
			} else {
				count = DEFAULT_RANDOMCOUNT;

			}
			final int selectedStartState = inputStartStateCombo.getSelectionIndex();
			if (selectedStartState == -1) { // nothing selected
				startState = "START"; //$NON-NLS-1$
			} else {
				startState = inputStartStateCombo.getItem(selectedStartState);
			}
			super.buttonPressed(buttonId);
		}

		private List<String> getEvents() {
			return ServiceSequenceUtils.splitAndCleanList(inputEventText.getText(), ";"); //$NON-NLS-1$
		}

		private List<String> getParameters() {
			return ServiceSequenceUtils.splitAndCleanList(inputParameterText.getText(), ";"); //$NON-NLS-1$
		}

		private String getCountText() {
			return (inputCount.getText());
		}

		public boolean isAppend() {
			return append;
		}

		public boolean isRandom() {
			return random;
		}

		public int getCount() {
			return count;
		}
	}
}
