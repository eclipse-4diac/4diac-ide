/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial implementation
 *   Dunja Životin - added updateAllInstances for type lib import
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdatePinInTypeDeclarationCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.search.types.InstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public final class FBUpdater {

	private static Set<FB> updatedElements;

	private FBUpdater() {
		// just a utility class, no instancing here, por favor
	}

	public static Set<FB> getUpdatedElements() {
		return updatedElements;
	}

	public static Command createUpdateUntypedSubappInterfaceCommand(final SubApp subApp,
			final DataTypeEntry dataTypeEntry) {
		return new UpdateUntypedSubAppInterfaceCommand(subApp, dataTypeEntry);
	}

	public static Command createUpdateUntypedSubappInterfaceCommand(final Collection<SubApp> subApps,
			final DataTypeEntry dataTypeEntry) {
		final List<Command> commands = new ArrayList<>();
		commands.addAll(
				subApps.stream().map(s -> createUpdateUntypedSubappInterfaceCommand(s, dataTypeEntry)).toList());
		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	public static Command createUpdatePinInTypeDeclarationCommand(final List<FBType> types,
			final List<DataTypeEntry> dataTypeEntries, final String oldName) {
		final List<Command> commands = new ArrayList<>();
		types.forEach(type -> commands
				.add(createUpdatePinInTypeDeclarationCommand(type, dataTypeEntries.get(types.indexOf(type)), oldName)));
		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	public static Command createUpdatePinInTypeDeclarationCommand(final FBType type, final DataTypeEntry dataTypeEntry,
			final String oldName) {
		return oldName == null ? new UpdatePinInTypeDeclarationCommand(type, dataTypeEntry)
				: new UpdatePinInTypeDeclarationCommand(type, dataTypeEntry, oldName);
	}

	public static Command createStructManipulatorsUpdateCommand(final List<StructManipulator> muxes,
			final List<DataTypeEntry> dataTypeEntries) {
		final List<Command> commands = new ArrayList<>();
		muxes.stream().forEach(mux -> {
			final StructuredType structuredType = (StructuredType) dataTypeEntries.get(muxes.indexOf(mux))
					.getTypeEditable();
			final EObject rootContainer = EcoreUtil.getRootContainer(EcoreUtil.getRootContainer(mux));

			if (rootContainer instanceof final AutomationSystem autoSys) {
				autoSys.getCommandStack().execute(new ChangeStructCommand(mux, structuredType));
			} else if (rootContainer instanceof final SubAppType subApp) {
				final IFile file = subApp.getTypeEntry().getFile();
				final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.findEditor(new FileEditorInput(file));
				if (editor != null) {
					HandlerHelper.getCommandStack(editor).execute(new ChangeStructCommand(mux, structuredType));
				} else {
					commands.add(new ChangeStructCommand(mux, structuredType));
				}
			}
		});
		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	public static void updateAllInstances(final IProject project, final Set<TypeEntry> typeEntries,
			final TypeLibrary typeLib) {
		final Command cmd = collectUpdateInstanceCommands(project, typeEntries, typeLib);
		executeCommand(cmd);
	}

	private static Command collectUpdateInstanceCommands(final IProject project, final Set<TypeEntry> typeEntries,
			final TypeLibrary typeLib) {
		final List<Command> commands = new ArrayList<>();
		updatedElements = new HashSet<>();
		final InstanceSearch instanceSearch = new InstanceSearch(FB.class::isInstance);
		instanceSearch.performProjectSearch(project).forEach(namedElem -> {
			if (namedElem instanceof final FB fb) {
				typeEntries.forEach(typeEntry -> {
					if (typeEntry.getFullTypeName().equalsIgnoreCase(fb.getFullTypeName())) {
						commands.add(new UpdateFBTypeCommand(fb, typeLib.getFBTypeEntry(fb.getFullTypeName())));
						updatedElements.add(fb);
					}
				});
			}
		});
		final CompoundCommand cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd.add(subCmd);
		}
		return cmd;
	}

	private static void executeCommand(final Command cmd) {
		final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (editor == null) {
			cmd.execute();
		} else {
			editor.getAdapter(CommandStack.class).execute(cmd);
		}
	}

}
