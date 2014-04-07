package gameMechanic;

import gameClasses.Field;
import gameClasses.Field.checker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.misc.ExtensionInstallationException;
import utils.VFS;

import static org.mockito.Mockito.*;

/**
 * Created by phil on 07.04.14.
 */
public class GameSessionTest {
    GameSession game = new GameSession(1,2);
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetAnotherColor() throws Exception {
        checker check = checker.black;
        Assert.assertEquals(checker.white, game.getAnotherColor_pub(check));
        check = checker.white;
        Assert.assertEquals(checker.black,game.getAnotherColor_pub(check));
        check = checker.nothing;
        Assert.assertEquals(checker.nothing,game.getAnotherColor_pub(check));
    }

    @Test
    public void testGetPlayerColor() throws Exception {
        int id =1;
        Assert.assertEquals(checker.white, game.getPlayerColor_pub(id));
        id = 2;
        Assert.assertEquals(checker.black, game.getPlayerColor_pub(id));
    }

    @Test
    public void testChecking() throws Exception {
        Assert.assertEquals(false, game.Checking_pub(1, 1, 1, 1, 1));
        Assert.assertEquals(false, game.Checking_pub(2, 1, 1, 1, 1));
        Assert.assertEquals(false, game.Checking_pub(3, 1, 1, 1, 1));
        Assert.assertEquals(false, game.Checking_pub(4, 1, 1, 1, 3));
        Assert.assertEquals(false, game.Checking_pub(1, 1, 3, 1, 3));
    }

    @Test
    public void testStandartCheck() throws Exception {
        Assert.assertEquals(false, game.standartCheck_pub( 0, 1, 1, 0));
        Assert.assertEquals(false, game.standartCheck_pub( 1, 1, 1, 0));
        Assert.assertEquals(false, game.standartCheck_pub( 1, 0, 1, 1));
        Assert.assertEquals(false, game.standartCheck_pub( 1, 1, 1, 1));
        Assert.assertEquals(false, game.standartCheck_pub( 65535, 1, 1, 1));
        Assert.assertEquals(false, game.standartCheck_pub( 1, 65535, 1, 1));
        Assert.assertEquals(false, game.standartCheck_pub( 1, 1, 65535, 1));
        Assert.assertEquals(false, game.standartCheck_pub( 1, 1, 1, 65535));
        Assert.assertEquals(true, game.standartCheck_pub(1,1,1,3));
    }

    @Test
    public void testpawnEating() throws Exception {
        Assert.assertEquals(false, game.pawnEating_pub(0,1,0,1));
        Assert.assertEquals(false, game.pawnEating_pub(0,1,2,0));
        Assert.assertEquals(false, game.pawnEating_pub(0,2,2,1));
        Assert.assertEquals(false, game.pawnEating_pub(0,0,2,2));
        Assert.assertEquals(true, game.pawnEating_pub(3,0,5,2));

    }

    @Test
    public void testEating() throws Exception {
        Assert.assertEquals(false, game.eating_pub(0,1,0,1));
        Assert.assertEquals(false, game.eating_pub(0, 1, 2, 0));
        Assert.assertEquals(false, game.eating_pub(0, 2, 2, 1));
        Assert.assertEquals(false, game.eating_pub(0, 0, 2, 2));
        Assert.assertEquals(true, game.eating_pub(3, 0, 5, 2));
    }

    @Test
    public void testMakeUsualStroke() throws Exception {
        Assert.assertEquals(true, game.makeUsualStroke_pub(0,0,0,0));
        Assert.assertEquals(false, game.makeUsualStroke_pub(0,0,0,0));
    }
    @Test
    public void testCheckStroke() throws Exception {

    }

    @Test
    public void testGetAnotherId() throws Exception {

    }

    @Test
    public void testGetWinnerId() throws Exception {

    }

    @Test
    public void testGetSnapshot() throws Exception {

    }

    @Test
    public void testSaveAILog() throws Exception {

    }

    @Test
    public void testSaveLog() throws Exception {
        int id = 1;
        int winnerid = 1;
        game.saveLog(winnerid);
        String fileName="/log/AI/"+String.valueOf(id)+".txt";
        String compare = VFS.readFile(fileName);
        Assert.assertEquals("white", compare);
        winnerid = 2;
        game.saveLog(winnerid);
        fileName="/log/AI/"+String.valueOf(id)+".txt";
        compare = VFS.readFile(fileName);
        Assert.assertEquals("black", compare);
    }

    @Test
    public void testGetNext() throws Exception {

    }

    @Test
    public void testGetFields() throws Exception {

    }

    @Test
    public void testGetWhiteQuantity() throws Exception {

    }

    @Test
    public void testGetBlackQuantity() throws Exception {

    }
}
