/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH, fortiss GmbH, Johannes Kepler University,
 *                    Primetals Technologies Germany GmbH
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
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
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
		return switch (modelElement) {
		case final GroupContentNetwork _ -> new GroupContentEditPart();
		case final FBNetwork network -> {
			if (context instanceof SubAppForFBNetworkEditPart) {
				yield new UnfoldedSubappContentEditPart();
			}
			yield getPartForFBNetwork(network);
		}
		case final FBNetworkElement fbnel -> getPartForFBNetworkElement(context, fbnel);
		case final StructuredType _ -> new StructuredTypeEditPart();
		case final ErrorDataType _ -> new ErrorDataTypeEditPart();
		case final InstanceName _ -> new InstanceNameEditPart();
		case final InstanceComment _ -> new InstanceCommentEditPart();
		case final InstanceContract _ -> new InstanceContractEditPart();
		case final Connection _ -> new ConnectionEditPart();
		case final IInterfaceElement _ -> createInterfaceEditPart(modelElement);
		case final Value _ -> new FBNValueEditPart();
		case final HiddenPinIndicator _ -> new HiddenPinIndicatorEditPart();
		case final TargetInterfaceElement _ -> new TargetInterfaceElementEditPart();
		case final Mapping _ -> new MappingEditPart();
		default -> throw createEditpartCreationException(context, modelElement);
		};
	}

	private static EditPart getPartForFBNetworkElement(final EditPart context, final FBNetworkElement element) {
		return switch (element) {
		case final ErrorMarkerFBNElement _ -> new ErrorMarkerFBNEditPart();
		case final CommunicationChannel _ -> new CommunicationChannelEditPart();
		case final ConfigurableMoveFB _ -> new ConfigurableMoveFBEditPart();
		case final StructManipulator _ -> new StructManipulatorEditPart();
		case final FB _ -> new FBEditPart();
		case final SubApp _ -> new SubAppForFBNetworkEditPart();
		case final Group _ -> new GroupEditPart();
		case final Comment _ -> new CommentEditPart();
		case null, default -> throw createEditpartCreationException(context, element);
		};
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

		if ((element.getBlockFBNetworkElement() instanceof StructManipulator)
				&& (element.getType() instanceof StructuredType) && (isMuxOutput(element) || isDemuxInput(element))) {
			return new StructInterfaceEditPart();
		}

		if (element.getBlockFBNetworkElement() instanceof UntypedSubApp) {
			return new UntypedSubAppInterfaceElementEditPart();
		}
		return new InterfaceEditPartForFBNetwork();
	}

	public static boolean isDemuxInput(final IInterfaceElement element) {
		return (element.getBlockFBNetworkElement() instanceof Demultiplexer) && (element.isIsInput());
	}

	public static boolean isMuxOutput(final IInterfaceElement element) {
		return (element.getBlockFBNetworkElement() instanceof Multiplexer) && (!element.isIsInput());
	}

}
