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
 * Melanie Winter - initial API and implementation and/or initial documentation
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
import org.eclipse.fordiac.ide.fb.interpreter.monitorgen.CompositeMonitorFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.monitorgen.MonitorFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateMonitorFBHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final FBType type = editor.getAdapter(FBType.class);

		// user should have a service sequence editor open
		if (type == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					Messages.RecordExecutionTraceHandler_Incorrect_Selection,
					Messages.CreateRuntimeTestFunctionBlockHandler_Select_Service_Model);
			return Status.CANCEL_STATUS;
		}
		if (type.getService().getServiceSequence().isEmpty()) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "No Service Sequence found", //$NON-NLS-1$
					"No Service Sequence found, please create a forbidden sequence."); //$NON-NLS-1$
			return Status.CANCEL_STATUS;
		}
		final TestSuite testSuite = new TestSuite(type);

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(
				type.getTypeEntry().getFile().getProject()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				perform(selection, type, testSuite, monitor);
			}
		};
		try {
			editor.getSite().getWorkbenchWindow().run(true, false, operation);
		} catch (final Exception e) {
			return Status.error(e.getMessage(), e);
		}

		return Status.OK_STATUS;
	}

	private static void perform(final IStructuredSelection selection, final FBType type, final TestSuite testSuite,
			final IProgressMonitor monitor) throws CoreException {
		FBType testtype;

		// if a service sequence is selected only the monitorFB for this sequence is
		// generated
		if (((EditPart) selection.getFirstElement() != null)
				&& (((EditPart) selection.getFirstElement()).getModel() instanceof final ServiceSequenceImpl s)) {
			TestCase testCase = null;
			for (final TestCase testC : testSuite.getTestCases()) {
				if (testC.getName().equals(s.getName())
						&& testC.getdataSource().getServiceSequenceType().equals("FORBIDDEN")) { //$NON-NLS-1$
					testCase = testC;
				}
			}
			if (testCase != null) {
				testtype = new MonitorFBGenerator(type, testSuite, testCase).generateTestFb();
				testtype.getTypeEntry().save(testtype, monitor);

			}
		}
		// if there is no selection a complete monitoring composite fb is created
		else if (selection instanceof StructuredSelection) {
			final List<FBType> monitorFBs = new ArrayList<>();
			for (final TestCase testCase : testSuite.getTestCases()) {
				if (testCase.getdataSource().getServiceSequenceType().equals("FORBIDDEN")) { //$NON-NLS-1$
					testtype = new MonitorFBGenerator(type, testSuite, testCase).generateTestFb();
					testtype.getTypeEntry().save(testtype, monitor);
					monitorFBs.add(testtype);
				}
			}
			final CompositeFBType compositeType = new CompositeMonitorFBGenerator(type, monitorFBs)
					.generateCompositeFB();
			compositeType.getTypeEntry().save(compositeType, monitor);
		}
	}
}
