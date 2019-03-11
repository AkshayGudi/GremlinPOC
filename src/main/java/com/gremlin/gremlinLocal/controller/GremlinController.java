package com.gremlin.gremlinLocal.controller;

import com.gremlin.gremlinLocal.service.GremlinEdgeAccess;
import com.gremlin.gremlinLocal.service.GremlinLocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GremlinController {

  @Autowired
  GremlinLocalService gremlinLocalService;

  @Autowired
  GremlinEdgeAccess gremlinEdgeAccess;

  @GetMapping("/getVertex")
  public String getVertex() {

    try {
      gremlinLocalService.getAllPeople();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "done";
  }

  @GetMapping("/getEdge")
  public String getEdge() {

    try {
      gremlinLocalService.getAllRelations();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "done";
  }

  @GetMapping("/saveVertex")
  public String saveVertex(){
    gremlinLocalService.saveVertex();
    return "done";
  }

  @GetMapping("/saveEdge")
  public String saveEdge(){
    gremlinLocalService.saveEdge();
    return "done";
  }

}