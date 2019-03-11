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

  public void saveVertex(){
    String label = "person";
    Map<String,String> fieldNameValueMap = new LinkedHashMap<>();
    fieldNameValueMap.put("id", "thomas");
    fieldNameValueMap.put("firstName", "Mary");
    fieldNameValueMap.put("lastName", "Andersen");
    fieldNameValueMap.put("age", "39");

    dbTemplate.saveVertex(label, fieldNameValueMap);
  }


}
