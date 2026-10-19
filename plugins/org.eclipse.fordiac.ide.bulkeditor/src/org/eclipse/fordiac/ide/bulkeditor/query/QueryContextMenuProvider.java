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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

/** Context menu of the query viewer to edit, save, load and search queries. */
public class QueryContextMenuProvider extends FordiacContextMenuProvider {

	private enum MenuGroup {
		//@formatter:off
		FIELDS(1, Messages.QueryMenu_AddField),
		REMOVE_FIELDS(1, Messages.QueryMenu_RemoveField),
		CONSTRAINTS(2, Messages.QueryMenu_AddConstraint),
		AND_CONSTRAINTS(2, Messages.QueryMenu_AddAndConstraint),
		OR_CONSTRAINTS(2, Messages.QueryMenu_AddOrConstraint),
		INSTANCES(3, Messages.QueryMenu_AddInstance),
		TYPES(3, Messages.QueryMenu_AddType),
		CONNECTIONS(3, null),
		DATA_TYPES(4, null),
		OCCURRENCES(5, Messages.QueryMenu_AddOccurrence),
		TARGETS(6, null),
		OTHERS(7, null);
		//@formatter:onn

		private final int section;
		private final String submenuLabel;

		MenuGroup(final int section, final String submenuLabel) {
			this.section = section;
			this.submenuLabel = submenuLabel;
		}

		boolean isSubmenu() {
			return submenuLabel != null;
		}

		boolean isSameSection(final MenuGroup other) {
			return section == other.section;
		}

		static MenuGroup of(final EReference ref, final EClass type) {
			if (QueryModelHelper.FIELD_CONSTRAINT.equals(type.getName())) {
				return FIELDS;
			}
			if (QueryModelHelper.isConstraintClass(type)) {
				return switch (ref.getName()) {
				case QueryModelHelper.REF_AND_CONSTRAINTS -> AND_CONSTRAINTS;
				case QueryModelHelper.REF_OR_CONSTRAINTS -> OR_CONSTRAINTS;
				default -> CONSTRAINTS;
				};
			}
			if (QueryModelHelper.isSubtypeOf(type, QueryModelHelper.INSTANCE)) {
				return INSTANCES;
			}
			if (QueryModelHelper.CONNECTION.equals(type.getName())) {
				return CONNECTIONS;
			}
			if (QueryModelHelper.REF_STRUCT_TYPE.equals(ref.getName())
					|| QueryModelHelper.REF_ATTRIBUTE_TYPE.equals(ref.getName())) {
				return DATA_TYPES;
			}
			if (QueryModelHelper.isSubtypeOf(type, QueryModelHelper.TYPE)) {
				return TYPES;
			}
			if (QueryModelHelper.isSubtypeOf(type, QueryModelHelper.OCCURRENCE)) {
				return OCCURRENCES;
			}
			if (QueryModelHelper.isSubtypeOf(type, QueryModelHelper.TARGET_OPTION)) {
				return TARGETS;
			}
			return OTHERS;
		}
	}

	/**
	 * Names of the children in the menu: constraints and target options by class
	 * name, the others by reference name.
	 */
	private static final Map<String, String> CHILD_NAMES = Map.ofEntries(
			Map.entry(QueryModelHelper.FEATURE_NAME, Messages.QueryMenu_Name),
			Map.entry(QueryModelHelper.FEATURE_TYPE, Messages.QueryMenu_Type),
			Map.entry(QueryModelHelper.FEATURE_COMMENT, Messages.QueryMenu_Comment),
			Map.entry(QueryModelHelper.FEATURE_VALUE, Messages.QueryMenu_Value),
			Map.entry(QueryModelHelper.CONSTRAINT, Messages.QueryMenu_Constraint),
			Map.entry(QueryModelHelper.ATTRIBUTE_CONSTRAINT, Messages.QueryMenu_AttributeConstraint),
			Map.entry(QueryModelHelper.REF_SIMPLE_FB, Messages.QueryMenu_SimpleFB),
			Map.entry(QueryModelHelper.REF_BASIC_FB, Messages.QueryMenu_BasicFB),
			Map.entry(QueryModelHelper.REF_COMPOSITE_FB, Messages.QueryMenu_CompositeFB),
			Map.entry(QueryModelHelper.REF_SERVICE_INTERFACE_FB, Messages.QueryMenu_ServiceInterfaceFB),
			Map.entry(QueryModelHelper.REF_FUNCTION_FB, Messages.QueryMenu_FunctionFB),
			Map.entry(QueryModelHelper.REF_TYPED_SUBAPP, Messages.QueryMenu_TypedSubapp),
			Map.entry(QueryModelHelper.REF_UNTYPED_SUBAPP, Messages.QueryMenu_UntypedSubapp),
			Map.entry(QueryModelHelper.REF_CONNECTION, Messages.QueryMenu_Connection),
			Map.entry(QueryModelHelper.REF_SIMPLE_TYPE, Messages.QueryMenu_SimpleType),
			Map.entry(QueryModelHelper.REF_BASIC_TYPE, Messages.QueryMenu_BasicType),
			Map.entry(QueryModelHelper.REF_COMPOSITE_TYPE, Messages.QueryMenu_CompositeType),
			Map.entry(QueryModelHelper.REF_SERVICE_INTERFACE_TYPE, Messages.QueryMenu_ServiceInterfaceType),
			Map.entry(QueryModelHelper.REF_FUNCTION_TYPE, Messages.QueryMenu_FunctionType),
			Map.entry(QueryModelHelper.REF_SUBAPP_TYPE, Messages.QueryMenu_SubappType),
			Map.entry(QueryModelHelper.REF_STRUCT_TYPE, Messages.QueryMenu_StructType),
			Map.entry(QueryModelHelper.REF_ATTRIBUTE_TYPE, Messages.QueryMenu_AttributeType),
			Map.entry(QueryModelHelper.REF_APPLICATION_OCCURRENCE, Messages.QueryMenu_ApplicationOccurrence),
			Map.entry(QueryModelHelper.REF_COMPOSITE_FB_OCCURRENCE, Messages.QueryMenu_CompositeFBOccurrence),
			Map.entry(QueryModelHelper.REF_TYPED_SUBAPP_OCCURRENCE, Messages.QueryMenu_TypedSubappOccurrence),
			Map.entry(QueryModelHelper.REF_PIN, Messages.QueryMenu_Pin),
			Map.entry(QueryModelHelper.FEATURE_PLACEHOLDER, Messages.QueryMenu_Placeholder),
			Map.entry(QueryModelHelper.PIN_TARGET, Messages.QueryMenu_PinTarget),
			Map.entry(QueryModelHelper.ATTRIBUTE, Messages.QueryMenu_Attribute),
			Map.entry(QueryModelHelper.ATTRIBUTE_DECLARATION, Messages.QueryMenu_AttributeDeclaration));

