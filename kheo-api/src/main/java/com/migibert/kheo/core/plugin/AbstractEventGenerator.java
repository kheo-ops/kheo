package com.migibert.kheo.core.plugin;

import java.util.ArrayList;
import java.util.List;

import com.migibert.kheo.core.ServerEvent;

public abstract class AbstractEventGenerator<S extends ServerProperty> implements EventGenerator<S> {

	@Override
	public List<ServerEvent> generateEvents(List<ServerProperty> original, List<ServerProperty> discovered) {
		List<S> filterOriginal = filterServerPropertiesByType(original, getPropertyClass());
		List<S> filterDiscovered = filterServerPropertiesByType(discovered, getPropertyClass());
		return generateSpecificEvents(filterOriginal, filterDiscovered);
	}
	
	public abstract List<ServerEvent> generateSpecificEvents(List<S> original, List<S> discovered);

    private List<S> filterServerPropertiesByType(List<ServerProperty> properties, Class<S> type) {
    	List<S> result = new ArrayList<S>();
    	
    	for(ServerProperty property : properties) {
    		if(type.isInstance(property)) {
    			result.add((S) property);    			
    		}
    	}
    	
    	return result;
    }
}
