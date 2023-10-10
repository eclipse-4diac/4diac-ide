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
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

public class STStandardFunctionScope extends AbstractScope {

	private final STStandardFunctionProvider standardFunctionProvider;

	private final List<DataType> argumentTypes;

	public STStandardFunctionScope(final IScope parent, final STStandardFunctionProvider standardFunctionProvider,
			final List<DataType> argumentTypes, final boolean ignoreCase) {
		super(parent, ignoreCase);
		this.standardFunctionProvider = standardFunctionProvider;
		this.argumentTypes = argumentTypes;
	}

	@Override
	public Iterable<IEObjectDescription> getAllLocalElements() {
		return standardFunctionProvider.getDescriptions();
	}

	@Override
	public IEObjectDescription getSingleLocalElementByName(final QualifiedName name) {
		if (name.getSegmentCount() == 1) {
			return standardFunctionProvider.findDescription(name.getFirstSegment(), argumentTypes).orElse(null);
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("STStandardFunctionScope [argumentTypes=%s]", //$NON-NLS-1$
				argumentTypes.stream().map(DataType::getName).collect(Collectors.joining(", ", "(", ")"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
