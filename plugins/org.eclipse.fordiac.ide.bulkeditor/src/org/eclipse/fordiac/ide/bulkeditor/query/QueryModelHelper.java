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
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute;
import org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Connection;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Instance;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Occurrence;
import org.eclipse.fordiac.ide.bulkeditor.query.model.PinTarget;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Place;
import org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Query;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryFactory;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Target;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Type;
import org.eclipse.fordiac.ide.bulkeditor.query.model.UntypedSubapp;

/** Rules of the query viewer for the query model defined in query.ecore. */
public final class QueryModelHelper {

	private QueryModelHelper() {
		// utility class
	}

	public static boolean isPinTargetQuery(final Query query) {
		return query.getTarget().getTarget() instanceof PinTarget;
	}

	public static List<FieldConstraint> getFieldConstraints(final Constraint constraint) {
		if (constraint instanceof final PropertyConstraint propertyConstraint) {
			return Stream.of(propertyConstraint.getName(), propertyConstraint.getType(),
					propertyConstraint.getComment(), propertyConstraint.getValue()).filter(Objects::nonNull).toList();
		}
		return List.of();
	}

	public static boolean isFieldAllowedForConstraint(final Constraint constraint, final EReference field) {
		if (constraint instanceof AttributeConstraint) {
			// AttributeConstraints should always have: name, type, comment, value
			return true;
		}

		final EObject owner = findConstraintOwner(constraint);
		if (owner instanceof Connection) {
			// Connections: no name and value
			return field == QueryPackage.Literals.PROPERTY_CONSTRAINT__TYPE
					|| field == QueryPackage.Literals.PROPERTY_CONSTRAINT__COMMENT;
		}
		if (field == QueryPackage.Literals.PROPERTY_CONSTRAINT__NAME
				|| field == QueryPackage.Literals.PROPERTY_CONSTRAINT__COMMENT) {
			return true;
		}
		if (owner instanceof Type || owner instanceof Occurrence || owner instanceof UntypedSubapp) {
			// Types, UntypedSubapp, Occurrences: no type and value
			return false;
		}
		if (owner instanceof Instance) {
			// Instances: no value
			return field != QueryPackage.Literals.PROPERTY_CONSTRAINT__VALUE;
		}
		return true;
	}

	private static boolean isChildTypeAllowed(final EObject parent, final EClass childType) {
		return childType != QueryPackage.Literals.ATTRIBUTE_CONSTRAINT
				|| !(findConstraintOwner(parent) instanceof Attribute);
	}

	private static EObject findConstraintOwner(final EObject element) {
		EObject current = element;
		while (current instanceof Constraint || current instanceof FieldConstraint) {
			current = current.eContainer();
		}
		return current;
	}

	// mandatory elements
	public static boolean isMandatoryChild(final EObject element) {
		return element instanceof Target || element instanceof Place;
	}

	public static void ensureMandatoryChildren(final Query query) {
		if (query.getTarget() == null) {
			query.setTarget(QueryFactory.eINSTANCE.createTarget());
		}
		if (query.getPlace() == null) {
			query.setPlace(QueryFactory.eINSTANCE.createPlace());
		}
	}

	// graph structure
	public static List<EObject> getChildNodes(final EObject element) {
		// field constraints are shown in the node of their constraint
		return element.eContents().stream().filter(child -> !(child instanceof FieldConstraint)).toList();
	}

	public static boolean hasCollapsibleChildren(final EObject element) {
		return !getChildNodes(element).isEmpty();
	}

	// child creation
	public static List<EClass> getAddableClasses(final EObject parent, final EClass type) {
		if (QueryPackage.Literals.CONSTRAINT.isSuperTypeOf(type)) {
			// a constraint slot accepts every concrete constraint class
			return getConcreteSubclasses(type).stream().filter(cls -> isChildTypeAllowed(parent, cls)).toList();
		}
		return isInstantiable(type) ? List.of(type) : getConcreteSubclasses(type);
	}

	private static List<EClass> getConcreteSubclasses(final EClass type) {
		return QueryPackage.eINSTANCE.getEClassifiers().stream() //
				.filter(EClass.class::isInstance).map(EClass.class::cast) //
				.filter(QueryModelHelper::isInstantiable) //
				.filter(type::isSuperTypeOf) //
				.toList();
	}

	private static boolean isInstantiable(final EClass type) {
		return !type.isAbstract() && !type.isInterface();
	}
}
