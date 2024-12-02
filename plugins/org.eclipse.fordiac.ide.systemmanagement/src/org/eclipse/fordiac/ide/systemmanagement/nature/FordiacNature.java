/*******************************************************************************
 * Copyright (c) 2008, 2014 Profactor GmbH, fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *     - add configure and deconfigure implementation
 *     - add validation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.nature;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.systemmanagement.Messages;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.ui.XtextProjectHelper;

public class FordiacNature implements IProjectNature {

	public static final int MISSING_XTEXT_NATURE = 1;
	public static final int MISSING_EXPORT_BUILDER = 2;

	/** The project. */
	private IProject project;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure() throws CoreException {
		final IProjectDescription description = project.getDescription();
		boolean changed = false;
		changed |= configureXtextNature(description);
		changed |= configureExportBuilder(description);
		if (changed) {
			project.setDescription(description, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deconfigure() throws CoreException {
		final IProjectDescription description = project.getDescription();
		if (deconfigureExportBuilder(description)) {
			project.setDescription(description, null);
		}
		project.deleteMarkers(FordiacErrorMarker.PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
	}

	public static boolean configureXtextNature(final IProjectDescription description) {
		if (!description.hasNature(XtextProjectHelper.NATURE_ID)) {
			final String[] natures = description.getNatureIds();
			final String[] newNatures = Arrays.copyOf(natures, natures.length + 1);
			newNatures[natures.length] = XtextProjectHelper.NATURE_ID;
			description.setNatureIds(newNatures);
			return true;
		}
		return false;
	}

	public static boolean configureExportBuilder(final IProjectDescription description) {
		final ICommand[] commands = description.getBuildSpec();
		if (Stream.of(commands).noneMatch(FordiacNature::isExportBuilderCommand)) {
			final ICommand[] newCommands = Arrays.copyOf(commands, commands.length + 1);
			final ICommand command = description.newCommand();
			command.setBuilderName(SystemManager.FORDIAC_EXPORT_BUILDER_ID);
			newCommands[commands.length] = command;
			description.setBuildSpec(newCommands);
			return true;
		}
		return false;
	}

	public static boolean deconfigureExportBuilder(final IProjectDescription description) {
		final ICommand[] commands = description.getBuildSpec();
		final ICommand[] newCommands = Stream.of(commands).filter(Predicate.not(FordiacNature::isExportBuilderCommand))
				.toArray(ICommand[]::new);
		if (newCommands.length != commands.length) {
			description.setBuildSpec(newCommands);
			return true;
		}
		return false;
	}

	public void validate() throws CoreException {
		final List<ErrorMarkerBuilder> builders = new ArrayList<>();
		if (!project.hasNature(XtextProjectHelper.NATURE_ID)) {
			builders.add(ErrorMarkerBuilder
					.createErrorMarkerBuilder(
							MessageFormat.format(Messages.FordiacNature_MissingXtextNature, project.getName()))
					.setType(FordiacErrorMarker.PROJECT_CONFIGURATION_MARKER)
					.setLocation(Messages.FordiacNature_Location).setSource(getClass().getName())
					.setCode(MISSING_XTEXT_NATURE));
		}
		if (!hasExportBuilderCommand()) {
			builders.add(ErrorMarkerBuilder
					.createErrorMarkerBuilder(
							MessageFormat.format(Messages.FordiacNature_MissingExportBuilder, project.getName()))
					.setType(FordiacErrorMarker.PROJECT_CONFIGURATION_MARKER)
					.setLocation(Messages.FordiacNature_Location).setSource(getClass().getName())
					.setCode(MISSING_EXPORT_BUILDER));
		}
		FordiacMarkerHelper.updateMarkers(project, FordiacErrorMarker.PROJECT_CONFIGURATION_MARKER, builders, true);
	}

	public boolean hasExportBuilderCommand() throws CoreException {
		return Stream.of(project.getDescription().getBuildSpec()).anyMatch(FordiacNature::isExportBuilderCommand);
	}

	protected static boolean isExportBuilderCommand(final ICommand command) {
		return SystemManager.FORDIAC_EXPORT_BUILDER_ID.equals(command.getBuilderName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProject getProject() {
		return project;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProject(final IProject project) {
		this.project = project;
		try {
			validate();
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}
}
