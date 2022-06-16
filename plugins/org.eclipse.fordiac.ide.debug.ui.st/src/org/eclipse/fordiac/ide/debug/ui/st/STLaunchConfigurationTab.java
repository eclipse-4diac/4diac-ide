/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.st;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.debug.st.STLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.eval.st.variable.STVariableOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class STLaunchConfigurationTab extends MainLaunchConfigurationTab {
	private static final String FILE_EXTENSION = "stfunc"; //$NON-NLS-1$

	private ComboViewer functionCombo;

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		final Composite functionComponent = createFunctionComponent((Composite) getControl());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(functionComponent);

		final Composite argumentsComponent = createArgumentsComponent((Composite) getControl());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(argumentsComponent);
	}

	protected Composite createFunctionComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Function"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		functionCombo = new ComboViewer(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		functionCombo.setContentProvider(ArrayContentProvider.getInstance());
		functionCombo.setLabelProvider(new FunctionsLabelProvider());
		functionCombo.addSelectionChangedListener(e -> handleFunctionUpdated());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(functionCombo.getCombo());

		return group;
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.removeAttribute(STLaunchConfigurationAttributes.FUNCTION);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		try {
			final STFunctionSource source = getSource();
			final List<STFunction> functions = getFunctions(source);
			functionCombo.setInput(functions);
			if (!functions.isEmpty()) {
				final STFunction function = STLaunchConfigurationAttributes.getFunction(configuration, source,
						functions.get(0));
				functionCombo.setSelection(new StructuredSelection(function), true);
			}
		} catch (final CoreException e) {
			// ignore
		}
		initializeArgumentsFrom(configuration);
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		final STFunction function = (STFunction) functionCombo.getStructuredSelection().getFirstElement();
		if (function != null) {
			configuration.setAttribute(STLaunchConfigurationAttributes.FUNCTION, function.getName());
		} else {
			configuration.removeAttribute(STLaunchConfigurationAttributes.FUNCTION);
		}
	}

	@Override
	protected void handleResourceUpdated() {
		final STFunctionSource source = getSource();
		// function
		final STFunction oldFunction = getFunction();
		final List<STFunction> functions = getFunctions(source);
		functionCombo.setInput(functions);
		if (!functions.isEmpty()) {
			STFunction function;
			if (oldFunction != null) {
				function = functions.stream().filter(e -> e.getName().equals(oldFunction.getName())).findFirst()
						.orElse(functions.get(0));
			} else {
				function = functions.get(0);
			}
			functionCombo.setSelection(new StructuredSelection(function), true);
		}
		super.handleResourceUpdated();
	}

	protected void handleFunctionUpdated() {
		updateArguments();
		updateLaunchConfigurationDialog();
	}

	@Override
	protected boolean filterTargetResource(final IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			return resource.getFileExtension() != null && resource.getFileExtension().equalsIgnoreCase(FILE_EXTENSION);
		}
		return super.filterTargetResource(resource);
	}

	@Override
	protected List<Variable<?>> getDefaultArguments() {
		final STFunction function = getFunction();
		if (function != null) {
			return Stream.concat(function.getInputParameters().stream(), function.getInOutParameters().stream())
					.map(STVarDeclaration.class::cast).map(STVariableOperations::newVariable)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	protected static List<STFunction> getFunctions(final STFunctionSource source) {
		if (source != null) {
			return source.getFunctions();
		}
		return Collections.emptyList();
	}

	protected STFunction getFunction() {
		return (STFunction) functionCombo.getStructuredSelection().getFirstElement();
	}

	protected STFunctionSource getSource() {
		final IResource resource = getResource();
		if (resource instanceof IFile) {
			return STFunctionParseUtil.parse(URI.createPlatformResourceURI(resource.getFullPath().toString(), true),
					null, null, null);
		}
		return null;
	}

	private static class FunctionsLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof STFunction) {
				final STFunction function = (STFunction) element;
				return function.getName();
			}
			return super.getText(element);
		}
	}
}
