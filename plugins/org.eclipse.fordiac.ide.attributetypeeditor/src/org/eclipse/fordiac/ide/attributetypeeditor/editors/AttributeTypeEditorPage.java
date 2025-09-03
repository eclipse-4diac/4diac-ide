/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University, Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Muttenthaler Benjamin
 *     - fixed reload of view if file on file system did change
 *     - use new saveType method of AbstractTypeExporter
 *     - replaced DataTypeListener by AdapterImpl
 *     - keep a copy of the datatype object in the view, otherwise the content of the file is changed even the save button was not pressed
 *   Lukas Wais
 *     - enabled Save As
 *   Sebastian Hollersbacher
 *     - Changed from DataTypeEditor to AttributeTypeEditor
 *******************************************************************************/

package org.eclipse.fordiac.ide.attributetypeeditor.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.attributetypeeditor.Messages;
import org.eclipse.fordiac.ide.attributetypeeditor.widgets.DirectlyDerivedTypeComposite;
import org.eclipse.fordiac.ide.datatypeeditor.widgets.StructEditingComposite;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeDeclarationTypeCommand;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditorPage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorSite;

public class AttributeTypeEditorPage extends AbstractTypeEditorPage {
	private static final int STRUCT_EDITOR_INDEX = 0;
	private static final int DIRECTLYDERIVEDTYPE_EDITOR_INDEX = 1;

	private GraphicalAnnotationModel annotationModel;
	private StructEditingComposite structComposite;
	private DirectlyDerivedTypeComposite directlyDerivedTypeComposite;
	private Combo comboBox;

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (notification.getOldValue() instanceof AnyDerivedType
					&& notification.getNewValue() instanceof AnyDerivedType) {
				if (notification.getNewValue() instanceof StructuredType) {
					comboBox.select(STRUCT_EDITOR_INDEX);
				} else {
					comboBox.select(DIRECTLYDERIVEDTYPE_EDITOR_INDEX);
				}
				changeEditingComposite();
			}
		}
	};

	@Override
	public void dispose() {
		removeListenerFromAttributeDeclaration();
		if (structComposite != null) {
			structComposite.dispose();
		}
		if (directlyDerivedTypeComposite != null) {
			directlyDerivedTypeComposite.dispose();
		}
		super.dispose();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// nothing to do in this type editor page on save
	}

	@Override
	public void doSaveAs() {
		// nothing to do in this type editor page on save as
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		addListenerToAttributeDeclaration();
		setPartName(Messages.AttributeTypeEditorPage_Title);
		setTitleImage(FordiacImage.ICON_ATTRIBUTE_DECLARATION.getImage());
	}

	@Override
	public void setInput(final IEditorInput input) {
		checkEditorInput(input);

		if (getSite() instanceof final MultiPageEditorSite mpes) {
			annotationModel = mpes.getMultiPageEditor().getAdapter(GraphicalAnnotationModel.class);
		}
		if (structComposite != null) {
			structComposite.setAnnotationModel(annotationModel);
		}
		super.setInputWithNotify(input);
	}

	private void addListenerToAttributeDeclaration() {
		if (getType() != null) {
			getType().eAdapters().add(adapter);
		}
	}

	private void removeListenerFromAttributeDeclaration() {
		if (getType() != null && getType().eAdapters().contains(adapter)) {
			getType().eAdapters().remove(adapter);
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		final Composite editorComposite = new Composite(parent, SWT.NONE);
		editorComposite.setLayout(new GridLayout(1, false));

		comboBox = new Combo(editorComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboBox.add(Messages.AttributeTypeEditorPage_ComboBox_StructEditingComposite, STRUCT_EDITOR_INDEX);
		comboBox.add(Messages.AttributeTypeEditorPage_ComboBox_DirectlyDerivedTypeComposite,
				DIRECTLYDERIVEDTYPE_EDITOR_INDEX);
		comboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final AnyDerivedType type = getType().getType();
				if (comboBox.getSelectionIndex() == STRUCT_EDITOR_INDEX && type instanceof DirectlyDerivedType
						|| comboBox.getSelectionIndex() == DIRECTLYDERIVEDTYPE_EDITOR_INDEX
								&& type instanceof StructuredType) {
					getCommandStack().execute(new ChangeAttributeDeclarationTypeCommand(getType()));
				}
			}
		});

		final AnyDerivedType type = getType().getType();
		if (type instanceof final StructuredType structType) {
			createStructComposite(editorComposite, structType);
			comboBox.select(STRUCT_EDITOR_INDEX);
		} else if (type instanceof final DirectlyDerivedType directlyDerivedType) {
			createDirectlyDerivedComposite(editorComposite, directlyDerivedType);
			comboBox.select(DIRECTLYDERIVEDTYPE_EDITOR_INDEX);
		}
	}

	private void changeEditingComposite() {
		final AnyDerivedType type = getType().getType();
		if (structComposite != null && type instanceof final DirectlyDerivedType directlyDerivedType) {
			createDirectlyDerivedComposite(structComposite.getParent(), directlyDerivedType);
			if (!structComposite.isDisposed()) {
				structComposite.dispose();
			}
			structComposite = null;
			directlyDerivedTypeComposite.requestLayout();
		} else if (directlyDerivedTypeComposite != null && type instanceof final StructuredType structType) {
			createStructComposite(directlyDerivedTypeComposite.getParent(), structType);
			if (!directlyDerivedTypeComposite.isDisposed()) {
				directlyDerivedTypeComposite.dispose();
			}
			directlyDerivedTypeComposite = null;
			structComposite.requestLayout();
		}
	}

	private void createStructComposite(final Composite parent, final StructuredType structType) {
		structComposite = new StructEditingComposite(parent, getCommandStack(), structType, annotationModel, getSite());
		getSite().setSelectionProvider(structComposite.getSelectionProvider());
	}

	private void createDirectlyDerivedComposite(final Composite parent, final DirectlyDerivedType directlyDerivedType) {
		directlyDerivedTypeComposite = new DirectlyDerivedTypeComposite(parent, directlyDerivedType, getCommandStack());
		getSite().setSelectionProvider(directlyDerivedTypeComposite);
	}

	@Override
	public void setFocus() {
		if (structComposite != null) {
			structComposite.setFocus();
		} else if (directlyDerivedTypeComposite != null) {
			directlyDerivedTypeComposite.setFocus();
		}
	}

	@Override
	public void reloadType() {
		removeListenerFromAttributeDeclaration();
		changeEditingComposite(); // make sure we got the right editing composite for the reloaded type
		final AnyDerivedType type = getType().getType();
		if (type instanceof final StructuredType structType) {
			structComposite.setStructType(structType);
		}
		if (type instanceof final DirectlyDerivedType directlyDerivedType) {
			directlyDerivedTypeComposite.setDirectlyDerivedType(directlyDerivedType);
		}
		addListenerToAttributeDeclaration();
	}

	@Override
	public AttributeDeclaration getType() {
		return (AttributeDeclaration) super.getType();
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// currently we don't support markers
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// currently we don't have outline for data types so we don't need to do
		// anything
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return false;
	}

	@Override
	public Object getSelectableObject() {
		return null;
	}

}
