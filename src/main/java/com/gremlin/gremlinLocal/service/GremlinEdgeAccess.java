package com.gremlin.gremlinLocal.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.RequestOptions;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.springframework.stereotype.Service;

@Service
public class GremlinEdgeAccess {


  public void saveEdge() throws ExecutionException, InterruptedException, FileNotFoundException {

    Cluster cluster;
    Client client;

    cluster = Cluster.build(new File("src/remote-secure.yaml")).create();
    client = cluster.connect();

    String query1 = "g.addV('person').property('id', 'mary').property('firstName', 'Mary').property('lastName', 'Andersen').property('age', 39)";
    String query2 = "g.V('5e402fd7-6f7e-4a80-a606-70cf273a75c7').addE('knows').to(g.V('mary'))";

    RequestOptions options = RequestOptions.build().timeout(1800000L).create();
    //List<Result> results1 = client.submit(query1, options).all().get();
    List<Result> results2 = client.submit(query2, options).all().get();

/*    for (Result result : results1) {
      System.out.println(result.toString());
    }*/

    for (Result result : results2) {
      System.out.println(result.toString());
    }
  }


}
