package introsde.assignment.soap.endpoint;

import introsde.assignment.soap.ws.PeopleImpl;

import javax.xml.ws.Endpoint;

public class PeoplePublisher {
    public static String SERVER_URL = "http://localhost";
    public static String PORT = "6900";
    //public static String BASE_URL = "/ws/people";

    public static String getEndpointURL(String base_url) {
        return SERVER_URL+":"+PORT+base_url;
    }

    public static void main(String[] args) {
        String endpointUrl = getEndpointURL("/ws/people");
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = "+endpointUrl);
        Endpoint.publish(endpointUrl, new PeopleImpl());
             
    }
}