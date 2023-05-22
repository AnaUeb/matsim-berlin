package org.matsim.analysis.homework;

import org.matsim.core.events.EventsUtils;

public class RunAnalysis {
    public static void main(String[] args) {

        var handler = new VerySimplePersonEventHandler();
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler);                        //connect handler wirh manager
        EventsUtils.readEvents(manager,"./scenarios/berlin-v5.5-1pct/output_base_1pct/berlin-v5.5-1pct.output_events.xml.gz");

    }
}
