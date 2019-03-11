package com.gremlin.gremlinLocal.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.RequestOptions;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;

public class DbTemplateImpl implements DbTemplate {

  private Cluster cluster;

  public DbTemplateImpl() throws FileNotFoundException {
    cluster = Cluster.build(new File("src/remote-secure.yaml")).create();
  }

  /**
   * method to get all Vertex based on Label
   */
  @Override
  public <T> List<T> findVertexByLabel(Class<T> clazz, String label)
      throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {

    //TODO /*Add this separately - START*/
    Client client;
    client = cluster.connect();
    //TODO /*Add this separately - END*/

    String query = String.format("g.V().hasLabel('%s')", label);
    ResultSet results = client.submit(query);

    //RequestOptions options = RequestOptions.build().timeout(1800000L).create();
    T modelClassObj = clazz.newInstance();
    List<T> objs = new ArrayList<>();

    mapToModelVertex(results, modelClassObj, objs);

    //TODO /*Add this separately - START*/
    client.close();
    cluster.close();
    //TODO /*Add this separately - END*/

    return objs;
  }

  /**
   * Method to find all Edges based on Label
   */
  @Override
  public <T> List<T> findEdgeByLabel(Class<T> clazz, String label)
      throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
    //TODO /*Add this separately - START*/
    Client client = cluster.connect();
    //TODO /*Add this separately - END*/
    final String inVertexFieldName = "inV";
    final String outVertexFieldName = "outV";

    String query = String.format("g.E().hasLabel('%s')", label);
    ResultSet results = client.submit(query);
    T modelClassObj = clazz.newInstance();

    for (Result result : results) {
      LinkedHashMap fieldNameValueMap = result.get(LinkedHashMap.class);
      Object inVertexObject;
      Object outVertexObject;

      for (Object fieldName : fieldNameValueMap.keySet()) {
        Field field = modelClassObj.getClass().getDeclaredField(fieldName.toString());
        field.setAccessible(true);
        Object value = null;
        if (fieldName.toString().equals(inVertexFieldName) || fieldName.toString()
            .equals(outVertexFieldName)) {
          String vertexId = fieldNameValueMap.get(fieldName).toString();
          value = findVertexById(field.getType(), vertexId);
        } else {
          value = fieldNameValueMap.get(fieldName);
        }
        field.set(modelClassObj, value);
      }

    }

    //TODO /*Add this separately - START*/
    client.close();
    cluster.close();
    //TODO /*Add this separately - END*/

    return null;
  }


  public void saveVertex(String label, Map<String, String> fieldMap) {
    //Sample Query
    //g.addV('person').property('id','thomas').property('age',26)

    Client client = cluster.connect();
    StringBuilder stringBuilder = new StringBuilder();
    String query = String.format("g.addV('%s')", label);
    stringBuilder.append(query);
    for (String fieldName : fieldMap.keySet()) {
      String property = String.format(".property('%s','%s')", fieldName, fieldMap.get(fieldName));
      stringBuilder.append(property);
    }

    ResultSet results = client.submit(stringBuilder.toString());

  }

  /**
   * The current version of save is using Id's of Vertices, which will be generalized later
   *
   * @param vertex1id
   * @param vertex2id
   * @param edgeLabel
   */
  public void saveEdge(String vertex1id, String vertex2id, String edgeLabel) {
    //Sample Query
    //g.V().has('id','akshay1').addE('KNOWS').to(g.V().has('id','akshay2'))

    Client client = cluster.connect();
    String query = String
        .format("g.V().has('id','%s').addE('%s').to(g.V().has('id','%s'))", vertex1id, vertex2id,
            edgeLabel);

    ResultSet results = client.submit(query);
  }

  /**
   * Private method to map DB output to Model class
   */
  private <T> void mapToModelVertex(ResultSet results, T modelClassObj, List<T> objs)
      throws NoSuchFieldException, IllegalAccessException {
    for (Result result : results) {
      LinkedHashMap outerFieldNameMap = result.get(LinkedHashMap.class);
      for (Object key : outerFieldNameMap.keySet()) {

        if (key.toString().equals("properties")) {

          LinkedHashMap<Object, Object> props = (LinkedHashMap<Object, Object>) outerFieldNameMap
              .get(key);

          for (Object eachPropertyKey : props.keySet()) {
            Field field = modelClassObj.getClass().getDeclaredField(eachPropertyKey.toString());
            field.setAccessible(true);
            ArrayList<LinkedHashMap<Object, Object>> innerMap = (ArrayList<LinkedHashMap<Object, Object>>) props
                .get(eachPropertyKey);
            String value = null;
            for (LinkedHashMap<Object, Object> propMap : innerMap
            ) {

              if (propMap.get("value") != null) {
                value = propMap.get("value").toString();
                break;
              }


            }
            //Object value = innerMap.get("value");
            field.set(modelClassObj, value);
          }

        } else {
          Field field = modelClassObj.getClass().getDeclaredField(key.toString());
          field.setAccessible(true);
          field.set(modelClassObj, outerFieldNameMap.get(key).toString());
        }

      }
      objs.add(modelClassObj);
    }
  }


  private Object findVertexById(Class<?> clazz, String id)
      throws IllegalAccessException, InstantiationException, NoSuchFieldException {
    Object obj = clazz.newInstance();
    Client client = cluster.connect();

    String query = String.format("g.V(%s)", id);
    ResultSet results = client.submit(query);

    List<Object> objs = new ArrayList<>();
    mapToModelVertex(results, obj, objs);
    return objs.get(0);
  }


}
