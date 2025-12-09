/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.contentprovider;

import org.eclipse.fordiac.ide.fbtypeeditor.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateFromNewAdapterAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateInputEventAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateInputVariableAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateNewPlugAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateNewSocketAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateOutputEventAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateOutputVariableAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreatePlugAction;
import org.eclipse.fordiac.ide.fbtypeeditor.actions.CreateSocketAction;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typeeditor.TypeEditorInput;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorPart;

public class InterfaceContextMenuProvider extends FordiacContextMenuProvider {

	private static final String CREATE_PLUG = Messages.InterfaceContextMenuProvider_CreatePlug;
	private static final String CREATE_SOCKET = Messages.InterfaceContextMenuProvider_CreateSocket;

	private final DataTypeLibrary dataTypeLib;

	public InterfaceContextMenuProvider(final EditPartViewer viewer, final ZoomManager zoomManager,
			final ActionRegistry registry, final DataTypeLibrary dataTypeLib) {
		super(viewer, zoomManager, registry);
		this.dataTypeLib = dataTypeLib;
	}

	@Override
	public void buildContextMenu(final IMenuManager menu) {
		super.buildContextMenu(menu);

		// rename action
		IAction action = getRegistry().getAction(GEFActionConstants.DIRECT_EDIT);
		if ((action != null) && action.isEnabled()) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}

		action = getRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		buildInterfaceEditEntries(menu, getRegistry(), dataTypeLib);
	}

	public static void buildInterfaceEditEntries(final IMenuManager menu, final ActionRegistry registry,
			final DataTypeLibrary dataTypeLib) {
		IAction action;

		action = registry.getAction(CreateInputEventAction.ID);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, action);

		final IWorkbenchPart part = ((CreateInputEventAction) action).getWorkbenchPart();
		final FBType fbType = ((CreateInputEventAction) action).getFbType();

		action = registry.getAction(CreateOutputEventAction.ID);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, action);

		MenuManager submenu = new MenuManager(Messages.InterfaceContextMenuProvider_CreateDataInput);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, submenu);

		for (final DataType dataType : dataTypeLib.getDataTypesSorted()) {
			action = registry.getAction(CreateInputVariableAction.getID(dataType.getName()));
			if (null == action) {
				action = new CreateInputVariableAction(part, fbType, dataType);
				registry.registerAction(action);
			}
			submenu.add(action);
		}

		submenu = new MenuManager(Messages.InterfaceContextMenuProvider_CreateDataOutput);
		menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, submenu);

		for (final DataType dataType : dataTypeLib.getDataTypesSorted()) {
			action = registry.getAction(CreateOutputVariableAction.getID(dataType.getName()));
			if (null == action) {
				action = new CreateOutputVariableAction(part, fbType, dataType);
				registry.registerAction(action);
			}
			submenu.add(action);
		}
		if (!(fbType instanceof AdapterType)) {
			buildAdapterMenuEntries(menu, registry, part, fbType);
		}
	}

	private static void buildAdapterMenuEntries(final IMenuManager menu, final ActionRegistry registry,
			final IWorkbenchPart part, final FBType fbType) {

		if (((EditorPart) part).getEditorInput() instanceof final TypeEditorInput untypedInput) {
			final TypeLibrary typeLib = untypedInput.getTypeEntry().getTypeLibrary();
			if (null != typeLib) {
				final MenuManager socketEntry = new MenuManager(CREATE_SOCKET);
				menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, socketEntry);

				final MenuManager plugEntry = new MenuManager(CREATE_PLUG);
				menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD, plugEntry);

				Action action = (Action) registry.getAction(CreateNewPlugAction.ID);
				if (null != action) {
					((CreateFromNewAdapterAction) action).setTypeEntry(untypedInput.getTypeEntry());
				}
				plugEntry.add(action);

				action = (Action) registry.getAction(CreateNewSocketAction.ID);
				if (null != action) {
					((CreateFromNewAdapterAction) action).setTypeEntry(untypedInput.getTypeEntry());
				}
				socketEntry.add(action);

				fillMenuForPalletteGroup(socketEntry, plugEntry, registry, part, fbType, typeLib);
			}
		}
	}

	private static void fillMenuForPalletteGroup(final MenuManager socketEntry, final MenuManager plugEntry,
			final ActionRegistry registry, final IWorkbenchPart part, final FBType fbType, final TypeLibrary typeLib) {
		IAction action;

		for (final AdapterTypeEntry entry : typeLib.getAdapterTypesSorted()) {
			// add socket entry
			action = registry.getAction(CreateSocketAction.getID(entry));
			if (null == action) {
				action = new CreateSocketAction(part, fbType, entry);
			}
			socketEntry.add(action);

			// added plug entry
			action = registry.getAction(CreatePlugAction.getID(entry));
			if (null == action) {
				action = new CreatePlugAction(part, fbType, entry);
			}
			plugEntry.add(action);
		}
	}

	public static void createInterfaceEditingActions(final IWorkbenchPart workBenchPart, final ActionRegistry registry,
			final FBType fbType) {
		IAction action;

		action = new CreateInputEventAction(workBenchPart, fbType);
		registry.registerAction(action);

		action = new CreateOutputEventAction(workBenchPart, fbType);
		registry.registerAction(action);

		if (!(fbType instanceof AdapterType)) {
			action = new CreateNewPlugAction(workBenchPart, fbType);
			registry.registerAction(action);

			action = new CreateNewSocketAction(workBenchPart, fbType);
			registry.registerAction(action);
		}
	}

}
