/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class TypeFromTemplateCreator {

	private final IFile targetTypeFile;
	private final File typeTemplate;
	private final String packageName;
	private TypeEntry entry;

	public TypeFromTemplateCreator(final IFile targetTypeFile, final File typeTemplate) {
		this(targetTypeFile, typeTemplate, null);
	}

	public TypeFromTemplateCreator(final IFile targetTypeFile, final File typeTemplate, final String packageName) {
		this.targetTypeFile = targetTypeFile;
		this.typeTemplate = typeTemplate;
		this.packageName = packageName;
	}

	public void createTypeFromTemplate(final IProgressMonitor monitor) {
		entry = TypeLibraryManager.INSTANCE.getTypeLibrary(targetTypeFile.getProject()).createTypeEntry(targetTypeFile);
		if (entry == null) {
			return;
		}
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(getFirstExistingParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				final LibraryElement type = loadTemplate();
				type.setName(TypeEntry.getTypeNameFromFile(targetTypeFile));
				PackageNameHelper.setPackageName(type, packageName);
				setupIdentifcationAndVersionInfo(type, targetTypeFile.getProject());
				performTypeSpecificSetup(type);
				entry.save(type, monitor);
			}
		};
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getTargetException().getMessage(), e.getTargetException());
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}

	private LibraryElement loadTemplate() throws InvocationTargetException, CoreException {
		try {
			final LibraryElementResource resource = FordiacTypeResourceFactory.INSTANCE
					.createResource(URI.createFileURI(typeTemplate.getCanonicalPath()));
			resource.load(Map.of(LibraryElementResource.OPTION_TYPE_ENTRY, entry));
			if (!resource.getErrors().isEmpty()) {
				throw new CoreException(new MultiStatus(
						getClass(), IStatus.ERROR, resource.getErrors().stream().map(Diagnostic::getMessage)
								.map(Status::error).toArray(IStatus[]::new),
						Messages.TypeFromTemplateCreator_TemplateErrors, null));
			}
			return resource.getLibraryElement();
		} catch (final IOException e) {
			throw new InvocationTargetException(e);
		}
	}

	private IContainer getFirstExistingParent() {
		IContainer parent = targetTypeFile.getParent();

		while (parent != null && !parent.exists()) {
			parent = parent.getParent();
		}

		return (parent != null) ? parent : targetTypeFile.getProject();
	}

	@SuppressWarnings("static-method") // allow subclasses to override
	protected void performTypeSpecificSetup(final LibraryElement type) {
		// hook for subclasses to perform any type specific setup, e.g.,
		// saveassubapptype -> setup interface and network
		if ((type instanceof final FunctionFBType functionFBType)
				&& (functionFBType.getBody() instanceof final TextFunctionBody body)) {
			// for function types we need to also set the name inside the body
			body.setText(body.getText().replace("Function", functionFBType.getName())); //$NON-NLS-1$
		}
	}

	private static void setupIdentifcationAndVersionInfo(final LibraryElement type, final IProject project) {
		TypeManagementPreferencesHelper.setupIdentification(type, project);
		TypeManagementPreferencesHelper.setupVersionInfo(type, project);
	}

	public TypeEntry getTypeEntry() {
		return entry;
	}
}
