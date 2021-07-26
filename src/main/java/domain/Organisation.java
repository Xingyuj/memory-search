package domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static domain.CommonUtil.tableFormatter;

@Data
public class Organisation {
    Long _id;
    String url;
    String external_id;
    String name;
    List<String> domain_names;
    String created_at;
    String details;
    Boolean shared_tickets;
    List<String> tags;

    @Override
    public String toString(){
        return tableFormatter(this);
    }
}
