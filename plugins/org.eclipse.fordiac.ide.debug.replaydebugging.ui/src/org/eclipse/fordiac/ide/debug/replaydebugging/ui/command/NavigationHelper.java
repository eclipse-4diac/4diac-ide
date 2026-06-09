/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineModel;

public class NavigationHelper {
	private NavigationHelper() {
		// this class should not be instantiated
	}

	public static ReplayNavigator.EventPosition findNextTimelineWithValidEventNumber(
			final ReplayNavigator replayNavigator, final Timeline currentTimeline, final int eventNumber,
			final boolean reversed) {

		final List<Timeline> timelines = new ArrayList<>();

		// Collect all Timelines inside this Resource
		collectTimelinesBFS(replayNavigator.getRootTimeline(), timelines);

		if (reversed) {
			Collections.reverse(timelines);
		}

		// Find index of current timeline
		int currentIndex = -1;
		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i) == currentTimeline) {
				currentIndex = i;
				break;
			}
		}

		if (currentIndex == -1) {
			return null; // current timeline not found
		}

		final int currentGlobalEventNumber = currentTimeline.getGlobalIndexStart() + eventNumber;

		// Continue searching AFTER the current one
		for (int i = currentIndex + 1; i < timelines.size(); i++) {

			final Timeline timeline = timelines.get(i);

			final int start = timeline.getGlobalIndexStart();
			final int end = timeline.getGlobalIndexEnd();

			if (currentGlobalEventNumber >= start && currentGlobalEventNumber <= end) {
				return new ReplayNavigator.EventPosition(timeline, currentGlobalEventNumber - start);
			}
		}

		return null;
	}

	private static void collectTimelinesBFS(final Timeline timeline, final List<Timeline> result) {

		result.add(timeline);

		final var spawnedTimelines = new ArrayList<>(timeline.getSpawnedTimelines());

		Collections.sort(spawnedTimelines, TimelineModel.getTimelineComparator(timeline, Function.identity()));

		for (final var spawned : spawnedTimelines) {
			collectTimelinesBFS(spawned, result);
		}

	}

}
