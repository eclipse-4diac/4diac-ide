/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Schwarz - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.previews;

import java.util.Objects;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.domain.ICompareEditingDomain;
import org.eclipse.emf.compare.domain.impl.EMFCompareEditingDomain;
import org.eclipse.emf.compare.ide.ui.internal.configuration.EMFCompareConfiguration;
import org.eclipse.emf.compare.ide.ui.internal.editor.ComparisonEditorInput;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.refactoring.InterfaceDataTypeChange;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

@SuppressWarnings("restriction")
public class InterfaceDataTypeChangePreviewViewer implements IChangePreviewViewer {

	private static Composite parent;
	private static Control previewControl;
	private EMFCompareConfiguration emfCompareConfiguration;
	private AdapterFactory adapterFactory;
	private EMFCompare emfCompare;

	@Override
	public void createControl(final Composite parent) {
		parent.setSize(500, 500);
		this.emfCompareConfiguration = new EMFCompareConfiguration(new CompareConfiguration());
		this.emfCompareConfiguration.setLeftEditable(false);
		this.emfCompareConfiguration.setLeftLabel("Before Refactor");
		this.emfCompareConfiguration.setRightEditable(false);
		this.emfCompareConfiguration.setRightLabel("After Refactor");

		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.emfCompare = EMFCompare.builder().build();
		if (Objects.nonNull(previewControl)) {
			previewControl.dispose();
			previewControl = null;
			InterfaceDataTypeChangePreviewViewer.parent.dispose();
		}
		InterfaceDataTypeChangePreviewViewer.parent = parent;

	}

	@Override
	public Control getControl() {
		return previewControl;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {
		if (!(input.getChange() instanceof InterfaceDataTypeChange)) {
			return;
		}

		final InterfaceDataTypeChange change = (InterfaceDataTypeChange) input.getChange();
		final TypeEntry oldTypeEntry = change.getOldTypeEntry();
		final FBType originalFbType = (FBType) change.getModifiedElement();
		final FBType refactoredFbType = EcoreUtil.copy(originalFbType);

		refactoredFbType.getInterfaceList().getInputs()
				.filter(var -> var.getTypeName().equals(oldTypeEntry.getTypeName())).forEach(var -> {
					var.setType(EcoreUtil.copy(var.getType()));
					var.getType().setName("TODO");
				});

		refactoredFbType.getInterfaceList().getOutputs()
				.filter(var -> var.getTypeName().equals(oldTypeEntry.getTypeName())).forEach(var -> {
					var.setType(EcoreUtil.copy(var.getType()));
					var.getType().setName("TODO");
				});

		final Comparison comparison = this.emfCompare
				.compare(new DefaultComparisonScope(originalFbType, refactoredFbType, null));

		final ICompareEditingDomain editingDomain = EMFCompareEditingDomain.create(originalFbType, refactoredFbType,
				null);

		final CompareEditorInput compareEditorInput = new ComparisonEditorInput(this.emfCompareConfiguration,
				comparison, editingDomain, this.adapterFactory);

		try {

			compareEditorInput.run(new NullProgressMonitor());
			compareEditorInput.setTitle("Refactor comparison");
			previewControl = compareEditorInput.createContents(parent);

		} catch (final Exception e) {
			System.out.println("Comparison failure");
			e.printStackTrace();
		}

	}

}
