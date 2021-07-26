package domain;

import config.DataPreparation;
import lombok.Data;

import java.util.List;

import static domain.CommonUtil.tableFormatter;

@Data
public class Ticket {
    String _id;
    String url;
    String external_id;
    String created_at;
    String type;
    String subject;
    String description;
    String priority;
    String status;
    Long submitter_id;
    Long assignee_id;
    Long organization_id;
    List<String> tags;
    Boolean has_incidents;
    String due_at;
    String via;

    @Override
    public String toString() {
        return tableFormatter(this) +
                "\n related entities:\n ---------------------------- \n" +
                "======Submitter:====== \n" + DataPreparation.users.stream()
                .filter(user -> user.get_id().equals(submitter_id)).findFirst() +
                "\n======Assignee:===== \n" + DataPreparation.users.stream()
                .filter(user -> user.get_id().equals(assignee_id)).findFirst() +
                "\n======organisation:====== \n" + DataPreparation.organisations.stream()
                .filter(organisation -> organisation.get_id().equals(organization_id)).findFirst();
    }

}