/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;

public abstract class AbstractGlobalConstantsValidator extends STCoreValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>(super.getEPackages());
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/fordiac/ide/structuredtextcore/STCore"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/fordiac/ide/globalconstantseditor/GlobalConstants"));
		return result;
	}
}
