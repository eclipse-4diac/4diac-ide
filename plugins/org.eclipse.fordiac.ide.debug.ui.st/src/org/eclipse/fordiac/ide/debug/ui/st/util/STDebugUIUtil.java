/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.st.util;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource.STAlgorithmResourceSetInitializer;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

public final class STDebugUIUtil {
	private STDebugUIUtil() {
	}

	public static Resource getSourceResource(final IResource file) {
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final XtextResourceSet resourceSet = new XtextResourceSet();
		new STAlgorithmResourceSetInitializer().initialize(resourceSet, file.getProject());
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		return resourceSet.getResource(uri, true);
	}

	public static Collection<? extends EObject> getSourceElements(final Resource resource) {
		if (resource instanceof XtextResource) {
			final IParseResult parseResult = ((XtextResource) resource).getParseResult();
			if (parseResult != null) {
				final EObject source = parseResult.getRootASTElement();
				if (source instanceof STAlgorithmSource) {
					return ((STAlgorithmSource) source).getElements();
				} else if (source instanceof STFunctionSource) {
					return ((STFunctionSource) source).getFunctions();
				}
			}
		}
		return Collections.emptyList();
	}

	public static EObject getSourceElement(final Resource resource, final int line) {
		return getSourceElements(resource).stream().filter(element -> {
			final INode node = NodeModelUtils.getNode(element);
			return node != null && node.getStartLine() <= line && node.getEndLine() >= line;
		}).findFirst().orElse(null);
	}

	public static Collection<? extends EObject> getAdditionalScope(final IResource resource, final int line) {
		return getAdditionalScope(getSourceElement(getSourceResource(resource), line));
	}

	public static Collection<? extends EObject> getAdditionalScope(final EObject sourceElement) {
		if (sourceElement instanceof STAlgorithm) {
			return ((STAlgorithm) sourceElement).getBody().getVarTempDeclarations();
		} else if (sourceElement instanceof STMethod) {
			return ((STMethod) sourceElement).getBody().getVarDeclarations();
		} else if (sourceElement instanceof STFunction) {
			return ((STFunction) sourceElement).getVarDeclarations();
		}
		return Collections.emptyList();
	}
}
