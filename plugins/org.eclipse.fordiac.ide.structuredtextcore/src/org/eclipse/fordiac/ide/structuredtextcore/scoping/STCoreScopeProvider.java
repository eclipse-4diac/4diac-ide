/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - literal data types
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

/** This class contains custom scoping description.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping on how and when to use it. */
public class STCoreScopeProvider extends AbstractSTCoreScopeProvider {
	private static final EObjectDescription[] ADDITIONAL_LITERAL_TYPES = {
			descriptionForType("D", ElementaryTypes.DATE), //$NON-NLS-1$
			descriptionForType("LD", ElementaryTypes.LDATE), //$NON-NLS-1$
			descriptionForType("T", ElementaryTypes.TIME),  //$NON-NLS-1$
			descriptionForType("LT", ElementaryTypes.LTIME) //$NON-NLS-1$
	};

	@Override
	public IScope getScope(final EObject context, final EReference reference) {
		if (reference == STCorePackage.Literals.ST_VAR_DECLARATION__TYPE) {
			final IScope globalScope = super.getScope(context, reference);
			final List<DataType> candidates = DataTypeLibrary.getNonUserDefinedDataTypes();
			return new SimpleScope(globalScope,
					Scopes.scopedElementsFor(candidates, QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)),
					true);
		} else if (reference == STCorePackage.Literals.ST_NUMERIC_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_DATE_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_TIME_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__TYPE
				|| reference == STCorePackage.Literals.ST_STRING_LITERAL__TYPE) {
			final List<DataType> candidates = DataTypeLibrary.getNonUserDefinedDataTypes();
			return new SimpleScope(
					Stream.concat(
							StreamSupport.stream(
									Scopes.scopedElementsFor(candidates,
											QualifiedName.wrapper(SimpleAttributeResolver.NAME_RESOLVER)).spliterator(),
									false),
							Stream.of(ADDITIONAL_LITERAL_TYPES)).collect(Collectors.toList()),
					true);
		}
		return super.getScope(context, reference);
	}

	private static EObjectDescription descriptionForType(final String name, final DataType type) {
		return new EObjectDescription(QualifiedName.create(name), type, null);
	}
}
