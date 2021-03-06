package com.gremlin.gremlinLocal.persistence;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface DbTemplate {

  public <T> List<T> findVertexByLabel(Class<T> clazz, String label)
      throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException;


  public <T> List<T> findEdgeByLabel(Class<T> clazz, String label)
      throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException;

  public void saveVertex(String label, Map<String, String> fieldMap);

  public  void saveEdge(String vertex1id, String vertex2id, String edgeLabel);
}
