/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University
 *               2020        Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *   Bianca Wiesmayr - added struct
 *   Alois Zoitl, Bianca Wiesmayr - unfolded subapp
 *   Daniel Lindhuber - InstanceComment
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/**
 * A factory for creating new EditParts.
 */
public class ElementEditPartFactory extends Abstract4diacEditPartFactory {

	public ElementEditPartFactory(GraphicalEditor editor) {
		super(editor);
	}

	/**
	 * Maps an object to an EditPart.
	 *
	 * @throws RuntimeException if no match was found (programming error)
	 */
	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		EditPart part = null;
		if (modelElement instanceof UnfoldedSubappContentNetwork) {
			part = new UnfoldedSubappContentEditPart();
		} else if (modelElement instanceof FBNetwork) {
			if (((FBNetwork) modelElement).eContainer() instanceof SubApp) {
				part = new UISubAppNetworkEditPart();
			} else {
				part = new FBNetworkEditPart();
			}
		} else if (modelElement instanceof FB) {
			if (null != ((FB) modelElement).getType()) {
				if (((FB) modelElement).getType().getName().contentEquals("STRUCT_MUX")) { //$NON-NLS-1$
					part = new MultiplexerEditPart();
				} else if (((FB) modelElement).getType().getName().contentEquals("STRUCT_DEMUX")) { //$NON-NLS-1$
					part = new DemultiplexerEditPart();
				} else {
					part = new FBEditPart();
				}
			}
		} else if (modelElement instanceof InstanceName) {
			part = new InstanceNameEditPart();
		} else if (modelElement instanceof InstanceComment) {
			part = new InstanceCommentEditPart();
		} else if (modelElement instanceof Connection) {
			part = new ConnectionEditPart();
		} else if (modelElement instanceof SubApp) {
			part = new SubAppForFBNetworkEditPart();
		} else if (modelElement instanceof IInterfaceElement) {
			part = createInterfaceEditPart(modelElement, context);
		} else if (modelElement instanceof Value) {
			part = new ValueEditPart();
		} else {
			throw createEditpartCreationException(modelElement);
		}
		return part;
	}

	private static EditPart createInterfaceEditPart(final Object modelElement, final EditPart context) {
		EditPart part;
		IInterfaceElement element = (IInterfaceElement) modelElement;
		if ((element.getFBNetworkElement() instanceof StructManipulator)
				&& (element.getType() instanceof StructuredType)) {
			if (isMuxOutput(element)) {
				return new StructInterfaceEditPart();
			}
			if (isDemuxInput(element)) {
				return new StructInterfaceEditPart();
			}
		}
		if ((element.getFBNetworkElement() instanceof SubApp) && (null == element.getFBNetworkElement().getType())) {
			part = new UntypedSubAppInterfaceElementEditPart();
		} else {
			part = new InterfaceEditPartForFBNetwork();
		}
		return part;
	}

	private static boolean isDemuxInput(IInterfaceElement element) {
		return (element.getFBNetworkElement() instanceof Demultiplexer) && (element.isIsInput());
	}

	private static boolean isMuxOutput(IInterfaceElement element) {
		return (element.getFBNetworkElement() instanceof Multiplexer) && (!element.isIsInput());
	}

}
