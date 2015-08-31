package utils.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.User;
import models.serialization.Views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;

public abstract class BaseController extends Controller {
	private static final Logger.ALogger logger = Logger.of(BaseController.class);
	
	protected User getAuthUser() {
		return (User) Http.Context.current().args.get("user");
	}
	
	protected String getParam(String key) {
		return request().getQueryString(key);
	}
	
	protected String getParam(String key, String def) {
		String value = getParam(key);
		if (value == null) value = def;
		return value;
	}
	
	protected Double getDoubleParam(String key) {
		return Double.parseDouble(getParam(key));
	}
	
	protected Double getDoubleParam(String key, Double def) {
		String value = getParam(key);
		if (value == null) {
			return def;
		}
		return Double.parseDouble(value);
	}
	
	protected Integer getIntParam(String key) {
		return Integer.parseInt(getParam(key));
	}
	
	protected Integer getIntParam(String key, Integer def) {
		String value = getParam(key);
		if (value == null) {
			return def;
		}
		return Integer.parseInt(value);
	}
	
	public static Status status(Object obj, int code) {
		return status(obj, code, Object.class);
	}

	public static Status status(Object obj, int code, Class<?> serializationView) {
		if (request().accepts("application/json")) {
			try {
				return status(code, toJson(obj, serializationView));
			} catch (IOException ex) {
				logger.error("Error while parsing object to JSON", ex);
				return internalServerError("Something wrong happened");
			}
	    } else {
	        return badRequest("Bad Request");
	    }
	}
	
	public static Status invalidForm(Form form) {
		if (request().accepts("application/json")) {
	        return status(422, form.errorsAsJson());
	    } else {
	        return status(422, form.errors().toString());
	    }
	}
	
	public static Status invalidField(String fieldName, String...messages) {
		List<String> msgs = new ArrayList<String>(messages.length);
		
		for (String msg : messages) {
			msgs.add(msg);
		}

		return status(ImmutableMap.of(fieldName, msgs), 422);
	}
	
	public static JsonNode toJson(Object obj, Class<?> serializationView) throws IOException {
		if (obj instanceof Form) {
			return ((Form)obj).errorsAsJson();
		} else {
			ObjectWriter w = Json.mapper().writerWithView(serializationView);
			String json = w.writeValueAsString(obj);
			return Json.mapper().readTree(json);
			//return Json.toJson(obj);
		}
	}
	
	public static Message message(String message, int code) {
		return new Message(message, code);
	}
	
	public static Status ok(Object obj) {
		return status(obj, 200);
	}
	
	public static Status ok(Object obj, Class<?> serializationView) {
		return status(obj, 200, serializationView);
	}
	
	public static Status created(Object obj) {
		return status(obj, 201);
	}
	
	public static Status created(Object obj, Class<?> serializationView) {
		return status(obj, 201, serializationView);
	}
	
	public static Status notModified(Object obj) {
		return status(obj, 304);
	}
	
	public static Status notModified(Object obj, Class<?> serializationView) {
		return status(obj, 304, serializationView);
	}
	
	public static Status badRequest(Object obj) {
		return status(obj, 400);
	}
	
	public static Status badRequest(Object obj, Class<?> serializationView) {
		return status(obj, 400, serializationView);
	}
	
	public static Status unauthorized(Object obj) {
		return status(obj, 401);
	}
	
	public static Status unauthorized(Object obj, Class<?> serializationView) {
		return status(obj, 401, serializationView);
	}
	
	public static Status forbidden(Object obj) {
		return status(obj, 403);
	}
	
	public static Status forbidden(Object obj, Class<?> serializationView) {
		return status(obj, 403, serializationView);
	}
	
	public static Status notFound(Object obj) {
		return status(obj, 404);
	}
	
	public static Status notFound(Object obj, Class<?> serializationView) {
		return status(obj, 404, serializationView);
	}
	
	public static Status notFound(String message, int code) {
		return status(message(message, code), 404);
	}
	
	public static Status internalServerError(Object obj) {
		return status(obj, 500);
	}
	
	public static Status internalServerError(Object obj, Class<?> serializationView) {
		return status(obj, 500, serializationView);
	}

	public static Status conflict(Object obj) {
		return status(obj, 409);
	}

	public static Status conflict(Object obj, Class<?> serializationView) {
		return status(obj, 409, serializationView);
	}
}
