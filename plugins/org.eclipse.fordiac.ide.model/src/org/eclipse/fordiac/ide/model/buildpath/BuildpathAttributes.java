/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.buildpath;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

public final class BuildpathAttributes {

	@AttributeType(Boolean.class)
	@AttributeDefault("false")
	public static final String IGNORE_WARNINGS = "ignore_warnings"; //$NON-NLS-1$

	@AttributeType(Boolean.class)
	@AttributeDefault("true")
	public static final String EXPORT = "export"; //$NON-NLS-1$

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface AttributeType {
		Class<?> value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface AttributeDefault {
		String value();
	}

	private static final Map<String, Field> ATTRIBUTES = Stream.of(BuildpathAttributes.class.getDeclaredFields())
			.filter(field -> field.isAnnotationPresent(AttributeType.class))
			.collect(Collectors.toUnmodifiableMap(BuildpathAttributes::getAttributeName, Function.identity()));

	public static List<Attribute> addDefaultAttributes(final List<Attribute> attributes) {
		final Set<String> names = attributes.stream().map(Attribute::getName).collect(Collectors.toSet());
		ATTRIBUTES.entrySet().stream().filter(entry -> !names.contains(entry.getKey()))
				.map(entry -> createAttribute(entry.getKey(), entry.getValue())).filter(Objects::nonNull)
				.forEachOrdered(attributes::add);
		if (attributes instanceof final EList<Attribute> attributesEList) {
			ECollections.sort(attributesEList, Comparator.comparing(Attribute::getName));
		} else {
			attributes.sort(Comparator.comparing(Attribute::getName));
		}
		return attributes;
	}

	public static List<Attribute> removeDefaultAttributes(final List<Attribute> attributes) {
		attributes.removeIf(BuildpathAttributes::hasDefaultValue);
		return attributes;
	}

	public static Attribute createAttribute(final String name) {
		return createAttribute(name, ATTRIBUTES.get(name));
	}

	private static Attribute createAttribute(final String name, final Field field) {
		final Attribute result = BuildpathFactory.eINSTANCE.createAttribute();
		result.setName(name);
		if (field != null && field.isAnnotationPresent(AttributeDefault.class)) {
			result.setValue(field.getAnnotation(AttributeDefault.class).value());
		}
		return result;
	}

	public static Optional<Attribute> getAttribute(final List<Attribute> attributes, final String name) {
		return attributes.stream().filter(attribute -> name.equals(attribute.getName())).findAny();
	}

	public static String getAttributeValue(final List<Attribute> attributes, final String name) {
		return getAttribute(attributes, name).map(Attribute::getValue).orElseGet(() -> getAttributeDefault(name));
	}

	public static boolean hasDefaultValue(final Attribute attribute) {
		return Objects.equals(attribute.getValue(), getAttributeDefault(attribute.getName()));
	}

	public static Class<?> getAttributeType(final String name) {
		final Field field = ATTRIBUTES.get(name);
		if (field != null && field.isAnnotationPresent(AttributeType.class)) {
			return field.getAnnotation(AttributeType.class).value();
		}
		return null;
	}

	public static String getAttributeDefault(final String name) {
		final Field field = ATTRIBUTES.get(name);
		if (field != null && field.isAnnotationPresent(AttributeDefault.class)) {
			return field.getAnnotation(AttributeDefault.class).value();
		}
		return null;
	}

	private static String getAttributeName(final Field field) {
		try {
			return (String) field.get(null);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	private BuildpathAttributes() {
		throw new UnsupportedOperationException();
	}
}
