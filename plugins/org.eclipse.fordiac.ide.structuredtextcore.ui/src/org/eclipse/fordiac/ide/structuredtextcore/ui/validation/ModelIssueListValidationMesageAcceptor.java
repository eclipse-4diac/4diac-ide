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
package org.eclipse.fordiac.ide.structuredtextcore.ui.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.IssueListValidationMesageAcceptor;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;

public class ModelIssueListValidationMesageAcceptor extends IssueListValidationMesageAcceptor {

	@Override
	protected void addIssue(final Severity severity, final String message, final EObject object,
			final EStructuralFeature feature, final int index, final String code, final String... issueData) {
		final Issue.IssueImpl issue = new Issue.IssueImpl();
		issue.setSeverity(severity);
		issue.setMessage(message);
		if (object != null) {
			issue.setUriToProblem(EcoreUtil.getURI(object));
			issue.setData(new String[] { FordiacMarkerHelper.getLocation(object), // [0] LOCATION
					EcoreUtil.getURI(object).toString(), // [1] TARGET_URI
					EcoreUtil.getURI(object.eClass()).toString() // [2] TARGET_TYPE
			});
		}
		issue.setCode(LibraryElementValidator.DIAGNOSTIC_SOURCE);
		issue.setType(CheckType.FAST);
		getIssues().add(issue);
	}

	@Override
	protected void addIssue(final Severity severity, final String message, final EObject object, final int offset,
			final int length, final String code, final String... issueData) {
		final Issue.IssueImpl issue = new Issue.IssueImpl();
		issue.setSeverity(severity);
		issue.setMessage(message);
		if (object != null) {
			issue.setUriToProblem(EcoreUtil.getURI(object));
			issue.setData(new String[] { FordiacMarkerHelper.getLocation(object), // [0] LOCATION
					EcoreUtil.getURI(object).toString(), // [1] TARGET_URI
					EcoreUtil.getURI(object.eClass()).toString() // [2] TARGET_TYPE
			});
		}
		issue.setCode(LibraryElementValidator.DIAGNOSTIC_SOURCE);
		issue.setType(CheckType.FAST);
		getIssues().add(issue);
	}
}
