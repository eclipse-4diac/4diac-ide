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
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.commands.ChangeQueryFeatureCommand;
import org.eclipse.fordiac.ide.bulkeditor.commands.CreateQueryElementCommand;
import org.eclipse.fordiac.ide.bulkeditor.commands.DeleteQueryElementCommand;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Query;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
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
		//@formatter:on

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
			if (type == QueryPackage.Literals.FIELD_CONSTRAINT) {
				return FIELDS;
			}
			if (QueryPackage.Literals.CONSTRAINT.isSuperTypeOf(type)) {
				if (ref == QueryPackage.Literals.CONSTRAINT__AND_CONSTRAINT) {
					return AND_CONSTRAINTS;
				}
				return ref == QueryPackage.Literals.CONSTRAINT__OR_CONSTRAINT ? OR_CONSTRAINTS : CONSTRAINTS;
			}
			if (QueryPackage.Literals.INSTANCE.isSuperTypeOf(type)) {
				return INSTANCES;
			}
			if (type == QueryPackage.Literals.CONNECTION) {
				return CONNECTIONS;
			}
			if (ref == QueryPackage.Literals.PLACE__STRUCT_TYPE || ref == QueryPackage.Literals.PLACE__ATTRIBUTE_TYPE) {
				return DATA_TYPES;
			}
			if (QueryPackage.Literals.TYPE.isSuperTypeOf(type)) {
				return TYPES;
			}
			if (QueryPackage.Literals.OCCURRENCE.isSuperTypeOf(type)) {
				return OCCURRENCES;
			}
			if (QueryPackage.Literals.TARGET_OPTION.isSuperTypeOf(type)) {
				return TARGETS;
			}
			return OTHERS;
		}
	}

	/**
	 * Names of the children in the menu: constraints and target options by class,
	 * the others by reference.
	 */
	private static final Map<ENamedElement, String> CHILD_NAMES = Map.ofEntries(
			//@formatter:off
			Map.entry(QueryPackage.Literals.PROPERTY_CONSTRAINT__NAME, Messages.QueryMenu_Name),
			Map.entry(QueryPackage.Literals.PROPERTY_CONSTRAINT__TYPE, Messages.QueryMenu_Type),
			Map.entry(QueryPackage.Literals.PROPERTY_CONSTRAINT__COMMENT, Messages.QueryMenu_Comment),
			Map.entry(QueryPackage.Literals.PROPERTY_CONSTRAINT__VALUE, Messages.QueryMenu_Value),
			Map.entry(QueryPackage.Literals.PROPERTY_CONSTRAINT, Messages.QueryMenu_PropertyConstraint),
			Map.entry(QueryPackage.Literals.ATTRIBUTE_CONSTRAINT, Messages.QueryMenu_AttributeConstraint),
			Map.entry(QueryPackage.Literals.PLACE__SIMPLE_FB, Messages.QueryMenu_SimpleFB),
			Map.entry(QueryPackage.Literals.PLACE__BASIC_FB, Messages.QueryMenu_BasicFB),
			Map.entry(QueryPackage.Literals.PLACE__COMPOSITE_FB, Messages.QueryMenu_CompositeFB),
			Map.entry(QueryPackage.Literals.PLACE__SERVICE_INTERFACE_FB, Messages.QueryMenu_ServiceInterfaceFB),
			Map.entry(QueryPackage.Literals.PLACE__FUNCTION_FB, Messages.QueryMenu_FunctionFB),
			Map.entry(QueryPackage.Literals.PLACE__TYPED_SUBAPP, Messages.QueryMenu_TypedSubapp),
			Map.entry(QueryPackage.Literals.PLACE__UNTYPED_SUBAPP, Messages.QueryMenu_UntypedSubapp),
			Map.entry(QueryPackage.Literals.PLACE__CONNECTION, Messages.QueryMenu_Connection),
			Map.entry(QueryPackage.Literals.PLACE__SIMPLE_TYPE, Messages.QueryMenu_SimpleType),
			Map.entry(QueryPackage.Literals.PLACE__BASIC_TYPE, Messages.QueryMenu_BasicType),
			Map.entry(QueryPackage.Literals.PLACE__COMPOSITE_TYPE, Messages.QueryMenu_CompositeType),
			Map.entry(QueryPackage.Literals.PLACE__SERVICE_INTERFACE_TYPE, Messages.QueryMenu_ServiceInterfaceType),
			Map.entry(QueryPackage.Literals.PLACE__FUNCTION_TYPE, Messages.QueryMenu_FunctionType),
			Map.entry(QueryPackage.Literals.PLACE__SUBAPP_TYPE, Messages.QueryMenu_SubappType),
			Map.entry(QueryPackage.Literals.PLACE__STRUCT_TYPE, Messages.QueryMenu_StructType),
			Map.entry(QueryPackage.Literals.PLACE__ATTRIBUTE_TYPE, Messages.QueryMenu_AttributeType),
			Map.entry(QueryPackage.Literals.NETWORK_ELEMENT__APPLICATION_OCCURRENCE, Messages.QueryMenu_ApplicationOccurrence),
			Map.entry(QueryPackage.Literals.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE, Messages.QueryMenu_CompositeFBOccurrence),
			Map.entry(QueryPackage.Literals.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE, Messages.QueryMenu_TypedSubappOccurrence),
			Map.entry(QueryPackage.Literals.TYPE__PIN, Messages.QueryMenu_Pin),
			Map.entry(QueryPackage.Literals.INSTANCE__PIN, Messages.QueryMenu_Pin),
			Map.entry(QueryPackage.Literals.QUERY__PLACEHOLDER, Messages.QueryMenu_Placeholder),
			Map.entry(QueryPackage.Literals.PIN_TARGET, Messages.QueryMenu_PinTarget),
			Map.entry(QueryPackage.Literals.ATTRIBUTE, Messages.QueryMenu_Attribute),
			Map.entry(QueryPackage.Literals.ATTRIBUTE_DECLARATION, Messages.QueryMenu_AttributeDeclaration));
			//@formatter:on

	private static final String GROUP_CHILDREN = IWorkbenchActionConstants.GROUP_ADD;

	private final Runnable onSave;
	private final Runnable onLoad;
	private final Runnable onSearch;

	public QueryContextMenuProvider(final EditPartViewer viewer, final ZoomManager zoomManager,
			final ActionRegistry registry, final Runnable onSave, final Runnable onLoad, final Runnable onSearch) {
		super(viewer, zoomManager, registry);
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
		if (selected instanceof final Constraint constraint) {
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, createNegateAction(constraint));
		}
		appendChildActions(menu, createChildActions(selected));
		menu.add(new Separator());
		menu.add(getRegistry().getAction(ActionFactory.DELETE.getId()));
	}

	private Map<MenuGroup, List<IAction>> createChildActions(final EObject selected) {
		final Map<MenuGroup, List<IAction>> actions = new EnumMap<>(MenuGroup.class);
		for (final EReference ref : selected.eClass().getEAllContainments()) {
			if (canAddChild(selected, ref)) {
				for (final EClass type : QueryModelHelper.getAddableClasses(selected, ref.getEReferenceType())) {
					final MenuGroup group = MenuGroup.of(ref, type);
					actions.computeIfAbsent(group, _ -> new ArrayList<>())
							.add(createAddChildAction(selected, ref, type, group.isSubmenu()));
				}
			}
		}
		if (selected instanceof final Constraint constraint) {
			for (final FieldConstraint fieldConstraint : QueryModelHelper.getFieldConstraints(constraint)) {
				actions.computeIfAbsent(MenuGroup.REMOVE_FIELDS, _ -> new ArrayList<>())
						.add(createRemoveFieldAction(fieldConstraint));
			}
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
		if (ref.getEReferenceType() == QueryPackage.Literals.PIN
				&& EcoreUtil.getRootContainer(parent) instanceof final Query query
				&& QueryModelHelper.isPinTargetQuery(query)) {
			return false;
		}
		if (parent instanceof final Constraint constraint
				&& ref.getEReferenceType() == QueryPackage.Literals.FIELD_CONSTRAINT) {
			return QueryModelHelper.isFieldAllowedForConstraint(constraint, ref);
		}
		return true;
	}

	private IAction createNegateAction(final Constraint constraint) {
		final boolean negated = constraint.isNegate();
		final IAction action = new Action(Messages.Negate, IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				execute(Messages.Negate, new ChangeQueryFeatureCommand(constraint,
						QueryPackage.Literals.CONSTRAINT__NEGATE, Boolean.valueOf(!negated)));
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

	private IAction createRemoveFieldAction(final FieldConstraint fieldConstraint) {
		final String name = getChildName(fieldConstraint.eContainmentFeature(), fieldConstraint.eClass());
		return createCommandAction(name, NLS.bind(Messages.RemoveChild, name),
				() -> new DeleteQueryElementCommand(fieldConstraint));
	}

	private static String getChildName(final EReference ref, final EClass type) {
		final boolean namedByClass = QueryPackage.Literals.CONSTRAINT.isSuperTypeOf(type)
				|| QueryPackage.Literals.TARGET_OPTION.isSuperTypeOf(type);
		final ENamedElement key = namedByClass ? type : ref;
		return CHILD_NAMES.getOrDefault(key, key.getName());
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
