/**
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ulzii Jargalsaikhan
 *       - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextcore.documentation;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class STCoreCommentDocumentationProvider extends MultiLineCommentDocumentationProvider {
	@Override
	protected String findComment(final EObject o) {
		final List<INode> primaryDocumentationNodes = getSingleLineDocumentationForSTVarDeclarationNodes(o);

		if (!primaryDocumentationNodes.isEmpty()) {
			return primaryDocumentationNodes.get(0).getText().replace("//", "");
		}

		final List<INode> secondaryDocumentationNodes = getDocumentationNodes(o);
		if (!secondaryDocumentationNodes.isEmpty()) {
			return secondaryDocumentationNodes.get(0).getText();
		}
		return null;
	}

	private static List<INode> getSingleLineDocumentationForSTVarDeclarationNodes(final EObject object) {
		if (object instanceof STVarDeclaration) {
			INode node = NodeModelUtils.getNode(object);
			while (node != null && node.hasNextSibling()) {
				final var iter = node.getNextSibling().getAsTreeIterable();
				for (final INode iNode : iter) {
					if (iNode.getGrammarElement() instanceof final TerminalRule terminalrule
							&& "SL_Comment".equalsIgnoreCase(terminalrule.getName())) {
						return Collections.<INode>singletonList(iNode);
					}
				}
				if (node.getNextSibling().getSemanticElement() instanceof STVarDeclaration) {
					break;
				}
				node = node.getNextSibling();
			}
		}
		return Collections.emptyList();
	}
}
