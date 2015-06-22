package hijack.dockerservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/22/15
 * Time:    11:36 AM
 * Purpose:
 */
public class JsonConvert<T> {
    public String toJson(T t) {
        ObjectMapper mapper = new ObjectMapper();
        String ret = "";
        try {
            ret = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;

    }

}
