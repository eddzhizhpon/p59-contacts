package ec.edu.ups.p59contactsapi.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.p59contactsapi.business.ContactManagement;
import ec.edu.ups.p59contactsapi.model.Contact;
import ec.edu.ups.p59contactsapi.util.APIResponse;

@Path("/contact")
public class ContactService {

	@Inject
	private ContactManagement contactManagement;
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Contact contact) {
		try {
			contactManagement.save(contact);
			return APIResponse.getResponse(201, contact);
		} catch (Exception e) {
			return APIResponse.getResponse(500, e.getMessage());
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") int id) {
		try {
			return APIResponse.getResponse(200, contactManagement.read(id));
		} catch (Exception e) {
			return APIResponse.getResponse(500, e.getMessage());
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Contact contact) {
		try {
			contactManagement.save(contact);
			return APIResponse.getResponse(201, contact);
		} catch (Exception e) {
			return APIResponse.getResponse(500, e.getMessage());
		} 
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) {
		try {
			Contact contact = contactManagement.read(id);
			contactManagement.delete(contact);
			return APIResponse.getResponse(200, "Contac deleted");
		} catch (Exception e) {
			return APIResponse.getResponse(500, e.getMessage());
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		try {
			return APIResponse.getResponse(200, contactManagement.list());
		} catch (Exception e) {
			return APIResponse.getResponse(500, e.getMessage());
		}
	}
	
	
}
