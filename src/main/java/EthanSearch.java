import config.DataPreparation;
import domain.Organisation;
import domain.Ticket;
import domain.User;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

enum Type {
    USER,
    TICKET,
    ORGANISATION
}

public class EthanSearch {
    private final List<User> users;
    private final List<Ticket> tickets;
    private final List<Organisation> organisations;

    public EthanSearch() {
        DataPreparation.loadData();
        this.users = DataPreparation.users;
        this.tickets = DataPreparation.tickets;
        this.organisations = DataPreparation.organisations;
    }


    public static void main(String[] args) {
        String type = "TICKET";
        String key = "_id";
        String value = "1a227508-9f39-427c-8f57-1b72f3fab87c";

        EthanSearch ethanSearch = new EthanSearch();
        ethanSearch.search(key, value, Type.valueOf(type)).forEach(System.out::println);
    }

    public List<?> search(String key, String value, Type type) {
        switch (type) {
            case USER:
                return filter(key, value, users);
            case TICKET:
                return filter(key, value, tickets);
            case ORGANISATION:
                return filter(key, value, organisations);
            default:
                System.out.println("not a support type");
                return null;
        }
    }

    private List<?> filter(String key, String value, List<?> list) {
        return list.stream().filter(user -> {
            Field field;
            try {
                field = user.getClass().getDeclaredField(key);
                field.setAccessible(true);
                Object valueStored = field.get(user);
                if (field.getType().isArray()) {
                    return ((List<?>) valueStored).contains(value);
                } else {
                    if (valueStored != null) {
                        return value.equals(valueStored.toString());
                    }
                    return StringUtils.isBlank(value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());
    }
}
