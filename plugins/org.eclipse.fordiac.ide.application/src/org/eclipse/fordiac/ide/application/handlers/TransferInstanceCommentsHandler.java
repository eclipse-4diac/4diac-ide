/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractStructManipulatorEditPart;
import org.eclipse.fordiac.ide.model.commands.change.TransferInstanceCommentsCommand;
import org.eclipse.fordiac.ide.model.helpers.FBEndpointFinder;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.search.dialog.StructuredDataTypeDataHandler;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransferInstanceCommentsHandler extends AbstractHandler {

	private static final int DEFAULT_BUTTON_INDEX = 0; // Save
	private Button outputConnectedOnlyBtn;
	private StructManipulator selectedItem;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final AbstractStructManipulatorEditPart struct = (AbstractStructManipulatorEditPart) sel.getFirstElement();
		selectedItem = struct.getModel();
		final String[] labels = { Messages.TransferInstanceComments_TransferLabel, SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$

		final FBTypeUpdateDialog<DataTypeEntry> structUpdateDialog = new FBTypeUpdateDialog<>(null,
				Messages.TransferInstanceComments_WizardTitle, "", labels, DEFAULT_BUTTON_INDEX, //$NON-NLS-1$
				new StructuredDataTypeDataHandler((DataTypeEntry) struct.getModel().getDataType().getTypeEntry()) {
					@Override
					public final List<? extends EObject> performStructSearch() {

						final DataTypeInstanceSearch search = new DataTypeInstanceSearch(typeEntry);

						final List<? extends EObject> result = search.performSearch();

						result.removeIf(el -> el.equals(struct.getModel())
								|| isTypedOrContainedInTypedInstance((INamedElement) el));

						// output connected elements only searchFilter
						if (outputConnectedOnlyBtn != null && !outputConnectedOnlyBtn.isDisposed()
								&& outputConnectedOnlyBtn.getSelection()) {
							final List<FBNetworkElement> connectedFbs = FBEndpointFinder
									.getConnectedFbs(new ArrayList<>(), selectedItem);
							result.removeIf(res -> (res instanceof final StructManipulator structMan
									&& (!structMan.getInterface().getEventInputs().isEmpty()
											|| !structMan.getInterface().getInputVars().isEmpty())
									&& !connectedFbs.contains(res)));

						}
						return result;
					}
				});

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		if (structUpdateDialog.open() == DEFAULT_BUTTON_INDEX) {
			final TransferInstanceCommentsCommand cmd = new TransferInstanceCommentsCommand(struct.getModel(),
					structUpdateDialog.getDataHandler().getCollectedElements().stream()
							.filter(StructManipulator.class::isInstance).map(StructManipulator.class::cast)
							.collect(Collectors.toSet()));
			commandStack.execute(cmd);
		}
		return null;
	}

	public static List<FBNetworkElement> getConnectedFbs(final FBNetworkElement src) {
		final List<FBNetworkElement> connectedElements = new ArrayList<>();

		final List<IInterfaceElement> pins = new ArrayList<>();
		pins.addAll(src.getInterface().getEventOutputs());
		pins.addAll(src.getInterface().getOutputVars());

		for (final IInterfaceElement pin : pins) {
			connectedElements.addAll(getConnectedFbs(pin));
		}

		// search for connected elements of connected elements
		if (!connectedElements.isEmpty() && ((connectedElements.size() == 1 && !connectedElements.get(0).equals(src))
				|| (connectedElements.size() > 1))) {
			final List<FBNetworkElement> connectedOfConnectedElements = new ArrayList<>();
			for (final FBNetworkElement element : connectedElements) {
				if (!element.equals(src)) {
					connectedOfConnectedElements.addAll(getConnectedFbs(element));
				}
			}
			connectedOfConnectedElements.forEach(el -> {
				if (!connectedElements.contains(el)) {
					connectedElements.add(el);
				}
			});
		}

		return connectedElements.stream().distinct().toList();
	}

	private static List<FBNetworkElement> getConnectedFbs(final IInterfaceElement srcPin) {

		final List<FBNetworkElement> connectedElements = new ArrayList<>();
		for (final Connection con : srcPin.getOutputConnections()) {
			if (con.getDestinationElement() instanceof SubApp) {
				connectedElements.addAll(getConnectedFbs(con.getDestination()));
			} else {
				connectedElements.add(con.getDestinationElement());
			}
		}

		return connectedElements;

	}

	private static boolean isTypedOrContainedInTypedInstance(final INamedElement element) {
		return element.eContainer() != null && element.eContainer().eContainer() instanceof final SubApp subApp
				&& (subApp.isContainedInTypedInstance() || subApp.isTyped());
	}

}
