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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.LineAndColumn;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

public class IssueListValidationMesageAcceptor implements ValidationMessageAcceptor {

	private final List<Issue> issues = new ArrayList<>();

	@Override
	public void acceptError(final String message, final EObject object, final EStructuralFeature feature,
			final int index, final String code, final String... issueData) {
		addIssue(Severity.ERROR, message, object, feature, index, code, issueData);
	}

	@Override
	public void acceptWarning(final String message, final EObject object, final EStructuralFeature feature,
			final int index, final String code, final String... issueData) {
		addIssue(Severity.WARNING, message, object, feature, index, code, issueData);
	}

	@Override
	public void acceptInfo(final String message, final EObject object, final EStructuralFeature feature,
			final int index, final String code, final String... issueData) {
		addIssue(Severity.INFO, message, object, feature, index, code, issueData);
	}

	@Override
	public void acceptError(final String message, final EObject object, final int offset, final int length,
			final String code, final String... issueData) {
		addIssue(Severity.ERROR, message, object, offset, length, code, issueData);
	}

	@Override
	public void acceptWarning(final String message, final EObject object, final int offset, final int length,
			final String code, final String... issueData) {
		addIssue(Severity.WARNING, message, object, offset, length, code, issueData);
	}

	@Override
	public void acceptInfo(final String message, final EObject object, final int offset, final int length,
			final String code, final String... issueData) {
		addIssue(Severity.INFO, message, object, offset, length, code, issueData);
	}

	protected void addIssue(final Severity severity, final String message, final EObject object,
			final EStructuralFeature feature, final int index, final String code, final String... issueData) {
		final Issue.IssueImpl issue = new Issue.IssueImpl();
		issue.setSeverity(severity);
		issue.setMessage(message);
		if (object != null) {
			issue.setUriToProblem(EcoreUtil.getURI(object));
		}
		issue.setCode(code);
		issue.setData(issueData);
		issue.setType(CheckType.FAST);
		addLocation(issue, object, feature, index);
		issues.add(issue);
	}

	protected void addIssue(final Severity severity, final String message, final EObject object, final int offset,
			final int length, final String code, final String... issueData) {
		final Issue.IssueImpl issue = new Issue.IssueImpl();
		issue.setSeverity(severity);
		issue.setMessage(message);
		if (object != null) {
			issue.setUriToProblem(EcoreUtil.getURI(object));
		}
		issue.setCode(code);
		issue.setData(issueData);
		issue.setType(CheckType.FAST);
		addLocation(issue, object, offset, length);
		issues.add(issue);
	}

	protected static void addLocation(final IssueImpl issue, final EObject object, final EStructuralFeature feature,
			final int index) {
		INode node = NodeModelUtils.getNode(object);
		if (node != null) {
			if (feature != null) {
				final List<INode> featureNodes = NodeModelUtils.findNodesForFeature(object, feature);
				if (index < 0 && !featureNodes.isEmpty()) {
					node = featureNodes.get(0);
				} else if (index < featureNodes.size()) {
					node = featureNodes.get(index);
				}
			}
			addLocation(issue, node);
		} else if (object != null && object.eContainer() != null) {
			final EObject container = object.eContainer();
			final EStructuralFeature containingFeature = object.eContainingFeature();
			if (containingFeature.isMany()) {
				addLocation(issue, container, containingFeature,
						((EList<?>) container.eGet(containingFeature)).indexOf(object));
			} else {
				addLocation(issue, container, containingFeature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
			}
		}
	}

	protected static void addLocation(final IssueImpl issue, final EObject object, final int offset, final int length) {
		issue.setOffset(Integer.valueOf(offset));
		issue.setLength(Integer.valueOf(length));
		final INode node = NodeModelUtils.getNode(object);
		if (node != null) {
			addLineAndColumn(issue, node, offset, length);
		}
	}

	protected static void addLocation(final IssueImpl issue, final INode node) {
		issue.setOffset(Integer.valueOf(node.getOffset()));
		issue.setLength(Integer.valueOf(node.getLength()));
		addLineAndColumn(issue, node, node.getOffset(), node.getLength());
	}

	protected static void addLineAndColumn(final IssueImpl issue, final INode node, final int offset,
			final int length) {
		final LineAndColumn startLineAndColumn = NodeModelUtils.getLineAndColumn(node, offset);
		issue.setLineNumber(Integer.valueOf(startLineAndColumn.getLine()));
		issue.setColumn(Integer.valueOf(startLineAndColumn.getColumn()));
		final LineAndColumn endLineAndColumn = NodeModelUtils.getLineAndColumn(node, offset + length);
		issue.setLineNumberEnd(Integer.valueOf(endLineAndColumn.getLine()));
		issue.setColumnEnd(Integer.valueOf(endLineAndColumn.getColumn()));
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public boolean isEmpty() {
		return issues.isEmpty();
	}
}
