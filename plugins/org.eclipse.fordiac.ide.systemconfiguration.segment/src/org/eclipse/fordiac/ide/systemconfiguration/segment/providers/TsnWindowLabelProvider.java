package org.eclipse.fordiac.ide.systemconfiguration.segment.providers;

import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TsnWindowLabelProvider extends LabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof TsnWindow) {
			switch (columnIndex) {
			case 0:
				return ((TsnWindow) element).getName();
			case 1:
				return String.valueOf(((TsnWindow) element).getDuration());
			case 2:
				return "TIME"; ////$NON-NLS-1$
			case 3:
				return ((TsnWindow) element).getComment() != null ? ((TsnWindow) element).getComment() : ""; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}