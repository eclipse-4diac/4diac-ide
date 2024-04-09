package org.eclipse.fordiac.ide.typemanagement.previews;

import java.lang.reflect.InvocationTargetException;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.fordiac.ide.typemanagement.PreviewCommand;
import org.eclipse.fordiac.ide.typemanagement.refactoring.StructuredTypeMemberChange;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class StructChangePreviewViewer implements IChangePreviewViewer {

	private Composite parent;
	private Control previewControl;

	@Override
	public void createControl(final Composite parent) {
		parent.setSize(500, 500);
		this.parent = parent;
	}

	@Override
	public Control getControl() {
		return previewControl;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {

		// There is a with EMF compare where it will not show struct member changes!

		System.out.println("===>> change: " + input.getChange());
		if (!(input.getChange() instanceof StructuredTypeMemberChange)) {
			return;
		}

		final StructuredTypeMemberChange change = (StructuredTypeMemberChange) input.getChange();
		final EObject model1 = change.getModifiedElement();
		final PreviewCommand previewCommand = new PreviewCommand(change.getRefactoringCommand(), model1);
		change.getRefactoringCommand().execute();
		final EObject model2 = change.getModifiedElement().getTypeEntry().getTypeEditable();

		// final EObject model2 = previewCommand.getRootCopy();
		// final EObject model2 = change.getModifiedElement();

		System.out.println("===>> model1: " + model1);
		System.out.println("===>> model2: " + model2);
		final EMFCompare compare = EMFCompare.builder().build();
		final Comparison comparison = compare.compare(EMFCompare.createDefaultScope(model2, model1));
		final AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final ICompareEditingDomain editingDomain = EMFCompareEditingDomain.create(model2, model1, null);
		final CompareEditorInput compareEditorInput = new ComparisonEditorInput(
				new EMFCompareConfiguration(new CompareConfiguration()), comparison, editingDomain, adapterFactory);
		try {
			compareEditorInput.run(new NullProgressMonitor());
			previewControl = compareEditorInput.createContents(parent);
			previewControl.setLayoutData(new FillLayout());
		} catch (final InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}

	}

}
