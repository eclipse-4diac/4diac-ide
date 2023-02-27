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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.ServiceFactory;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.InputGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.AppendServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPartFactory;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.MinSpaceFreeformFigure;
import org.eclipse.fordiac.ide.model.ServiceSequenceTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.ViewPart;

public class ServiceSequenceAssignView extends ViewPart {

	private static final String DEFAULT_SEQUENCE_NAME = "Service Sequence"; //$NON-NLS-1$
	private static final int DEFAULT_SEQUENCE_NUM = 10;
	private GraphicalViewer viewer;
	private ActionRegistry actionRegistry;
	private FBType fbType;
	private Label lblHead;
	private Text textEvent;
	private Text textParam;
	private Text textName;

	private final int MARGIN = 10;
	private boolean sequenceVisible = false;
	private boolean settingsVisible = false;
	private boolean isRepeat = true;
	private ServiceSequence serviceSequence;
	private int count = DEFAULT_SEQUENCE_NUM;

	@Override
	public void createPartControl(final Composite parent) {
		final Composite parentComposite = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.fillDefaults().generateLayout(parentComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(parentComposite);

		final Composite headerComposite = new Composite(parentComposite, SWT.BORDER);
		GridLayoutFactory.fillDefaults().extendedMargins(MARGIN, MARGIN, MARGIN, MARGIN)
		.generateLayout(headerComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(headerComposite);
		final Composite subheaderComposite = new Composite(headerComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(subheaderComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(subheaderComposite);

		final Composite bodyComposite = new Composite(parentComposite, SWT.BORDER);
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(bodyComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(bodyComposite);
		bodyComposite.setVisible(sequenceVisible);

		lblHead = new Label(subheaderComposite, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lblHead);
		final Button reloadBtn = ButtonFactory.newButton(SWT.RIGHT).text("Reload").onSelect(x -> { //$NON-NLS-1$
			initializeGraphicalViewer();
		}).create(subheaderComposite);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(reloadBtn);

		final Composite expandComposite = new Composite(subheaderComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(expandComposite);
		GridDataFactory.fillDefaults().grab(false, false).span(2, 1).applyTo(expandComposite);

		final Composite expandButtonComposite = new Composite(expandComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().generateLayout(expandButtonComposite);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(expandButtonComposite);

		final Composite settingsComposite = new Composite(expandComposite, SWT.BORDER);
		settingsComposite.setVisible(settingsVisible);
		GridLayoutFactory.fillDefaults().numColumns(9).margins(MARGIN, MARGIN).generateLayout(settingsComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(settingsComposite);

		final Button expandToggle = ButtonFactory.newButton(SWT.TOGGLE).text("...").onSelect(x -> { //$NON-NLS-1$
			final Button source = (Button) x.getSource();
			settingsVisible = source.getSelection();
			settingsComposite.setVisible(settingsVisible);
		}).create(expandButtonComposite);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(expandToggle);

		final Label lblName = LabelFactory.newLabel(SWT.NONE).text("Name:").create(settingsComposite);
		GridDataFactory.fillDefaults().applyTo(lblName);
		textName = TextFactory.newText(SWT.NONE).text(DEFAULT_SEQUENCE_NAME).create(settingsComposite);
		GridDataFactory.fillDefaults().applyTo(textName);

		final Label lblEvent = LabelFactory.newLabel(SWT.NONE).text("Initial Events:").create(settingsComposite);
		GridDataFactory.fillDefaults().applyTo(lblEvent);
		textEvent = TextFactory.newText(SWT.NONE).create(settingsComposite);
		GridDataFactory.fillDefaults().applyTo(textEvent);

		final Label lblParam = LabelFactory.newLabel(SWT.NONE).text("Initial Parameters:").create(settingsComposite);
		GridDataFactory.fillDefaults().applyTo(lblParam);
		textParam = TextFactory.newText(SWT.NONE).create(settingsComposite);
		GridDataFactory.fillDefaults().applyTo(textParam);

		final Label lblRandom = LabelFactory.newLabel(SWT.NONE).text("Event Count:").create(settingsComposite); //$NON-NLS-1$
		GridDataFactory.fillDefaults().applyTo(lblRandom);
		final Text numRandom = TextFactory.newText(SWT.NONE).text("10").create(settingsComposite); //$NON-NLS-1$
		numRandom.addVerifyListener(e -> {
			for (final char c : e.text.toCharArray()) {
				if ((c < '0') || (c > '9')) {
					e.doit = false;
					return;
				}
			}
		});
		GridDataFactory.fillDefaults().applyTo(numRandom);

		final Button contBtn = ButtonFactory.newButton(SWT.CHECK).text("Auto-continue").create(settingsComposite); //$NON-NLS-1$
		GridDataFactory.fillDefaults().applyTo(contBtn);
		contBtn.setSelection(isRepeat);

		final Composite viewerComposite = new Composite(bodyComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewerComposite);
		viewerComposite.setLayout(new FillLayout());

		final Consumer<SelectionEvent> gen = x -> {
			try {
				count = Integer.parseInt(numRandom.getText());
			} catch (final NumberFormatException e) {
				count = DEFAULT_SEQUENCE_NUM;
			}
			serviceSequence = getNext();
			sequenceVisible = true;
			refreshGraphicalViewer();
			viewerComposite.redraw();
			bodyComposite.setVisible(sequenceVisible);
		};
		final Button genBtn = ButtonFactory.newButton(SWT.PUSH).text("Generate Sequence").onSelect(gen) //$NON-NLS-1$
				.create(subheaderComposite);
		GridDataFactory.fillDefaults().applyTo(genBtn);

		final Composite selectorComposite = new Composite(bodyComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(selectorComposite);
		selectorComposite.setLayout(new GridLayout());
		final Composite subselectorComposite = new Composite(selectorComposite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(subselectorComposite);
		subselectorComposite.setLayout(new GridLayout());
		final Button possibleBtn = ButtonFactory.newButton(SWT.PUSH).text("POSSIBLE").onSelect(x -> { //$NON-NLS-1$
			serviceSequence.setServiceSequenceType(ServiceSequenceTypes.DEFAULT);
			saveSequence();
			isRepeat = contBtn.getSelection();
			if (isRepeat) {
				gen.accept(null);
			} else {
				sequenceVisible = false;
				refreshGraphicalViewer();
				viewerComposite.redraw();
				bodyComposite.setVisible(sequenceVisible);
			}
		}).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(possibleBtn);
		final Button conditionalBtn = ButtonFactory.newButton(SWT.PUSH).text("\u2753 CONDITIONAL").onSelect(x -> { //$NON-NLS-1$
			serviceSequence.setServiceSequenceType(ServiceSequenceTypes.CONDITIONAL);
			saveSequence();
			isRepeat = contBtn.getSelection();
			if (isRepeat) {
				gen.accept(null);
			} else {
				sequenceVisible = false;
				refreshGraphicalViewer();
				viewerComposite.redraw();
				bodyComposite.setVisible(sequenceVisible);
			}

		}).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(conditionalBtn);
		final Button alwaysBtn = ButtonFactory.newButton(SWT.PUSH).text("\u2705 ALWAYS").onSelect(x -> { //$NON-NLS-1$
			serviceSequence.setServiceSequenceType(ServiceSequenceTypes.ALWAYS);
			saveSequence();
			isRepeat = contBtn.getSelection();
			if (isRepeat) {
				gen.accept(null);
			} else {
				sequenceVisible = false;
				refreshGraphicalViewer();
				viewerComposite.redraw();
				bodyComposite.setVisible(sequenceVisible);
			}

		}).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(alwaysBtn);
		final Button forbiddenBtn = ButtonFactory.newButton(SWT.PUSH).text("\u26D4 FORBIDDEN").onSelect(x -> { //$NON-NLS-1$
			serviceSequence.setServiceSequenceType(ServiceSequenceTypes.FORBIDDEN);
			saveSequence();
			isRepeat = contBtn.getSelection();
			if (isRepeat) {
				gen.accept(null);
			} else {
				sequenceVisible = false;
				refreshGraphicalViewer();
				viewerComposite.redraw();
				bodyComposite.setVisible(sequenceVisible);
			}

		}).create(subselectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(forbiddenBtn);

		final Button skipBtn = ButtonFactory.newButton(SWT.PUSH).text("Skip").onSelect(x -> { //$NON-NLS-1$
			isRepeat = contBtn.getSelection();
			if (isRepeat) {
				gen.accept(null);
			} else {
				sequenceVisible = false;
				refreshGraphicalViewer();
				viewerComposite.redraw();
				bodyComposite.setVisible(sequenceVisible);
			}
		}).create(selectorComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(skipBtn);

		createGraphicalViewer(viewerComposite);

		setTitleImage(FordiacImage.ICON_BASIC_FB.getImage());
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
		}

	}

	private void refreshGraphicalViewer() {
		if ((fbType != null) && (serviceSequence != null)) {
			final FBType typeCopy = EcoreUtil.copy(fbType);
			typeCopy.setService(ServiceFactory.createDefaultServiceModel());
			typeCopy.getService().getServiceSequence().set(0, serviceSequence);
			viewer.setContents(typeCopy);
		}
	}

	private void hookGraphicalViewer() {
		getSite().setSelectionProvider(viewer);
	}

	private void saveSequence() {
		final CommandStack cs = getSite().getPage().getActiveEditor().getAdapter(CommandStack.class);
		cs.execute(new AppendServiceSequenceCommand(fbType.getService(), serviceSequence));
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

	private EditPartFactory getEditpartFactory() {
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
			final FBType typeCopy = EcoreUtil.copy(fbType);

			try {
				setParameters(typeCopy, parameters);
				runInterpreter(seq, events, true, true, typeCopy, count);
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
			return seq;
		}

		return null;
	}

	private static void runInterpreter(final ServiceSequence seq, final List<String> eventNames, final boolean isAppend,
			final boolean isRandom, final FBType fbType, final int count) {
		final List<Event> events;
		final FBType typeCopy = EcoreUtil.copy(fbType);
		events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
				.filter(Objects::nonNull).collect(Collectors.toList());
		if (isRandom && (count > 0)) {
			events.addAll(InputGenerator.getRandomEventsSequence(typeCopy, count));
		}
		final EventManager eventManager = EventManagerFactory.createEventManager(typeCopy, events, isRandom, "START"); //$NON-NLS-1$
		EventManagerUtils.process(eventManager);
		if (!isAppend) {
			seq.getServiceTransaction().clear();
		}
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