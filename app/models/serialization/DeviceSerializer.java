package models.serialization;

import java.io.IOException;

import models.Device;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DeviceSerializer extends JsonSerializer<Device> {

	@Override
	public void serialize(Device device, JsonGenerator json, SerializerProvider provider) 
			throws IOException, JsonProcessingException {
		json.writeStartObject();
		json.writeNumberField("id", device.getId());
		json.writeStringField("code", device.getCode());
		json.writeStringField("name", device.getName());
		json.writeObjectField("type", device.getDeviceType());
		json.writeObjectField("functionalities", device.getFunctionalities());
		json.writeEndObject();
	}

}
