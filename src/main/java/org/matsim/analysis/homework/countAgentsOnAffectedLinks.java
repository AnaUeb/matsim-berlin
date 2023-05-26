package org.matsim.analysis.homework;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.matsim.core.events.EventsReaderTXT;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.events.algorithms.EventWriterXML;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.population.PopulationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class countAgentsOnAffectedLinks {

    public static void main (String args[]) throws FileNotFoundException {

    // read in affected links
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader("scenarios/berlin-v5.5-1pct/input/affectedLinks.csv"));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        List<String> affectedLinks = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            affectedLinks.add(records.get(i).get(0).toString());
        }

        System.out.println(affectedLinks.contains("100012"));

        // var populationBase = PopulationUtils.readPopulation(".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct.output_plans.xml.gz");
        //var networkBase = NetworkUtils.readNetwork(".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct.output_network.xml.gz");
        //var populationCase = PopulationUtils.readPopulation(".\\scenarios\\berlin-v5.5-1pct\\output_scenario1_1pct\\berlin-v5.5-1pct.output_plans.xml.gz");
       // var networkCase = NetworkUtils.readNetwork(".\\scenarios\\berlin-v5.5-1pct\\output_scenario1_1pct\\berlin-v5.5-1pct.output_network.xml.gz");

        var handler = new EventHandler();
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler);
        EventsUtils.readEvents(manager, ".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct.output_events.xml.gz");
        //new EventsReaderTXT(manager);
        //new EventWriterXML(".\\scenarios\\berlin-v5.5-1pct\\output_base_1pct\\berlin-v5.5-1pct.output.xml");





    }
}
