package org.matsim.prepare.networkSpeed;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.utils.gis.ShapeFileReader;

public class ChangeSpeed {

    public static void main(String[] args) {
        // keep track on which step filters out how many links
        int postMode = 0;
        int postSpeed = 0;
        int postTypes = 0;

        // 1. read the network file
        var network = NetworkUtils.readNetwork("https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.5-10pct/input/berlin-v5.5-network.xml.gz");

        //   2. apply changes
        for (Link link : network.getLinks().values()) {
            // 2.1 don't change links where cars are not allowed anyway
            if (!link.getAllowedModes().contains("car")) continue; // only link with cars
            postMode += 1;

            // 2.2 don't change speed road links that are slower than new speed maximum anyway
            if (link.getFreespeed() <= 30.001/3.6*0.5) continue; // link with max speed <=30km
            if (link.getFreespeed() > 60.001/3.6*0.5) continue; // link with max speed >60kmh
            postSpeed += 1;

            // 2.3 don't change speed of links that are of the following road types
            String linkType = (String) link.getAttributes().getAsMap().get("type");
            if ("primary".equals(linkType)) continue;
            if ("motorway".equals(linkType)) continue;
            if ("primary_link".equals(linkType)) continue;
            if ("trunk".equals(linkType)) continue;
            if ("motorway_link".equals(linkType)) continue;
            postTypes += 1;

            // reduce speed for filtered links // does this also modify the original link or only the instance in for loop?
            link.setFreespeed(30/3.6*0.5);

        }


        System.out.println(
                "Number of Links in network:                         " + network.getLinks().values().size() +
                        "\nNumber of Links where car is allowed:               " + postMode +
                        "\nNumber of Links after removing links<=30kmh, >60kmh: " + postSpeed +
                        "\nNumber of Links after removing types to not change:  " + postTypes
        );

        //3. Write the network into file
        NetworkUtils.writeNetwork(network, "./scenarios/berlin-v5.5-1pct/input/test-network.xml.gz");
    }


}
