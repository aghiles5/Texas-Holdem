package players;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * This is a test file for the Player class and its child classes, AI class and
 * Human class
 *
 * @author John Lowie
 * @version 04/06/19
 */

public class PlayerTest {

    @Test
    public void testHumanConstructor() { // Tests Human class constructors
        Human a = new Human("Jeff");
        assertEquals("Jeff", a.getName());
        assertEquals(null, a.getStack());

        Human b = new Human(a.getName(), 1000);
        assertEquals("Jeff", b.getName());
        assertEquals(1000, b.getStack());
    }

    @Test
    public void testHumanDecisions() {
        Human a = new Human("Johnny", 2500);

        
    }
    }
}