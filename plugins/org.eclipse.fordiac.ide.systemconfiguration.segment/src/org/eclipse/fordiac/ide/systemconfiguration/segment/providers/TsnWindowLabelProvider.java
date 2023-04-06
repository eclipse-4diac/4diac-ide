package org.eclipse.fordiac.ide.systemconfiguration.segment.providers;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
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
		if (element instanceof final TsnWindow window) {
			switch (columnIndex) {
			case 0:
				if (!(window.eContainer() instanceof TsnConfiguration)) {
					break;
				}
				return "TsnWindow" + ((TsnConfiguration) window.eContainer()).getWindows().indexOf(window); //$NON-NLS-1$
			case 1:
				return MessageFormat.format("TIME#{0}ms", String.valueOf(window.getDuration())); //$NON-NLS-1$
			case 2:
				return "TIME"; ////$NON-NLS-1$
			case 3:
				return ((TsnWindow) element).getComment() != null ? ((TsnWindow) element).getComment() : "empty"; //$NON-NLS-1$
			default:
				break;
			}
		}
		return element.toString();
	}
}