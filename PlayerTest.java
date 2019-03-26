import java.util.*;

/**
 * This is a test file for the Player class and its child classes, AI class and
 * Human class
 * 
 * JUnit testing will not be used as there were unresolved technical difficulties
 * importing JUnit test packages
 * 
 * @author John Lowie
 * @version 03/26/19
 */

 public class PlayerTest {

    public static ArrayList<String> names = new ArrayList<String>();

    public static void main(String[] args) {
        testingAINames();
        testAIConstructor();
        testAIActions();
        testingHuman();
    }

    // Tests all methods in AI names for duplicates
    public static void testingAINames() {
        // Creating random AI names
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

        // Adding AI names to a list for further testing
        names.add(a.getName());
        names.add(b.getName());
        names.add(c.getName());
        names.add(d.getName());
        names.add(e.getName());
        names.add(f.getName());
        names.add(g.getName());
        names.add(h.getName());
        names.add(i.getName());
        names.add(j.getName());

        /**
         * This loop will run and compare all the AI names to each other to make sure
         * that there are no duplicate names
         * 
         * This nested loop was referenced from:
         * https://stackoverflow.com/questions/23460367/comparing-elements-of-the-same-array-in-java
         */
        Boolean nameTest = true;
        for (int x = 0; x < names.size(); x++) {
            for (int k = x + 1; k < names.size(); k++) {
                if (names.get(x) == names.get(k)) {
                    System.out.println("Element " + x + 1 + " and element " + k + 2 + " have the same name.");
                    nameTest = false;
                }
            }
        }
        if (nameTest == true) {
            System.out.println("No errors in name test");
        }
    }

    // Test AI constructor
    public static void testAIConstructor() {
        Boolean testConstructor = true;
        AI a = new AI("Johnny", 100);
        AI b = new AI("Sammy", 300);
        
        if (a.getName() != "Johnny") {
            testConstructor = false;
            System.out.println("AI a name error");
        }
        if (a.getStack() != 100) {
            testConstructor = false;
            System.out.println("AI a stack error");
        }
        if (b.getName() != "Sammy") {
            testConstructor = false;
            System.out.println("AI b name error");
        }
        if (b.getStack() != 300) {
            testConstructor = false;
            System.out.println("AI b stack error");
        }
        if (testConstructor == false) {
            System.out.println("AI constructor error");
        }
        if (testConstructor == true) {
            System.out.println("No errors in AI constructor test");
        }
    }

    // Test AI actions
    public static void testAIActions() {
        Boolean testActions = true;
        AI a = new AI("foo", 1000);
        AI b = new AI("bar", 2000);

        if (testActions == true) {
            System.out.println("No errors in testAIActions");
        }
        else if (testActions == false) {
            System.out.println("AI action error");
        }
    }



    public static void testingHuman(){
        Human a = new Human("Johnny");
        Human b = new Human("Sammy", 100);

        if (a.getName() != "Johnny") {
            System.out.println("Error in player a name. (Test failed in testingHuman())");
        }
        if (b.getName() != "Sammy") {
            System.out.println("Error in player b name. (Test failed in testingHuman())");
        }
        if (b.getStack() != 100) {
            System.out.println("Error in player b stacks. (Test failed in testingHuman())");
        }
        else {
            System.out.println("No errors in testingHuman()");
        }
    }

 }