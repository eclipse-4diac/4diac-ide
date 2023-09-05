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
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.ResourceValidatorImpl;

public class STCoreResourceValidator extends ResourceValidatorImpl {
	@Override
	protected void validate(final Resource resource, final CheckMode mode, final CancelIndicator monitor,
			final IAcceptor<Issue> acceptor) {
		if (resource instanceof STCoreResource && !mode.shouldCheck(CheckType.NORMAL)) {
			final List<EObject> contents = resource.getContents();
			if (!contents.isEmpty()) {
				getOperationCanceledManager().checkCanceled(monitor);
				validate(resource, contents.get(0), mode, monitor, acceptor);
			}
		} else {
			super.validate(resource, mode, monitor, acceptor);
		}
	}
}
