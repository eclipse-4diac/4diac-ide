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
package org.eclipse.fordiac.ide.structuredtextcore.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.emf.FordiacMetaData;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

public class STCoreQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	@Override
	protected QualifiedName computeFullyQualifiedName(final EObject obj) {
		if (FordiacMetaData.isSynthetic(obj.eContainmentFeature())) {
			return null;
		}
		return super.computeFullyQualifiedName(obj);
	}
}