	private static final String GROUP_CHILDREN = IWorkbenchActionConstants.GROUP_ADD;

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

	private void addQueryActions(final IMenuManager menu) {
		menu.appendToGroup(GEFActionConstants.GROUP_SAVE, createAction(Messages.Save, onSave));
		menu.appendToGroup(GEFActionConstants.GROUP_SAVE, createAction(Messages.Load, onLoad));
		menu.appendToGroup(GEFActionConstants.GROUP_REST, createAction(Messages.Search, onSearch));
	}

	private void addElementActions(final IMenuManager menu, final EObject selected) {
		if (QueryModelHelper.isConstraint(selected)) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, createNegateAction(selected));
		}
		appendChildActions(menu, createChildActions(selected));
		menu.add(new Separator());
		menu.add(getRegistry().getAction(ActionFactory.DELETE.getId()));
	}

	private Map<MenuGroup, List<IAction>> createChildActions(final EObject selected) {
		final Map<MenuGroup, List<IAction>> actions = new EnumMap<>(MenuGroup.class);
		for (final EReference ref : selected.eClass().getEAllContainments()) {
			if (canAddChild(selected, ref)) {
				for (final EClass type : QueryModelHelper.getAddableClasses(queryPackage, selected,
						ref.getEReferenceType())) {
					final MenuGroup group = MenuGroup.of(ref, type);
					actions.computeIfAbsent(group, _ -> new ArrayList<>())
							.add(createAddChildAction(selected, ref, type, group.isSubmenu()));
				}
			}
		}
		for (final FieldConstraintEntry entry : QueryModelHelper.getContainedFieldConstraints(selected)) {
			actions.computeIfAbsent(MenuGroup.REMOVE_FIELDS, _ -> new ArrayList<>())
					.add(createRemoveFieldAction(entry));
		}
		return actions;
	}

	private static void appendChildActions(final IMenuManager menu, final Map<MenuGroup, List<IAction>> actions) {
		MenuGroup previousGroup = null;
		for (final Map.Entry<MenuGroup, List<IAction>> entry : actions.entrySet()) {
			final MenuGroup group = entry.getKey();
			if (previousGroup != null && !previousGroup.isSameSection(group)) {
				menu.appendToGroup(GROUP_CHILDREN, new Separator());
			}
			if (group.isSubmenu()) {
				final MenuManager submenu = new MenuManager(group.submenuLabel);
				entry.getValue().forEach(submenu::add);
				menu.appendToGroup(GROUP_CHILDREN, submenu);
			} else {
				entry.getValue().forEach(action -> menu.appendToGroup(GROUP_CHILDREN, action));
			}
			previousGroup = group;
		}
	}

	private static boolean canAddChild(final EObject parent, final EReference ref) {
		if (!ref.isMany() && parent.eIsSet(ref)) {
			return false;
		}
		if (QueryModelHelper.REF_PIN.equals(ref.getName())
				&& QueryModelHelper.isPinTargetQuery(EcoreUtil.getRootContainer(parent))) {
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

	private IAction createAddChildAction(final EObject parent, final EReference ref, final EClass type,
			final boolean inSubmenu) {
		final String name = getChildName(ref, type);
		final String label = NLS.bind(Messages.AddChild, name);
		return createCommandAction(inSubmenu ? name : label, label,
				() -> new CreateQueryElementCommand(parent, ref, type));
	}

	private IAction createRemoveFieldAction(final FieldConstraintEntry entry) {
		final String name = getChildName(entry.reference(), entry.fieldConstraint().eClass());
		return createCommandAction(name, NLS.bind(Messages.RemoveChild, name),
				() -> new DeleteQueryElementCommand(entry.fieldConstraint()));
	}

	private static String getChildName(final EReference ref, final EClass type) {
		final boolean namedByClass = QueryModelHelper.isConstraintClass(type)
				|| QueryModelHelper.isSubtypeOf(type, QueryModelHelper.TARGET_OPTION);
		final String key = namedByClass ? type.getName() : ref.getName();
		return CHILD_NAMES.getOrDefault(key, QueryModelHelper.getChildLabel(ref, type));
	}

	private IAction createCommandAction(final String text, final String commandLabel,
			final Supplier<Command> commandSupplier) {
		return createAction(text, () -> execute(commandLabel, commandSupplier.get()));
	}

	private static IAction createAction(final String text, final Runnable runnable) {
		return new Action(text) {
			@Override
			public void run() {
				runnable.run();
			}
		};
	}

	private void execute(final String label, final Command command) {
		command.setLabel(label);
		getViewer().getEditDomain().getCommandStack().execute(command);
	}
}
