/*******************************************************************************
 * Copyright (c) 2019, 2026 Profactor GbmH, Johannes Kepler University Linz,
 *                         HR Agrartechnik
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl - initial API and implementation and/or
 *   								  initial documentation
 *   Moritz Ortmeier - added page limit scaling, paper format and orientation
 *                     selection, printer based margins and robust handling of
 *                     missing printers
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.print;

import java.util.regex.Pattern;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrintFigureOperation;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A print preview Dialog where the user can specify some print options. Setting
 * / changing the properties of the printer lead to recalculating the "print"
 * area. Therefore, the preview should represent the output of the printer.
 */
public class PrintPreview extends Dialog {

	/**
	 * Standard paper formats the user can pick as the print's target page size,
	 * independent of the paper actually loaded in the physically connected
	 * printer.
	 */
	private enum PaperFormat {
		A5(148, 210), A4(210, 297), A3(297, 420), A2(420, 594), A1(594, 841), A0(841, 1189), LETTER(215.9, 279.4),
		LEGAL(215.9, 355.6), TABLOID(279.4, 431.8);

		private static final double MM_PER_INCH = 25.4;

		private final double widthMm;
		private final double heightMm;

		PaperFormat(final double widthMm, final double heightMm) {
			this.widthMm = widthMm;
			this.heightMm = heightMm;
		}

		double widthInch() {
			return widthMm / MM_PER_INCH;
		}

		double heightInch() {
			return heightMm / MM_PER_INCH;
		}
	}

	private static final String ONLY_DIGIT_REGEX = "^\\d*$"; //$NON-NLS-1$
	private static final Pattern ONLY_DIGIT_PATTERN = Pattern.compile(ONLY_DIGIT_REGEX, Pattern.MULTILINE);

	private static final int PAGE_LIMIT = 5;

	/**
	 * Tolerance subtracted before Math.ceil() when computing page counts, so that
	 * a ratio which is mathematically exactly 1.0 (e.g. content scaled to exactly
	 * fit one page) doesn't get pushed to 2 by floating-point rounding noise from
	 * the scale computation.
	 */
	private static final double PAGE_COUNT_EPSILON = 1e-6;

	/**
	 * The current page shown in the print preview. Always starting with 1.
	 */
	private int currentPage = 1;

	/**
	 * The number of pages that would be printed with the current settings.
	 */
	private int numberOfPages = 1;

	private Label numberOfPagesLabel;
	private Text currentPageText;

	private Button printBorder;

	private Combo scaleSelection;
	private Combo combo;
	private Combo orientationCombo;
	private Combo paperFormatCombo;
	private Text pageLimitText;
	private Text percentText;

	private boolean isLandscape = false;

	private PaperFormat paperFormat = PaperFormat.A4;

	private PrintMargin margin;

	private Printer printer;

	private Canvas canvas;

	private boolean blockCurrentPageUpdate = false;

	private final String printName;
	private final IFigure figure;

	/**
	 * Instantiates a new prints the preview.
	 *
	 * @param shell     the shell
	 * @param viewer    the viewer
	 * @param printName the print name
	 */
	public PrintPreview(final Shell shell, final GraphicalViewer viewer, final String printName) {
		super(shell);
		this.printName = printName;
		final LayerManager lm = (LayerManager) viewer.getEditPartForModel(LayerManager.ID);
		figure = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		newShell.setText(Messages.PrintPreview_LABEL_PrintPreview);
		super.configureShell(newShell);
	}

	@Override
	protected int getShellStyle() {
		return SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL;
	}

	@Override
	public boolean close() {
		if (printer != null && !printer.isDisposed()) {
			printer.dispose();
			printer = null;
		}
		return super.close();
	}

