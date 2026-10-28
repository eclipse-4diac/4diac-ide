/*******************************************************************************
 * Copyright (c) 2026 Huzaifa Abdul Rehman
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 AND CC0-1.0
 *
 * AI Disclosure: This file was largely generated with OpenAI Codex.
 * The AI-generated portions are made available under CC0-1.0.
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.data.impl;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.util.DataValidator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

final class StructuredTypeAnnotations {

	static boolean validateNoCircularReferences(final StructuredType type, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		final List<DataType> path = findCircularReference(type);
		if (!path.isEmpty()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, DataValidator.DIAGNOSTIC_SOURCE,
						DataValidator.STRUCTURED_TYPE__VALIDATE_NO_CIRCULAR_REFERENCES,
						MessageFormat.format(Messages.StructuredTypeAnnotations_CircularReference, type.getName(),
								path.stream().map(StructuredTypeAnnotations::typeName)
										.collect(Collectors.joining(" -> "))), //$NON-NLS-1$
						FordiacMarkerHelper.getDiagnosticData(type,
								DataPackage.Literals.STRUCTURED_TYPE__MEMBER_VARIABLES)));
			}
			return false;
		}
		return true;
	}

	private static List<DataType> findCircularReference(final StructuredType root) {
		final Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
		final Set<Object> active = Collections.newSetFromMap(new IdentityHashMap<>());
		final var path = new ArrayDeque<TypePath>();
		final Object rootIdentity = identity(root);
		visited.add(rootIdentity);
		active.add(rootIdentity);
		path.push(new TypePath(root, rootIdentity, references(root).iterator()));
		while (!path.isEmpty()) {
			final TypePath current = path.peek();
			if (!current.references().hasNext()) {
				active.remove(current.identity());
				path.pop();
				continue;
			}
			final DataType next = current.references().next();
			final Object nextIdentity = identity(next);
			if (active.contains(nextIdentity)) {
				return Stream.concat(path.reversed().stream().map(TypePath::type), Stream.of(next)).toList();
			}
			if (visited.add(nextIdentity)) {
				active.add(nextIdentity);
				path.push(new TypePath(next, nextIdentity, references(next).iterator()));
			}
		}
		return List.of();
	}

	private static String typeName(final DataType type) {
		final String fullName = PackageNameHelper.getFullTypeName(type);
		return fullName == null || fullName.isBlank() ? type.eClass().getName() : fullName;
	}

	private static Object identity(final DataType type) {
		return type.getTypeEntry() != null ? type.getTypeEntry() : type;
	}

	private static Stream<DataType> references(final DataType type) {
		return switch (type) {
		case final StructuredType structure ->
			structure.getMemberVariables().stream().map(VarDeclaration::getType).filter(Objects::nonNull);
		case final ArrayType array -> Stream.ofNullable(array.getBaseType());
		case final DirectlyDerivedType derived -> Stream.ofNullable(derived.getBaseType());
		default -> Stream.empty();
		};
	}

	private record TypePath(DataType type, Object identity, Iterator<DataType> references) {
	}

	private StructuredTypeAnnotations() {
		throw new UnsupportedOperationException();
	}
}
