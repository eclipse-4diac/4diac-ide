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
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public final class QueryModelHelper {

	public static final String QUERY = "Query"; //$NON-NLS-1$
	public static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$
	public static final String ATTRIBUTE_CONSTRAINT = "AttributeConstraint"; //$NON-NLS-1$
	public static final String FIELD_CONSTRAINT = "FieldConstraint"; //$NON-NLS-1$
	public static final String PLACE = "Place"; //$NON-NLS-1$
	public static final String INSTANCE = "Instance"; //$NON-NLS-1$
	public static final String TARGET_OPTION = "TargetOption"; //$NON-NLS-1$
	public static final String PLACEHOLDER = "Placeholder"; //$NON-NLS-1$
	public static final String ATTRIBUTE_DECLARATION = "AttributeDeclaration"; //$NON-NLS-1$

	public static final String FEATURE_NAME = "name"; //$NON-NLS-1$
	public static final String FEATURE_VALUE = "value"; //$NON-NLS-1$
	public static final String FEATURE_CASE_SENSITIVE = "caseSensitive"; //$NON-NLS-1$
	public static final String FEATURE_WHOLE_WORD = "wholeWord"; //$NON-NLS-1$
	public static final String FEATURE_ENTIRE = "entire"; //$NON-NLS-1$
	public static final String FEATURE_REGEX = "regex"; //$NON-NLS-1$

	public static final String FEATURE_OCCURRENCE = "occurrence"; //$NON-NLS-1$
	public static final String FEATURE_KEY = "key"; //$NON-NLS-1$
	public static final String FEATURE_VAL = "val"; //$NON-NLS-1$

	private QueryModelHelper() {
		// utility class
	}

	public static boolean isOfType(final EObject eObj, final String className) {
		return eObj != null && eObj.eClass().getName().equals(className);
	}

	public static boolean isConstraint(final EObject eObj) {
		return isOfType(eObj, CONSTRAINT) || isOfType(eObj, ATTRIBUTE_CONSTRAINT);
	}

	public static boolean isPlaceholder(final EObject eObj) {
		return isOfType(eObj, PLACEHOLDER);
	}

	public static void setPlaceholderFeature(final EObject placeholder, final String featureName, final String value) {
		final EStructuralFeature feature = placeholder.eClass().getEStructuralFeature(featureName);
		if (feature != null) {
			placeholder.eSet(feature, value);
		}
	}

	public static boolean isInstance(final EObject eObj) {
		return eObj.eClass().getEAllSuperTypes().stream().anyMatch(st -> INSTANCE.equals(st.getName()));
	}

	public static boolean isAttributeDeclaration(final EObject eObj) {
		return isOfType(eObj, ATTRIBUTE_DECLARATION);
	}

	public static void setAttributeDeclarationName(final EObject attrDecl, final String name) {
		final EStructuralFeature feature = attrDecl.eClass().getEStructuralFeature(FEATURE_NAME);
		if (feature != null) {
			attrDecl.eSet(feature, name);
		}
	}

	public static Object getFeatureValue(final EObject eObj, final String featureName) {
		final EStructuralFeature feature = eObj.eClass().getEStructuralFeature(featureName);
		return (feature != null && eObj.eIsSet(feature)) ? eObj.eGet(feature) : null;
	}

	public record FieldConstraintData(String value, boolean caseSensitive, boolean wholeWord, boolean entire,
			boolean regex) {
	}

	public static FieldConstraintData readFieldConstraint(final EObject fc) {
		final String value = (String) fc.eGet(fc.eClass().getEStructuralFeature(FEATURE_VALUE));
		final boolean caseSensitive = Boolean.TRUE
				.equals(fc.eGet(fc.eClass().getEStructuralFeature(FEATURE_CASE_SENSITIVE)));
		final boolean wholeWord = Boolean.TRUE.equals(fc.eGet(fc.eClass().getEStructuralFeature(FEATURE_WHOLE_WORD)));
		final boolean entire = Boolean.TRUE.equals(fc.eGet(fc.eClass().getEStructuralFeature(FEATURE_ENTIRE)));
		final boolean regex = Boolean.TRUE.equals(fc.eGet(fc.eClass().getEStructuralFeature(FEATURE_REGEX)));
		return new FieldConstraintData(value, caseSensitive, wholeWord, entire, regex);
	}

	public static void writeFieldConstraint(final EObject fc, final FieldConstraintData data) {
		fc.eSet(fc.eClass().getEStructuralFeature(FEATURE_VALUE), data.value());
		fc.eSet(fc.eClass().getEStructuralFeature(FEATURE_CASE_SENSITIVE), data.caseSensitive());
		fc.eSet(fc.eClass().getEStructuralFeature(FEATURE_WHOLE_WORD), data.wholeWord());
		fc.eSet(fc.eClass().getEStructuralFeature(FEATURE_ENTIRE), data.entire());
		fc.eSet(fc.eClass().getEStructuralFeature(FEATURE_REGEX), data.regex());
	}

	public static List<FieldConstraintEntry> getContainedFieldConstraints(final EObject constraint) {
		final List<FieldConstraintEntry> result = new ArrayList<>();
		for (final EReference ref : constraint.eClass().getEAllContainments()) {
			if (constraint.eIsSet(ref)) {
				final Object val = constraint.eGet(ref);
				if (val instanceof final EObject child && isOfType(child, FIELD_CONSTRAINT)) {
					result.add(new FieldConstraintEntry(ref, child));
				}
			}
		}
		return result;
	}

	public record FieldConstraintEntry(EReference reference, EObject fieldConstraint) {
	}

	public static void setOccurrences(final EObject instance, final List<?> occurrences) {
		final EStructuralFeature feature = instance.eClass().getEStructuralFeature(FEATURE_OCCURRENCE);
		if (feature != null) {
			instance.eSet(feature, occurrences);
		}
	}

	public static EObject addChild(final AdapterFactoryEditingDomain editingDomain, final EPackage queryPackage,
			final EObject parent, final EReference reference, final EClass childType) {
		final EObject child = queryPackage.getEFactoryInstance().create(childType);
		final Command cmd = reference.isMany() //
				? AddCommand.create(editingDomain, parent, reference, child)
				: SetCommand.create(editingDomain, parent, reference, child);
		editingDomain.getCommandStack().execute(cmd);
		return child;
	}

	public static void removeChild(final AdapterFactoryEditingDomain editingDomain, final EObject child) {
		final EReference containment = child.eContainmentFeature();
		final Command cmd = (containment != null && !containment.isMany())
				? SetCommand.create(editingDomain, child.eContainer(), containment, SetCommand.UNSET_VALUE)
				: RemoveCommand.create(editingDomain, child);
		editingDomain.getCommandStack().execute(cmd);
	}

	public static List<EClass> getConcreteSubclasses(final EPackage queryPackage, final EClass abstractType) {
		final List<EClass> result = new ArrayList<>();
		for (final EClassifier classifier : queryPackage.getEClassifiers()) {
			if (classifier instanceof final EClass candidate && !candidate.isAbstract() && !candidate.isInterface()
					&& abstractType.isSuperTypeOf(candidate)) {
				result.add(candidate);
			}
		}
		return result;
	}

	public static void populateAddChildMenuItems(final Menu menu, final EObject selected,
			final AdapterFactoryEditingDomain editingDomain, final EPackage queryPackage, final Runnable afterAdd) {
		for (final EReference ref : selected.eClass().getEAllContainments()) {
			if (!ref.isMany() && selected.eIsSet(ref)) {
				continue;
			}
			final EClass childType = ref.getEReferenceType();
			if (childType.isAbstract() || childType.isInterface()) {
				final boolean isTargetNode = childType.getName().equals(TARGET_OPTION);
				for (final EClass concrete : getConcreteSubclasses(queryPackage, childType)) {
					final var name = isTargetNode ? concrete.getName() : ref.getName();
					addMenuItem(menu, "Add " + name, () -> { //$NON-NLS-1$
						addChild(editingDomain, queryPackage, selected, ref, concrete);
						afterAdd.run();
					});
				}
			} else {
				addMenuItem(menu, "Add " + ref.getName(), () -> { //$NON-NLS-1$
					addChild(editingDomain, queryPackage, selected, ref, childType);
					afterAdd.run();
				});
			}
		}
	}

	public static void populateRemoveMenuItem(final Menu menu, final EObject selected,
			final AdapterFactoryEditingDomain editingDomain, final Runnable afterRemove) {
		if (selected.eContainer() == null) {
			return;
		}
		addSeparatorIfNeeded(menu);
		addMenuItem(menu, "Remove " + selected.eClass().getName(), () -> { //$NON-NLS-1$
			removeChild(editingDomain, selected);
			afterRemove.run();
		});
	}

	public static void populateFieldConstraintRemoval(final Menu menu, final EObject selected,
			final AdapterFactoryEditingDomain editingDomain, final Runnable afterRemove) {
		if (!isConstraint(selected)) {
			return;
		}
		final List<FieldConstraintEntry> entries = getContainedFieldConstraints(selected);
		if (entries.isEmpty()) {
			return;
		}
		addSeparatorIfNeeded(menu);
		for (final FieldConstraintEntry entry : entries) {
			final String label = "Remove " + entry.reference().getName(); //$NON-NLS-1$
			addMenuItem(menu, label, () -> {
				removeChild(editingDomain, entry.fieldConstraint());
				afterRemove.run();
			});
		}
	}

	private static void addMenuItem(final Menu menu, final String text, final Runnable action) {
		final MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(text);
		item.addListener(SWT.Selection, _ -> action.run());
	}

	@SuppressWarnings("unused")
	private static void addSeparatorIfNeeded(final Menu menu) {
		if (menu.getItemCount() > 0) {
			new MenuItem(menu, SWT.SEPARATOR);
		}
	}

	public static boolean hasCollapsibleChildren(final EObject eObj) {
		final boolean constraintNode = isConstraint(eObj);
		for (final EReference ref : eObj.eClass().getEAllContainments()) {
			if (!eObj.eIsSet(ref)) {
				continue;
			}
			final Object val = eObj.eGet(ref);
			if (val instanceof final EObject child) {
				if (constraintNode && isOfType(child, FIELD_CONSTRAINT)) {
					continue; // inline, not a separate node
				}
				return true;
			}
			if (val instanceof final List<?> list && !list.isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
