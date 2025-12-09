/*******************************************************************************
 * Copyright (c) 2022 JKU
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.ServiceFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.InputGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPartFactory;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.MinSpaceFreeformFigure;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.ServiceSequenceTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.ButtonFactory;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.TextFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.ViewPart;

public class ServiceSequenceAssignView extends ViewPart {

	private static final String DEFAULT_SEQUENCE_NAME = "ServiceSequence"; //$NON-NLS-1$
	private static final int DEFAULT_SEQUENCE_NUM = 10;
	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private FBType fbType;
	private Label lblHead;
	private Text textEvent;
	private Text textParam;
	private Text textName;
	private Group settingsGroup;

	private static final int MARGIN = 10;
	private boolean sequenceVisible = false;
	private ServiceSequence generatedSequence;
	private int count = DEFAULT_SEQUENCE_NUM;

	private static Composite createParentComposite(final Composite parent) {
		final Composite returnComposite = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.fillDefaults().generateLayout(returnComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(returnComposite);
		return returnComposite;
	}

	private static Composite createHeaderComposite(final Composite parent) {
		final Composite returnComposite = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.fillDefaults().extendedMargins(MARGIN, MARGIN, MARGIN, MARGIN)
				.generateLayout(returnComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(returnComposite);
		return returnComposite;
	}

	private static Composite createSubheaderComposite(final Composite parent) {
		final Composite returnComposite = new Composite(parent, SWT.FILL);
		GridLayoutFactory.fillDefaults().numColumns(11).generateLayout(returnComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(returnComposite);
		return returnComposite;
	}

	private static Composite createBodyComposite(final Composite parent) {
		final Composite returnComposite = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(returnComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(returnComposite);
		return returnComposite;
	}

	@Override
	public void createPartControl(final Composite parent) {
		// create composites
		final Composite parentComposite = createParentComposite(parent);
		final Composite headerComposite = createHeaderComposite(parentComposite);
		final Composite bodyComposite = createBodyComposite(parentComposite);
		bodyComposite.setVisible(sequenceVisible);

		// create settings group
		settingsGroup = new Group(headerComposite, 1);
		settingsGroup.setLayout(new GridLayout(2, false));
		settingsGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		final Composite subheaderComposite = createSubheaderComposite(settingsGroup);
		subheaderComposite.setLayout(new GridLayout(11, false));
		subheaderComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

		// name
		LabelFactory.newLabel(SWT.NONE).text(Messages.ServiceSequenceAssignView_NAME).create(subheaderComposite);
		textName = TextFactory.newText(SWT.NONE).text(DEFAULT_SEQUENCE_NAME).create(subheaderComposite);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

		// initial events
		LabelFactory.newLabel(SWT.NONE).text(Messages.ServiceSequenceAssignView_INITIAL_EVENTS)
				.create(subheaderComposite);
		textEvent = TextFactory.newText(SWT.NONE).create(subheaderComposite);
		textEvent.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

		// initial parameters
		LabelFactory.newLabel(SWT.NONE).text(Messages.ServiceSequenceAssignView_INITIAL_PARAMETERS)
				.create(subheaderComposite);
		textParam = TextFactory.newText(SWT.NONE).create(subheaderComposite);
		textParam.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

		// event-count field
		LabelFactory.newLabel(SWT.NONE).text(Messages.ServiceSequenceAssignView_EVENT_COUNT).create(subheaderComposite);
		final Text numRandom = TextFactory.newText(SWT.NONE).text(String.valueOf(DEFAULT_SEQUENCE_NUM))
				.create(subheaderComposite);
		numRandom.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));
		numRandom.addVerifyListener(e -> {
			for (final char c : e.text.toCharArray()) {
				if ((c < '0') || (c > '9')) {
					e.doit = false;
					return;
				}
			}
		});
		GridDataFactory.fillDefaults().applyTo(numRandom);

		// reload button
		final Button reloadBtn = ButtonFactory.newButton(SWT.PUSH).text(Messages.ServiceSequenceAssignView_RELOAD)
				.onSelect(x -> refreshGraphicalViewer()).create(subheaderComposite);
		reloadBtn.setLayoutData(new GridData(SWT.None, SWT.RIGHT));
		GridDataFactory.fillDefaults().applyTo(reloadBtn);

		// Generate Sequence Button
		final Consumer<SelectionEvent> gen = x -> {
			try {
				count = Integer.parseInt(numRandom.getText());
			} catch (final NumberFormatException e) {
				count = DEFAULT_SEQUENCE_NUM;
			}
			generatedSequence = getNext();
			sequenceVisible = true;
			refreshGraphicalViewer();
			subheaderComposite.redraw();
			bodyComposite.setVisible(sequenceVisible);
		};
		final Button genBtn = ButtonFactory.newButton(SWT.PUSH)
				.text(Messages.ServiceSequenceAssignView_GENERATE_SEQUENCE).onSelect(gen).create(subheaderComposite);
		genBtn.setLayoutData(new GridData(SWT.None, SWT.RIGHT));
		GridDataFactory.fillDefaults().applyTo(genBtn);

		// Graphical Viewer + assign type buttons
		final Composite viewerComposite = new Composite(bodyComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewerComposite);
		viewerComposite.setLayout(new FillLayout());

		final Composite selectorComposite = new Composite(bodyComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(selectorComposite);
		selectorComposite.setLayout(new GridLayout());
		final Composite subselectorComposite = new Composite(selectorComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(subselectorComposite);
		subselectorComposite.setLayout(new GridLayout());

		final Button possibleBtn = ButtonFactory.newButton(SWT.PUSH).text(Messages.ServiceSequenceAssignView_POSSIBLE)
				.onSelect(x -> assignType(ServiceSequenceTypes.DEFAULT, gen)).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(possibleBtn);
		final Button conditionalBtn = ButtonFactory.newButton(SWT.PUSH)
				.text("\u2753 " + Messages.ServiceSequenceAssignView_CONDITIONAL).onSelect(x -> //$NON-NLS-1$
				assignType(ServiceSequenceTypes.CONDITIONAL, gen)).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(conditionalBtn);
		final Button alwaysBtn = ButtonFactory.newButton(SWT.PUSH)
				.text("\u2705 " + Messages.ServiceSequenceAssignView_ALWAYS).onSelect(x -> //$NON-NLS-1$
				assignType(ServiceSequenceTypes.ALWAYS, gen)).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(alwaysBtn);
		final Button forbiddenBtn = ButtonFactory.newButton(SWT.PUSH)
				.text("\u26D4 " + Messages.ServiceSequenceAssignView_FORBIDDEN).onSelect(x -> //$NON-NLS-1$
				assignType(ServiceSequenceTypes.FORBIDDEN, gen)).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(forbiddenBtn);

		final Button skipBtn = ButtonFactory.newButton(SWT.PUSH).text(Messages.ServiceSequenceAssignView_SKIP)
				.onSelect(x -> gen.accept(null)).create(selectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(skipBtn);

		createGraphicalViewer(viewerComposite);

		setTitleImage(FordiacImage.ICON_BASIC_FB.getImage());
	}

	private void assignType(final String type, final Consumer<SelectionEvent> gen) {
		generatedSequence.setServiceSequenceType(type);
		saveSequence();
		gen.accept(null);

	}

	private void createGraphicalViewer(final Composite parent) {
		viewer = new ScrollingGraphicalViewer();
		viewer.createControl(parent);
		configureGraphicalViewer();
		initializeGraphicalViewer();
		hookGraphicalViewer();
	}

	private void configureGraphicalViewer() {
		viewer.getControl().setBackground(ColorConstants.listBackground);
		final ScalableFreeformRootEditPart root = createRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditpartFactory());
		viewer.setContextMenu(new FordiacContextMenuProvider(viewer, root.getZoomManager(), getActionRegistry()));
	}

	private void initializeGraphicalViewer() {
		final IEditorPart ep = getSite().getPage().getActiveEditor();
		fbType = ep.getAdapter(FBType.class);

		if (fbType != null) {
			refreshGraphicalViewer();
			if (lblHead != null) {
				lblHead.setText(fbType.getName());
			}
			if (settingsGroup != null) {
				settingsGroup.setText(fbType.getName());
			}
		}

	}

	private void refreshGraphicalViewer() {
		if ((fbType != null) && (generatedSequence != null)) {
			final FBType typeCopy = EcoreUtil.copy(fbType);
			typeCopy.setService(ServiceFactory.createDefaultServiceModel());
			typeCopy.getService().getServiceSequence().set(0, generatedSequence);
			viewer.setContents(typeCopy);
		}
	}

	private void hookGraphicalViewer() {
		getSite().setSelectionProvider(viewer);
	}

	private void saveSequence() {
		final CommandStack cs = getSite().getPage().getActiveEditor().getAdapter(CommandStack.class);
		// cs.execute(new AppendServiceSequenceCommand(fbType.getService(),
		// generatedSequence));

		// create generated sequence
		cs.execute(new CreateServiceSequenceCommand(fbType.getService()));
		final ServiceSequence sequence = fbType.getService().getServiceSequence()
				.get(fbType.getService().getServiceSequence().size() - 1);
		sequence.setName(NameRepository.createUniqueName(sequence, generatedSequence.getName() + "_1")); //$NON-NLS-1$
		sequence.setComment(generatedSequence.getComment());
		for (final ServiceTransaction genTransaction : generatedSequence.getServiceTransaction()) {
			cs.execute(new CreateTransactionCommand(sequence));
			final ServiceTransaction transaction = sequence.getServiceTransaction()
					.get(sequence.getServiceTransaction().size() - 1);
			transaction.getInputPrimitive().setParameters(genTransaction.getInputPrimitive().getParameters());
			for (final OutputPrimitive outP : genTransaction.getOutputPrimitive()) {
				boolean isLeftInterface = false;
				if (fbType.getService().getLeftInterface().getName().equals(outP.getInterface().getName())) {
					isLeftInterface = true;
				}
				cs.execute(new CreateOutputPrimitiveCommand(transaction, isLeftInterface));
				transaction.getOutputPrimitive().get(transaction.getOutputPrimitive().size() - 1)
						.setEvent(outP.getEvent());
				transaction.getOutputPrimitive().get(transaction.getOutputPrimitive().size() - 1)
						.setParameters(outP.getParameters());
			}
		}
		sequence.setServiceSequenceType(generatedSequence.getServiceSequenceType());
		sequence.setStartState(generatedSequence.getStartState());

	}

	@Override
	public void setFocus() {
		// probably do something cool here
	}

	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()) {
			@Override
			protected AbstractFreeformFigure createDrawingAreaContainer() {
				return new MinSpaceFreeformFigure();
			}
		};
	}

	private static EditPartFactory getEditpartFactory() {
		return new ServiceSequenceEditPartFactory(null);
	}

	private ActionRegistry getActionRegistry() {
		if (actionRegistry == null) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	private ServiceSequence getNext() {
		if (fbType != null) {
			final ServiceSequence seq = LibraryElementFactory.eINSTANCE.createServiceSequence();

			String name = textName.getText();
			if ((name == null) || name.isBlank()) {
				name = DEFAULT_SEQUENCE_NAME;
			}
			seq.setName(name);
			final List<String> events = ServiceSequenceUtils.splitList(textEvent.getText());
			final List<String> parameters = ServiceSequenceUtils.splitList(textParam.getText());

			try {
				setParameters(fbType, parameters);
				runInterpreter(seq, events, fbType, count, parameters);
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
			seq.setStartState("START"); //$NON-NLS-1$
			return seq;
		}

		return null;
	}

	private static void runInterpreter(final ServiceSequence seq, final List<String> eventNames, final FBType fbType,
			final int count, final List<String> parameters) {
		final FBType typeCopy = EcoreUtil.copy(fbType);
		final List<Event> events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
				.filter(Objects::nonNull).toList();

		if (!events.isEmpty() && (count > 0)) {
			if (!parameters.isEmpty()) {
				addToGenSequence(fbType, seq, events.subList(0, 1), false);
				seq.getServiceTransaction().get(seq.getServiceTransaction().size() - 1).getInputPrimitive()
						.setParameters(formatInputParameter(parameters));
				addToGenSequence(fbType, seq, events.subList(1, events.size()), true);
			} else {
				addToGenSequence(fbType, seq, events, true);
			}
		}
		if ((count - events.size()) > 0) {
			final List<Event> generatedEvents = new ArrayList<>();
			generatedEvents.addAll(InputGenerator.getRandomEventsSequence(typeCopy, count - events.size()));
			addToGenSequence(typeCopy, seq, generatedEvents, true);
		}
	}

	private static String formatInputParameter(final List<String> list) {
		final StringBuilder sb = new StringBuilder();

		for (final String s : list) {
			sb.append(s);
			sb.append("; \n"); //$NON-NLS-1$
		}

		return sb.toString();
	}

	private static void addToGenSequence(final FBType fbType, final ServiceSequence seq, final List<Event> eventList,
			final boolean isRandom) {
		final FBType typeCopy = EcoreUtil.copy(fbType);
		final EventManager eventManager = EventManagerFactory.createEventManager(typeCopy, eventList, isRandom,
				Messages.ServiceSequenceAssignView_START);
		if (!isRandom) {
			TransactionFactory.addTraceInfoTo(eventManager.getTransactions());
		}
		EventManagerUtils.process(eventManager);

		for (final Transaction transaction : eventManager.getTransactions()) {
			ServiceSequenceUtils.convertTransactionToServiceModel(seq, fbType, (FBTransaction) transaction);
		}
	}

	private static void setParameters(final FBType fbType, final List<String> parameters) {
		// parameter: format "VarName:=Value"
		for (final String param : parameters) {
			final String[] paramValues = param.split(":=", 2); //$NON-NLS-1$
			if (paramValues.length == 2) {
				VariableUtils.setVariable(fbType, paramValues[0], paramValues[1]);
			}
		}
	}

	private static Event findEvent(final FBType fbType, final String eventName) {
		final Event event = (Event) fbType.getInterfaceList().getInterfaceElement(eventName);
		if ((event == null) || !event.isIsInput()) {
			throw new IllegalArgumentException("input primitive: event " + eventName + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return event;
	}

}