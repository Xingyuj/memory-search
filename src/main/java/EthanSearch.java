import domain.Organisation;
import domain.Ticket;
import domain.User;

import java.util.Map;
import java.util.Scanner;

import static domain.CommonUtil.printSearchableFields;

public class EthanSearch {

    public static void main(String[] args) {
        final String welcomeInfo = "Welcome to ethan search\ntype 'quit' to exit at any time, press 'enter' to continue";
        final String instructionInfo = "\n=========================" +
                "\n* press 1 to search an entity\n* press 2 to view a list of searchable fields" +
                "\n* Type 'quit' to exit\n";
        Scanner in = new Scanner(System.in);
        System.out.println(welcomeInfo);
        in.nextLine();
        while (true) {
            System.out.println(instructionInfo);
            String input = in.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }
            switch (input) {
                case "1":
                    try {
                        Map<String, String> param = SearchStep.assembleParam(in);
                        SearchStep searchStep = new SearchStep();
                        searchStep.search(param.get("key"), param.get("value"), EntityType.valueOf(param.get("type")))
                                .forEach(System.out::println);
                    } catch (NoSuchFieldException e) {
                        System.out.println("No such field in the Entity, " +
                                "please check list of searchable fields by press 2");
                        continue;
                    }
                    break;
                case "2":
                    printSearchableFields(User.class.getDeclaredFields(), "User");
                    printSearchableFields(Ticket.class.getDeclaredFields(), "Ticket");
                    printSearchableFields(Organisation.class.getDeclaredFields(), "Organisation");
                    break;
                default:
            }
        }
        in.close();
    }
}
