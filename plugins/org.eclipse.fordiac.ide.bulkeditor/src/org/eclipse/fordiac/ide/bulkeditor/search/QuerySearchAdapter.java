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
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorMode;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.InstanceConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.PinConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.TypeConfig;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;

public class QuerySearchAdapter {

	/** Placeholder reference syntax inside FieldConstraint values: ${key} */
	private static final Pattern PLACEHOLDER_REF = Pattern.compile("\\$\\{([^}]+)\\}"); //$NON-NLS-1$

	private QuerySearchAdapter() {
	}

	public static Map<String, String> resolvePlaceholders(final EObject queryRoot) {
		final Map<String, String> result = new HashMap<>();
		if (queryRoot == null) {
			return result;
		}
		final EStructuralFeature feature = queryRoot.eClass()
				.getEStructuralFeature(QueryModelHelper.FEATURE_PLACEHOLDER);
		if (feature == null || !(queryRoot.eGet(feature) instanceof final List<?> placeholders)) {
			return result;
		}
		for (final Object obj : placeholders) {
			if (obj instanceof final EObject placeholder) {
				final String key = (String) QueryModelHelper.getFeatureValue(placeholder, QueryModelHelper.FEATURE_KEY);
				final String val = (String) QueryModelHelper.getFeatureValue(placeholder, QueryModelHelper.FEATURE_VAL);
				if (key != null && !key.isBlank()) {
					result.put(key, val != null ? val : ""); //$NON-NLS-1$
				}
			}
		}
		return result;
	}

