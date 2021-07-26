package domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class CommonUtil {
    public static String tableFormatter(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapObj = mapper.convertValue(object, Map.class);
        StringBuilder builder = new StringBuilder();
        mapObj.forEach((key, value) -> builder.append(String.format("%1$-25s %2$-30s\n", key, value)));
        return builder.toString();
    }
}
