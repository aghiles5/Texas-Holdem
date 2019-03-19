/**
 * This is a test file for the Player class and its child classes, AI class and
 * Human class
 * 
 * JUnit testing will not be used as there were unresolved technical difficulties
 * importing JUnit test packages
 * 
 * @author John Lowie
 * @version 03/18/19
 */

 public class PlayerTest {

    public static void main(String[] args) {
        testingAI();
    }

    public static void testingAI() {
        AI.addCPUName();
        AI a = new AI();
        AI b = new AI();
        AI c = new AI();
        AI d = new AI();
        AI e = new AI();
        AI f = new AI();
        AI g = new AI();
        AI h = new AI();
        AI i = new AI();
        AI j = new AI();
        System.out.println(a.getName());
        System.out.println(b.getName());
        System.out.println(c.getName());
        System.out.println(d.getName());
        System.out.println(e.getName());
        System.out.println(f.getName());
        System.out.println(g.getName());
        System.out.println(h.getName());
        System.out.println(i.getName());
        System.out.println(j.getName());
        // NAMING ERRORS: REPEATED NAMES OF AI PLAYERS
    }

 }