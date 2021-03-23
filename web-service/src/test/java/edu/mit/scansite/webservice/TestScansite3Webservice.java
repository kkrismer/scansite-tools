package edu.mit.scansite.webservice;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

public class TestScansite3Webservice {

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost/").port(9998).build();
  }

  public static final URI BASE_URI = getBaseURI();

//  protected static HttpServer startServer() throws IOException {
//    System.out.println("Starting grizzly...");
//    ResourceConfig rc = new PackagesResourceConfig("edu.mit.scansite.webservice");
//    return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
//  }
//
//  public static void main(String[] args) throws IOException {
//    HttpServer httpServer = startServer();
//    System.out.println(String.format("Jersey app started with WADL available at "
//        + "%sapplication.wadl\nTry out!\nHit enter to stop it...",
//        BASE_URI));
//    System.in.read();
//    httpServer.stop();
//  }
}