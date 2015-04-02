/**
 * 
 */
package com.restdoc.services;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.documentation.model.DocService;
import com.documentation.model.DocServices;
import com.restdoc.annotations.RESTMethod;
import com.restdoc.annotations.RESTService;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.docbuilders.ServiceDocGenerator;
import com.restdoc.docbuilders.abstracts.ServiceDocDirector;
import com.sun.jersey.api.core.ResourceConfig;

/** Servicio que muestra la informacion de todos los servicios REST desplegados en la presente aplicacion web
 * 
 * Para desplegarlo hay que meter en el web.xml el paquete com.restdoc.services como paquete que contiene servicios REST y poner el jar dentro del path de la aplicacion en ejecucion.
 * 
 * P.Ej:
 * 
 * El jar en el directorio: <base>/WEB-INF/lib 
 * En el web.xml: 
 * {@code<init-param>
 *   	<param-name>com.sun.jersey.config.property.packages</param-name>
 *   	<param-value><project packages>;com.restdoc.services</param-value>
 * 	</init-param>}
 * @author inigo
 *
 */
@Path("/rest-doc")
@RESTService(description="REST services descriptions", path="/rest-doc", modelpath="com.documentation.model")
public class RESTDocService {
	
	AbstractContextReader contexReader;
	ServiceDocDirector sdd;
	
	@Context
	ResourceConfig context;
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@RESTMethod(httpMethod="GET", path="all", description="Returns objects describing the services in this web application", producedObject="DocService", productedMimetype="JSON")
	public DocServices doGet(){
		contexReader = AbstractContextReader.initContextReader(context, "com.restdoc.contextreaders.DefaultContextReader");
		String[] jerseyPath = contexReader.readPackagesToDocumentate();
		sdd = new ServiceDocGenerator(contexReader.readAvailableDocGenerators());
		DocServices services = new DocServices();
		ArrayList<DocService> serv = new ArrayList<DocService>();
		for (String packag : jerseyPath){
			serv.addAll(sdd.generateDocForPackage(packag));
		}
		services.setServices(serv);
		return services;
	}
	
	@PUT
	@Path("{param1}/put")
	@Produces(MediaType.APPLICATION_JSON)
	public String doPut(@PathParam(value = "") String param1){
		System.out.println("llega " + param1);
		return param1;	
	}
	
	// Para tests...
	public void setContextReader(AbstractContextReader contextReader) {
		this.contexReader = contextReader;
	}
}
