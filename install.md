Add your content here.


# Introduction #

RESTDOC it's based (actually) in servlets, so it's configured via web.xml.

## Details ##

This project have a test that works with project itself.

Lets see how it works:

### Services: ###

In services, in this case in RESTDOCService, we must put the annotation:

`@RESTService(description="REST services descriptions", modelpath="com.documentation.model")`

Which will mark that this class is a service. It have two parameters: a description and 'modelpath', which points to the base package of the models of this service.

For each service, now, we have to mark in:

`@RESTMethod(description="Returns objects describing the services in this web application", producedObject="DocService", productedMimetype="JSON")`

Here, after a description, it have a parameter named 'producedObject' which tells that an object from class named DocService will be returned (serialized like a JSON)

.... to be continued
