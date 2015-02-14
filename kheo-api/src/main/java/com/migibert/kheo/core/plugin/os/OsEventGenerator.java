package com.migibert.kheo.core.plugin.os;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.migibert.kheo.core.ServerEvent;
import com.migibert.kheo.core.plugin.AbstractEventGenerator;

public class OsEventGenerator extends AbstractEventGenerator<OsServerProperty> {

	private Logger logger = LoggerFactory.getLogger(OsEventGenerator.class);

	@Override
	public List<ServerEvent> generateSpecificEvents(List<OsServerProperty> original, List<OsServerProperty> discovered) {
		List<ServerEvent> generatedEvents = new ArrayList<>();
//
//		if (!original.equals(discovered)) {
//			if (!original.hardwarePlatform.equals(discovered.hardwarePlatform)) {
//				logger.info("OS Hardware platform changed! Generating event...");
//				generatedEvents.add(new ServerEvent(EventType.OS_HARDWARE_PLATFORM_CHANGED.name(), original.hardwarePlatform, discovered.hardwarePlatform));
//			}
//			if (!original.kernelName.equals(discovered.kernelName)) {
//				logger.info("OS Kernel name changed! Generating event...");
//				generatedEvents.add(new ServerEvent(EventType.OS_KERNEL_NAME_CHANGED.name(), original.kernelName, discovered.kernelName));
//			}
//			if (!original.kernelRelease.equals(discovered.kernelRelease)) {
//				logger.info("OS Kernel release changed! Generating event...");
//				generatedEvents.add(new ServerEvent(EventType.OS_KERNEL_RELEASE_CHANGED.name(), original.kernelRelease, discovered.kernelRelease));
//			}
//			if (!original.name.equals(discovered.name)) {
//				logger.info("OS name platform changed! Generating event...");
//				generatedEvents.add(new ServerEvent(EventType.OS_NAME_CHANGED.name(), original.name, discovered.name));
//			}
//		}
		return generatedEvents;

	}

	@Override
	public Class<OsServerProperty> getPropertyClass() {
		return OsServerProperty.class;
	}
}
