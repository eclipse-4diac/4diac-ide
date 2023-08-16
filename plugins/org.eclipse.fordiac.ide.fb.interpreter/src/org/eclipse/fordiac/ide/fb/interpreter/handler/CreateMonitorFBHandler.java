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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fb.interpreter.Messages;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.MonitorFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
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
					Messages.RecordExecutionTraceHandler_Incorrect_Selection, "Please open a Service model");
			return Status.CANCEL_STATUS;
		}
		final TestSuite testSuite = new TestSuite(type);
		FBType testtype;

		if (((EditPart) selection.getFirstElement() != null)
				&& (((EditPart) selection.getFirstElement()).getModel() instanceof ServiceSequenceImpl)) {
			final ServiceSequenceImpl s = (ServiceSequenceImpl) ((EditPart) selection.getFirstElement()).getModel();
			TestCase testCase = null;
			for (final TestCase testC : testSuite.getTestCases()) {
				if (testC.getName().equals(s.getName())
						&& testC.getdataSource().getServiceSequenceType().equals("FORBIDDEN")) { //$NON-NLS-1$
					testCase = testC;
				}
			}
			if (testCase != null) {
				testtype = new MonitorFBGenerator(type, testSuite, testCase).generateTestFb();
				testtype.getTypeEntry().save();

			}
		} else if (selection instanceof StructuredSelection) {
			for (final TestCase testCase : testSuite.getTestCases()) {
				if (testCase.getdataSource().getServiceSequenceType().equals("FORBIDDEN")) { //$NON-NLS-1$
					testtype = new MonitorFBGenerator(type, testSuite, testCase).generateTestFb();
					testtype.getTypeEntry().save();
				}
			}
		}
		return Status.OK_STATUS;
	}
}
