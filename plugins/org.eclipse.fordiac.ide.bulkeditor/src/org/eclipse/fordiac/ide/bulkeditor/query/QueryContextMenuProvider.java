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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.commands.ChangeQueryFeatureCommand;
import org.eclipse.fordiac.ide.bulkeditor.commands.CreateQueryElementCommand;
import org.eclipse.fordiac.ide.bulkeditor.commands.DeleteQueryElementCommand;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper.FieldConstraintEntry;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

/** Context menu of the query viewer to edit, save, load and search queries. */
public class QueryContextMenuProvider extends FordiacContextMenuProvider {

	private final EPackage queryPackage;
	private final Runnable onSave;
	private final Runnable onLoad;
	private final Runnable onSearch;

	public QueryContextMenuProvider(final EditPartViewer viewer, final ZoomManager zoomManager,
			final ActionRegistry registry, final EPackage queryPackage, final Runnable onSave, final Runnable onLoad,
			final Runnable onSearch) {
		super(viewer, zoomManager, registry);
		this.queryPackage = queryPackage;
		this.onSave = onSave;
		this.onLoad = onLoad;
		this.onSearch = onSearch;
	}

	@Override
	public void buildContextMenu(final IMenuManager menu) {
		super.buildContextMenu(menu);

		final List<? extends EditPart> selection = getViewer().getSelectedEditParts();
		if (!selection.isEmpty() && selection.get(0).getModel() instanceof final EObject selected) {
			addElementActions(menu, selected);
		} else {
			addQueryActions(menu);
		}
	}

	private void addElementActions(final IMenuManager menu, final EObject selected) {
		if (QueryModelHelper.isConstraint(selected)) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, createNegateAction(selected));
		}
		addCreateChildActions(menu, selected);
		for (final FieldConstraintEntry entry : QueryModelHelper.getContainedFieldConstraints(selected)) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
					createCommandAction(NLS.bind(Messages.RemoveChild, entry.reference().getName()),
							() -> new DeleteQueryElementCommand(entry.fieldConstraint())));
		}
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, getRegistry().getAction(ActionFactory.DELETE.getId()));
	}

	private void addCreateChildActions(final IMenuManager menu, final EObject selected) {
		for (final EReference ref : selected.eClass().getEAllContainments()) {
			if (canAddChild(selected, ref)) {
				for (final EClass type : QueryModelHelper.getAddableClasses(queryPackage, selected,
						ref.getEReferenceType())) {
					menu.appendToGroup(IWorkbenchActionConstants.GROUP_ADD,
							createCommandAction(NLS.bind(Messages.AddChild, QueryModelHelper.getChildLabel(ref, type)),
									() -> new CreateQueryElementCommand(selected, ref, type)));
				}
			}
		}
	}

	private static boolean canAddChild(final EObject parent, final EReference ref) {
		if (!ref.isMany() && parent.eIsSet(ref)) {
			return false;
		}
		if (QueryModelHelper.REF_PIN.equals(ref.getName())
				&& QueryModelHelper.isPinTargetQuery(EcoreUtil.getRootContainer(parent))) {
			// pins are already the target of the query
			return false;
		}
		if (QueryModelHelper.isConstraint(parent)
				&& QueryModelHelper.FIELD_CONSTRAINT.equals(ref.getEReferenceType().getName())) {
			return QueryModelHelper.isFieldAllowedForConstraint(parent, ref.getName());
		}
		return true;
	}

	private IAction createNegateAction(final EObject constraint) {
		final boolean negated = QueryModelHelper.isNegatedConstraint(constraint);
		final IAction action = new Action(Messages.Negate, IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				execute(Messages.Negate, new ChangeQueryFeatureCommand(constraint, QueryModelHelper.FEATURE_NEGATE,
						Boolean.valueOf(!negated)));
			}
		};
		action.setChecked(negated);
		return action;
	}

	private IAction createCommandAction(final String text, final Supplier<Command> commandSupplier) {
		return new Action(text) {
			@Override
			public void run() {
				execute(text, commandSupplier.get());
			}
		};
	}

	private void execute(final String label, final Command command) {
		command.setLabel(label);
		getViewer().getEditDomain().getCommandStack().execute(command);
	}

	private void addQueryActions(final IMenuManager menu) {
		menu.appendToGroup(GEFActionConstants.GROUP_SAVE, createAction(Messages.Save, onSave));
		menu.appendToGroup(GEFActionConstants.GROUP_SAVE, createAction(Messages.Load, onLoad));
		menu.appendToGroup(GEFActionConstants.GROUP_REST, createAction(Messages.Search, onSearch));
	}

	private static IAction createAction(final String text, final Runnable runnable) {
		return new Action(text) {
			@Override
			public void run() {
				runnable.run();
			}
		};
	}
}
