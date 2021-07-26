package domain;

import config.DataPreparation;
import lombok.Data;

import java.util.List;

import static domain.CommonUtil.tableFormatter;

@Data
public class User {
    Long _id;
    String url;
    String external_id;
    String name;
    String alias;
    String created_at;
    Boolean active;
    Boolean verified;
    Boolean shared;
    String locale;
    String timezone;
    String last_login_at;
    String email;
    String phone;
    String signature;
    Long organization_id;
    List<String> tags;
    Boolean suspended;
    String role;

    @Override
    public String toString(){
        return tableFormatter(this) +
                "\n related entities:\n ---------------------------- \n" +
                "======organisation:====== \n" + DataPreparation.organisations.stream()
                .filter(organisation -> organisation.get_id().equals(organization_id)).findFirst();
    }
}
