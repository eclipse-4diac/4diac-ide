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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/** Constants and helpers for the query model defined in searchQuery.ecore. */
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
	public static final String TYPE = "Type"; //$NON-NLS-1$
	public static final String OCCURRENCE = "Occurrence"; //$NON-NLS-1$
	public static final String PIN = "PIN"; //$NON-NLS-1$
	public static final String UNTYPED_SUBAPP = "UntypedSubapp"; //$NON-NLS-1$

	public static final String FEATURE_NEGATE = "negate"; //$NON-NLS-1$
	public static final String REF_TARGET = "target"; //$NON-NLS-1$
	public static final String REF_PLACE = "place"; //$NON-NLS-1$
	public static final String REF_PIN = "pin"; //$NON-NLS-1$
	public static final String REF_CONSTRAINT = "constraint"; //$NON-NLS-1$

	public static final String REF_SIMPLE_TYPE = "simpleType"; //$NON-NLS-1$
	public static final String REF_BASIC_TYPE = "basicType"; //$NON-NLS-1$
	public static final String REF_COMPOSITE_TYPE = "compositeType"; //$NON-NLS-1$
	public static final String REF_SERVICE_INTERFACE_TYPE = "serviceInterfaceType"; //$NON-NLS-1$
	public static final String REF_FUNCTION_TYPE = "functionType"; //$NON-NLS-1$
	public static final String REF_SUBAPP_TYPE = "subappType"; //$NON-NLS-1$
	public static final String REF_STRUCT_TYPE = "structType"; //$NON-NLS-1$
	public static final String REF_ATTRIBUTE_TYPE = "attributeType"; //$NON-NLS-1$
	public static final String REF_SIMPLE_FB = "simpleFB"; //$NON-NLS-1$
	public static final String REF_BASIC_FB = "basicFB"; //$NON-NLS-1$
	public static final String REF_COMPOSITE_FB = "compositeFB"; //$NON-NLS-1$
	public static final String REF_SERVICE_INTERFACE_FB = "serviceInterfaceFB"; //$NON-NLS-1$
	public static final String REF_FUNCTION_FB = "functionFB"; //$NON-NLS-1$
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

	public static boolean isAttributeConstraint(final EObject eObj) {
		return isOfType(eObj, ATTRIBUTE_CONSTRAINT);
	}

	private static boolean isConstraintClass(final EClass eClass) {
		return CONSTRAINT.equals(eClass.getName()) || ATTRIBUTE_CONSTRAINT.equals(eClass.getName());
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

	public static boolean isOccurrence(final EObject eObj) {
		return eObj != null
				&& eObj.eClass().getEAllSuperTypes().stream().anyMatch(st -> OCCURRENCE.equals(st.getName()));
	}

	public static boolean isType(final EObject eObj) {
		return eObj != null && eObj.eClass().getEAllSuperTypes().stream().anyMatch(st -> TYPE.equals(st.getName()));
	}

	public static boolean isFieldAllowedForConstraint(final EObject constraint, final String fieldRefName) {
		if (FEATURE_NAME.equals(fieldRefName) || FEATURE_COMMENT.equals(fieldRefName)) {
			return true;
		}
		if (isAttributeConstraint(constraint)) {
			// AttributeConstraints should always have: name, type, comment, value
			return true;
		}

		final EObject owner = findConstraintOwner(constraint);
		if (owner == null) {
			return true;
		}

		if (isOfType(owner, PIN)) {
			return true;
		}
		if (isType(owner) || isOccurrence(owner) || isOfType(owner, UNTYPED_SUBAPP)) {
			// Types, UntypedSubapp, Occurrences: no type and value
			return false;
		}
		if (isInstance(owner)) {
			// Instances: no value
			return !FEATURE_VALUE.equals(fieldRefName);
		}
		return true;
	}

	private static boolean isChildTypeAllowed(final EObject parent, final EClass childType) {
		return !ATTRIBUTE_CONSTRAINT.equals(childType.getName()) || !isOfType(findConstraintOwner(parent), ATTRIBUTE);
	}

	private static EObject findConstraintOwner(final EObject obj) {
		EObject current = obj;
		while (current != null) {
			if (!isConstraint(current) && !isOfType(current, FIELD_CONSTRAINT)) {
				return current;
			}
			current = current.eContainer();
		}
		return null;
	}

	public static boolean isNegatedConstraint(final EObject eObj) {
		return getBooleanFeature(eObj, FEATURE_NEGATE);
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
		return feature != null ? eObj.eGet(feature) : null;
	}

	public static String getFeatureText(final EObject eObj, final String featureName) {
		final Object value = getFeatureValue(eObj, featureName);
		return value != null ? String.valueOf(value) : ""; //$NON-NLS-1$
	}

	private static boolean getBooleanFeature(final EObject eObj, final String featureName) {
		return Boolean.TRUE.equals(getFeatureValue(eObj, featureName));
	}

	public static EObject getContainedChild(final EObject parent, final String refName) {
		return (getFeatureValue(parent, refName) instanceof final EObject eObj) ? eObj : null;
	}

	// field constraints
	public static FieldConstraintData readFieldConstraint(final EObject fc) {
		return new FieldConstraintData((String) getFeatureValue(fc, FEATURE_VALUE),
				getBooleanFeature(fc, FEATURE_CASE_SENSITIVE), getBooleanFeature(fc, FEATURE_WHOLE_WORD),
				getBooleanFeature(fc, FEATURE_ENTIRE), getBooleanFeature(fc, FEATURE_REGEX));
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

	// child creation
	public static List<EClass> getAddableClasses(final EPackage queryPackage, final EObject parent, final EClass type) {
		if (isConstraintClass(type)) {
			// a constraint slot accepts both Constraint and AttributeConstraint
			return getConcreteSubclasses(queryPackage, type).stream().filter(cls -> isChildTypeAllowed(parent, cls))
					.toList();
		}
		return getInstantiableClasses(queryPackage, type);
	}

	public static String getChildLabel(final EReference ref, final EClass childType) {
		final EClass refType = ref.getEReferenceType();
		if (TARGET_OPTION.equals(refType.getName())) {
			return childType.getName();
		}
		if (isConstraintClass(refType)) {
			final String refName = ref.getName();
			final String prefix = refName.regionMatches(true, refName.length() - CONSTRAINT.length(), CONSTRAINT, 0,
					CONSTRAINT.length()) ? refName.substring(0, refName.length() - CONSTRAINT.length()) : refName;
			final String className = childType.getName();
			return prefix.isEmpty() ? Character.toLowerCase(className.charAt(0)) + className.substring(1)
					: prefix + className;
		}
		return ref.getName();
	}
}
