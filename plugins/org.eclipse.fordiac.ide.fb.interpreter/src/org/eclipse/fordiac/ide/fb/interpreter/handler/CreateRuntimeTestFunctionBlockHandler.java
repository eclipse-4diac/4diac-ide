/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *   Melanie Winter - added synchronization
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.CompositeTestFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.MatchFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.MuxFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.RunAllFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.TestsignalFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBlockGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateRuntimeTestFunctionBlockHandler extends AbstractHandler {
	private static final boolean SE_COMPLIANT = false; // for portability, allows generating files more compliant with
														// other tool

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		for (final FBType type : getSelectedFbTypes(event)) {
			final TestSuite testSuite = new TestSuite(type.getService().getServiceSequence());
			testSuite.getTestCases().removeIf(n -> (n.getdataSource().getServiceSequenceType().equals("FORBIDDEN"))); //$NON-NLS-1$

			if (!testSuite.getTestCases().isEmpty()) {
				final List<FBType> generated = performGeneration(type, testSuite);
				performSave(generated, type, event);
			}
		}
		return Status.OK_STATUS;
	}

	private static List<FBType> getSelectedFbTypes(final ExecutionEvent event) {
		final List<FBType> selected = HandlerUtil.getCurrentStructuredSelection(event).stream()
				.map(CreateRuntimeTestFunctionBlockHandler::getModel) //
				.filter(FBType.class::isInstance) //
				.map(FBType.class::cast).toList();
		if (selected.isEmpty()) {
			// try with an open editor, maybe there is an FBType
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			if (editor == null) {
				return Collections.emptyList();
			}
			return List.of(editor.getAdapter(FBType.class));
		}
		return selected;
	}

	private static Object getModel(final Object o) {
		if (o instanceof final IFile resource) {
			return TypeLibraryManager.INSTANCE.getTypeEntryForFile(resource).getType();
		}
		if (o instanceof final EditPart ep) {
			return ep.getModel();
		}
		if (o instanceof final ServiceSequence serv) {
			return serv.getService().getFBType();
		}
		if (o instanceof final Service s) {
			return s.getFBType();
		}
		return o;
	}

	private static void performSave(final List<FBType> generated, final FBType type, final ExecutionEvent event) {
		final IProject project = type.getTypeEntry().getFile().getProject();
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(project) {
			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				for (final FBType type : generated) {
					if (SE_COMPLIANT) {
						type.getTypeEntry().save(makeBlockCompliant(type), monitor);
					} else {
						type.getTypeEntry().save(type, monitor);
					}
				}
			}
		};

		Display.getDefault().syncExec(() -> {
			try {
				operation.run(new NullProgressMonitor());
			} catch (final Exception e) {
				displayErrorMessageSave(type.getName(), event);
			}

		});
	}

	private static LibraryElement makeBlockCompliant(final FBType type) {
		if (type instanceof final BasicFBType basic) {
			return AbstractBlockGenerator.createComplianceEcc(basic);
		}
		return type;
	}

	private static void displayErrorMessageSave(final String typeName, final ExecutionEvent event) {
		MessageDialog.openError(HandlerUtil.getActiveShell(event), "Save failed", //$NON-NLS-1$
				"Could not save generated files for type " + typeName); //$NON-NLS-1$
	}

	private static List<FBType> performGeneration(final FBType type, final TestSuite testSuite) {
		// testsignal generator block
		final FBType testType = new TestsignalFBGenerator(type, testSuite).generateTestFb();
		// matches the expected with the actual behaviour
		final FBType matchType = new MatchFBGenerator(type, testSuite).generateMatchFB();
		// sends the outcome of the test and the testname to the outputs of the
		// composite
		final FBType muxType = new MuxFBGenerator(type, testSuite).generateMuxFB();
		// generates the signals for the testsignal and mux fb
		final FBType runAllType = new RunAllFBGenerator(type, testSuite).generateRunAllFB();

		// list in specific order required by CompositeTestFBGenerator
		final List<FBType> fbTypes = List.of(testType, type, matchType, muxType, runAllType);
		final CompositeFBType compositeType = new CompositeTestFBGenerator(type, testSuite, fbTypes)
				.generateCompositeFB();

		return List.of(testType, matchType, muxType, runAllType, compositeType);
	}
}