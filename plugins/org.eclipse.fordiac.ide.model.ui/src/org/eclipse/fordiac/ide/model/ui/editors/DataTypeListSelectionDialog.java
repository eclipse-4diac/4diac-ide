package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class DataTypeListSelectionDialog extends ElementListSelectionDialog {

	public DataTypeListSelectionDialog(final Shell parent, final boolean useFQN) {
		this(parent, new ListLabelProvider(useFQN));
	}

	public DataTypeListSelectionDialog(final Shell parent, final ILabelProvider renderer) {
		super(parent, renderer);
	}

	public static class ListLabelProvider extends LabelProvider {
		private final boolean useFQN;

		public ListLabelProvider(final boolean useFQN) {
			this.useFQN = useFQN;
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final LibraryElement libElement) {
				return useFQN ? libElement.getTypeEntry().getFullTypeName() : libElement.getTypeEntry().getTypeName();
			}
			if (element instanceof final TypeEntry typeEntry) {
				return useFQN ? typeEntry.getFullTypeName() : typeEntry.getTypeName();
			}
			return super.getText(element);
		}
	}
}
