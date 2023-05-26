package org.matsim.analysis.homework;

import org.matsim.api.core.v01.events.*;
import org.matsim.api.core.v01.events.handler.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventHandler implements LinkEnterEventHandler,
        LinkLeaveEventHandler {

    private static List<String> agents = new ArrayList<>();

    @Override
    public void reset(int iteration) {
        System.out.println("reset...");
    }


    @Override
    public void handleEvent(LinkEnterEvent event) {
        System.out.println("LinkEnterEvent");
        System.out.println("Time: " + event.getTime());
        System.out.println("LinkId: " + event.getLinkId());
    }

    @Override
    public void handleEvent(LinkLeaveEvent event) {
        System.out.println("LinkLeaveEvent");
        System.out.println("Time: " + event.getTime());
        System.out.println("LinkId: " + event.getLinkId());
        System.out.println("VehicleId: "+event.getVehicleId());
        agents.add(event.getLinkId()+";"+event.getVehicleId().toString());
        try {
            write(agents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*@Override
    public void handleEvent(PersonArrivalEvent event) {
        System.out.println("AgentArrivalEvent");
        System.out.println("Time: " + event.getTime());
        System.out.println("LinkId: " + event.getLinkId());
        System.out.println("PersonId: " + event.getPersonId());
    }

    @Override
    public void handleEvent(PersonDepartureEvent event) {
        System.out.println("AgentDepartureEvent");
        System.out.println("Time: " + event.getTime());
        System.out.println("LinkId: " + event.getLinkId());
        System.out.println("PersonId: " + event.getPersonId());
    }*/

    public void write(List <String> agents) throws IOException {
        FileWriter writer = new FileWriter("scenarios/berlin-v5.5-1pct/output_base_1pct/affectedVehicles");

        for (int j = 0; j < agents.size(); j++) {
            writer.append(String.valueOf(agents.get(j)));
            writer.append("\n");
        }
        writer.close();
        System.out.println("File created");

    }
}