	public static String substitutePlaceholders(final String value, final Map<String, String> placeholders) {
		if (value == null || placeholders.isEmpty() || value.indexOf('$') < 0) {
			return value;
		}
		final Matcher matcher = PLACEHOLDER_REF.matcher(value);
		final StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			final String replacement = placeholders.get(matcher.group(1));
			matcher.appendReplacement(sb,
					Matcher.quoteReplacement(replacement != null ? replacement : matcher.group()));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static BulkEditorMode resolveTargetMode(final EObject queryRoot) {
		final EObject target = getContainedChild(queryRoot, QueryModelHelper.REF_TARGET);
		final EObject targetOption = (target != null) ? getContainedChild(target, QueryModelHelper.REF_TARGET) : null;

		if (targetOption == null) {
			return BulkEditorMode.VARIABLE;
		}

		return switch (targetOption.eClass().getName()) {
		case QueryModelHelper.ATTRIBUTE_DECLARATION -> BulkEditorMode.SIMPLE_ATTRIBUTE;
		case QueryModelHelper.ATTRIBUTE -> BulkEditorMode.ADVANCED_ATTRIBUTE;
		default -> BulkEditorMode.VARIABLE;
		};
	}

	public static IEC61499SearchFilter buildTargetSearchFilter(final EObject queryRoot) {
		final EObject target = getContainedChild(queryRoot, QueryModelHelper.REF_TARGET);
		final EObject targetOption = (target != null) ? getContainedChild(target, QueryModelHelper.REF_TARGET) : null;

		if (targetOption == null) {
			return searchCandidate -> false;
		}

		final Map<String, String> placeholders = resolvePlaceholders(queryRoot);
		final String targetType = targetOption.eClass().getName();

		return switch (targetType) {
		case QueryModelHelper.PIN_TARGET -> buildDefaultFilter(targetOption, BulkEditorMode.VARIABLE, placeholders);
		case QueryModelHelper.ATTRIBUTE ->
			buildDefaultFilter(targetOption, BulkEditorMode.ADVANCED_ATTRIBUTE, placeholders);
		case QueryModelHelper.ATTRIBUTE_DECLARATION -> buildAttributeDeclarationFilter(targetOption, placeholders);
		default -> searchCandidate -> false;
		};
	}

	private static IEC61499SearchFilter buildDefaultFilter(final EObject targetOption, final BulkEditorMode mode,
			final Map<String, String> placeholders) {
		final FilterRecord constraint = readFilterRecord(targetOption, QueryModelHelper.REF_CONSTRAINT, placeholders);

		return searchCandidate -> {
			if (!isValidCandidate(searchCandidate, mode)) {
				return false;
			}
			final ITypedElement typed = (ITypedElement) searchCandidate;
			return constraint.matches(typed.getName(), typed.getTypeName(), typed.getComment());
		};
	}

	private static IEC61499SearchFilter buildAttributeDeclarationFilter(final EObject targetOption,
			final Map<String, String> placeholders) {
		final String rawName = (String) QueryModelHelper.getFeatureValue(targetOption, QueryModelHelper.FEATURE_NAME);
		final String declName = substitutePlaceholders(rawName, placeholders);

		if (declName == null || declName.isBlank()) {
			return AttributeDeclaration.class::isInstance;
		}

		return searchCandidate -> {
			if (!(searchCandidate instanceof final AttributeDeclaration attrDecl)) {
				return false;
			}
			return declName.equals(attrDecl.getName());
		};
	}

	private static boolean isValidCandidate(final Object candidate, final BulkEditorMode mode) {
		return (candidate instanceof VarDeclaration && mode == BulkEditorMode.VARIABLE)
				|| (candidate instanceof Attribute && mode == BulkEditorMode.ADVANCED_ATTRIBUTE);
	}

	public static PlaceConfig buildPlaceConfig(final EObject queryRoot) {
		final EObject place = getContainedChild(queryRoot, QueryModelHelper.REF_PLACE);
		final Map<String, String> placeholders = resolvePlaceholders(queryRoot);
		// in attribute mode traverse pins only with pin node
		final boolean pinsImplicit = QueryModelHelper.isPinTargetQuery(queryRoot);

		//@formatter:off
		return new PlaceConfig(
		        buildTypeConfig(place, QueryModelHelper.REF_SIMPLE_TYPE, placeholders, pinsImplicit),
		        buildTypeConfig(place, QueryModelHelper.REF_BASIC_TYPE, placeholders, pinsImplicit),
		        buildTypeConfig(place, QueryModelHelper.REF_COMPOSITE_TYPE, placeholders, pinsImplicit),
		        buildTypeConfig(place, QueryModelHelper.REF_SERVICE_INTERFACE_TYPE, placeholders, pinsImplicit),
		        buildTypeConfig(place, QueryModelHelper.REF_SUBAPP_TYPE, placeholders, pinsImplicit),
		        buildTypeConfig(place, QueryModelHelper.REF_STRUCT_TYPE, placeholders, pinsImplicit),
		        buildTypeConfig(place, QueryModelHelper.REF_ATTRIBUTE_TYPE, placeholders, pinsImplicit),
		        buildInstanceConfig(place, QueryModelHelper.REF_SIMPLE_FB, placeholders, pinsImplicit),
		        buildInstanceConfig(place, QueryModelHelper.REF_BASIC_FB, placeholders, pinsImplicit),
		        buildInstanceConfig(place, QueryModelHelper.REF_COMPOSITE_FB, placeholders, pinsImplicit),
		        buildInstanceConfig(place, QueryModelHelper.REF_SERVICE_INTERFACE_FB, placeholders, pinsImplicit),
		        buildInstanceConfig(place, QueryModelHelper.REF_TYPED_SUBAPP, placeholders, pinsImplicit),
		        buildInstanceConfig(place, QueryModelHelper.REF_UNTYPED_SUBAPP, placeholders, pinsImplicit),
		        readIgnoreLinkedLibraries(queryRoot));
		//@formatter:on
	}

	private static boolean readIgnoreLinkedLibraries(final EObject queryRoot) {
		final EObject place = getContainedChild(queryRoot, QueryModelHelper.REF_PLACE);
		if (place == null) {
			return true;
		}
		final var feature = place.eClass().getEStructuralFeature(QueryModelHelper.FEATURE_IGNORE_LINKED_LIBRARIES);
		if (feature == null || !(place.eGet(feature) instanceof final Boolean ignore)) {
			return true;
		}

		return ignore.booleanValue();
	}

	private static TypeConfig buildTypeConfig(final EObject place, final String refName,
			final Map<String, String> placeholders, final boolean pinsImplicit) {
		if (place == null) {
			return TypeConfig.INACTIVE;
		}
		final EObject child = getContainedChild(place, refName);
		if (child == null) {
			return TypeConfig.INACTIVE;
		}
		final FilterRecord constraintRecord = readFilterRecord(child, QueryModelHelper.REF_CONSTRAINT, placeholders);
		final FilterRecord attributeConstraintRecord = readFilterRecord(child,
				QueryModelHelper.REF_ATTRIBUTE_CONSTRAINT, placeholders);

		return new TypeConfig(true, constraintRecord, attributeConstraintRecord,
				buildPinConfig(child, placeholders, pinsImplicit));
	}

	private static InstanceConfig buildInstanceConfig(final EObject place, final String refName,
			final Map<String, String> placeholders, final boolean pinsImplicit) {
		if (place == null) {
			return InstanceConfig.INACTIVE;
		}
		final EObject child = getContainedChild(place, refName);
		if (child == null) {
			return InstanceConfig.INACTIVE;
		}
		final FilterRecord constraintRecord = readFilterRecord(child, QueryModelHelper.REF_CONSTRAINT, placeholders);
		final FilterRecord attributeConstraintRecord = readFilterRecord(child,
				QueryModelHelper.REF_ATTRIBUTE_CONSTRAINT, placeholders);

		final Set<String> occurrences = readOccurrences(child);

		return new InstanceConfig(true, constraintRecord, attributeConstraintRecord, occurrences,
				buildPinConfig(child, placeholders, pinsImplicit));
	}

	private static PinConfig buildPinConfig(final EObject searchTarget, final Map<String, String> placeholders,
			final boolean pinsImplicit) {
		final EObject pin = (searchTarget != null) ? getContainedChild(searchTarget, QueryModelHelper.REF_PIN) : null;
		if (pin == null) {
			return pinsImplicit ? PinConfig.ACTIVE_UNFILTERED : PinConfig.INACTIVE;
		}

		final FilterRecord constraintRecord = readFilterRecord(pin, QueryModelHelper.REF_CONSTRAINT, placeholders);
		final FilterRecord attributeConstraintRecord = readFilterRecord(pin, QueryModelHelper.REF_ATTRIBUTE_CONSTRAINT,
				placeholders);

		return new PinConfig(true, constraintRecord, attributeConstraintRecord);
	}

	private static FilterRecord readFilterRecord(final EObject parent, final String constraintRefName,
			final Map<String, String> placeholders) {
		final EObject constraint = QueryModelHelper.getContainedChild(parent, constraintRefName);
		return readConstraintTree(constraint, placeholders);
	}

	private static FilterRecord readConstraintTree(final EObject constraint, final Map<String, String> placeholders) {
		if (constraint == null) {
			return FilterRecord.INACTIVE;
		}
		return new FilterRecord(true, //
				readConstraintField(constraint, QueryModelHelper.FEATURE_NAME, placeholders),
				readConstraintField(constraint, QueryModelHelper.FEATURE_TYPE, placeholders),
				readConstraintField(constraint, QueryModelHelper.FEATURE_COMMENT, placeholders),
				readSubConstraints(constraint, QueryModelHelper.REF_OR_CONSTRAINTS, placeholders),
				readSubConstraints(constraint, QueryModelHelper.REF_AND_CONSTRAINTS, placeholders));
	}

	private static FilterRecord readSubConstraints(final EObject constraint, final String refName,
			final Map<String, String> placeholders) {
		final EStructuralFeature feature = constraint.eClass().getEStructuralFeature(refName);
		if (feature == null || !(constraint.eGet(feature) instanceof final EObject eobj)) {
			return FilterRecord.INACTIVE;
		}
		return readConstraintTree(eobj, placeholders);
	}

	private static Set<String> readOccurrences(final EObject instance) {
		final EStructuralFeature feature = instance.eClass().getEStructuralFeature(QueryModelHelper.FEATURE_OCCURRENCE);
		if (feature == null) {
			return Set.of();
		}
		final Object raw = instance.eGet(feature);
		if (raw instanceof final List<?> list && !list.isEmpty()) {
			final Set<String> result = new HashSet<>();
			for (final Object item : list) {
				if (item instanceof final Enumerator enumerator) {
					result.add(enumerator.getName());
				}
			}
			return result;
		}
		return Set.of();
	}

	private static MatcherConfig readConstraintField(final EObject constraint, final String fieldName,
			final Map<String, String> placeholders) {
		if (constraint == null) {
			return MatcherConfig.INACTIVE;
		}
		final EObject fc = getContainedChild(constraint, fieldName);
		if (fc == null) {
			return MatcherConfig.INACTIVE;
		}
		final var data = QueryModelHelper.readFieldConstraint(fc);
		return new MatcherConfig(true, substitutePlaceholders(data.value(), placeholders), data.caseSensitive(),
				data.wholeWord(), data.entire(), data.regex());
	}

	private static EObject getContainedChild(final EObject parent, final String refName) {
		return QueryModelHelper.getContainedChild(parent, refName);
	}
}