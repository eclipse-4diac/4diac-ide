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
 *   Michael Oberlehner, Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class MapHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final CompoundCommand mapCommands = openDialog(event);
		final CommandStack cmdstack = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor().getAdapter(CommandStack.class);
		cmdstack.execute(mapCommands);
		return null;
	}

	@SuppressWarnings("unchecked")
	private static CompoundCommand openDialog(final ExecutionEvent event) {
		final CompoundCommand mapCommands = new CompoundCommand();
		final ITreeContentProvider treeProvider = createTreeContentProvider();
		final LabelProvider labelProvider =  createTreeLabelProvider();


		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final Object firstElement = selection.getFirstElement();
		if (firstElement instanceof AbstractFBNElementEditPart) {
			final FBNetworkElement fb = ((AbstractFBNElementEditPart) firstElement).getModel();
			if (fb == null) {
				return new CompoundCommand();
			}
			// save system for the auto select with one resource
			final AutomationSystem system = fb.getFbNetwork().getAutomationSystem();
			final ElementTreeSelectionDialog dialog = createDialog(event, treeProvider, labelProvider, system);
			dialog.setInput(system);
			dialog.setTitle(Messages.MapToDialog_Title);
			dialog.setMessage(Messages.MapToDialog_Message);
			dialog.setHelpAvailable(false);
			dialog.setAllowMultiple(false);

			if (Window.OK == dialog.open()) {
				final Object firstResult = dialog.getFirstResult();
				if (firstResult instanceof Resource) {
					selection.forEach(sel -> {
						if (sel instanceof AbstractFBNElementEditPart) {
							final FBNetworkElement f = ((AbstractFBNElementEditPart) sel).getModel();
							mapCommands.add(new MapToCommand(f, (Resource) firstResult));
						}
					});
				}
			}
		}
		return mapCommands;
	}

	private static ElementTreeSelectionDialog createDialog(final ExecutionEvent event,
			final ITreeContentProvider treeProvider,
			final LabelProvider labelProvider, final AutomationSystem system) {
		return new ElementTreeSelectionDialog(HandlerUtil.getActiveEditor(event).getSite().getShell(),
				labelProvider, treeProvider) {
			@Override
			public int open() {
				final Object[] elements = treeProvider.getElements(system);
				// if only one resource exists, default select it
				if (elements.length == 1) {
					setResult(Arrays.asList(elements));
					return Window.OK;
				}
				return super.open();
			}
		};
	}


	private static LabelProvider createTreeLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if(element instanceof Resource) {
					return ((Resource) element).getDevice().getName() + "." //$NON-NLS-1$
							+((Resource)element).getName();
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(final Object element) {
				return FordiacImage.ICON_RESOURCE.getImage();
			}
		};
	}

	private static ITreeContentProvider createTreeContentProvider() {
		return new ITreeContentProvider() {

			@Override
			public boolean hasChildren(final Object element) {
				return false;
			}

			@Override
			public Object getParent(final Object element) {
				if (element instanceof EObject) {
					return ((EObject) element).eContainer();
				}
				return null;
			}

			@Override
			public Object[] getElements(final Object inputElement) {

				if (inputElement instanceof AutomationSystem) {
					final AutomationSystem system = (AutomationSystem) inputElement;

					final List<Object> elements = new ArrayList<>();
					system.getSystemConfiguration().getDevices()
					.forEach(d -> elements.addAll(d.getResource()));

					return elements.toArray();
				}

				if (inputElement instanceof Device) {
					final Device device = (Device) inputElement;

					return device.getResource().toArray();

				}

				return null;
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof Device) {
					final Device device = (Device) parentElement;
					return device.getResource().toArray();
				}

				if (parentElement instanceof Resource) {
					final Device device = (Device) parentElement;
					return device.getResource().toArray();
				}

				return null;
			}
		};
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		final FBNetwork network = editor.getAdapter(FBNetwork.class);
		setBaseEnabled(isValidSelection(evaluationContext) && isMapable(network) && hasDevices(network));
	}

	private static boolean isValidSelection(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			@SuppressWarnings("unchecked")
			final Iterator<Object> iterator = ((StructuredSelection) selection).iterator();
			while (iterator.hasNext()) {
				// enable handler if selection contains one valid element
				if (iterator.next() instanceof AbstractFBNElementEditPart) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean hasDevices(final FBNetwork network) {
		final AutomationSystem system = network.getAutomationSystem();
		return system != null && !system.getSystemConfiguration().getDevices().isEmpty();
	}

	public static boolean isMapable(final FBNetwork network) {
		return ((network != null) && !(network.isSubApplicationNetwork() || network.isCFBTypeNetwork()
				|| network.eContainer() instanceof Resource));
	}

}
