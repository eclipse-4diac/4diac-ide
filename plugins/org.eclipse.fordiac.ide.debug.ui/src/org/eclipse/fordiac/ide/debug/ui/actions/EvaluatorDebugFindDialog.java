/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.actions;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.osgi.framework.FrameworkUtil;

public class EvaluatorDebugFindDialog extends FilteredItemsSelectionDialog {

	private final IStackFrame stackFrame;

	public EvaluatorDebugFindDialog(final Shell shell, final IStackFrame stackFrame) {
		super(shell);
		this.stackFrame = stackFrame;
		setTitle(Messages.EvaluatorDebugFindDialog_Title);
		setSelectionHistory(getSelectionHistory());
		setListLabelProvider(new EvaluatorDebugFindDialogListLabelProvider());
		setDetailsLabelProvider(new EvaluatorDebugFindDialogDetailsLabelProvider());
	}

	@Override
	protected Control createExtendedContentArea(final Composite parent) {
		return null;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ItemsFilter() {

			@Override
			public boolean matchItem(final Object item) {
				return matches(getElementSimpleName(item)) || matches(getElementName(item));
			}

			@Override
			public boolean isConsistentItem(final Object item) {
				return true;
			}
		};
	}

	@Override
	protected Comparator getItemsComparator() {
		return Comparator.comparing(this::getElementName).thenComparing(this::getElementExpression);
	}

	@Override
	protected IStatus validateItem(final Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected void fillContentProvider(final AbstractContentProvider contentProvider, final ItemsFilter itemsFilter,
			final IProgressMonitor progressMonitor) throws CoreException {
		fillContentProvider(stackFrame.getVariables(), contentProvider, itemsFilter, progressMonitor);
	}

	protected void fillContentProvider(final IVariable[] variables, final AbstractContentProvider contentProvider,
			final ItemsFilter itemsFilter, final IProgressMonitor progressMonitor) throws CoreException {
		final SubMonitor subMonitor = SubMonitor.convert(progressMonitor, variables.length);
		for (final IVariable variable : variables) {
			contentProvider.add(variable, itemsFilter);
			fillContentProvider(variable.getValue().getVariables(), contentProvider, itemsFilter, subMonitor.split(1));
		}
	}

	protected Optional<IVariable> findVariable(final Object element, final String expression) {
		try {
			return switch (element) {
			case final IStackFrame frame -> Stream.of(frame.getVariables())
					.map(child -> findVariable(child, expression)).flatMap(Optional::stream).findAny();
			case final IVariable variable when getElementExpression(variable).equals(expression) ->
				Optional.of(variable);
			case final IVariable variable when expression.startsWith(getElementExpression(variable)) ->
				Stream.of(variable.getValue().getVariables()).map(child -> findVariable(child, expression))
						.flatMap(Optional::stream).findAny();
			case null, default -> Optional.empty();
			};
		} catch (final DebugException e) {
			return Optional.empty();
		}
	}

	@Override
	public String getElementName(final Object item) {
		if (item instanceof final IVariable variable) {
			try {
				return variable.getName();
			} catch (final DebugException e) {
				return e.getLocalizedMessage();
			}
		}
		return Objects.toString(item);
	}

	public String getElementSimpleName(final Object item) {
		final String name = getElementName(item);
		final int index = name.lastIndexOf('.');
		if (index >= 0) {
			return name.substring(index + 1);
		}
		return name;
	}

	public String getElementExpression(final Object item) {
		if (item instanceof final EvaluatorDebugVariable variable) {
			return variable.getExpression();
		}
		return getElementName(item);
	}

	public String getElementDetail(final Object item) {
		if (item instanceof final EvaluatorDebugVariable variable) {
			return variable.getValue().getValueString();
		}
		return getElementName(item);
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		final IDialogSettings settings = PlatformUI
				.getDialogSettingsProvider(FrameworkUtil.getBundle(EvaluatorDebugFindDialog.class)).getDialogSettings();
		return DialogSettings.getOrCreateSection(settings, getClass().getSimpleName());
	}

	class EvaluatorDebugFindDialogListLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public String getText(final Object element) {
			return getElementName(element);
		}

		@Override
		public StyledString getStyledText(final Object element) {
			return new StyledString(getElementName(element)).append(" - " + getElementExpression(element), //$NON-NLS-1$
					StyledString.QUALIFIER_STYLER);
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof IVariable) {
				return DebugUITools.getImage(IDebugUIConstants.IMG_OBJS_VARIABLE);
			}
			return null;
		}
	}

	class EvaluatorDebugFindDialogDetailsLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object element) {
			return getElementDetail(element);
		}
	}

	class EvaluatorDebugFindDialogSelectionHistory extends SelectionHistory {

		@Override
		protected Object restoreItemFromMemento(final IMemento memento) {
			final String expression = memento.getTextData();
			if (expression != null) {
				return findVariable(stackFrame, expression);
			}
			return null;
		}

		@Override
		protected void storeItemToMemento(final Object item, final IMemento memento) {
			memento.putTextData(getElementExpression(item));
		}
	}
}
