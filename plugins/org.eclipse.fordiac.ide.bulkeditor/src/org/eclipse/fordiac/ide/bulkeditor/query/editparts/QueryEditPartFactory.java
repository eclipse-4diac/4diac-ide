/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.gef.EditPart;

/** Creates the edit parts for the diagram, nodes and connections of queries. */
public class QueryEditPartFactory extends Abstract4diacEditPartFactory {

	private final IProject project;

	public QueryEditPartFactory(final IProject project) {
		// the query viewer is a page of the bulk editor and not of a graphical editor
		super(null);
		this.project = project;
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		return switch (modelElement) {
		case final QueryDiagram _ -> new QueryDiagramEditPart();
		case final QueryConnection _ -> new QueryConnectionEditPart();
		case final EObject _ -> new QueryNodeEditPart(project);
		default -> throw createEditpartCreationException(context, modelElement);
		};
	}
}
