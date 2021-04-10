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
	private final String type;

	public TransactionContentProvider(final String type) {
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

	private static Object[] getLeftOutputs(final Object inputElement) {
		final EList<Primitive> list = new BasicEList<>();
		final Service service = (Service) ((ServiceTransaction) inputElement).eContainer().eContainer();
		for (final OutputPrimitive primitive : ((ServiceTransaction) inputElement).getOutputPrimitive()) {
			if (primitive.getInterface().getName().equals(service.getLeftInterface().getName())) {
				list.add(primitive);
			}
		}
		return list.toArray();
	}

	private static Object[] getRightOutputs(final Object inputElement) {
		final EList<Primitive> list = new BasicEList<>();
		final Service service = (Service) ((ServiceTransaction) inputElement).eContainer().eContainer();
		for (final OutputPrimitive primitive : ((ServiceTransaction) inputElement).getOutputPrimitive()) {
			if (primitive.getInterface().getName().equals(service.getRightInterface().getName())) {
				list.add(primitive);
			}
		}
		return list.toArray();
	}

	@Override
	public void dispose() {
		// currently nothing to be done here
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// currently nothing to be done here
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof ServiceTransaction) {
			final EList<Primitive> list = new BasicEList<>();
			list.add(((ServiceTransaction) parentElement).getInputPrimitive());
			list.addAll(((ServiceTransaction) parentElement).getOutputPrimitive());
			return list.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof ServiceTransaction) {
			return ((ServiceTransaction) element).eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof ServiceTransaction) {
			return (!((ServiceTransaction) element).getOutputPrimitive().isEmpty());
		}
		return false;
	}
}
