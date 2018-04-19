/*******************************************************************************
 * Copyright (c) 2008 - 2011, 2014, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * The Class ToolTipFigure.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ToolTipFigure extends Figure {
	
	protected VerticalLineCompartmentFigure line;

	/**
	 * Instantiates a new tool tip figure.
	 * 
	 * @param element
	 *            the element
	 */
	public ToolTipFigure(final INamedElement element) {
		ToolbarLayout mainLayout = new ToolbarLayout(false);
		setLayoutManager(mainLayout);
		mainLayout.setStretchMinorAxis(true);

		String nameLine = element.getName();

		if ((element instanceof VarDeclaration) &&  (((VarDeclaration) element).getType() != null)) {
				nameLine += " - " + ((VarDeclaration) element).getType().getName();
		}

		add(new Label(nameLine));

		line = new VerticalLineCompartmentFigure();
		add(line);

		String comment = element.getComment();		
		if ((comment != null) && (!comment.isEmpty())) {
			line.add(new Label(comment));
		}
		if(element instanceof Event){
			addWiths((Event)element);
		} else if (element instanceof VarDeclaration) {
			addVarDefaultValue((VarDeclaration)element);
		}
	}
	
	private void addWiths(Event element) {
		List<With> withs = element.getWith();
		if(!withs.isEmpty()){
			boolean first = true;
			StringBuilder withText = new StringBuilder("With: [");
			for (With with : withs) {
				if (first) {
					first = false;
				} else {
					withText.append(", ");
				}
				if (with != null && with.getVariables() != null) {
					withText.append(with.getVariables().getName());
				}
			}
			withText.append("]");
			line.add(new Label(withText.toString()));			
		}
		
	}

	private void addVarDefaultValue(VarDeclaration var) {
		VarDeclaration typeVar = getTypevariable(var);		
		if(null != typeVar && null != typeVar.getVarInitialization()){
			String initvalue = "Inital value: ";  
			if(null != typeVar.getVarInitialization().getInitialValue() && 
					!typeVar.getVarInitialization().getInitialValue().isEmpty()){
				initvalue += var.getVarInitialization().getInitialValue();
			}
			line.add(new Label(initvalue));
		}
	}

	private static VarDeclaration getTypevariable(VarDeclaration var) {
		if(var.eContainer() instanceof Device){
			Device dev = (Device)var.eContainer();
			if(null != dev.getType()) {
				for(VarDeclaration typeVar : dev.getType().getVarDeclaration()){
					if(typeVar.getName().equals(var.getName())){
						return typeVar;
					}
				}
			}
		} else if(null != var.getFBNetworkElement() && var.getFBNetworkElement().getType() instanceof FBType){
			return ((FBType)var.getFBNetworkElement().getType()).getInterfaceList().getVariable(var.getName());
		}
		return null;
	}
	

}
