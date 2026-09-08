/*******************************************************************************
 * Copyright (c) 2018, 2026 Laurent Caron, Sebastian Hollersbacher
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * W.P. van Paassen - Original Version
 * Laurent CARON (laurent.caron at gmail dot com) - Conversion to SWT
 * Sebastian Hollersbacher - Draw2D integration
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import java.util.Random;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ExplosionFigure extends Figure {

	private static final int TIMER_INTERVAL = 20;
	private static final int NUMBER_OF_PARTICLES = 200;

	private final Display display;
	private final Runnable frameRunnable = this::onFrame;
	private final Random rng = new Random();

	private int w;
	private int h;
	private int[] fire;
	private Particle[] particles;
	private ImageData imageData;
	private Runnable onComplete;

	public ExplosionFigure(final Display display) {
		this.display = display;
		setOpaque(false);
	}

	public void start(final Runnable completionCallback) {
		this.onComplete = completionCallback;
		init();
		display.timerExec(TIMER_INTERVAL, frameRunnable);
	}

	private void onFrame() {
		if (display.isDisposed() || getParent() == null) {
			return;
		}
		if (animate()) {
			if (onComplete != null) {
				onComplete.run();
			}
		} else {
			repaint();
			display.timerExec(TIMER_INTERVAL, frameRunnable);
		}
	}

	private void init() {
		final Rectangle b = getBounds();
		w = Math.max(b.width, 4);
		h = Math.max(b.height, 4);

		fire = new int[w * h];
		particles = new Particle[NUMBER_OF_PARTICLES];

		/* create a suitable shadebob palette, this is crucial for a good effect */
		/* black to blue, blue to red, red to white */
		final RGB[] colors = new RGB[256];
		for (int i = 0; i < 32; ++i) {
			/* black to blue, 32 values */
			colors[i] = new RGB(0, 0, i << 1);

			/* blue to red, 32 values */
			colors[i + 32] = new RGB(i << 3, 0, 64 - (i << 1));

			/* red to yellow, 32 values */
			colors[i + 64] = new RGB(255, i << 3, 0);

			/* yellow to white, 162 */
			colors[i + 96] = new RGB(255, 255, i << 2);
			colors[i + 128] = new RGB(255, 255, 64 + (i << 2));
			colors[i + 160] = new RGB(255, 255, 128 + (i << 2));
			colors[i + 192] = new RGB(255, 255, 192 + i);
			colors[i + 224] = new RGB(255, 255, 224 + i);
		}

		imageData = new ImageData(w, h, 8, new PaletteData(colors));
		imageData.transparentPixel = 0;

		initParticles(true);
	}

	private void initParticles(final boolean create) {
		for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
			if (create) {
				particles[i] = new Particle();
			}

			particles[i].xpos = (w >> 1) - 20 + rng.nextInt(40);
			particles[i].ypos = (h >> 1) - 20 + rng.nextInt(40);
			particles[i].xdir = -10 + rng.nextInt(20);
			particles[i].ydir = -17 + rng.nextInt(19);
			particles[i].colorindex = 255;
			particles[i].dead = false;
		}
	}

	private boolean animate() {
		/* move and draw particles into fire array */
		final int nbDead = updateParticles();

		/* create fire effect */
		for (int i = 1; i < h - 2; i++) {
			final int index = (i - 1) * w;
			for (int j = 1; j < w - 2; j++) {
				int buf = index + j;

				int temp = fire[buf];
				temp += fire[buf + 1];
				temp += fire[buf - 1];
				buf += w;
				temp += fire[buf - 1];
				temp += fire[buf + 1];
				buf += w;
				temp += fire[buf];
				temp += fire[buf + 1];
				temp += fire[buf - 1];

				temp >>= 3;

				if (temp > 4) {
					temp -= 4;
				} else {
					temp = 0;
				}

				fire[buf - w] = temp;
			}
		}

		/* draw fire array to image */
		for (int y = 0; y < h; y++) {
			final int rowStart = y * w;
			for (int x = 0; x < w; x++) {
				imageData.setPixel(x, y, fire[rowStart + x]);
			}
		}

		return nbDead == NUMBER_OF_PARTICLES;
	}

	private int updateParticles() {
		int nbDead = 0;

		for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
			if (!particles[i].dead) {
				particles[i].xpos += particles[i].xdir;
				particles[i].ypos += particles[i].ydir;

				/* is particle dead? */
				if (particles[i].ypos >= h - 3 || particles[i].ypos <= 1 || particles[i].colorindex == 0
						|| particles[i].xpos <= 1 || particles[i].xpos >= w - 3) {
					particles[i].dead = true;
					continue;
				}

				/* gravity takes over */
				particles[i].ydir++;

				/* particle cools off */
				particles[i].colorindex--;

				/* draw particle */
				final int pos = particles[i].ypos * w + particles[i].xpos;
				fire[pos] = particles[i].colorindex;
				fire[pos - 1] = particles[i].colorindex;
				fire[pos + 1] = particles[i].colorindex;
				fire[pos + w] = particles[i].colorindex;
				fire[pos - w] = particles[i].colorindex;
			} else {
				nbDead++;
			}
		}
		return nbDead;
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		if (imageData == null) {
			return;
		}
		final Image image = new Image(display, imageData);
		try {
			graphics.drawImage(image, getBounds().x(), getBounds().y());
		} finally {
			image.dispose();
		}
	}

	@Override
	public void removeNotify() {
		fire = null;
		particles = null;
		imageData = null;
		super.removeNotify();
	}

	private class Particle {
		int xpos;
		int ypos;
		int xdir;
		int ydir;
		int colorindex;
		boolean dead;
	}
}