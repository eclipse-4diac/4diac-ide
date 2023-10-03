/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *					  Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added FB type update
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class UpdateInstancesChange extends Change {

	private final Collection<FBNetworkElement> instances;
	private final TypeEntry typeEntry;

	public UpdateInstancesChange(final Collection<FBNetworkElement> instances, final TypeEntry typeEntry) {
		this.instances = instances;
		this.typeEntry = typeEntry;
	}

	public UpdateInstancesChange(final FBNetworkElement instance, final TypeEntry typeEntry) {
		this(new ArrayList<>(), typeEntry);
		this.instances.add(instance);
	}

	public UpdateInstancesChange(final FBNetworkElement instance) {
		this(instance, null);
	}

	@Override
	public String getName() {
		final StringJoiner fbList = new StringJoiner(", "); //$NON-NLS-1$
		for (final FBNetworkElement fb : instances) {
			if (fb.getTypeName() != null) {
				fbList.add(fb.getQualifiedName() + " : " + fb.getTypeName()); //$NON-NLS-1$
			} else {
				fbList.add(fb.getQualifiedName());
			}
		}
		return "Update FB instances - " + fbList.toString(); //$NON-NLS-1$
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// can't think of a way to validate an update
	}

	private Command getCommand(final FBNetworkElement instance) {
		if (instance instanceof final SubApp subApp && !subApp.isTyped()) {
			return new UpdateUntypedSubAppInterfaceCommand(instance, (DataTypeEntry) typeEntry);
		}
		return new UpdateFBTypeCommand(instance, typeEntry);
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		Display.getDefault().syncExec(() -> {
			instances.stream().forEach(instance -> {
				final EObject rootContainer = EcoreUtil.getRootContainer(instance);
				if (rootContainer instanceof final LibraryElement libElement) {
					final Command command = getCommand(instance);
					final IEditorPart editor = getEffectedEditor(instance);
					executeCommand(libElement.getTypeEntry(), editor, command);
				}
			});

		});
		return null;
	}

	private static void executeCommand(final TypeEntry rootTypeEntry, final IEditorPart editor, final Command cmd) {
		if (editor == null) {
			cmd.execute();
			rootTypeEntry.save();
		} else {
			editor.getAdapter(CommandStack.class).execute(cmd);
		}
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

	private static IEditorPart getEffectedEditor(final FBNetworkElement fbNe) {
		final List<IEditorPart> editors = new ArrayList<>();
		final EObject rootContainer = EcoreUtil.getRootContainer(fbNe);
		if (rootContainer instanceof final LibraryElement elem) {
			Display.getDefault().syncExec(() -> {
				editors.add(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.findEditor(new FileEditorInput(elem.getTypeEntry().getFile())));
			});
		}
		if (!editors.isEmpty()) {
			return editors.get(0);
		}
		return null;
	}
}
