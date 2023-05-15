package org.matsim.prepare.networkSpeed;

import org.matsim.api.core.v01.network.Link;
import org.matsim.core.network.NetworkUtils;

public class ChangeSpeed {

    public static void main(String[] args) {

        // 1. read the network file
        var network = NetworkUtils.readNetwork("https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/berlin-v5.5-10pct/input/berlin-v5.5-network.xml.gz");
        //var network = NetworkUtils.readNetwork("Ordner");

        // 2. apply changes
        for (Link link : network.getLinks().values()) {
           // System.out.println(link.getFreespeed());
            //System.out.println(link.getAttributes().getAttribute("type"));
            if (link.getFreespeed() > 8.33  &&
                    link.getAttributes().getAsMap().get("type") != "primary" &&
                    link.getAttributes().getAsMap().get("type") != "motorway" &&
                    link.getAttributes().getAsMap().get("type") != "primary_link" &&
                    link.getAttributes().getAsMap().get("type") != "trunk" &&
                    link.getAttributes().getAsMap().get("type") != "motorway_link")
            {
                System.out.println(link.getFreespeed());
                System.out.println(link.getAttributes().getAsMap().get("type"));
                link.setFreespeed(8.33);
                //.getClass().getSimpleName())
            }
            //link.setFreespeed(5);
        }

        //3. Write the network into file
        NetworkUtils.writeNetwork(network, "./scenarios/berlin-v5.5-1pct/input/test-network.xml.gz");
    }


}
