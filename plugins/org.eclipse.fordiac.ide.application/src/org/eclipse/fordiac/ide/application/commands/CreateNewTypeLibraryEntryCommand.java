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
 *   Dario Romano
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public class CreateNewTypeLibraryEntryCommand extends Command {
	private final TypeLibrary typeLib;
	private final TypeEntry oldTypeEntry;
	private List<ErrorMarkerInterface> interfaces;
	private IFile newTypeEntryFile;
	private TypeEntry newTypeEntry;

	public CreateNewTypeLibraryEntryCommand(final TypeLibrary typeLib, final TypeEntry entry) {
		this.typeLib = typeLib;
		this.oldTypeEntry = entry;
	}

	public CreateNewTypeLibraryEntryCommand(final TypeLibrary typeLib, final TypeEntry entry,
			final List<ErrorMarkerInterface> interfaces) {
		this(typeLib, entry);
		this.interfaces = interfaces;
	}

	@Override
	public void execute() {
		newTypeEntryFile = getTargetTypeFile(oldTypeEntry.getType());
		newTypeEntry = typeLib.createTypeEntry(newTypeEntryFile);
		newTypeEntry.setType(oldTypeEntry.getType());
		addNetwork(newTypeEntry);
		if (interfaces != null) {
			addConnections(newTypeEntry, interfaces);
		}
		// check back here later. might want to have some user interaction as well when
		// creating the command
		try {
			newTypeEntry.save();
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage());
		}
	}

	private static void addConnections(final TypeEntry entry, final List<ErrorMarkerInterface> markers) {
		final InterfaceList interfaceList = entry.getType() instanceof FBType
				? ((FBType) entry.getType()).getInterfaceList()
				: LibraryElementFactory.eINSTANCE.createInterfaceList();
		// maybe filter correct sets out to add later or something.
		// markers.stream().filter(ErrorMarkerInterface::isIsInput).filter(Event.class::isInterface).map()
		for (final ErrorMarkerInterface marker : markers) {
			// do something with repaired endpoints
			// interfaceList.getEventInputs().add(marker.getRepairedEndpoint());
			// interfaceList.getRepairedEndpoint();
		}
		((FBType) entry.getType()).setInterfaceList(interfaceList);
	}

	private static void addNetwork(final TypeEntry entry) {
		if (entry.getType() instanceof final CompositeFBType compositeType) {
			final FBNetwork network = LibraryElementFactory.eINSTANCE.createFBNetwork();
			compositeType.setFBNetwork(network);
			// addConnections()
		}
	}

	private IFile getTargetTypeFile(final LibraryElement element) {
		String typeEnding = null;
		if (element instanceof SubAppType) {
			typeEnding = TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT;
		} else if (element instanceof AdapterFBType) {
			typeEnding = TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING_WITH_DOT;
		} else if (element instanceof BaseFBType || element instanceof CompositeFBType) {
			typeEnding = TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT;
		}
		final Path path = new Path(typeLib.getProject() + File.separator + element.getName() + typeEnding);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
	}

	@Override
	public void undo() {
		typeLib.removeTypeEntry(newTypeEntry);
	}

	@Override
	public void redo() {
		newTypeEntry = typeLib.createTypeEntry(newTypeEntryFile);
	}
}
