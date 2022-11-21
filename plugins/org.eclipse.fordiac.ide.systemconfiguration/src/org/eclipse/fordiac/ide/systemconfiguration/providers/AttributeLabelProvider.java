package org.eclipse.fordiac.ide.systemconfiguration.providers;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof Attribute) {
			switch (columnIndex) {
			case 0:
				return ((Attribute) element).getName();
			case 1:
				return ((Attribute) element).getType().getName();
			case 2:
				return ((Attribute) element).getValue();
			case 3:
				return ((Attribute) element).getComment() != null ? ((Attribute) element).getComment() : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}