/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.contentprovider;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class ServiceSequenceContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if(inputElement instanceof ServiceSequence) {
			return ((ServiceSequence)inputElement).getServiceTransaction().toArray();
		}
		return new Object[] {};
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof ServiceSequence && null != ((ServiceSequence)parentElement).getServiceTransaction()){
			return ((ServiceSequence)parentElement).getServiceTransaction().toArray();
		}
		if(parentElement instanceof ServiceTransaction){
			EList<Primitive> list = new BasicEList<Primitive>();
			list.add(((ServiceTransaction)parentElement).getInputPrimitive());
			list.addAll(((ServiceTransaction)parentElement).getOutputPrimitive());
			return list.toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof ServiceSequence){
			return ((ServiceSequence)element).eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof ServiceSequence){
			return ((ServiceSequence)element).getServiceTransaction().isEmpty();
		}
		if(element instanceof ServiceTransaction){
			return null != ((ServiceTransaction)element).getInputPrimitive() || ((ServiceTransaction)element).getOutputPrimitive().size() > 0 ? true : false;
		}
		return false;
	}
}
