/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Germany GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.junit.jupiter.api.Test;

public class AbstractDirectEditableEditPartTest {

	private static class TestDirectEditableEditPart extends AbstractDirectEditableEditPart {
		private INamedElement element;
		private final Label label = new Label();

		public void setElement(final INamedElement element) {
			this.element = element;
		}

		@Override
		public INamedElement getINamedElement() {
			return element;
		}

		@Override
		public Label getNameLabel() {
			return label;
		}

		@Override
		protected IFigure createFigure() {
			return label;
		}
	}

	@Test
	public void testRenameWhenElementBecomesNull() {
		final Event event = LibraryElementFactory.eINSTANCE.createEvent();
		event.setName("EVENT1");

		final TestDirectEditableEditPart part = new TestDirectEditableEditPart();
		part.setElement(event);
		part.activate();

		part.setElement(null);

		final ChangeNameCommand cmd = ChangeNameCommand.forName(event, "EVENT2");
		cmd.execute();
		part.refreshName();

		part.setElement(event);
		part.refreshName();
		assertEquals("EVENT2", part.getNameLabel().getText());
	}
}
