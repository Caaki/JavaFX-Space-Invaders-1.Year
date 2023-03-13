package sample;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author nikol
 */
public class PlayerTest {

    public PlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    @Test
    public void testFinalScore() {
        System.out.println("brojParnihBrojeva");
        Player a = new Player();
        int result = a.finalScore(20);
        assertEquals(60, result);
    }
    @Test
    public void testFinalScore2() {
        System.out.println("brojParnihBrojeva");
        Player a = new Player();
        int result = a.finalScore(-56);
        assertEquals(-1, result);
    }
}

