import java.util.*;

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
        testing();
    }

    public static void testing() {
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
        System.out.println(a.getCPUName());
        System.out.println(b.getCPUName());
        System.out.println(c.getCPUName());
        System.out.println(d.getCPUName());
        System.out.println(e.getCPUName());
        System.out.println(f.getCPUName());
        System.out.println(g.getCPUName());
        System.out.println(h.getCPUName());
        System.out.println(i.getCPUName());
        System.out.println(j.getCPUName());
    }

 }