	/**
	 * Adds some GUI elements for defining some print options to the specified
	 * composite
	 *
	 * @param composite The container of the elements
	 */
	private void createOptionsGUI(final Composite parent) {
		final GridLayout layout = new GridLayout(8, false);
		layout.marginHeight = 0;
		parent.setLayout(layout);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Scale);
		scaleSelection = new Combo(parent, SWT.READ_ONLY);
		scaleSelection.add(Messages.PrintPreview_LABEL_Tile);
		scaleSelection.add(Messages.PrintPreview_LABEL_FitPage);
		scaleSelection.add(Messages.PrintPreview_LABEL_FitWidth);
		scaleSelection.add(Messages.PrintPreview_LABEL_FitHeight);
		scaleSelection.add(Messages.PrintPreview_LABEL_PageLimit);
		scaleSelection.select(0);

		pageLimitText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		pageLimitText.setText("1"); //$NON-NLS-1$
		pageLimitText.addListener(SWT.Verify, ev -> {
			if (!ev.doit) {
				return;
			}
			if (ev.keyCode == SWT.DEL || ev.keyCode == SWT.BS) {
				return;
			}
			if (ev.character == SWT.NULL) {
				ev.doit = true;
			} else {
				final String currentValue = ((Text) ev.widget).getText();
				final String resultingValue = currentValue.substring(0, ev.start) + ev.text + currentValue.substring(ev.end);
				ev.doit = ONLY_DIGIT_PATTERN.matcher(resultingValue).matches();
			}
		});
		pageLimitText.setEnabled(false);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Pages);

		percentText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		percentText.setText("100"); //$NON-NLS-1$
		percentText.addListener(SWT.Verify, ev -> {
			if (!ev.doit) {
				return;
			}
			if (ev.keyCode == SWT.DEL || ev.keyCode == SWT.BS) {
				return;
			}
			if (ev.character == SWT.NULL) {
				ev.doit = true;
			} else {
				final String currentValue = ((Text) ev.widget).getText();
				final String resultingValue = currentValue.substring(0, ev.start) + ev.text + currentValue.substring(ev.end);
				ev.doit = ONLY_DIGIT_PATTERN.matcher(resultingValue).matches();
			}
		});
		percentText.setEnabled(true);
		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Percent);
		final Button setPercentButton = new Button(parent, SWT.PUSH);
		setPercentButton.setText(Messages.PrintPreview_LABEL_Set);
		setPercentButton.setEnabled(true);
		setPercentButton.addListener(SWT.Selection, ev -> {
			updatePageNumbers();
			canvas.redraw();
		});

		scaleSelection.addListener(SWT.Selection, ev -> {
			pageLimitText.setEnabled(scaleSelection.getSelectionIndex() == PAGE_LIMIT - 1);
			percentText.setEnabled(true);
			setPercentButton.setEnabled(true);
			updatePageNumbers();
			canvas.redraw();
		});

		printBorder = new Button(parent, SWT.CHECK);
		printBorder.setText(Messages.PrintPreview_LABEL_PrintBorder);
		printBorder.setSelection(true);
		printBorder.addListener(SWT.Selection, _ -> canvas.redraw());

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Margin);
		combo = new Combo(parent, SWT.READ_ONLY);
		combo.add("0.5"); //$NON-NLS-1$
		combo.add("1.0"); //$NON-NLS-1$
		combo.add("1.5"); //$NON-NLS-1$
		combo.add("2.0"); //$NON-NLS-1$
		combo.add("2.5"); //$NON-NLS-1$
		combo.add("3.0"); //$NON-NLS-1$
		combo.select(1);
		combo.addListener(SWT.Selection, _ -> {
			final double value = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
			// calculate from cm to inches
			setPrinter(printer, value / 2.54);
		});
		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_CM);

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_Orientation);
		orientationCombo = new Combo(parent, SWT.READ_ONLY);
		orientationCombo.add(Messages.PrintPreview_LABEL_Portrait);
		orientationCombo.add(Messages.PrintPreview_LABEL_Landscape);
		orientationCombo.select(0);
		orientationCombo.addListener(SWT.Selection, ev -> {
			isLandscape = orientationCombo.getSelectionIndex() == 1;
			final double marginValue = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
			setPrinter(printer, marginValue / 2.54);
		});

		new Label(parent, SWT.NULL).setText(Messages.PrintPreview_LABEL_PaperFormat);
		paperFormatCombo = new Combo(parent, SWT.READ_ONLY);
		for (final PaperFormat format : PaperFormat.values()) {
			paperFormatCombo.add(format.name());
		}
		paperFormatCombo.select(paperFormat.ordinal());
		paperFormatCombo.addListener(SWT.Selection, ev -> {
			paperFormat = PaperFormat.values()[paperFormatCombo.getSelectionIndex()];
			final double marginValue = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
			setPrinter(printer, marginValue / 2.54);
		});
	}

	/**
	 * Checks which print option (Tile, Fit Page, ...) is selected.
	 *
	 * @return the PrintFigureOperation
	 */
	private int getOptionsSelection() {
		return scaleSelection.getSelectionIndex() + 1;
	}

	/**
	 * Returns the effective page bounds for the selected paper format, swapping
	 * width/height when landscape orientation is selected.
	 */
	private Rectangle getEffectivePrinterBounds() {
		final Point dpi = (printer != null && !printer.isDisposed()) ? printer.getDPI() : Display.getCurrent().getDPI();
		Rectangle bounds = new Rectangle(0, 0, (int) (paperFormat.widthInch() * dpi.x),
				(int) (paperFormat.heightInch() * dpi.y));
		if (isLandscape) {
			bounds = new Rectangle(bounds.y, bounds.x, bounds.height, bounds.width);
		}
		return bounds;
	}

	@Override
	protected Control createContents(final Composite parent) {
		parent.setSize(800, 600);

		final Composite composite = (Composite) super.createDialogArea(parent);
		createButtonArea(composite);

		final Label seperator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		seperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		/* the preview */
		canvas = new Canvas(composite, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 4;
		canvas.setLayoutData(gridData);

		canvas.addPaintListener(e -> {
			final Rectangle printerBounds = getEffectivePrinterBounds();
			final Point canvasSize = canvas.getSize();

			double viewScaleFactor = canvasSize.x * 1.0 / printerBounds.width;
			viewScaleFactor = Math.min(viewScaleFactor, canvasSize.y * 1.0 / printerBounds.height);

			final int offsetX = (canvasSize.x - (int) (viewScaleFactor * printerBounds.width)) / 2;
			final int offsetY = (canvasSize.y - (int) (viewScaleFactor * printerBounds.height)) / 2;

			e.gc.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			// draws the page layout
			e.gc.fillRectangle(offsetX, offsetY, (int) (viewScaleFactor * printerBounds.width),
					(int) (viewScaleFactor * printerBounds.height));

			final int marginOffsetX = offsetX + (int) (viewScaleFactor * margin.getLeft());
			final int marginOffsetY = offsetY + (int) (viewScaleFactor * margin.getTop());

			final double scale = getScale();
			final double previewScaleFactor = viewScaleFactor * scale;

			final Graphics g = new SWTGraphics(e.gc);
			g.scale(previewScaleFactor);
			g.translate((int) (marginOffsetX / previewScaleFactor), (int) (marginOffsetY / previewScaleFactor));

			drawOnePage(scale, g, currentPage);

			g.dispose();

		});
		final double value = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
		setPrinter(null, value / 2.54); // calculate from cm to inches

		return composite;
	}

	private org.eclipse.draw2d.geometry.Point getClipRectLocationForPage(int page, final double scale) {
		final org.eclipse.draw2d.geometry.Rectangle bounds = getPrintArea();
		final double scaledPageWidth = margin.getWidth() / scale;
		final double scaledPageHeight = margin.getHeight() / scale;
		page -= 1;

		final int cols = (int) Math.ceil((bounds.width()) / scaledPageWidth);
		final int currentColumn = page % cols;
		final int currentRow = page / cols;
		return new org.eclipse.draw2d.geometry.Point((int) (bounds.x + currentColumn * scaledPageWidth),
				(int) (bounds.y + currentRow * scaledPageHeight));
	}

	/**
	 * Returns the area of the printed content, i.e. the bounds of the printable
	 * layers.
	 */
	private org.eclipse.draw2d.geometry.Rectangle getPrintArea() {
		return figure.getBounds().getCopy();
	}

	private void updatePageNumbers() {

		final org.eclipse.draw2d.geometry.Rectangle rectangle = getPrintArea();

		final double scale = getScale();
		numberOfPages = (int) (Math.ceil((rectangle.preciseWidth() * scale) / margin.getWidth() - PAGE_COUNT_EPSILON)
				* Math.ceil((rectangle.preciseHeight() * scale) / margin.getHeight() - PAGE_COUNT_EPSILON));
		numberOfPagesLabel.setText(String.valueOf(numberOfPages));
		if (currentPage > numberOfPages) {
			setCurrentPage(numberOfPages);
		}
	}

	private double getScale() {
		final org.eclipse.draw2d.geometry.Rectangle printArea = getPrintArea();
		final Point displayDpi = Display.getCurrent().getDPI();
		final Point printerDpi = (printer != null && !printer.isDisposed()) ? printer.getDPI() : displayDpi;
		double scale = printerDpi.x * 1.0 / displayDpi.x * 1.0;

		switch (getOptionsSelection()) {
		case PrintFigureOperation.FIT_PAGE:
			scale *= Math.min(margin.getWidth() / (scale * printArea.width),
					margin.getHeight() / (scale * printArea.height));
			break;
		case PrintFigureOperation.FIT_WIDTH:
			scale *= (margin.getWidth() / (scale * printArea.width));
			break;
		case PrintFigureOperation.FIT_HEIGHT:
			scale *= (margin.getHeight() / (scale * printArea.height));
			break;
		case PAGE_LIMIT:
			final double naturalScale = scale; // scale at 100%, i.e. true/unscaled print size
			int limit = 1;
			try {
				limit = Integer.parseInt(pageLimitText.getText());
			} catch (final NumberFormatException e) {
				// fallback to 1
			}
			int percent = 100;
			try {
				percent = Integer.parseInt(percentText.getText());
			} catch (final NumberFormatException e) {
				// fallback to 100
			}
			final double maxFitScale = computePageLimitScale(limit, printArea.width, printArea.height,
					margin.getWidth(), margin.getHeight());
			scale = Math.min(naturalScale * (percent / 100.0), maxFitScale);

			// keep the percent field in sync when the requested scale had to be reduced
			// to honor the page limit (e.g. going back to 100% would need more pages)
			final int effectivePercent = (int) Math.round((scale / naturalScale) * 100.0);
			if (effectivePercent != percent && percentText != null && !percentText.isDisposed()) {
				percentText.setText(String.valueOf(effectivePercent));
			}
			break;
		case PrintFigureOperation.TILE: // when tile is selected we keep the default printer scale factor
		default:
			break;
		}

		return scale;
	}

	private double computePageLimitScale(final int pageLimit, final double figW, final double figH,
			final double marginW, final double marginH) {
		final int limit = Math.max(1, pageLimit);
		final Point displayDpi = Display.getCurrent().getDPI();
		final Point printerDpi = (printer != null && !printer.isDisposed()) ? printer.getDPI() : displayDpi;
		// Device DPI ratio: converts a length in display pixels to the equivalent
		// physical length in printer device units. Capping at this value (rather
		// than just 1.0) is what keeps small content at true 1:1 physical size on
		// paper instead of letting it print oversized just because a page limit
		// allows more scale-up room.
		final double base = printerDpi.x * 1.0 / displayDpi.x * 1.0;
		final double areaFit = Math.sqrt(limit * marginW * marginH / (figW * figH));
		double scale = Math.min(areaFit, base);
		final double step = 0.01;
		while ((Math.ceil(figW * scale / marginW - PAGE_COUNT_EPSILON)
				* Math.ceil(figH * scale / marginH - PAGE_COUNT_EPSILON)) > limit && scale > step) {
			scale -= step;
		}
		return Math.max(scale, step);
	}

	private void createButtonArea(final Composite parent) {
		final Composite buttonArea = new Composite(parent, SWT.NONE);
		final GridLayout buttonAreaLayout = new GridLayout(7, false);
		buttonAreaLayout.marginHeight = 0;
		buttonArea.setLayout(buttonAreaLayout);
		final GridData buttonLayoutData = new GridData();
		buttonLayoutData.horizontalAlignment = SWT.FILL; /* grow to fill available width */
		buttonArea.setLayoutData(buttonLayoutData);

		final Button buttonPrint = new Button(buttonArea, SWT.PUSH);
		buttonPrint.setText(Messages.PrintPreview_LABEL_Print);
		buttonPrint.addListener(SWT.Selection, _ -> performPrinting());

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_VERTICAL));
		createPageNavigation(new Composite(buttonArea, SWT.NONE));

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_VERTICAL));
		createOptionsGUI(new Composite(buttonArea, SWT.NONE));

		new Label(buttonArea, SWT.SEPARATOR | SWT.VERTICAL).setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite closeArea = new Composite(buttonArea, SWT.NONE);
		final GridLayout closeAreaLayout = new GridLayout(2, false);
		closeAreaLayout.marginHeight = 0;
		closeAreaLayout.marginWidth = 0;
		closeArea.setLayout(closeAreaLayout);

		closeArea.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(closeArea, SWT.NONE).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button closeButton = new Button(closeArea, SWT.PUSH);
		closeButton.setText(Messages.PrintPreview_LABEL_Close);
		closeButton.addListener(SWT.Selection, _ -> close());
		closeButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
	}

	private void createPageNavigation(final Composite parent) {
		final GridLayout layout = new GridLayout(6, false);
		layout.marginHeight = 0;
		parent.setLayout(layout);
		final Label pageLabel = new Label(parent, SWT.NONE);
		pageLabel.setText(Messages.PrintPreview_LABEL_Page);

		final Button left = new Button(parent, SWT.ARROW | SWT.LEFT);
		left.addListener(SWT.Selection, _ -> {
			if (currentPage > 1) {
				setCurrentPage(currentPage - 1);
			}
		});

		currentPageText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		final GC gc = new GC(currentPageText);
		final FontMetrics fm = gc.getFontMetrics();
		final int width = (int) (3 * fm.getAverageCharacterWidth());
		final int height = fm.getHeight();
		gc.dispose();
		currentPageText.setSize(currentPageText.computeSize(width, height));
		currentPageText.setText(String.valueOf(currentPage));
		currentPageText.addListener(SWT.Modify, _ -> {
			try {
				final int newCurrentPage = Integer.parseInt(currentPageText.getText());
				if (0 < newCurrentPage && newCurrentPage <= numberOfPages) {
					setCurrentPage(newCurrentPage);
				}
			} catch (final Exception e) {
				// as we have a verify listener we should never be here, still just ignore it
			}
		});
		currentPageText.addListener(SWT.Verify, this::pageNumberVerifier);

		final Label of = new Label(parent, SWT.NONE);
		of.setText(Messages.PrintPreview_LABEL_Of);

		numberOfPagesLabel = new Label(parent, SWT.NONE);
		numberOfPagesLabel.setText(String.valueOf(numberOfPages));

		final Button right = new Button(parent, SWT.ARROW | SWT.RIGHT);
		right.addListener(SWT.Selection, _ -> {
			if (currentPage < numberOfPages) {
				setCurrentPage(currentPage + 1);
			}
		});

	}

	private void pageNumberVerifier(final Event ev) {
		if (!ev.doit) {
			// other verifylisteners which are first can already set it
			return;
		}

		if (ev.keyCode == SWT.DEL || ev.keyCode == SWT.BS) {
			return;
		}

		if (ev.character == SWT.NULL) {
			ev.doit = true;
		} else {
			final String currentValue = ((Text) ev.widget).getText();
			String resultingValue = currentValue.substring(0, ev.start) + ev.text + currentValue.substring(ev.end);
			if (resultingValue.isEmpty()) {
				resultingValue = String.valueOf(ev.character);
			}
			ev.doit = ONLY_DIGIT_PATTERN.matcher(resultingValue).matches();
			// TODO consider if we should allow only numbers in the valid range
		}
	}

	private void setCurrentPage(final int newCurrentPage) {
		if (!blockCurrentPageUpdate) {
			blockCurrentPageUpdate = true;
			currentPage = newCurrentPage;
			currentPageText.setText(String.valueOf(currentPage));
			canvas.redraw();
			blockCurrentPageUpdate = false;
		}
	}

	private void performPrinting() {
		PrinterData printerData = null;
		try {
			final PrintDialog dialog = new PrintDialog(getShell());
			// Prompts the printer dialog to let the user select a printer.
			printerData = dialog.open();
		} catch (final Throwable e) {
			FordiacLogHelper.logError(Messages.PrintPreview_ERROR_StartingPrintJob, e);
			return;
		}

		if (printerData == null) {
			return;
		}
		// Apply the landscape/portrait orientation chosen in the preview
		if (isLandscape) {
			printerData.orientation = PrinterData.LANDSCAPE;
		} else {
			printerData.orientation = PrinterData.PORTRAIT;
		}
		// Loads the printer.
		Printer newPrinter = null;
		try {
			newPrinter = new Printer(printerData);
		} catch (final Throwable e) {
			FordiacLogHelper.logError(Messages.PrintPreview_ERROR_StartingPrintJob, e);
			return;
		}
		final double value = Double.parseDouble(combo.getItem(combo.getSelectionIndex()));
		// calculate from cm to inches
		setPrinter(newPrinter, value / 2.54);
		// print the document
		print(newPrinter);
		if (printer != null && !printer.isDisposed()) {
			printer.dispose();
			printer = null;
		}
		close();
	}

	/**
	 * Prints the figure current displayed to the specified printer.
	 *
	 * @param printer the printer
	 */
	void print(final Printer printer) {

		if (!printer.startJob(printName)) {
			FordiacLogHelper.logError(Messages.PrintPreview_ERROR_StartingPrintJob);
			return;
		}

		final GC gc = new GC(printer);
		final SWTGraphics g = new SWTGraphics(gc);
		final PrinterGraphics graphics = new PrinterGraphics(g, printer);

		graphics.setForegroundColor(figure.getForegroundColor());
		graphics.setBackgroundColor(figure.getBackgroundColor());
		graphics.setFont(figure.getFont());

		final double scale = getScale();

		graphics.scale(scale);
		graphics.translate((int) (margin.getLeft() / scale), (int) (margin.getTop() / scale));

		for (int i = 1; i <= numberOfPages; i++) {
			if (!printer.startPage()) {
				FordiacLogHelper.logError(Messages.PrintPreview_ERROR_StartingNewPage);
				return;
			}
			graphics.pushState();

			drawOnePage(scale, graphics, i);

			printer.endPage();
			graphics.popState();
		}

		printer.endJob();

		gc.dispose();
	}

	/**
	 * Sets target printer.
	 *
	 * @param newPrinter the printer
	 * @param marginSize the margin size
	 */
	void setPrinter(Printer newPrinter, final double marginSize) {
		if (newPrinter == null) {
			try {
				final PrinterData defaultPrinterData = Printer.getDefaultPrinterData();
				if (defaultPrinterData != null) {
					newPrinter = new Printer(defaultPrinterData);
				}
			} catch (final Throwable e) {
				FordiacLogHelper.logError("Could not initialize default printer", e); //$NON-NLS-1$
			}
		}
		if (null != printer && !printer.isDisposed()) {
			printer.dispose();
		}

		printer = newPrinter;
		margin = PrintMargin.getPrintMargin(newPrinter, marginSize, isLandscape, paperFormat.widthInch(),
				paperFormat.heightInch());
		updatePageNumbers();
		if (canvas != null && !canvas.isDisposed()) {
			canvas.redraw();
		}
	}

	private void drawOnePage(final double scale, final Graphics g, final int pageNumber) {
		final org.eclipse.draw2d.geometry.Point p = getClipRectLocationForPage(pageNumber, scale);
		final org.eclipse.draw2d.geometry.Rectangle clipRect = new org.eclipse.draw2d.geometry.Rectangle(p.x, p.y,
				(int) (margin.getWidth() / scale), (int) (margin.getHeight() / scale));

		g.translate(-p.x, -p.y);
		g.setLineStyle(Graphics.LINE_DASH);
		g.setForegroundColor(ColorConstants.black);
		if (printBorder.getSelection()) {
			g.drawRectangle(clipRect);
		}

		g.setLineStyle(Graphics.LINE_SOLID);
		g.clipRect(clipRect);
		figure.paint(g);
	}

}

