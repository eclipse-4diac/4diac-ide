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
package org.eclipse.fordiac.ide.gef.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.widgets.InitialValueVariableWidget;
import org.eclipse.fordiac.ide.gef.widgets.VariableWidget;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class InitialValueVariableDialog extends VariableDialog {

	private final Variable<?> baseVariable;
	private final Set<Variable<?>> explicitlyInitialized;

	protected InitialValueVariableDialog(final Shell shell, final String title, final Variable<?> variable,
			final Variable<?> baseVariable, final Set<Variable<?>> explicitlyInitialized) {
		super(shell, title, List.of(variable));
		this.baseVariable = baseVariable;
		this.explicitlyInitialized = explicitlyInitialized;
	}

	@Override
	protected VariableWidget createVariableWidget() {
		return new InitialValueVariableWidget(List.of(baseVariable), explicitlyInitialized);
	}

	public static Optional<String> open(final Shell shell, final ITypedElement element, final String initialValue) {
		return open(shell, Messages.VariableDialog_DefaultTitle, element, initialValue);
	}

	public static Optional<String> open(final Shell shell, final String title, final ITypedElement element,
			final String initialValue) {
		final Variable<?>[] variable = new Variable[1];
		final Variable<?>[] baseVariable = new Variable[1];
		final Set<Variable<?>> explicitlyInitialized = Collections.newSetFromMap(new IdentityHashMap<>());
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(monitor -> {
				variable[0] = newDialogVariable(element, getDialogInitialValue(element, initialValue),
						explicitlyInitialized);
				baseVariable[0] = newBaseVariable(element);
			});
		} catch (final InvocationTargetException e) {
			ErrorDialog.openError(shell, title, null,
					Status.error(MessageFormat.format(Messages.VariableDialog_ValueError, element.getQualifiedName()),
							e.getTargetException()));
			return Optional.empty();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return Optional.empty();
		}

		final InitialValueVariableDialog dialog = new InitialValueVariableDialog(shell, title, variable[0],
				baseVariable[0], explicitlyInitialized);
		if (dialog.open() == OK) {
			return Optional.of(toExplicitInitialValue(variable[0],
					((InitialValueVariableWidget) dialog.getVariableWidget())::isExplicitInitialValue));
		}
		return Optional.empty();
	}

	private static Variable<?> newDialogVariable(final ITypedElement element, final String initialValue,
			final Set<Variable<?>> explicitlyInitialized) {
		return switch (element) {
		case final VarDeclaration varDeclaration ->
			VariableOperations.newVariable(varDeclaration, initialValue, explicitlyInitialized);
		case final Attribute attribute ->
			VariableOperations.newVariable(attribute, initialValue, explicitlyInitialized);
		case final DirectlyDerivedType directlyDerivedType ->
			VariableOperations.newVariable(directlyDerivedType, initialValue, explicitlyInitialized);
		default -> VariableOperations.newVariable(element, initialValue);
		};
	}

	private static Variable<?> newBaseVariable(final ITypedElement element) {
		return switch (element) {
		case final VarDeclaration varDeclaration ->
			VariableOperations.newVariableWithoutDeclaredInitialValue(varDeclaration);
		case final Attribute attribute -> VariableOperations.newVariable(attribute.getName(), attribute.getType());
		case final DirectlyDerivedType directlyDerivedType ->
			VariableOperations.newVariable(directlyDerivedType.getName(), directlyDerivedType.getBaseType());
		default -> VariableOperations.newVariable(element, null);
		};
	}

	protected static String toExplicitInitialValue(final Variable<?> variable,
			final Predicate<Variable<?>> explicitInitialValuePredicate) {
		return switch (variable) {
		case final StructVariable structVariable ->
			toExplicitStructInitialValue(structVariable, explicitInitialValuePredicate);
		case final ArrayVariable arrayVariable ->
			toExplicitArrayInitialValue(arrayVariable, explicitInitialValuePredicate);
		default -> explicitInitialValuePredicate.test(variable) ? variable.toString() : ""; //$NON-NLS-1$
		};
	}

	private static String toExplicitStructInitialValue(final StructVariable variable,
			final Predicate<Variable<?>> explicitInitialValuePredicate) {
		final String members = variable.getMembers().values().stream()
				.map(member -> toExplicitStructMemberInitialValue(member, explicitInitialValuePredicate))
				.filter(Predicate.not(String::isEmpty)).collect(Collectors.joining(", ")); //$NON-NLS-1$
		return members.isEmpty() ? "" : '(' + members + ')'; //$NON-NLS-1$
	}

	private static String toExplicitStructMemberInitialValue(final Variable<?> member,
			final Predicate<Variable<?>> explicitInitialValuePredicate) {
		final String value = toExplicitInitialValue(member, explicitInitialValuePredicate);
		return value.isEmpty() ? "" : member.getName() + " := " + value; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String toExplicitArrayInitialValue(final ArrayVariable variable,
			final Predicate<Variable<?>> explicitInitialValuePredicate) {
		final List<Variable<?>> elements = variable.getElements();
		final List<String> explicitValues = elements.stream()
				.map(element -> toExplicitInitialValue(element, explicitInitialValuePredicate)).toList();
		final int lastExplicitIndex = IntStream.range(0, explicitValues.size())
				.filter(index -> !explicitValues.get(index).isEmpty()).max().orElse(-1);
		if (lastExplicitIndex < 0) {
			return ""; //$NON-NLS-1$
		}

		return IntStream.rangeClosed(0, lastExplicitIndex)
				.mapToObj(index -> explicitValues.get(index).isEmpty() ? elements.get(index).toString()
						: explicitValues.get(index))
				.collect(Collectors.joining(", ", "[", "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private static String getDialogInitialValue(final ITypedElement element, final String initialValue) {
		final String declaredInitialValue = switch (element) {
		case final VarDeclaration varDeclaration -> VariableOperations.getDeclaredInitialValue(varDeclaration);
		case final Attribute attribute -> attribute.getValue();
		case final DirectlyDerivedType directlyDerivedType -> directlyDerivedType.getInitialValue();
		default -> null;
		};
		if (initialValue == null) {
			return declaredInitialValue;
		}
		if (declaredInitialValue == null
				&& Objects.equals(initialValue, VariableOperations.newVariable(element).toString())) {
			return null;
		}
		return initialValue;
	}
}
