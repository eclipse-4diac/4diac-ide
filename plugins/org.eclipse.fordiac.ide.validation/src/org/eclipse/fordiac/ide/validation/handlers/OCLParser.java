/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 * Copyright (c) 2026 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - load project and build path OCL files
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.fordiac.ide.validation.ocl.OCLSourceScanner;
import org.eclipse.ocl.OCLInput;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.osgi.framework.Bundle;

public class OCLParser {
	private static final String CONSTRAINT_DIRECTORY = "constraints"; //$NON-NLS-1$
	private static final String CONSTRAINT_FILE_FBTYPE = "ECC.ocl"; //$NON-NLS-1$
	private static final String CONSTRAINT_FILE_APP = "FB.ocl"; //$NON-NLS-1$

	private OCLParser() {
	}

	public static List<Constraint> loadOCLConstraints(final INamedElement element) {
		final List<Constraint> constraints = new ArrayList<>();
		constraints.addAll(loadBundledOCLConstraints(element));
		constraints.addAll(loadBuildpathOCLConstraints(getProject(element)));
		return constraints;
	}

	private static List<Constraint> loadBundledOCLConstraints(final INamedElement element) {
		final List<Constraint> constraints = new ArrayList<>();
		final String constraintFile = getOCLFile(element);
		if (constraintFile == null) {
			return constraints;
		}
		final Bundle bundle = Activator.getDefault().getBundle();
		final URL url = FileLocator.find(bundle, new Path(CONSTRAINT_DIRECTORY + IPath.SEPARATOR + constraintFile));
		if (url == null) {
			return constraints;
		}
		try (InputStream in = FileLocator.toFileURL(url).openStream()) {
			constraints.addAll(parse(in));
		} catch (ParserException | IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return constraints;
	}

	private static List<Constraint> loadBuildpathOCLConstraints(final IProject project) {
		final List<Constraint> constraints = new ArrayList<>();
		if (project == null || !project.isAccessible()) {
			return constraints;
		}
		for (final IFile file : OCLSourceScanner.findOclFiles(project)) {
			constraints.addAll(loadOCLConstraints(file));
		}
		return constraints;
	}

	private static List<Constraint> loadOCLConstraints(final IFile file) {
		try {
			file.deleteMarkers(IValidationMarker.TYPE, true, org.eclipse.core.resources.IResource.DEPTH_ZERO);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		try (InputStream in = file.getContents()) {
			return parse(in);
		} catch (final ParserException e) {
			addParseMarker(file, e);
		} catch (final CoreException | IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return List.of();
	}

	private static List<Constraint> parse(final InputStream inputStream) throws ParserException, IOException {
		return Activator.getDefault().getOclInstance().parse(new OCLInput(inputStream));
	}

	private static void addParseMarker(final IFile file, final ParserException exception) {
		FordiacLogHelper.logError(exception.getMessage(), exception);
		FordiacMarkerHelper.updateMarkers(file, IValidationMarker.TYPE,
				List.of(ErrorMarkerBuilder.createErrorMarkerBuilder(exception.getMessage())
						.setType(IValidationMarker.TYPE).setSeverity(IMarker.SEVERITY_ERROR)
						.setLocation(file.getProjectRelativePath().toString())),
				true);
	}

	private static String getOCLFile(final INamedElement element) {
		if (element instanceof FBType) {
			return CONSTRAINT_FILE_FBTYPE;
		}

		if (element instanceof Application || element instanceof SubApp) {
			return CONSTRAINT_FILE_APP;
		}
		return null;
	}

	private static IProject getProject(final INamedElement element) {
		return switch (element) {
		case final Application application when application.getAutomationSystem() != null
				&& application.getAutomationSystem().getTypeLibrary() != null ->
			application.getAutomationSystem().getTypeLibrary().getProject();
		case final SubApp subApp when subApp.getTypeLibrary() != null -> subApp.getTypeLibrary().getProject();
		case final LibraryElement libraryElement when libraryElement.getTypeLibrary() != null ->
			libraryElement.getTypeLibrary().getProject();
		default -> null;
		};
	}
}
