package utils.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date>
{
	private String format = "dd/MM/yyyy";
	
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctx)
    		throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String date = parser.getText();

        try {
			return formatter.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
    }

}