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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorMode;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeDeclaration;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Connection;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Instance;
import org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Occurrence;
import org.eclipse.fordiac.ide.bulkeditor.query.model.PIN;
import org.eclipse.fordiac.ide.bulkeditor.query.model.PinTarget;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Place;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder;
import org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Query;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Type;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.ConnectionConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.InstanceConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.OccurrenceConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.OccurrencesConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.PinConfig;
import org.eclipse.fordiac.ide.bulkeditor.search.PlaceConfig.TypeConfig;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.IEC61499SearchFilter;

public class QuerySearchAdapter {

	/** Placeholder reference syntax inside FieldConstraint values: ${key} */
	private static final Pattern PLACEHOLDER_REF = Pattern.compile("\\$\\{([^}]+)\\}"); //$NON-NLS-1$

	private QuerySearchAdapter() {
	}

	public static Map<String, String> resolvePlaceholders(final Query query) {
		final Map<String, String> result = new HashMap<>();
		for (final Placeholder placeholder : query.getPlaceholder()) {
			final String key = placeholder.getKey();
			final String val = placeholder.getVal();
			if (key != null && !key.isBlank()) {
				result.put(key, val != null ? val : ""); //$NON-NLS-1$
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

	public static BulkEditorMode resolveTargetMode(final Query query) {
		return switch (query.getTarget().getTarget()) {
		case final AttributeDeclaration _ -> BulkEditorMode.SIMPLE_ATTRIBUTE;
		case final org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute _ -> BulkEditorMode.ADVANCED_ATTRIBUTE;
		case null, default -> BulkEditorMode.VARIABLE;
		};
	}

	public static IEC61499SearchFilter buildTargetSearchFilter(final Query query) {
		final Map<String, String> placeholders = resolvePlaceholders(query);

		return switch (query.getTarget().getTarget()) {
		case final PinTarget pinTarget ->
			buildDefaultFilter(pinTarget.getConstraint(), BulkEditorMode.VARIABLE, placeholders);
		case final org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute attribute ->
			buildDefaultFilter(attribute.getConstraint(), BulkEditorMode.ADVANCED_ATTRIBUTE, placeholders);
		case final AttributeDeclaration declaration -> buildAttributeDeclarationFilter(declaration, placeholders);
		case null, default -> _ -> false;
		};
	}

	private static IEC61499SearchFilter buildDefaultFilter(final Constraint constraint, final BulkEditorMode mode,
			final Map<String, String> placeholders) {
		final FilterRecord filter = readConstraintTree(constraint, placeholders);

		return searchCandidate -> switch (searchCandidate) {
		case final Attribute attribute when mode == BulkEditorMode.ADVANCED_ATTRIBUTE ->
			filter.accepts(MatchTarget.ofAttribute(attribute));
		case final VarDeclaration varDecl when mode == BulkEditorMode.VARIABLE ->
			filter.accepts(MatchTarget.of(varDecl.getName(), varDecl.getTypeName(), varDecl.getComment(),
					InitialValueHelper.getInitialOrDefaultValue(varDecl), varDecl));
		case null, default -> false;
		};
	}

	private static IEC61499SearchFilter buildAttributeDeclarationFilter(final AttributeDeclaration declaration,
			final Map<String, String> placeholders) {
		final String declName = substitutePlaceholders(declaration.getName(), placeholders);

		if (declName == null || declName.isBlank()) {
			return _ -> false;
		}

		return searchCandidate -> (searchCandidate instanceof final Attribute attribute
				&& attribute.getAttributeDeclaration() != null
				&& declName.equalsIgnoreCase(PackageNameHelper.getFullTypeName(attribute.getAttributeDeclaration())));
	}

	public static PlaceConfig buildPlaceConfig(final Query query) {
		final Place place = query.getPlace();
		final Map<String, String> placeholders = resolvePlaceholders(query);
		// in attribute mode traverse pins only with pin node
		final boolean pinsImplicit = QueryModelHelper.isPinTargetQuery(query);

		//@formatter:off
		return new PlaceConfig(
		        buildTypeConfig(place.getSimpleType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getBasicType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getCompositeType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getServiceInterfaceType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getFunctionType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getSubappType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getStructType(), placeholders, pinsImplicit),
		        buildTypeConfig(place.getAttributeType(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getSimpleFB(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getBasicFB(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getCompositeFB(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getServiceInterfaceFB(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getFunctionFB(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getTypedSubapp(), placeholders, pinsImplicit),
		        buildInstanceConfig(place.getUntypedSubapp(), placeholders, pinsImplicit),
		        buildConnectionConfig(place.getConnection(), placeholders),
		        place.isIgnoreLinkedLibraries(),
		        place.isSubappHierarchy(),
		        List.copyOf(place.getSubappHierarchyElements()));
		//@formatter:on
	}

	private static TypeConfig buildTypeConfig(final Type type, final Map<String, String> placeholders,
			final boolean pinsImplicit) {
		if (type == null) {
			return TypeConfig.INACTIVE;
		}
		return new TypeConfig(true, readConstraintTree(type.getConstraint(), placeholders),
				buildPinConfig(type.getPin(), placeholders, pinsImplicit));
	}

	private static InstanceConfig buildInstanceConfig(final Instance instance, final Map<String, String> placeholders,
			final boolean pinsImplicit) {
		if (instance == null) {
			return InstanceConfig.INACTIVE;
		}
		return new InstanceConfig(true, readConstraintTree(instance.getConstraint(), placeholders),
				readOccurrencesConfig(instance, placeholders),
				buildPinConfig(instance.getPin(), placeholders, pinsImplicit));
	}

	private static ConnectionConfig buildConnectionConfig(final Connection connection,
			final Map<String, String> placeholders) {
		if (connection == null) {
			return ConnectionConfig.INACTIVE;
		}
		return new ConnectionConfig(true, readConstraintTree(connection.getConstraint(), placeholders),
				readOccurrencesConfig(connection, placeholders));
	}

	private static PinConfig buildPinConfig(final PIN pin, final Map<String, String> placeholders,
			final boolean pinsImplicit) {
		if (pin == null) {
			return pinsImplicit ? PinConfig.ACTIVE_UNFILTERED : PinConfig.INACTIVE;
		}
		return new PinConfig(true, readConstraintTree(pin.getConstraint(), placeholders));
	}

	private static FilterRecord readConstraintTree(final Constraint constraint,
			final Map<String, String> placeholders) {
		if (constraint == null) {
			return FilterRecord.INACTIVE;
		}
		return new FilterRecord(true, constraint.isNegate(), constraint instanceof AttributeConstraint,
				readConstraintField(constraint, PropertyConstraint::getName, placeholders),
				readConstraintField(constraint, PropertyConstraint::getType, placeholders),
				readConstraintField(constraint, PropertyConstraint::getComment, placeholders),
				readConstraintField(constraint, PropertyConstraint::getValue, placeholders),
				readSubConstraint(constraint.getOrConstraint(), placeholders),
				readSubConstraint(constraint.getAndConstraint(), placeholders));
	}

	private static FilterRecord readSubConstraint(final Constraint constraint, final Map<String, String> placeholders) {
		return constraint != null ? readConstraintTree(constraint, placeholders) : null;
	}

	private static OccurrencesConfig readOccurrencesConfig(final NetworkElement networkElement,
			final Map<String, String> placeholders) {
		return new OccurrencesConfig(readOccurrenceConfig(networkElement.getApplicationOccurrence(), placeholders),
				readOccurrenceConfig(networkElement.getCompositeFBOccurrence(), placeholders),
				readOccurrenceConfig(networkElement.getTypedSubappOccurrence(), placeholders));
	}

	private static OccurrenceConfig readOccurrenceConfig(final Occurrence occurrence,
			final Map<String, String> placeholders) {
		if (occurrence == null) {
			return OccurrenceConfig.INACTIVE;
		}
		return new OccurrenceConfig(true, readConstraintTree(occurrence.getConstraint(), placeholders));
	}

	private static MatcherConfig readConstraintField(final Constraint constraint,
			final Function<PropertyConstraint, FieldConstraint> field, final Map<String, String> placeholders) {
		// only property constraints have field constraints
		if (constraint instanceof final PropertyConstraint propertyConstraint
				&& field.apply(propertyConstraint) instanceof final FieldConstraint fieldConstraint) {
			return new MatcherConfig(true, substitutePlaceholders(fieldConstraint.getValue(), placeholders),
					fieldConstraint.isCaseSensitive(), fieldConstraint.isWholeWord(), fieldConstraint.isEntire(),
					fieldConstraint.isRegex());
		}
		return MatcherConfig.INACTIVE;
	}
}
