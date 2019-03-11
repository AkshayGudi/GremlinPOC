package com.gremlin.gremlinLocal.service;

import com.gremlin.gremlinLocal.model.Knows;
import com.gremlin.gremlinLocal.model.Person;
import com.gremlin.gremlinLocal.persistence.DbTemplate;
import com.gremlin.gremlinLocal.persistence.DbTemplateImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.RequestOptions;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.springframework.stereotype.Service;

@Service
public class GremlinLocalService {

  DbTemplate dbTemplate;

  GremlinLocalService() throws FileNotFoundException {
    dbTemplate = new DbTemplateImpl();
  }

  public void getAllPeople()
      throws FileNotFoundException,
      ExecutionException, InterruptedException, IllegalAccessException, InstantiationException, NoSuchFieldException {

    List<Person> personList = dbTemplate.findVertexByLabel(Person.class, "person");
    for (Person person : personList) {
      System.out.println(person.getId());
    }
  }


  public void getAllRelations()
      throws FileNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
    List<Knows> knowsRelList = dbTemplate.findEdgeByLabel(Knows.class, "KNOWS");
    for (Knows knowsObj : knowsRelList
    ) {
      System.out.println(knowsObj.getId());
    }

  }

  public void saveVertex() {
    String label = "person";
    Map<String, String> fieldNameValueMap1 = new LinkedHashMap<>();
    fieldNameValueMap1.put("id", "thomas");
    fieldNameValueMap1.put("age", "39");

    Map<String, String> fieldNameValueMap2 = new LinkedHashMap<>();
    fieldNameValueMap2.put("id", "mary");
    fieldNameValueMap2.put("age", "39");

    dbTemplate.saveVertex(label, fieldNameValueMap1);
    dbTemplate.saveVertex(label, fieldNameValueMap2);
  }

  public void saveEdge() {
    String edgeLabel = "KNOWS";
    String vertex1Id = "thomas";
    String vertex2Id = "mary";

    dbTemplate.saveEdge(vertex1Id, vertex2Id, edgeLabel);
  }

}
