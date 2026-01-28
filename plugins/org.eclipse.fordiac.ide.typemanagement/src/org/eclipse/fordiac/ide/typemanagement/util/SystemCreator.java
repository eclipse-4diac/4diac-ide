/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.commands.create.CreateApplicationCommand;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;

public class SystemCreator {

	private final IFile systemFile;
	private final String packageName;
	private final String applicationName;

	private SystemEntry entry;

	public SystemCreator(final IContainer location, final String name, final String applicationName) {
		this(location.getFile(new Path(name + SystemManager.SYSTEM_FILE_ENDING_WITH_DOT)), applicationName);
	}

	public SystemCreator(final IFile systemFile, final String applicationName) {
		this(systemFile, PackageNameHelper.getPackageNameFromFile(systemFile), applicationName);
	}

	public SystemCreator(final IFile systemFile, final String packageName, final String applicationName) {
		this.systemFile = systemFile;
		this.packageName = packageName;
		this.applicationName = applicationName;
	}

	public void createSystem(final IProgressMonitor monitor) throws CoreException {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(systemFile.getProject());
		entry = (SystemEntry) typeLibrary.createTypeEntry(systemFile);
		final AutomationSystem system = SystemImporter.createAutomationSystem();
		system.setName(TypeEntry.getTypeNameFromFile(systemFile));
		PackageNameHelper.setPackageName(system, packageName);
		TypeManagementPreferencesHelper.setupVersionInfo(system, systemFile.getProject());
		createApplication(system);
		entry.save(system, monitor);
	}

	private void createApplication(final AutomationSystem system) {
		if (applicationName != null) {
			final CreateApplicationCommand command = new CreateApplicationCommand(system, applicationName);
			if (command.canExecute()) {
				command.execute();
			}
		}
	}

	public SystemEntry getEntry() {
		return entry;
	}

	public AutomationSystem getSystem() {
		return entry.getType();
	}

	public Application getApplication() {
		return getSystem().getApplicationNamed(applicationName);
	}

	public IFile getSystemFile() {
		return systemFile;
	}

	public String getPackageName() {
		return packageName;
	}
}
