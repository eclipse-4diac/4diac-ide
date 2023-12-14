/*******************************************************************************
 * Copyright (c) 2008, 2023  Profactor GmbH, fortiss GmbH, Johannes Kepler University,
 * 							 Primetals Technologies Germany GmbH
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
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/** A factory for creating new EditParts. */
public class ElementEditPartFactory extends Abstract4diacEditPartFactory {

	public ElementEditPartFactory(final GraphicalEditor editor) {
		super(editor);
	}

	/**
	 * Maps an object to an EditPart.
	 *
	 * @throws RuntimeException if no match was found (programming error)
	 */
	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof UnfoldedSubappContentNetwork) {
			return new UnfoldedSubappContentEditPart();
		}
		if (modelElement instanceof GroupContentNetwork) {
			return new GroupContentEditPart();
		}
		if (modelElement instanceof final FBNetwork network) {
			return getPartForFBNetwork(network);
		}
		if (modelElement instanceof final FBNetworkElement fbnel) {
			return getPartForFBNetworkElement(fbnel);
		}
		if (modelElement instanceof StructuredType) {
			return new StructuredTypeEditPart();
		}
		if (modelElement instanceof InstanceName) {
			return new InstanceNameEditPart();
		}
		if (modelElement instanceof InstanceComment) {
			return new InstanceCommentEditPart();
		}
		if (modelElement instanceof Connection) {
			return new ConnectionEditPart();
		}
		if (modelElement instanceof IInterfaceElement) {
			return createInterfaceEditPart(modelElement);
		}
		if (modelElement instanceof Value) {
			return new FBNValueEditPart();
		}
		if (modelElement instanceof HiddenPinIndicator) {
			return new HiddenPinIndicatorEditPart();
		}

		if (modelElement instanceof UntypedSubAppInterfaceElementEditPart.TargetInterfaceElement) {
			return new TargetInterfaceElementEditPart();
		}

		throw createEditpartCreationException(modelElement);

	}

	private static EditPart getPartForFBNetworkElement(final FBNetworkElement element) {
		if (element instanceof ErrorMarkerFBNElement) {
			return new ErrorMarkerFBNEditPart();
		}
		if (element instanceof CommunicationChannel) {
			return new CommunicationChannelEditPart();
		}
		if (element instanceof final FB fb) {
			if (null != fb.getType()) {
				if (fb.getType().getName().contentEquals("STRUCT_MUX")) { //$NON-NLS-1$
					return new MultiplexerEditPart();
				}
				if (fb.getType().getName().contentEquals("STRUCT_DEMUX")) { //$NON-NLS-1$
					return new DemultiplexerEditPart();
				}
			}
			return new FBEditPart();
		}
		if (element instanceof SubApp) {
			return new SubAppForFBNetworkEditPart();
		}
		if (element instanceof Group) {
			return new GroupEditPart();
		}
		if (element instanceof Comment) {
			return new CommentEditPart();
		}

		throw createEditpartCreationException(element);
	}

	@SuppressWarnings("static-method") // not static to allow subclasses to provide own elements
	protected EditPart getPartForFBNetwork(final FBNetwork fbNetwork) {
		if (fbNetwork.eContainer() instanceof SubApp) {
			return new UISubAppNetworkEditPart();
		}
		return new FBNetworkEditPart();
	}

	private static EditPart createInterfaceEditPart(final Object modelElement) {

		if (modelElement instanceof ErrorMarkerInterface) {
			return new ErrorMarkerInterfaceEditPart();
		}

		final IInterfaceElement element = (IInterfaceElement) modelElement;

		if ((element.getFBNetworkElement() instanceof StructManipulator)
				&& (element.getType() instanceof StructuredType) && (isMuxOutput(element) || isDemuxInput(element))) {
			return new StructInterfaceEditPart();
		}

		if ((element.getFBNetworkElement() instanceof SubApp) && (null == element.getFBNetworkElement().getType())) {
			return new UntypedSubAppInterfaceElementEditPart();
		}
		return new InterfaceEditPartForFBNetwork();
	}

	public static boolean isDemuxInput(final IInterfaceElement element) {
		return (element.getFBNetworkElement() instanceof Demultiplexer) && (element.isIsInput());
	}

	public static boolean isMuxOutput(final IInterfaceElement element) {
		return (element.getFBNetworkElement() instanceof Multiplexer) && (!element.isIsInput());
	}

}
