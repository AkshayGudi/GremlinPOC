package com.gremlin.gremlinLocal.model;

public class Knows {

  public Knows() {
  }

  private String id;
  private String label;
  private Person inV;
  private Person outV;
  private String inVLabel;
  private String outVLabel;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Person getInV() {
    return inV;
  }

  public void setInV(Person inV) {
    this.inV = inV;
  }

  public Person getOutV() {
    return outV;
  }

  public void setOutV(Person outV) {
    this.outV = outV;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getInVLabel() {
    return inVLabel;
  }

  public void setInVLabel(String inVLabel) {
    this.inVLabel = inVLabel;
  }

  public String getOutVLabel() {
    return outVLabel;
  }

  public void setOutVLabel(String outVLabel) {
    this.outVLabel = outVLabel;
  }
}
