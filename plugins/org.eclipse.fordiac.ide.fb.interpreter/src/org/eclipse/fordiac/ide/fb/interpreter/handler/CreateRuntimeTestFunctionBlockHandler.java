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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fb.interpreter.Messages;
import org.eclipse.fordiac.ide.fb.interpreter.mm.TestFbGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.CompositeFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.RunAllFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.MatchFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.MuxFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.TestSuite;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateRuntimeTestFunctionBlockHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final FBType type = editor.getAdapter(FBType.class);

		// user should have a service sequence editor open
		if (type == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					Messages.RecordExecutionTraceHandler_Incorrect_Selection, "Please open a Service model");
			return Status.CANCEL_STATUS;
		}

		final TestSuite testSuite = new TestSuite(type.getService().getServiceSequence());
		testSuite.getTestCases().removeIf(n -> (n.getdataSource().getServiceSequenceType().equals("FORBIDDEN")));
		if (testSuite.getTestCases().isEmpty()) {
			return Status.OK_STATUS;
		}

		final FBType testtype = new TestFbGenerator(type, testSuite).generateTestFb();
		testtype.getTypeEntry().save();

		final FBType matchtype = new MatchFBGenerator(type, testSuite).generateMatchFB();
		matchtype.getTypeEntry().save();

		final FBType muxType = new MuxFBGenerator(type, testSuite).generateMuxFB();
		muxType.getTypeEntry().save();

		final FBType demuxType = new RunAllFBGenerator(type, testSuite).generateDemuxFB();
		demuxType.getTypeEntry().save();

		final List<FBType> list = new ArrayList<>();
		list.add(testtype);
		list.add(type);
		list.add(matchtype);
		list.add(muxType);
		list.add(demuxType);

		final CompositeFBType compositeType = new CompositeFBGenerator(type, testSuite, list).generateCompositeFB();
		compositeType.getTypeEntry().save();

		return Status.OK_STATUS;
	}
}