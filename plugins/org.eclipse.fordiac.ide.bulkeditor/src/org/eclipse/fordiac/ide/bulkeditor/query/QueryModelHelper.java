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
import java.util.function.Predicate;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.osgi.util.NLS;
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
	public static final String ATTRIBUTE = "Attribute"; //$NON-NLS-1$
	public static final String PIN_TARGET = "PinTarget"; //$NON-NLS-1$

	public static final String FEATURE_NEGATE = "negate"; //$NON-NLS-1$
	public static final String REF_TARGET = "target"; //$NON-NLS-1$
	public static final String REF_PLACE = "place"; //$NON-NLS-1$
	public static final String REF_PIN = "pin"; //$NON-NLS-1$
	public static final String REF_CONSTRAINT = "constraint"; //$NON-NLS-1$
	public static final String REF_ATTRIBUTE_CONSTRAINT = "attributeConstraint"; //$NON-NLS-1$

	public static final String REF_SIMPLE_TYPE = "simpleType"; //$NON-NLS-1$
	public static final String REF_BASIC_TYPE = "basicType"; //$NON-NLS-1$
	public static final String REF_COMPOSITE_TYPE = "compositeType"; //$NON-NLS-1$
	public static final String REF_SERVICE_INTERFACE_TYPE = "serviceInterfaceType"; //$NON-NLS-1$
	public static final String REF_SUBAPP_TYPE = "subappType"; //$NON-NLS-1$
	public static final String REF_STRUCT_TYPE = "structType"; //$NON-NLS-1$
	public static final String REF_ATTRIBUTE_TYPE = "attributeType"; //$NON-NLS-1$
	public static final String REF_SIMPLE_FB = "simpleFB"; //$NON-NLS-1$
	public static final String REF_BASIC_FB = "basicFB"; //$NON-NLS-1$
	public static final String REF_COMPOSITE_FB = "compositeFB"; //$NON-NLS-1$
	public static final String REF_SERVICE_INTERFACE_FB = "serviceInterfaceFB"; //$NON-NLS-1$
	public static final String REF_TYPED_SUBAPP = "typedSubapp"; //$NON-NLS-1$
	public static final String REF_UNTYPED_SUBAPP = "untypedSubapp"; //$NON-NLS-1$

	public static final String FEATURE_NAME = "name"; //$NON-NLS-1$
	public static final String FEATURE_TYPE = "type"; //$NON-NLS-1$
	public static final String FEATURE_COMMENT = "comment"; //$NON-NLS-1$
	public static final String FEATURE_VALUE = "value"; //$NON-NLS-1$
	public static final String FEATURE_CASE_SENSITIVE = "caseSensitive"; //$NON-NLS-1$
	public static final String FEATURE_WHOLE_WORD = "wholeWord"; //$NON-NLS-1$
	public static final String FEATURE_ENTIRE = "entire"; //$NON-NLS-1$
	public static final String FEATURE_REGEX = "regex"; //$NON-NLS-1$
	public static final String REF_AND_CONSTRAINTS = "andConstraint"; //$NON-NLS-1$
	public static final String REF_OR_CONSTRAINTS = "orConstraint"; //$NON-NLS-1$
	public static final String REF_APPLICATION_OCCURRENCE = "applicationOccurrence"; //$NON-NLS-1$
	public static final String REF_COMPOSITE_FB_OCCURRENCE = "compositeFBOccurrence"; //$NON-NLS-1$
	public static final String REF_TYPED_SUBAPP_OCCURRENCE = "typedSubappOccurrence"; //$NON-NLS-1$

	public static final String FEATURE_KEY = "key"; //$NON-NLS-1$
	public static final String FEATURE_VAL = "val"; //$NON-NLS-1$

	public static final String FEATURE_PLACEHOLDER = "placeholder"; //$NON-NLS-1$
	public static final String FEATURE_IGNORE_LINKED_LIBRARIES = "ignoreLinkedLibraries"; //$NON-NLS-1$

	public record FieldConstraintData(String value, boolean caseSensitive, boolean wholeWord, boolean entire,
			boolean regex) {
	}

	public record FieldConstraintEntry(EReference reference, EObject fieldConstraint) {
	}

	private QueryModelHelper() {
		// utility class
	}

	// type checks
	public static boolean isOfType(final EObject eObj, final String className) {
		return eObj != null && eObj.eClass().getName().equals(className);
	}

	public static boolean isConstraint(final EObject eObj) {
		return isOfType(eObj, CONSTRAINT) || isOfType(eObj, ATTRIBUTE_CONSTRAINT);
	}

	public static boolean isPlace(final EObject eObj) {
		return isOfType(eObj, PLACE);
	}

	public static boolean isPlaceholder(final EObject eObj) {
		return isOfType(eObj, PLACEHOLDER);
	}

	public static boolean isAttributeDeclaration(final EObject eObj) {
		return isOfType(eObj, ATTRIBUTE_DECLARATION);
	}

	public static boolean isInstance(final EObject eObj) {
		return eObj != null && eObj.eClass().getEAllSuperTypes().stream().anyMatch(st -> INSTANCE.equals(st.getName()));
	}

	public static boolean isNegatedConstraint(final EObject eObj) {
		return Boolean.TRUE.equals(QueryModelHelper.getFeatureValue(eObj, QueryModelHelper.FEATURE_NEGATE));
	}

	public static boolean isPinTargetQuery(final EObject queryRoot) {
		final EObject target = getContainedChild(queryRoot, REF_TARGET);
		final EObject targetOption = getContainedChild(target, REF_TARGET);
		return isOfType(targetOption, PIN_TARGET);
	}

	// generic feature access
	public static Object getFeatureValue(final EObject eObj, final String featureName) {
		if (eObj == null) {
			return null;
		}
		final EStructuralFeature feature = eObj.eClass().getEStructuralFeature(featureName);
		return (feature != null && eObj.eIsSet(feature)) ? eObj.eGet(feature) : null;
	}

	public static void setFeatureValue(final EObject eObj, final String featureName, final Object value) {
		final EStructuralFeature feature = eObj.eClass().getEStructuralFeature(featureName);
		if (feature != null) {
			eObj.eSet(feature, value);
		}
	}

	private static boolean getBooleanFeature(final EObject eObj, final String featureName) {
		return Boolean.TRUE.equals(getFeatureValue(eObj, featureName));
	}

	public static EObject getContainedChild(final EObject parent, final String refName) {
		return (getFeatureValue(parent, refName) instanceof final EObject eObj) ? eObj : null;
	}

	// named setters for readable call sites
	public static void setPlaceholderFeature(final EObject placeholder, final String featureName, final String value) {
		setFeatureValue(placeholder, featureName, value);
	}

	public static void setAttributeDeclarationName(final EObject attrDecl, final String name) {
		setFeatureValue(attrDecl, FEATURE_NAME, name);
	}

	public static void setIgnoreLinkedLibrary(final EObject instance, final boolean value) {
		setFeatureValue(instance, FEATURE_IGNORE_LINKED_LIBRARIES, Boolean.valueOf(value));
	}

	// field constraints
	public static FieldConstraintData readFieldConstraint(final EObject fc) {
		return new FieldConstraintData((String) getFeatureValue(fc, FEATURE_VALUE),
				getBooleanFeature(fc, FEATURE_CASE_SENSITIVE), getBooleanFeature(fc, FEATURE_WHOLE_WORD),
				getBooleanFeature(fc, FEATURE_ENTIRE), getBooleanFeature(fc, FEATURE_REGEX));
	}

	public static void writeFieldConstraint(final EObject fc, final FieldConstraintData data) {
		setFeatureValue(fc, FEATURE_VALUE, data.value());
		setFeatureValue(fc, FEATURE_CASE_SENSITIVE, Boolean.valueOf(data.caseSensitive()));
		setFeatureValue(fc, FEATURE_WHOLE_WORD, Boolean.valueOf(data.wholeWord()));
		setFeatureValue(fc, FEATURE_ENTIRE, Boolean.valueOf(data.entire()));
		setFeatureValue(fc, FEATURE_REGEX, Boolean.valueOf(data.regex()));
	}

	public static List<FieldConstraintEntry> getContainedFieldConstraints(final EObject constraint) {
		return constraint.eContents().stream().filter(child -> isOfType(child, FIELD_CONSTRAINT))
				.map(child -> new FieldConstraintEntry(child.eContainmentFeature(), child)).toList();
	}

	// structural rules
	private static boolean isInstantiable(final EClass type) {
		return !type.isAbstract() && !type.isInterface();
	}

	private static boolean isMandatorySlot(final EReference ref) {
		return !ref.isMany() && ref.getLowerBound() >= 1 && isInstantiable(ref.getEReferenceType());
	}

	public static boolean isMandatoryChild(final EObject obj) {
		final EReference containment = obj.eContainmentFeature();
		return containment != null && isMandatorySlot(containment);
	}

	public static void ensureMandatoryChildren(final EPackage queryPackage, final EObject parent) {
		if (parent == null) {
			return;
		}
		for (final EReference ref : parent.eClass().getEAllContainments()) {
			if (isMandatorySlot(ref)) {
				if (!parent.eIsSet(ref)) {
					parent.eSet(ref, queryPackage.getEFactoryInstance().create(ref.getEReferenceType()));
				}
				ensureMandatoryChildren(queryPackage, (EObject) parent.eGet(ref));
			}
		}
	}

	private static List<EClass> getInstantiableClasses(final EPackage queryPackage, final EClass type) {
		return isInstantiable(type) ? List.of(type) : getConcreteSubclasses(queryPackage, type);
	}

	public static List<EClass> getConcreteSubclasses(final EPackage queryPackage, final EClass abstractType) {
		return queryPackage.getEClassifiers().stream() //
				.filter(EClass.class::isInstance).map(EClass.class::cast) //
				.filter(QueryModelHelper::isInstantiable) //
				.filter(abstractType::isSuperTypeOf) //
				.toList();
	}

	// graph structure
	public static List<EObject> getChildNodes(final EObject eObj) {
		if (eObj == null) {
			return List.of();
		}
		if (isConstraint(eObj)) {
			return eObj.eContents().stream().filter(child -> !isOfType(child, FIELD_CONSTRAINT)).toList();
		}
		return eObj.eContents();
	}

	public static boolean hasCollapsibleChildren(final EObject eObj) {
		return !getChildNodes(eObj).isEmpty();
	}

	// model modification
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

	// context menu
	public static void populateAddChildMenuItems(final Menu menu, final EObject selected,
			final AdapterFactoryEditingDomain editingDomain, final EPackage queryPackage,
			final Predicate<EReference> referenceFilter, final Runnable afterAdd) {
		addSeparatorIfNeeded(menu);
		for (final EReference ref : selected.eClass().getEAllContainments()) {
			if (referenceFilter.test(ref) && (ref.isMany() || !selected.eIsSet(ref))) {
				addItemsForReference(menu, selected, editingDomain, queryPackage, ref, afterAdd);
			}
		}
	}

	private static void addItemsForReference(final Menu menu, final EObject selected,
			final AdapterFactoryEditingDomain editingDomain, final EPackage queryPackage, final EReference ref,
			final Runnable afterAdd) {
		final EClass type = ref.getEReferenceType();
		// TargetOption children are labeled by their concrete class, everything else by
		// the reference
		final boolean useClassName = type.getName().equals(TARGET_OPTION);
		for (final EClass concrete : getInstantiableClasses(queryPackage, type)) {
			final String name = useClassName ? concrete.getName() : ref.getName();
			addMenuItem(menu, NLS.bind(Messages.AddChild, name), () -> {
				addChild(editingDomain, queryPackage, selected, ref, concrete);
				afterAdd.run();
			});
		}
	}

	public static void populateRemoveMenuItem(final Menu menu, final EObject selected,
			final AdapterFactoryEditingDomain editingDomain, final Runnable afterRemove) {
		if (selected.eContainer() == null || isMandatoryChild(selected)) {
			return;
		}
		addSeparatorIfNeeded(menu);
		addMenuItem(menu, NLS.bind(Messages.RemoveChild, selected.eClass().getName()), () -> {
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
			final String label = NLS.bind(Messages.RemoveChild, entry.reference().getName());
			addMenuItem(menu, label, () -> {
				removeChild(editingDomain, entry.fieldConstraint());
				afterRemove.run();
			});
		}
	}

	public static void addMenuItem(final Menu menu, final String text, final Runnable action) {
		final MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(text);
		item.addListener(SWT.Selection, e -> action.run());
	}

	public static void populateNegateToggle(final Menu menu, final EObject selected, final Runnable afterChange) {
		if (!isConstraint(selected)) {
			return;
		}
		addSeparatorIfNeeded(menu);
		final boolean currentValue = Boolean.TRUE.equals(getFeatureValue(selected, FEATURE_NEGATE));
		final MenuItem item = new MenuItem(menu, SWT.NONE);
		item.setText(Messages.Negate);
		item.setSelection(currentValue);
		item.addListener(SWT.Selection, e -> {
			setFeatureValue(selected, FEATURE_NEGATE, Boolean.valueOf(!currentValue));
			afterChange.run();
		});
	}

	@SuppressWarnings("unused")
	private static void addSeparatorIfNeeded(final Menu menu) {
		if (menu.getItemCount() > 0) {
			new MenuItem(menu, SWT.SEPARATOR);
		}
	}
}