/**
 * Contains margin information (in pixels) for a print job.
 *
 */
class PrintMargin {
	// Margin to the left side, in pixels
	private final int left;

	// Margins to the right side, in pixels
	private final int right;

	// Margins to the top side, in pixels
	private final int top;

	// Margins to the bottom side, in pixels
	private final int bottom;

	private PrintMargin(final int left, final int right, final int top, final int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}

	int getWidth() {
		return right - left;
	}

	int getHeight() {
		return bottom - top;
	}

	/**
	 * Returns a PrintMargin object containing the true border margins for the
	 * specified printer, page size and given margin in inches. Note: all four
	 * sides share the same margin width.
	 *
	 * @param printer         the printer whose DPI/trim to use, or {@code null}
	 *                        for the display default
	 * @param margin          the margin width, in inches
	 * @param landscape       whether to swap the page's width/height
	 * @param paperWidthInch  the target page width, in inches
	 * @param paperHeightInch the target page height, in inches
	 * @return
	 */
	static PrintMargin getPrintMargin(final Printer printer, final double margin, final boolean landscape,
			final double paperWidthInch, final double paperHeightInch) {
		return getPrintMargin(printer, margin, margin, margin, margin, landscape, paperWidthInch, paperHeightInch);
	}

	/**
	 * Returns a PrintMargin object containing the true border margins for the
	 * specified printer and page size, with the given margin width (in inches)
	 * for each side, optionally swapping dimensions for landscape orientation.
	 * <p>
	 * Insets the requested margin directly from {@link Printer#getClientArea()}
	 * - the printer's own authoritative report of its real usable/printable
	 * area - instead of reconstructing that area from
	 * {@link Printer#computeTrim(int, int, int, int)} plus an assumed paper
	 * size. The previous computeTrim()-based reconstruction assumed a
	 * left/right (and top/bottom) trim relationship that does not hold on every
	 * platform: on Linux/GTK it produced a margin box that could extend past
	 * the printer's real client area on one side (observed: margin width wider
	 * than {@code getClientArea().width} on a "Print to File" PDF destination),
	 * which is exactly what would cut content off despite Fit Page appearing to
	 * fit it. getClientArea() already reflects the orientation set on the
	 * printer, so no manual landscape swap is needed for a real printer.
	 */
	static PrintMargin getPrintMargin(final Printer printer, final double marginLeft, final double marginRight,
			final double marginTop, final double marginBottom, final boolean landscape, final double paperWidthInch,
			final double paperHeightInch) {
		final Point dpi = (printer != null && !printer.isDisposed()) ? printer.getDPI() : Display.getCurrent().getDPI();
		final Rectangle printable;
		if (printer != null && !printer.isDisposed()) {
			printable = printer.getClientArea();
		} else {
			final Rectangle full = new Rectangle(0, 0, (int) (paperWidthInch * dpi.x), (int) (paperHeightInch * dpi.y));
			printable = landscape ? new Rectangle(full.y, full.x, full.height, full.width) : full;
		}

		final int leftMargin = printable.x + (int) (marginLeft * dpi.x);
		final int rightMargin = printable.x + printable.width - (int) (marginRight * dpi.x);
		final int topMargin = printable.y + (int) (marginTop * dpi.y);
		final int bottomMargin = printable.y + printable.height - (int) (marginBottom * dpi.y);

		return new PrintMargin(leftMargin, rightMargin, topMargin, bottomMargin);
	}

	@Override
	public String toString() {
		return "Margin { " + left + ", " + right + "; " + top + ", " + bottom //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ " }"; //$NON-NLS-1$
	}
}
