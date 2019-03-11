This is a small POC to connect to Azure Cosmos DB and save and retrieve vertex as well as Edges
then Map the result with corresponding Model class.

Models: Person (Vertex), Knows (Relation)

Data_model : (Person)--[KNOWS]--(Person)

Flow:
GremlinController -->  GremlinLocalService --> DbTemplateImpl


Private methods in DbTemplateImpl:
  1. mapToModelVertex:
  This method is used to map the result from Azure Cosmos DB to its corresponding Model Class
  (In this case Person Class).

  2. findVertexById:
  Used to find the get the vertex from DB using Id of Vertex