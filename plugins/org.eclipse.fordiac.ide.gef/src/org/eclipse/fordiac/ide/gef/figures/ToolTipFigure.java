/*******************************************************************************
 * Copyright (c) 2008 - 2011, 2014, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.ui.FordiacMessages;

/**
 * The Class ToolTipFigure.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ToolTipFigure extends Figure {

	private final VerticalLineCompartmentFigure line;

	/**
	 * Instantiates a new tool tip figure.
	 *
	 * @param element the element
	 */
	public ToolTipFigure(final INamedElement element, final GraphicalAnnotationModel annotationModel) {
		final ToolbarLayout mainLayout = new ToolbarLayout(false);
		setLayoutManager(mainLayout);
		mainLayout.setStretchMinorAxis(true);

		String nameLine = element.getName();

		if (element instanceof final ITypedElement typedElement && typedElement.getFullTypeName() != null) {
			nameLine += " - " + typedElement.getFullTypeName(); //$NON-NLS-1$
		}

		add(new Label(nameLine));

		line = new VerticalLineCompartmentFigure();
		add(line);

		final String comment = element.getComment();
		if ((comment != null) && (!comment.isEmpty())) {
			line.add(new Label(comment));
		}
		if (element instanceof final Event event) {
			addWiths(event);
		} else if (element instanceof final VarDeclaration varDecl) {
			addVarDefaultValue(varDecl);
		}

		if (annotationModel != null) {
			annotationModel.getAnnotations(element).stream().forEach(annotation -> line
					.add(new Label(annotation.getText(), GraphicalAnnotationStyles.getAnnotationImage(annotation))));
		}
	}

	public final VerticalLineCompartmentFigure getLine() {
		return line;
	}

	private void addWiths(final Event element) {
		final List<With> withs = element.getWith();
		if (!withs.isEmpty()) {
			boolean first = true;
			final StringBuilder withText = new StringBuilder(FordiacMessages.With + ": ["); //$NON-NLS-1$
			for (final With with : withs) {
				if (first) {
					first = false;
				} else {
					withText.append(", "); //$NON-NLS-1$
				}
				if (with != null && with.getVariables() != null) {
					withText.append(with.getVariables().getName());
				}
			}
			withText.append("]"); //$NON-NLS-1$
			line.add(new Label(withText.toString()));
		}

	}

	private void addVarDefaultValue(final VarDeclaration variable) {
		final VarDeclaration typeVar = getTypevariable(variable);
		if (null != typeVar && null != typeVar.getValue()) {
			String initvalue = FordiacMessages.InitialValue + ": "; //$NON-NLS-1$
			if (!typeVar.getValue().getValue().isEmpty()) {
				initvalue += typeVar.getValue().getValue();
			}
			line.add(new Label(initvalue));
		}
	}

	private static VarDeclaration getTypevariable(final VarDeclaration variable) {
		if (variable.eContainer() instanceof final Device dev) {
			if (null != dev.getType()) {
				for (final VarDeclaration typeVar : dev.getType().getVarDeclaration()) {
					if (typeVar.getName().equals(variable.getName())) {
						return typeVar;
					}
				}
			}
		} else if (null != variable.getFBNetworkElement() && null != variable.getFBNetworkElement().getType()) {
			return variable.getFBNetworkElement().getType().getInterfaceList().getVariable(variable.getName());
		}
		return null;
	}

}
