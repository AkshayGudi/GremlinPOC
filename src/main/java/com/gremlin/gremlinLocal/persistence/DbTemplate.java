package com.gremlin.gremlinLocal.persistence;

import java.io.FileNotFoundException;
import java.util.List;

public interface DbTemplate {

  public <T> List<T> findVertexByLabel(Class<T> clazz, String label)
      throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException;


  public <T> List<T> findEdgeByLabel(Class<T> clazz, String label)
      throws FileNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException;
}
