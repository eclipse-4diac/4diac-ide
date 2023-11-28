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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fb.interpreter.Messages;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.CompositeTestFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.MatchFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.MuxFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.RunAllFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.TestFbGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateRuntimeTestFunctionBlockHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final FBType type = editor.getAdapter(FBType.class);

		// user should have a service sequence editor open
		if (type == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					Messages.RecordExecutionTraceHandler_Incorrect_Selection,
					Messages.CreateRuntimeTestFunctionBlockHandler_Select_Service_Model);
			return Status.CANCEL_STATUS;
		}

		final TestSuite testSuite = new TestSuite(type.getService().getServiceSequence());
		testSuite.getTestCases().removeIf(n -> (n.getdataSource().getServiceSequenceType().equals("FORBIDDEN"))); //$NON-NLS-1$
		if (testSuite.getTestCases().isEmpty()) {
			return Status.OK_STATUS;
		}

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(
				type.getTypeEntry().getFile().getProject()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				perform(type, testSuite, monitor);
			}
		};
		try {
			editor.getSite().getWorkbenchWindow().run(true, false, operation);
		} catch (final Exception e) {
			return Status.error(e.getMessage(), e);
		}

		return Status.OK_STATUS;
	}

	private static void perform(final FBType type, final TestSuite testSuite, final IProgressMonitor monitor)
			throws CoreException {
		// testsignal generator block
		final FBType testType = new TestFbGenerator(type, testSuite).generateTestFb();
		testType.getTypeEntry().save(monitor);

		// matches the expected with the actual behaviour
		final FBType matchType = new MatchFBGenerator(type, testSuite).generateMatchFB();
		matchType.getTypeEntry().save(monitor);

		// sends the outcome of the test and the testname to the outputs of the
		// composite
		final FBType muxType = new MuxFBGenerator(type, testSuite).generateMuxFB();
		muxType.getTypeEntry().save(monitor);

		// generates the signals for the testsignal and mux fb
		final FBType runAllType = new RunAllFBGenerator(type, testSuite).generateRunAllFB();
		runAllType.getTypeEntry().save(monitor);

		// don't change order, generateCompositeFB won't work then
		final List<FBType> list = new ArrayList<>();
		list.add(testType);
		list.add(type);
		list.add(matchType);
		list.add(muxType);
		list.add(runAllType);

		final CompositeFBType compositeType = new CompositeTestFBGenerator(type, testSuite, list).generateCompositeFB();
		compositeType.getTypeEntry().save(monitor);
	}
}