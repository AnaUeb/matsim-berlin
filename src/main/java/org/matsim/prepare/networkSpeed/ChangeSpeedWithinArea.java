package org.matsim.prepare.networkSpeed;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.utils.gis.ShapeFileReader;


public class ChangeSpeedWithinArea {

    public static void main(String[] args) {
        // keep track on which step filters out how many links
        int postSpeed = 0;
        int postTypes = 0;
        int postSpatial = 0;

        // 1. read the network file
        var network = NetworkUtils.readNetwork("https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.5-10pct/input/berlin-v5.5-network.xml.gz");

        // 2. get area where changes should be applied to
        // read in shapefile with area covering sbahn ring "matsim-berlin/original-input-data/umweltzone_epsg_31468"
        var shpFile = new ShapeFileReader().readFileAndInitialize("original-input-data/umweltzone_epsg_31468/umweltzone_epsg_31468.shp");
        MultiPolygon areaToApplyChanges = (MultiPolygon) shpFile.iterator().next().getDefaultGeometry();

        //   3. apply changes
        for (Link link : network.getLinks().values()) {

            // 3.1 don't change speed road links that are slower than new speed maximum anyway
            if (link.getFreespeed() <= 8.33) continue;
            postSpeed += 1;

            // 3.2 don't change speed of links that are of the following road types
            String linkType = (String) link.getAttributes().getAsMap().get("type");
            if ("primary".equals(linkType)) continue;
            if ("motorway".equals(linkType)) continue;
            if ("primary_link".equals(linkType)) continue;
            if ("trunk".equals(linkType)) continue;
            if ("motorway_link".equals(linkType)) continue;
            postTypes += 1;

            // 3.3 check whether either the fromNode or the toNode lie within area where changes should be applied
            Coord cordStart = link.getFromNode().getCoord();
            Coord cordEnd = link.getToNode().getCoord();
            Coordinate coordinateStart = new Coordinate( cordStart.getX(), cordStart.getY() );
            Coordinate coordinateEnd = new Coordinate( cordEnd.getX(), cordEnd.getY() );
            Point pointStart =  new GeometryFactory().createPoint(coordinateStart);
            Point pointEnd =  new GeometryFactory().createPoint(coordinateEnd);
            // don't change speed if neither fromNode or toNode are within area where changes shall be applied
            if (! (areaToApplyChanges.contains(pointStart) || areaToApplyChanges.contains(pointEnd)) ) continue;
            postSpatial += 1;

            // reduce speed for filtered links // does this also modify the original link or only the instance in for loop?
            link.setFreespeed(8.33);

        }


        System.out.println(
                "Number of Links in network:                         " + network.getLinks().values().size() +
                "\nNumber of Links after removing links<=30kmh:         " + postSpeed +
                "\nNumber of Links after removing types to not change:  " + postTypes +
                "\nNumber of Links after spatial filter:                 " + postSpatial
        );

        //3. Write the network into file
        NetworkUtils.writeNetwork(network, "./scenarios/berlin-v5.5-1pct/input/test-network-wi-area.xml.gz");
    }


}
