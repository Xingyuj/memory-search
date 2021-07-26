import config.DataPreparation;
import domain.Organisation;
import domain.Ticket;
import domain.User;
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

    public static Map<String, String> assembleParam(Scanner in) throws NoSuchFieldException {
        Map<String, String> param = new HashMap<>();
        String info = "select 1. User 2. Ticket 3. Organisation";
        while(param.isEmpty()){
            System.out.println(info);
            String inputEntityType = in.nextLine();
            switch (inputEntityType) {
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
                    System.out.println("no such entity type\n");
            }
        }

        System.out.println("enter key");
        String inputKey = in.nextLine();
        param.put("key", inputKey);
        validKeyCheck(param);
        System.out.println("enter value");
        String input = in.nextLine();
        param.put("value", input);
        return param;
    }


    private static void validKeyCheck(Map<String, String> param) throws NoSuchFieldException {
        switch (param.get("type")) {
            case "USER":
                User.class.getDeclaredField(param.get("key"));
                break;
            case "TICKET":
                Ticket.class.getDeclaredField(param.get("key"));
                break;
            case "ORGANISATION":
                Organisation.class.getDeclaredField(param.get("key"));
                break;
            default:
        }
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
