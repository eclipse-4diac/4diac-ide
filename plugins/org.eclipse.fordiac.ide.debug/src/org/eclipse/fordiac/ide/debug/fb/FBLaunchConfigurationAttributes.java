/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.fb;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.value.DateAndTimeValueConverter;
import org.eclipse.fordiac.ide.model.value.TimeValueConverter;

public interface FBLaunchConfigurationAttributes extends LaunchConfigurationAttributes {
	String ID = "org.eclipse.fordiac.ide.debug.fbLaunch"; //$NON-NLS-1$
	String EVENT = "org.eclipse.fordiac.ide.debug.event"; //$NON-NLS-1$
	String REPEAT_EVENT = "org.eclipse.fordiac.ide.debug.repeatEvent"; //$NON-NLS-1$
	String KEEP_RUNNING_WHEN_IDLE = "org.eclipse.fordiac.ide.debug.keepRunningWhenIdle"; //$NON-NLS-1$

	String CLOCK_MODE = "org.eclipse.fordiac.ide.debug.clockMode"; //$NON-NLS-1$
	String CLOCK_INTERVAL = "org.eclipse.fordiac.ide.debug.clockInterval"; //$NON-NLS-1$
	String CLOCK_REALTIME_OFFSET = "org.eclipse.fordiac.ide.debug.clockRealtimeOffset"; //$NON-NLS-1$
	String CLOCK_MONOTONIC_OFFSET = "org.eclipse.fordiac.ide.debug.clockMonotonicOffset"; //$NON-NLS-1$

	static Event getEvent(final ILaunchConfiguration configuration, final FBType type, final Event defaultEvent)
			throws CoreException {
		final String eventAttribute = configuration.getAttribute(EVENT, ""); //$NON-NLS-1$
		if (eventAttribute != null && !eventAttribute.isEmpty()) {
			final var event = type.getInterfaceList().getEvent(eventAttribute);
			if (event != null && event.isIsInput()) {
				return event;
			}
			return Stream
					.concat(type.getInterfaceList().getSockets().stream(), type.getInterfaceList().getPlugs().stream())
					.map(AdapterDeclaration::getAdapterFB).map(AdapterFB::getInterface)
					.map(iface -> iface.getEvent(eventAttribute)).filter(Objects::nonNull)
					.filter(Predicate.not(Event::isIsInput)).findAny().orElse(defaultEvent);
		}
		return defaultEvent;
	}

	static boolean isRepeatEvent(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(REPEAT_EVENT, false);
	}

	static boolean isKeepRunningWhenIdle(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(KEEP_RUNNING_WHEN_IDLE, true);
	}

	static FBDebugClockMode getClockMode(final ILaunchConfiguration configuration) throws CoreException {
		final String modeAttribute = configuration.getAttribute(CLOCK_MODE, (String) null);
		if (modeAttribute != null) {
			try {
				return FBDebugClockMode.valueOf(modeAttribute);
			} catch (final IllegalArgumentException e) {
				throw new CoreException(Status.error("Invalid value for clock mode")); //$NON-NLS-1$
			}
		}
		return FBDebugClockMode.SYSTEM;
	}

	static Duration getClockInterval(final ILaunchConfiguration configuration) throws CoreException {
		final var value = configuration.getAttribute(CLOCK_INTERVAL, (String) null);
		if (value != null) {
			try {
				return TimeValueConverter.INSTANCE.toValue(value);
			} catch (final IllegalArgumentException e) {
				throw new CoreException(Status.error("Invalid value for clock interval", e)); //$NON-NLS-1$
			}
		}
		return Duration.ZERO;
	}

	static Instant getClockRealtimeOffset(final ILaunchConfiguration configuration) throws CoreException {
		final var value = configuration.getAttribute(CLOCK_REALTIME_OFFSET, (String) null);
		if (value != null) {
			try {
				return DateAndTimeValueConverter.INSTANCE.toValue(value).toInstant(ZoneOffset.UTC);
			} catch (final IllegalArgumentException e) {
				throw new CoreException(Status.error("Invalid value for clock monotonic offset", e)); //$NON-NLS-1$
			}
		}
		return Instant.EPOCH;
	}

	static Instant getClockMonotonicOffset(final ILaunchConfiguration configuration) throws CoreException {
		final var value = configuration.getAttribute(CLOCK_MONOTONIC_OFFSET, (String) null);
		if (value != null) {
			try {
				final Duration duration = TimeValueConverter.INSTANCE.toValue(value);
				return Instant.ofEpochSecond(duration.getSeconds(), duration.getNano());
			} catch (final IllegalArgumentException e) {
				throw new CoreException(Status.error("Invalid value for clock monotonic offset", e)); //$NON-NLS-1$
			}
		}
		return Instant.EPOCH;
	}

	static String getClockIntervalText(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(CLOCK_INTERVAL, "1s"); //$NON-NLS-1$
	}

	static String getClockRealtimeOffsetText(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(CLOCK_REALTIME_OFFSET, "1970-01-01-00:00:00.000"); //$NON-NLS-1$
	}

	static String getClockMonotonicOffsetText(final ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(CLOCK_MONOTONIC_OFFSET, "0s"); //$NON-NLS-1$
	}
}
