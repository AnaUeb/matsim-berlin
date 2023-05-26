package org.matsim.analysis.homework;


import org.matsim.core.events.EventsUtils;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.population.PopulationUtils;

public class RunHomeworkAnalysis {

    public static void main(String[] args) {

        var populationBase = PopulationUtils.readPopulation(".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct.output_plans.xml.gz");
        var networkBase = NetworkUtils.readNetwork(".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct.output_network.xml.gz");
        var populationCase = PopulationUtils.readPopulation(".\\scenarios\\berlin-v5.5-1pct\\output_scenario1_1pct\\berlin-v5.5-1pct.output_plans.xml.gz");
        var networkCase = NetworkUtils.readNetwork(".\\scenarios\\berlin-v5.5-1pct\\output_scenario1_1pct\\berlin-v5.5-1pct.output_network.xml.gz");

        System.out.println(populationBase.getClass().getSimpleName());
        System.out.println(networkBase.getClass().getSimpleName());
        //countAgentsOnAffectedLinks(populationBase,networkBase);

        //var handler = new EventHandler();
        //var manager = EventsUtils.createEventsManager();
      //  manager.addHandler(handler);
        //EventsUtils.readEvents(manager, ".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct\\output_events.xml.gz");
        //EventsUtils.readEvents(manager,".\\scenarios\\berlin-v5.5-1pct\\output_scenario1_1pct\\berlin-v5.5-1pct\\output_events.xml.gz");


    }


}
