import config.DataPreparation;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchStep {

    public SearchStep() {
        DataPreparation.loadData();
    }

    public static void assembleParam(Scanner in) {
        Map<String, String> param = new HashMap<>();
        String info = "select 1. User 2. Ticket 3. Organisation";
        System.out.println(info);
        String input = in.nextLine();
        switch (input) {
            case "1":
                param.put("type", "USER");
                break;
            case "2":
                param.put("type", "TICKET");
                break;
            case "3":
                param.put("type", "ORGANISATION");
                break;
            default:
        }
        System.out.println("enter key");
        input = in.nextLine();
        param.put("key", input);
        System.out.println("enter value");
        input = in.nextLine();
        param.put("value", input);

        SearchStep searchStep = new SearchStep();
        searchStep.search(param.get("key"), param.get("value"), EntityType.valueOf(param.get("type")))
                .forEach(System.out::println);
    }

    public List<?> search(String key, String value, EntityType type) {
        switch (type) {
            case USER:
                return filter(key, value, DataPreparation.users);
            case TICKET:
                return filter(key, value, DataPreparation.tickets);
            case ORGANISATION:
                return filter(key, value, DataPreparation.organisations);
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
