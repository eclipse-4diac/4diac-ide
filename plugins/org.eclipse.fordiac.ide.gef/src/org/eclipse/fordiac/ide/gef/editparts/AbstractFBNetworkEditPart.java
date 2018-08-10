/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public abstract class AbstractFBNetworkEditPart extends AbstractDiagramEditPart{

	/** The child providers. */
	List<IChildrenProvider> childProviders = null;

	
	@Override
	public FBNetwork getModel(){
		return (FBNetwork) super.getModel();
	}

	
	@Override
	public void deactivate() {
		super.deactivate();
		if (childProviders != null) {
			childProviders.clear();
			childProviders = null;
		}
	}
	
	@Override
	protected List<?> getModelChildren() {		
		List<Object> children = new ArrayList<>();
		children.addAll(getModel().getNetworkElements());
		children.addAll(getFBValues());
		
		for (IChildrenProvider provider : getChildrenProviders()) {
			if (provider.isEnabled()) {
				children.addAll(provider.getChildren(getModel()));
			}
		}
		return children;
	}
	
	/** go through all fb network elements and find inputs with paramters to be shown.
	 */
	private Collection<? extends Object> getFBValues() {
		ArrayList<Object> valueElenents = new ArrayList<>();
		for(FBNetworkElement element : getModel().getNetworkElements()){
			for (IInterfaceElement interfaceElement : element.getInterface().getInputVars()) {
				if (null != interfaceElement.getValue()) {
					valueElenents.add(interfaceElement.getValue());
				}
			}			
		}
		return valueElenents;
	}
	
	private List<IChildrenProvider> getChildrenProviders(){
		if (childProviders == null) {
			getExtensions();
		}
		return childProviders;
	}

	private void getExtensions() {
		childProviders = new ArrayList<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor("org.eclipse.fordiac.ide.gef", "ChildrenProvider"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IChildrenProvider) {
					IChildrenProvider childrenProvider = (IChildrenProvider) object;
					childProviders.add(childrenProvider);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading ChildrenProvider Extensions in org.eclipse.fordiac.ide.gef", corex); //$NON-NLS-1$
			}
		}
	}
	
}
