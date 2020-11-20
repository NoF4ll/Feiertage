import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class APIService {
	public void addDays(ArrayList<LocalDate> dates, String name, int year) {
    	try {
    		final JSONObject json = new JSONObject(IOUtils.toString(new URL("https://feiertage-api.de/api/?jahr="+year), Charset.forName("UTF-8")));
        	dates.add(LocalDate.parse(json.getJSONObject("BY").getJSONObject(name).get("datum").toString())); 
    	}
    	catch(MalformedURLException e) {
    		e.printStackTrace();
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}  	
    }  
}