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
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdatePinInTypeDeclarationCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public final class FBUpdater {

	private static Set<INamedElement> updatedElements;

	private FBUpdater() {
		// just a utility class, no instancing here, por favor
	}

	public static Set<INamedElement> getUpdatedElements() {
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
			final List<DataTypeEntry> dataTypeEntries) {
		final List<Command> commands = new ArrayList<>();
		types.forEach(type -> commands
				.add(createUpdatePinInTypeDeclarationCommand(type, dataTypeEntries.get(types.indexOf(type)))));
		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	public static Command createUpdatePinInTypeDeclarationCommand(final FBType type,
			final DataTypeEntry dataTypeEntry) {
		return new UpdatePinInTypeDeclarationCommand(type, dataTypeEntry);
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

	public static List<FBNetworkElement> updateAllInstances(final Set<TypeEntry> typeEntries) {
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(typeEntries);
		final List<FBNetworkElement> elements = search.performSearch().stream()
				.filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast).toList();
		elements.forEach(el -> {
			final var cmd = new UpdateFBTypeCommand(el, el.getTypeEntry());
			AbstractLiveSearchContext.executeAndSave(cmd, el, null);
		});
		return elements;
	}

}
