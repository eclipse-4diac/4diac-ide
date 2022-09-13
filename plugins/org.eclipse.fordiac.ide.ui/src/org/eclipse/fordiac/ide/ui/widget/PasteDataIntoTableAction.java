package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Display;

public class PasteDataIntoTableAction implements IKeyAction {

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		final Clipboard clipboard = new Clipboard(Display.getCurrent());
		final Object clipboardContents = clipboard.getContents(TextTransfer.getInstance());
		clipboard.dispose();
		final String[] content = parseContent(clipboardContents);

		final SelectionLayer selectionLayer = (SelectionLayer) natTable.getLayer().getUnderlyingLayerByPosition(0, 1)
				.getUnderlyingLayerByPosition(0, 0);

		int i = 0;
		for (final ILayerCell c : selectionLayer.getSelectedCells()) {
			if (i < content.length) {
				selectionLayer.doCommand(
						new UpdateDataCommand(selectionLayer, c.getColumnIndex(), c.getRowIndex(), content[i++]));
			}
		}
	}

	private static String[] parseContent(final Object contents) {
		final List<String[]> content = new ArrayList<>();
		((String) contents).lines().forEach(s -> {
			final String[] st = s.split("\t", -1); //$NON-NLS-1$
			content.add(st);
		});

		final List<String> c = new ArrayList<>();
		for (int i = 0; i < content.get(0).length; i++) {
			for (final String[] str : content) {
				// if (!str[i].isEmpty()) {
				c.add(str[i]);
				// }
			}
		}
		return c.toArray(new String[0]);
	}
}