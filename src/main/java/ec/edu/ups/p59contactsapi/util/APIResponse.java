package ec.edu.ups.p59contactsapi.util;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

public class APIResponse {
	
	public static String objectToJsonString(Object object) {
		return JsonbBuilder.create().toJson(object);
	}
	
	public static String toJsonString(int status, String message) {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("message", message);
		return json.toString();
	}
	
	public static Response getResponse(int status, Object object) {
		if (status == 0 || object== null) 
			throw new NullPointerException("\"status\" cannot be 0 or"
					+ " \"object\" cannot be null.");
		if (object instanceof String)
			return getMessageResponse(status, (String) object);
		return Response.status(status).entity(objectToJsonString(object)).build();
	}
	
	private static Response getMessageResponse(int status, String message) {
		return Response.status(status).entity(toJsonString(status, message)).build();
	}
	
}
