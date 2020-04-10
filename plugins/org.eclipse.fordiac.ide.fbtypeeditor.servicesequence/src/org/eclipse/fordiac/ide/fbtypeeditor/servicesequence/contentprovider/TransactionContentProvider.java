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
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TransactionContentProvider implements ITreeContentProvider {
	private String type;

	public TransactionContentProvider(String type) {
		this.type = type;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof ServiceTransaction) {
			switch (type) {
			case ServiceInterfacePaletteFactory.LEFT_OUTPUT_PRIMITIVE:
				return getLeftOutputs(inputElement);
			case ServiceInterfacePaletteFactory.RIGHT_OUTPUT_PRIMITIVE:
				return getRightOutputs(inputElement);
			default:
				if (null != ((ServiceTransaction) inputElement).getInputPrimitive()) {
					return new Object[] { ((ServiceTransaction) inputElement).getInputPrimitive() };
				}
			}
		}
		return new Object[] {};
	}

	private Object[] getLeftOutputs(final Object inputElement) {
		EList<Primitive> list = new BasicEList<Primitive>();
		Service service = (Service) ((ServiceTransaction) inputElement).eContainer().eContainer();
		for (OutputPrimitive primitive : ((ServiceTransaction) inputElement).getOutputPrimitive()) {
			if (primitive.getInterface().getName().equals(service.getLeftInterface().getName())) {
				list.add(primitive);
			}
		}
		return list.toArray();
	}

	private Object[] getRightOutputs(final Object inputElement) {
		EList<Primitive> list = new BasicEList<Primitive>();
		Service service = (Service) ((ServiceTransaction) inputElement).eContainer().eContainer();
		for (OutputPrimitive primitive : ((ServiceTransaction) inputElement).getOutputPrimitive()) {
			if (primitive.getInterface().getName().equals(service.getRightInterface().getName())) {
				list.add(primitive);
			}
		}
		return list.toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ServiceTransaction) {
			EList<Primitive> list = new BasicEList<Primitive>();
			list.add(((ServiceTransaction) parentElement).getInputPrimitive());
			list.addAll(((ServiceTransaction) parentElement).getOutputPrimitive());
			return list.toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof ServiceTransaction) {
			return ((ServiceTransaction) element).eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ServiceTransaction) {
			return ((ServiceTransaction) element).getOutputPrimitive().size() > 0 ? true : false;
		}
		return false;
	}
}
