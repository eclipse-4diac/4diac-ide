/**
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.search.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.xtext.EcoreUtil2;

public class AttributeSearchSupport extends StructuredTextSearchSupport {
	private final Attribute attribute;
	private STInitializerExpressionSource parseResult;

	public AttributeSearchSupport(final Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public Stream<EObject> prepare() throws CoreException {
		return Stream.<EObject>of(prepareInitialValue()).filter(Objects::nonNull);
	}

	protected STInitializerExpressionSource prepareInitialValue() throws CoreException {
		if (parseResult == null && attribute.getType() instanceof AnyType && attribute.getValue() != null
				&& !attribute.getValue().isBlank()) {
			final List<String> errors = new ArrayList<>();
			parseResult = StructuredTextParseUtil.parse(attribute.getValue(),
					attribute.eResource() != null ? attribute.eResource().getURI() : null, attribute.getType(),
					EcoreUtil2.getContainerOfType(attribute, LibraryElement.class), null, errors, null, null);
			if (parseResult == null) {
				throw new CoreException(Status.error(errors.stream().collect(Collectors.joining(", ")))); //$NON-NLS-1$
			}
		}
		return parseResult;
	}
}
