/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactoringUtil;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberContext;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberRefactoring;
import org.eclipse.fordiac.ide.util.ErrorMessenger;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public final class AddStructMemberRefactoringUI {
	public static void openAsync(final Shell shell, final VarDeclaration structPin) {
		final Optional<AddStructMemberContext> context = AddStructMemberContext.forStructPin(structPin);
		if (context.isEmpty()) {
			Display.getDefault().asyncExec(
					() -> ErrorMessenger.popUpErrorMessage(Messages.AddStructMemberRefactoring_InvalidContext));
			return;
		}
		Display.getDefault().asyncExec(() -> open(shell, context.orElseThrow()));
	}

	public static void openAsync(final Shell shell, final VarDeclaration connectionPin, final EObject target) {
		openAsync(shell, connectionPin, target, null);
	}

	public static void openAsync(final Shell shell, final VarDeclaration connectionPin, final EObject target,
			final BiFunction<IInterfaceElement, IInterfaceElement, Command> borderCrossingCommandFactory) {
		final Optional<AddStructMemberContext> context = AddStructMemberContext.forTarget(connectionPin, target,
				borderCrossingCommandFactory);
		if (context.isEmpty()) {
			Display.getDefault().asyncExec(
					() -> ErrorMessenger.popUpErrorMessage(Messages.AddStructMemberRefactoring_CannotConnect));
			return;
		}
		Display.getDefault().asyncExec(() -> open(shell, context.orElseThrow()));
	}

	private static void open(final Shell requestedShell, final AddStructMemberContext context) {
		try {
			RefactoringUtil.saveAllAndBuild(context.getProject());
			final AddStructMemberRefactoring refactoring = new AddStructMemberRefactoring(context);
			final RefactoringStatus initialStatus = refactoring.checkInitialConditions(new NullProgressMonitor());
			if (initialStatus.hasFatalError()) {
				ErrorMessenger.popUpErrorMessage(initialStatus.getMessageMatchingSeverity(initialStatus.getSeverity()));
				return;
			}

			final AddStructMemberRefactoringWizard wizard = new AddStructMemberRefactoringWizard(refactoring);
			new RefactoringWizardOpenOperation(wizard).run(getShell(requestedShell), refactoring.getName());
		} catch (final OperationCanceledException e) {
			// The user canceled saving or the refactoring wizard.
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (final InvocationTargetException e) {
			reportError(e.getCause() != null ? e.getCause() : e);
		} catch (final Exception e) {
			reportError(e);
		}
	}

	private static Shell getShell(final Shell requestedShell) {
		if (requestedShell != null && !requestedShell.isDisposed()) {
			return requestedShell;
		}
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	private static void reportError(final Throwable throwable) {
		FordiacLogHelper.logError(Messages.AddStructMemberRefactoring_UIError, throwable);
		ErrorMessenger.popUpErrorMessage(Messages.AddStructMemberRefactoring_UIError);
	}

	private AddStructMemberRefactoringUI() {
		throw new UnsupportedOperationException();
	}
}
