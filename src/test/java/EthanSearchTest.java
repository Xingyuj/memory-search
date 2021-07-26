import domain.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EthanSearchTest {
    @Test
    public void testSearchEntityValid() {
        String type = "TICKET";
        String key = "_id";
        String value = "1a227508-9f39-427c-8f57-1b72f3fab87c";

        EthanSearch ethanSearch = new EthanSearch();
        List<?> ticketResult = ethanSearch.search(key, value, Type.valueOf(type));
        Assertions.assertEquals(1, ticketResult.size());
        Assertions.assertInstanceOf(Ticket.class, ticketResult.get(0));
    }

    @Test
    public void testSearchEntityInvalid() {
        String type = "USER";
        String key = "_id";
        String value = "1a21___+";

        EthanSearch ethanSearch = new EthanSearch();
        List<?> ticketResult = ethanSearch.search(key, value, Type.valueOf(type));
        Assertions.assertEquals(ticketResult.size(), 0);
    }

    @Test
    public void testSearchEmptyValue() {
        String type = "TICKET";
        String key = "description";
        String value = "";

        EthanSearch ethanSearch = new EthanSearch();
        List<?> ticketResult = ethanSearch.search(key, value, Type.valueOf(type));
        Assertions.assertEquals(1, ticketResult.size());
    }
}
