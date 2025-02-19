/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2020 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.analysis.homework;

import org.matsim.analysis.DefaultAnalysisMainModeIdentifier;
import org.matsim.analysis.TripAnalysisFilter;
import org.matsim.analysis.TripAnalysisFilter.TripConsiderType;
import org.matsim.analysis.detailedPersonTripAnalysis.BasicPersonTripAnalysisHandler;
import org.matsim.analysis.detailedPersonTripAnalysis.PersonTripAnalysis;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.scenario.ScenarioUtils;

/**
* @author ikaddoura
*/

public class RunPersonTripAnalysis {

	public static void main(String[] args) {
		
//String outputDirectory = "scenarios/berlin-v5.5-1pct/output_base_1pct/";
		String outputDirectory = "scenarios/berlin-v5.5-1pct/output_scenario1_1pct/";
		String runId = "berlin-v5.5-1pct";

//		String shapeFileTripFilter = "../shared-svn/projects/avoev/matsim-input-files/vulkaneifel/v0/vulkaneifel.shp";
		String shapeFileTripFilter = "scenarios/shape/bezirksgrenzen.shp";
		String crs = "EPSG:4326";
		
		final String stageActivitySubString = "interaction";
		
		String networkFile = outputDirectory + runId + ".output_network.xml.gz";
		String populationFile = outputDirectory + runId + ".output_plans.xml.gz";
		String facilitiesFile = outputDirectory + runId + ".output_facilities.xml.gz";

		Config config = ConfigUtils.createConfig();
		
		config.controler().setRunId(runId);
		config.global().setCoordinateSystem(crs);
		config.controler().setOutputDirectory(outputDirectory);
		config.plans().setInputFile(populationFile);
		config.network().setInputFile(networkFile);
		config.facilities().setInputFile(facilitiesFile);
		
		Scenario scenario = ScenarioUtils.loadScenario(config);
		
		org.matsim.analysis.detailedPersonTripAnalysis.BasicPersonTripAnalysisHandler handler1 = new BasicPersonTripAnalysisHandler(stageActivitySubString, new DefaultAnalysisMainModeIdentifier());
		handler1.setScenario(scenario);
		
		EventsManager events = EventsUtils.createEventsManager();
		events.addHandler(handler1);
		
		String eventsFile = outputDirectory + runId + ".output_events.xml.gz";
		events.initProcessing();
		MatsimEventsReader reader = new MatsimEventsReader(events);
		reader.readFile(eventsFile);
		events.finishProcessing();

		org.matsim.analysis.detailedPersonTripAnalysis.PersonTripAnalysis personTripAnalysis = new PersonTripAnalysis();
		TripAnalysisFilter tripFilter = new TripAnalysisFilter("origin-and-destination-in-area");

		tripFilter.setZoneInformation(shapeFileTripFilter, crs);
		tripFilter.preProcess(scenario);
		tripFilter.setBuffer(0.);
		tripFilter.setTripConsiderType(TripConsiderType.OriginAndDestination);
		
		personTripAnalysis.printTripInformation(outputDirectory + runId + ".", TransportMode.car, handler1, null);
		personTripAnalysis.printTripInformation(outputDirectory + runId + ".", TransportMode.car, handler1, tripFilter);
	}

}

