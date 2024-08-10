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
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.TestsignalFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractBlockGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateRuntimeTestFunctionBlockHandler extends AbstractHandler {
	private static final boolean SCHNEIDER_COMPLICIT = false;

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
		final FBType testType = new TestsignalFBGenerator(type, testSuite).generateTestFb();
		if (SCHNEIDER_COMPLICIT) {
			final FBType fb = makeBlockCompliant((BasicFBType) testType);
			fb.getTypeEntry().save(fb, monitor);
		} else {
			testType.getTypeEntry().save(testType, monitor);
		}

		// matches the expected with the actual behaviour
		final List<FBType> list = new ArrayList<>();
		Display.getDefault().syncExec(() -> {
			final FBType matchType = new MatchFBGenerator(type, testSuite).generateMatchFB();

			try {
				if (SCHNEIDER_COMPLICIT) {
					final FBType fb = makeBlockCompliant((BasicFBType) matchType);
					fb.getTypeEntry().save(fb, monitor);
				} else {
					matchType.getTypeEntry().save(matchType, monitor);
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}

			// sends the outcome of the test and the testname to the outputs of the
			// composite
			final FBType muxType = new MuxFBGenerator(type, testSuite).generateMuxFB();
			try {
				if (SCHNEIDER_COMPLICIT) {
					final FBType fb = makeBlockCompliant((BasicFBType) muxType);
					fb.getTypeEntry().save(fb, monitor);
				} else {
					muxType.getTypeEntry().save(muxType, monitor);
				}

			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}

			// generates the signals for the testsignal and mux fb
			final FBType runAllType = new RunAllFBGenerator(type, testSuite).generateRunAllFB();
			try {
				if (SCHNEIDER_COMPLICIT) {
					final FBType fb = makeBlockCompliant((BasicFBType) runAllType);
					fb.getTypeEntry().save(fb, monitor);
				} else {
					runAllType.getTypeEntry().save(runAllType, monitor);
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}

			// don't change order, generateCompositeFB won't work then
			list.add(testType);
			list.add(type);
			list.add(matchType);
			list.add(muxType);
			list.add(runAllType);

			final CompositeFBType compositeType = new CompositeTestFBGenerator(type, testSuite, list)
					.generateCompositeFB();
			try {
				compositeType.getTypeEntry().save(compositeType, monitor);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}

		});
	}

	private static BasicFBType makeBlockCompliant(final BasicFBType fb) {
		return AbstractBlockGenerator.createComplianceEcc(fb);
	}
}