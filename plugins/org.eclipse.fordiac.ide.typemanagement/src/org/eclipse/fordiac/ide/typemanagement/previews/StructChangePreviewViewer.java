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
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.typemanagement.refactoring.StructuredTypeMemberChange;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

@SuppressWarnings("restriction")
public class StructChangePreviewViewer implements IChangePreviewViewer {

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
			StructChangePreviewViewer.parent.dispose();
		}
		StructChangePreviewViewer.parent = parent;

	}

	@Override
	public Control getControl() {
		return previewControl;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {
		if (!(input.getChange() instanceof StructuredTypeMemberChange)) {
			return;
		}

		final StructuredTypeMemberChange change = (StructuredTypeMemberChange) input.getChange();
		final StructuredType originalStructuredType = change.getModifiedElement();
		final StructuredType refactoredStructuredType = EcoreUtil.copy(originalStructuredType);

		refactoredStructuredType.getMemberVariables().stream()
				.filter(var -> var.getType().getTypeEntry() == change.getModifiedTypeEntry()).forEach(var -> {
					var.setType(EcoreUtil.copy(var.getType()));
					var.getType().setName(change.getNewTypeEntryName());
				});

		final Comparison comparison = this.emfCompare
				.compare(new DefaultComparisonScope(originalStructuredType, refactoredStructuredType, null));

		final ICompareEditingDomain editingDomain = EMFCompareEditingDomain.create(originalStructuredType,
				refactoredStructuredType, null);

		final CompareEditorInput compareEditorInput = new ComparisonEditorInput(this.emfCompareConfiguration,
				comparison, editingDomain, this.adapterFactory);

		try {

			System.out.println("Checking...");

			final IHandlerService service = PlatformUI.getWorkbench().getService(IHandlerService.class);

			if (Objects.nonNull(compareEditorInput.getActionBars())) {
				// This is an ugly workaround for a bug in EMF compare that will register
				// certain actions twice. See bug 580988
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=580988
				final IToolBarManager toolBarManager = compareEditorInput.getActionBars().getToolBarManager();
				final IContributionItem[] items = toolBarManager.getItems();
				for (final IContributionItem iContributionItem : items) {
					if (iContributionItem instanceof ActionContributionItem) {
						final IAction action = ((ActionContributionItem) iContributionItem).getAction();
						final String id = action.getActionDefinitionId();
						System.out.println("Action");
						System.out.println(id);
						if ("org.eclipse.compare.copyAllLeftToRight".equals(id)) {
							toolBarManager.remove(iContributionItem);
						} else if ("org.eclipse.compare.copyAllRightToLeft".equals(id)) {
							toolBarManager.remove(iContributionItem);
						}
					}
				}
			}

			compareEditorInput.run(new NullProgressMonitor());
			compareEditorInput.setTitle("Refactor comparison");

			previewControl = compareEditorInput.createContents(parent);

		} catch (final Exception e) {
			System.out.println("Comparison failure");
			e.printStackTrace();
		}

	}

}
