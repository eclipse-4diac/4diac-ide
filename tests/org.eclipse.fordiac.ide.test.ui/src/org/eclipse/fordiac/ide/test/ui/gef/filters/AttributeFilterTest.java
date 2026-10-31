/*******************************************************************************
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Tisya Gupta - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui.gef.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.filters.AttributeFilter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.junit.jupiter.api.Test;

class AttributeFilterTest {

	@Test
	void testAdapterDeclarationResolvesDirectly() {
		final AdapterDeclaration adapterDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		assertEquals(adapterDecl, AttributeFilter.parseObject(adapterDecl));
		assertTrue(new AttributeFilter().select(adapterDecl));
	}

	@Test
	void testAdapterFBResolvesToAdapterDeclaration() {
		final AdapterDeclaration adapterDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		final AdapterFB adapterFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
		adapterFB.setAdapterDecl(adapterDecl);
		adapterDecl.setAdapterFB(adapterFB);

		assertEquals(adapterDecl, AttributeFilter.parseObject(adapterFB));
		assertTrue(new AttributeFilter().select(adapterFB));
	}

	@Test
	void testAdapterFBEditPartResolvesToAdapterDeclaration() {
		final AdapterDeclaration adapterDecl = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		final AdapterFB adapterFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
		adapterFB.setAdapterDecl(adapterDecl);
		adapterDecl.setAdapterFB(adapterFB);

		final AbstractGraphicalEditPart editPart = new AbstractGraphicalEditPart() {
			@Override
			protected IFigure createFigure() {
				return null;
			}

			@Override
			protected void createEditPolicies() {
			}

			@Override
			public Object getModel() {
				return adapterFB;
			}
		};

		assertEquals(adapterDecl, AttributeFilter.parseObject(editPart));
		assertTrue(new AttributeFilter().select(editPart));
	}

	@Test
	void testAdapterFBWithoutDeclReturnsAdapterFB() {
		final AdapterFB adapterFB = LibraryElementFactory.eINSTANCE.createAdapterFB();
		assertEquals(adapterFB, AttributeFilter.parseObject(adapterFB));
	}

	@Test
	void testNullInputReturnsNull() {
		assertNull(AttributeFilter.parseObject(null));
	}
}

