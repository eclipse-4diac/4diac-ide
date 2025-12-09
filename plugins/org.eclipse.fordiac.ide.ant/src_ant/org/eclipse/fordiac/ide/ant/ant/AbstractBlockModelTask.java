/*******************************************************************************
 * Copyright (c) 2023 - 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.FunctionFBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public abstract class AbstractBlockModelTask extends Task {

	public void setProjectname(final String projectname) {
		this.projectname = nullCheckString(projectname);
	}

	public void setBlockname(final String blockname) {
		this.blockname = nullCheckString(blockname);
	}

	public void setFileending(final String fileending) {
		this.fileending = fileending == null ? TypeLibraryTags.FB_TYPE_FILE_ENDING : fileending;
	}

	@Override
	public final void execute() throws BuildException {

		final var fordiacProject = getFordiacProject(projectname);
		final var tl = getTypeLibrary(fordiacProject, projectname);
		if (fileending.equals(EMPTY_STRING)) {
			final var entries = getTypeEntries(tl, blockname);
			if (entries.isEmpty()) {
				throw new BuildException(MessageFormat.format("Can not get TypeEntry for {0}", blockname)); //$NON-NLS-1$
			}
			for (final var te : entries) {
				final LibraryElement fb = getType(te, blockname);

				modifyBlock(fb);
			}
		} else {
			final var te = getTypeEntry(tl, blockname, fileending);
			final LibraryElement fb = getType(te, blockname);

			modifyBlock(fb);
		}
	}

	protected static LibraryElement getType(final TypeEntry t, final String blockname) {
		final var fb = t.getType();
		if (fb == null) {
			throw new BuildException(MessageFormat.format("Can not get Type for {0}", blockname)); //$NON-NLS-1$
		}
		return fb;
	}

	protected static List<TypeEntry> getTypeEntries(final TypeLibrary tl, final String blockname) {
		final List<TypeEntry> entries = new LinkedList<>();
		TypeEntry entry;

		entry = tl.getAdapterTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getAttributeTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getDataTypeLibrary().getDerivedTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getDeviceTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getFBTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getGlobalConstantsEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getResourceTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getSegmentTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getSubAppTypeEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}
		entry = tl.getSystemEntry(blockname);
		if (entry != null) {
			entries.add(entry);
		}

		return entries;
	}

	protected static TypeEntry getTypeEntry(final TypeLibrary tl, final String blockname, final String fileending) {
		TypeEntry typeEntry = null;
		switch (fileending) {
		case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING: {
			typeEntry = tl.getAdapterTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING: {
			typeEntry = tl.getAttributeTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.DATA_TYPE_FILE_ENDING: {
			typeEntry = tl.getDataTypeLibrary().getDerivedTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING: {
			typeEntry = tl.getDeviceTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.FB_TYPE_FILE_ENDING: {
			typeEntry = tl.getFBTypeEntry(blockname);
			if (typeEntry instanceof FunctionFBTypeEntry) {
				typeEntry = null;
			}
			break;
		}
		case TypeLibraryTags.FC_TYPE_FILE_ENDING: {
			typeEntry = tl.getFBTypeEntry(blockname);
			if (!(typeEntry instanceof FunctionFBTypeEntry)) {
				typeEntry = null;
			}
			break;
		}
		case TypeLibraryTags.GLOBAL_CONST_FILE_ENDING: {
			typeEntry = tl.getGlobalConstantsEntry(blockname);
			break;
		}
		case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING: {
			typeEntry = tl.getResourceTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING: {
			typeEntry = tl.getSegmentTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING: {
			typeEntry = tl.getSubAppTypeEntry(blockname);
			break;
		}
		case TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING: {
			typeEntry = tl.getSystemEntry(blockname);
			break;
		}

		default: {
			throw new BuildException(MessageFormat.format("Unknown file ending: {0}", fileending)); //$NON-NLS-1$
		}
		}
		if (typeEntry == null) {
			throw new BuildException(MessageFormat.format("Can not get {0}-TypeEntry for {1}", fileending, blockname)); //$NON-NLS-1$
		}
		return typeEntry;
	}

	protected static TypeLibrary getTypeLibrary(final IProject fordiacProject, final String projectname) {
		final var tl = TypeLibraryManager.INSTANCE.getTypeLibrary(fordiacProject);
		if (tl == null) {
			throw new BuildException(MessageFormat.format("Can not get TypeLib for {0}", projectname)); //$NON-NLS-1$
		}
		return tl;
	}

	protected static IProject getFordiacProject(final String projectname) {
		final var fordiacProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectname);
		if (fordiacProject == null) {
			throw new BuildException(MessageFormat.format("Project named {0} not in workspace", projectname));//$NON-NLS-1$
		}
		return fordiacProject;
	}

	protected abstract void modifyBlock(final LibraryElement fb);

	protected String projectname = EMPTY_STRING;
	protected String blockname = EMPTY_STRING;
	protected String fileending = TypeLibraryTags.FB_TYPE_FILE_ENDING;

	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

	protected static String nullCheckString(final String s) {
		return s == null ? EMPTY_STRING : s;
	}

}
