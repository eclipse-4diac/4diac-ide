/*******************************************************************************
 * Copyright (c) 2018, 2021 TU Wien/ACIN, Johannes Kepler University
 *  						Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann - initial implementation and/or initial documentation
 *   Alois Zoitl     - reworked algorithm edting to reduce duplicated code
 *                   - introduced dedicated editpart for better interaction with
 *                     property sheets
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.simplefb.widgets.AlgorithmEditingCompositeSimpleFB;
import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class SimpleFBEditor extends GraphicalEditor implements IFBTEditorPart {

	/** simple placeholder editpart so that the property sheet can listen to something */
	public static final class SimpleFBEditPart extends AbstractGraphicalEditPart {

		@Override
		protected IFigure createFigure() {
			return new Figure();
		}

		@Override
		protected void createEditPolicies() {
			// edit policies need for this placeholder class
		}
	}

	private final AlgorithmEditingCompositeSimpleFB baseAlgorithm = new AlgorithmEditingCompositeSimpleFB();
	private SimpleFBType fbType;
	private CommandStack commandStack;

	public SimpleFBEditor() {
		// No initialization needed
	}

	@Override
	public void createPartControl(final Composite parent) {
		final FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		baseAlgorithm.createControls(parent, toolkit);
		baseAlgorithm.initialize(fbType, commandStack);
		baseAlgorithm.setAlgorithm(fbType.getAlgorithm().get(0));
		super.createPartControl(baseAlgorithm.getCodeEditors());
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			final FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			if (untypedInput.getContent() instanceof SimpleFBType) {
				fbType = (SimpleFBType) untypedInput.getContent();
			}
		}
		setCommonCommandStack(commandStack);
		setSite(site);
		setPartName(FordiacMessages.Algorithm);
		setTitleImage(FordiacImage.ICON_ALGORITHM.getImage());
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		commandStack.markSaveLocation();
		firePropertyChange(IEditorPart.PROP_DIRTY);

	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void setFocus() {
		// do nothing
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		baseAlgorithm.setAlgorithm(fbType.getAlgorithm().get(0));
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		super.dispose();
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// For now we don't handle markers in this editor
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// For now we don't handle markers in this editor
		return false;
	}

	@Override
	public void reloadType(final FBType type) {
		if (type instanceof SimpleFBType) {
			fbType = (SimpleFBType) type;
			baseAlgorithm.setAlgorithm(fbType.getAlgorithm().get(0));
			try {
				init(getEditorSite(), new FBTypeEditorInput(type, type.getPaletteEntry()));
				initializeGraphicalViewer();
			} catch (final PartInitException e) {
				FordiacLogHelper.logError(getContentDescription(), e);
			}

		}

	}



	@Override
	protected void initializeGraphicalViewer() {
		final var viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new Abstract4diacEditPartFactory(this) {

			@Override
			protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
				if (modelElement instanceof SimpleFBType) {
					return new SimpleFBEditPart();
				}
				return null;
			}

		});
		viewer.setContents(fbType);
	}

	@Override
	protected CommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public Object getSelectableEditPart() {
		if (getGraphicalViewer() == null) {
			return null;
		}
		return getGraphicalViewer().getEditPartRegistry().get(fbType);
	}

}
