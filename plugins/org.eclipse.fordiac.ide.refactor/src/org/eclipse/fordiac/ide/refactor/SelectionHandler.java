package org.eclipse.fordiac.ide.refactor;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class SelectionHandler {
	@Execute
    public void execute(Shell shell) {
        MessageDialog.openInformation(shell, "First", "Hello, e4 API world");
    }
}
