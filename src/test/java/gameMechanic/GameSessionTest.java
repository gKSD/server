package gameMechanic;

import gameClasses.Field;
import gameClasses.Field.checker;
import gameClasses.Snapshot;
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
    public void testkingCanEatRightUp() throws Exception {
        Assert.assertEquals(false,game.kingCanEatRightUp_pub(1,1));
        game.move_pub(1,1,4,2);
        Assert.assertEquals(false,game.kingCanEatRightUp_pub(4,2));
        Assert.assertEquals(false,game.kingCanEatRightUp_pub(4,4));

    }
    @Test
    public void testkingCanEatLeftUp() throws Exception {
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,6));
        game.move_pub(7,7,4,2);
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(5,0));
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,5));
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(5,1));
      /*  Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,4));
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,3));
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,2));
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,1));
        Assert.assertEquals(false,game.kingCanEatLeftUp_pub(0,0));*/

    }
    @Test
    public void testkingCanEatRightDown() throws Exception {
        Assert.assertEquals(false,game.kingCanEatRightDown_pub(7,1));
        Assert.assertEquals(false,game.kingCanEatRightDown_pub(0,1));
        Assert.assertEquals(false,game.kingCanEatRightDown_pub(0,0));


    }
    @Test
    public void testkingCanEatLeftDown() throws Exception {
        Assert.assertEquals(false,game.kingCanEatLeftDown_pub(6,6));
        Assert.assertEquals(false,game.kingCanEatLeftDown_pub(1,1));
    }

    @Test
    public void testcheckotherEatingOpportunity() throws Exception {
        Assert.assertEquals(false, game.checkOtherEatingOpportunityForField_pub(2, 2, 4, 4));
        game = new GameSession(1,2);
        game.move_pub(7,7,3,3);
        Assert.assertEquals(true, game.checkOtherEatingOpportunityForField_pub(2, 2, 4, 4));
        game = new GameSession(1,2);
        game.move_pub(7,7,3,3);
        game.move_pub(5,1,3,1);
        Assert.assertEquals(true, game.checkOtherEatingOpportunityForField_pub(2, 2, 4, 4));
    }


    @Test
    public void testEat() throws Exception {
        game.move_pub(7,7,3,3);
        game.eat_pub(2,2,4,4);
        Assert.assertEquals(23,game.getFields().length);
        game.move_pub(0,0,4,2);
        game.eat_pub(5,3,3,1);
        Assert.assertEquals(22,game.getFields().length);
        game.eat_pub(5,7,3,5);
       // Assert.assertEquals(22,game.getFields().length);

    }

    @Test
    public void testcheckOtherEatingOpportunity() throws Exception {
       Assert.assertEquals(false, game.checkOtherEatingOpportunity_pub(2, -7, 2, 2, 4, 4));
       Assert.assertEquals(true, game.checkOtherEatingOpportunity_pub(2, 2, 2, 2, 4, 4));
        //Assert.assertEquals(true, game.checkOtherEatingOpportunity_pub(1,2,2, 2, 4, 4);
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
        GameSession testObj = new GameSession(1,4,8,3);
        Field x;
        int i,j;
        boolean testRes = testObj.checkStroke(1,0,5,1,4);
        Assert.assertEquals(testRes,true);
        testRes = testObj.checkStroke(4,0,5,1,4);
        Assert.assertEquals(testRes, true);
        testObj.currentPositions[4][6] = new Field(checker.black);
        testObj.currentPositions[5][7] = new Field(checker.nothing);
        testObj.currentPositions[3][5] = new Field(checker.white);
        testObj.currentPositions[2][6] = new Field(checker.nothing);
        for (i=0;i<8;i++) {
            for (j=0; j<8; j++) {
                System.out.print(testObj.currentPositions[i][j].getType()+" ");
            }
            System.out.println();
        }
        testRes = testObj.checkStroke(1,5,4,7,2);
        Assert.assertEquals(testRes,true);
        Assert.assertEquals(false, game.checkStroke(1,1,1,0,0));
        Assert.assertEquals(false, game.checkStroke(2,1,1,0,0));
        GameSession testObj2 = new GameSession(2,3,8,3);
        testObj2.currentPositions[4][6] = new Field(checker.black);
        testObj2.currentPositions[5][7] = new Field(checker.nothing);
        testObj2.currentPositions[3][5] = new Field(checker.white);
        testObj2.currentPositions[2][6] = new Field(checker.nothing);
        //сделаем королём одну:
        testObj2.currentPositions[3][5].makeKing();
        testRes = testObj2.checkStroke(2,5,4,7,2);
        Assert.assertEquals(testRes,true);
        //пожирание своего:
        testObj2.currentPositions[3][5] = new Field(checker.white);
        testObj2.currentPositions[4][6] = new Field(checker.white);
        testObj2.currentPositions[5][7] = new Field(checker.nothing);
        testRes = testObj2.checkStroke(2,7,2,5,4);
        Assert.assertEquals(testRes,false);
        //проверка на возможность съесть ещё кого-то
        testObj2.currentPositions[4][6] = new Field(checker.black);
        testObj2.currentPositions[7][5] = new Field(checker.nothing);
        System.out.println();
        for (i=0;i<8;i++) {
            for (j=0; j<8; j++) {
                System.out.print(testObj.currentPositions[i][j].getType()+" ");
            }
            System.out.println();
        }
        testRes = testObj2.checkStroke(2,2,5,0,3);
        Assert.assertEquals(testRes, false);
    }

    @Test
    public void becameKingTest() {
        GameSession testObj = new GameSession(1,4,8,3);
        int i,j;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        testObj.currentPositions[5][7] = new Field(checker.white);
        testObj.currentPositions[2][6] = new Field(checker.black);
        boolean testRes = testObj.checkStroke(1,7,2,5,0);
        Assert.assertEquals(testRes,true);
        for (i=0;i<8;i++) {
            for (j=0; j<8; j++) {
                System.out.print(testObj.currentPositions[i][j].getType()+" ");
            }
            System.out.println();
        }
        testObj.changeLastStroke(1);
        testRes = testObj.checkStroke(4,6,5,4,7);
        Assert.assertEquals(testRes,false);
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        testObj.currentPositions[7][1] = new Field(checker.white);
        testRes =testObj.becameKing_pub(1,7);
        Assert.assertEquals(testRes, true);
        testRes =testObj.becameKing_pub(1,0);
        Assert.assertEquals(testRes, false);
        testObj.currentPositions[7][1] = new Field(checker.black);
        testRes =testObj.becameKing_pub(1,7);
        Assert.assertEquals(testRes, false);
        testRes =testObj.becameKing_pub(5,7);
        Assert.assertEquals(testRes, false);
        testObj.currentPositions[0][0] = new Field(checker.black);
        testRes =testObj.becameKing_pub(0,0);
        Assert.assertEquals(testRes,true) ;
    }

    @Test
    public void makeUsualStrokeTest() {
        GameSession testObj = new GameSession(1,4,8,3);
        int i,j;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
            }
        }
        testObj.currentPositions[6][0] = new Field(checker.white);
        boolean testRes = testObj.makeUsualStroke_pub(0,1,1,0);
        Assert.assertEquals(testRes,false);
        testObj.move_pub(7,7,1,0);
        testObj.currentPositions[7][7]= new Field(checker.white);
       // Assert.assertEquals(true, testObj.makeUsualStroke_pub(1,1,7,7));
    }

    @Test
    public void canEatTest() {
        GameSession testObj = new GameSession(1,2);
        Assert.assertTrue(testObj.canEat_pub(7, 4));
        testObj.checkStroke(1, 6, 5, 7, 4);
        Assert.assertTrue(testObj.canEat_pub(1, 4));
        testObj.checkStroke(2,2,5,1,4);
        Assert.assertTrue(testObj.canEat_pub(5, 2));
        testObj.checkStroke(1,7,4,5,2);
    }

    @Test
    public void testGetAnotherId() throws Exception {

    }

    @Test
    public void testGetWinnerId() throws Exception {

    }

/*    @Test
    public void testGetSnapshot() throws Exception {
        int id = 2;

        Assert.assertEquals('w', game.getSnapshot(id));
        id = 1;
        Assert.assertEquals(new Snapshot(), game.getSnapshot(id));
    }
*/
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
        int[] field = game.getFields();
        Assert.assertEquals(24,field.length);
        game.makeking_for_test(0,2);
        game.makeking_for_test(7,7);
        field = game.getFields();
        Assert.assertEquals(-16,field[8]);
        Assert.assertEquals(-63,field[23]);
        //Assert.assertEquals(game);
       // Assert.assertEquals(24,);

    }

    @Test
    public void testGetWhiteQuantity() throws Exception {

    }

    @Test
    public void testGetBlackQuantity() throws Exception {

}
    
    @Test
    public void normalTest() {
        GameSession testObj = new GameSession(1,2,8,3);
        int testRes = testObj.normal_pub(4);
        Assert.assertEquals(testRes,1);
        testRes = testObj.normal_pub(0);
        Assert.assertEquals(testRes,0);
    }

    @Test
    public void inBorderTest() {
        GameSession testObj = new GameSession(1,2,8,3);
        boolean testRes = testObj.inBorder_pub(1);
        Assert.assertEquals(testRes,true);
        testRes = testObj.inBorder_pub(10);
        Assert.assertEquals(testRes,false);
        testRes = testObj.inBorder_pub(-1);
        Assert.assertEquals(testRes,false);
    }

    @Test
    public void canMoveTests() {
        GameSession testObj = new GameSession(1,2,8,3);
        //canMoveRightUp
        boolean testRes = testObj.canMoveRightUp(7,6);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveRightUp(2,0);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveRightUp(4,50);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveRightUp(2,5);
        Assert.assertEquals(testRes,true);
        //RightDown
        testRes = testObj.canMoveRightDown(1, 0);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveRightDown(20, 15);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveRightDown(20, -15);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveRightDown(1, 2);
        Assert.assertEquals(testRes,true);
        //LeftUp
        testRes = testObj.canMoveLeftUp(4,0);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveLeftUp(-1,1);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveLeftUp(1,10);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveLeftUp(1,2);
        Assert.assertEquals(testRes,true);
        //LeftDown
        testRes = testObj.canMoveLeftDown(2, 6);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveLeftDown(-1,2);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveLeftDown(1,-2);
        Assert.assertEquals(testRes,false);
        testRes = testObj.canMoveLeftDown(2,5);
        Assert.assertEquals(testRes,true);
        //CanMoveInt white
        testObj.currentPositions[3][3]= new Field(checker.black);
        testRes = testObj.canMoveInt_pub(2, 2);
        Assert.assertEquals(testRes, true);
        testRes = testObj.canMoveInt_pub(0,2);
        Assert.assertEquals(testRes, true);
        testRes = testObj.canMoveInt_pub(1,1);
        Assert.assertEquals(testRes, false);
        //CanMoveInt black
        testRes = testObj.canMoveInt_pub(7,5);
        Assert.assertEquals(testRes, true);
        testRes = testObj.canMoveInt_pub(0,6);
        Assert.assertEquals(testRes, false);
        testObj.currentPositions[4][2]= new Field(checker.black);
        testRes = testObj.canMoveInt_pub(4,5);
        Assert.assertEquals(testRes, true);
    }

    @Test
    public void pawnCanEatTests() {
        GameSession testObj = new GameSession(1,2,8,3);
        int i,j;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        testObj.currentPositions[2][0]= new Field(checker.white);
        testObj.currentPositions[3][1]= new Field(checker.black);
        boolean testRes = testObj.pawnCanEatRightUp_pub(0,2);
        Assert.assertEquals(testRes,true);
        testObj.currentPositions[1][1]= new Field(checker.black);
        testRes = testObj.pawnCanEatRightDown_pub(0,2);
        Assert.assertEquals(testRes,true);
        testObj.currentPositions[0][2]= new Field(checker.black);
        testRes = testObj.pawnCanEatRightDown_pub(0,2);
        Assert.assertEquals(testRes,false);
        testObj.currentPositions[1][1]= new Field(checker.white);
        testRes = testObj.pawnCanEatRightDown_pub(0,2);
        Assert.assertEquals(testRes,false);
        testRes = testObj.pawnCanEatRightDown_pub(7,2);
        Assert.assertEquals(testRes,false);
        testRes = testObj.pawnCanEatRightDown_pub(0,0);
        Assert.assertEquals(testRes,false);
        //LeftDown
        testObj.currentPositions[2][2]= new Field(checker.white);
        testObj.currentPositions[1][1]= new Field(checker.black);
        testObj.currentPositions[0][0]= new Field(checker.nothing);
        testRes = testObj.pawnCanEatLeftDown_pub(2, 2);
        Assert.assertEquals(testRes,true);
        testObj.currentPositions[0][0]= new Field(checker.black);
        testRes = testObj.pawnCanEatLeftDown_pub(2, 2);
        Assert.assertEquals(testRes,false);
        testObj.currentPositions[1][1]= new Field(checker.white);
        testRes = testObj.pawnCanEatLeftDown_pub(2, 2);
        Assert.assertEquals(testRes,false);
        testRes = testObj.pawnCanEatLeftDown_pub(2, 2);
        Assert.assertEquals(testRes,false);
        testRes = testObj.pawnCanEatLeftDown_pub(2, 2);
        Assert.assertEquals(testRes,false);
    }
    
    @Test
    public void winLoseTests() {
        GameSession testObj = new GameSession(1,2,8,3);
        int i,j;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        //blackLose
        testObj.getBlackQuantity1_pub();
        boolean testRes = testObj.blackLose();
        Assert.assertEquals(testRes,false);
        testObj.getBlackQuantity_pub();
        testRes = testObj.blackLose();
        Assert.assertEquals(testRes,true);
        testObj.currentPositions[1][1]= new Field(checker.black);
        testRes = testObj.blackLose();
        Assert.assertEquals(testRes,true);
        testObj.getBlackQuantity1_pub();
        testRes = testObj.blackLose();
        Assert.assertEquals(testRes,false);
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.white);
                }
            }
        }
        testRes = testObj.blackLose();
        Assert.assertEquals(testRes,true);

        testObj.changeLastStroke(2);
        testRes = testObj.blackWin(111110000);
        Assert.assertEquals(testRes,false);
        testRes = testObj.blackWin(0);
        Assert.assertEquals(testRes,false);
        testObj.changeLastStroke(1);
        testRes = testObj.blackWin(11111);
        Assert.assertEquals(testRes,false);
        testRes = testObj.blackWin(0);
        Assert.assertEquals(testRes,false);

        //whiteLose
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        //blackLose
        testObj.getkWhiteQuantuty1_pub();
        testRes = testObj.whiteLose();
        Assert.assertEquals(testRes,false);
        testObj.getkWhiteQuantuty_pub();
        testRes = testObj.whiteLose();
        Assert.assertEquals(testRes,true);
        testObj.currentPositions[1][1]= new Field(checker.black);
        testRes = testObj.whiteLose();
        Assert.assertEquals(testRes,true);
        testObj.getkWhiteQuantuty1_pub();
        testRes = testObj.whiteLose();
        Assert.assertEquals(testRes,false);
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.black);
                }
            }
        }
        testRes = testObj.whiteLose();
        Assert.assertEquals(testRes,true);
        testObj.getkWhiteQuantuty_pub();
        testRes = testObj.whiteLose();
        Assert.assertEquals(testRes,true);
    }

    @Test
    public void getWinnerIdTest() {
        GameSession testObj = new GameSession(1,2,8,3);
        int i,j;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        //не blackLose и whiteWin
        testObj.currentPositions[3][3]= new Field(checker.black);
        testObj.getBlackQuantity1_pub();
        testObj.changeLastStroke(1);
        testObj.changeLastStrokeTime(0);
        int testRes = testObj.getWinnerId(240000);
        Assert.assertEquals(testRes,1);
        //blackLose,whiteWin
        testObj.getBlackQuantity_pub();
        testRes = testObj.getWinnerId(240000);
        Assert.assertEquals(testRes,1);
        //blackLose, !whiteWin
        testRes = testObj.getWinnerId(0);
        Assert.assertEquals(testRes,1);
        //!blackLose, !whiteWin
        testObj.getBlackQuantity1_pub();
        testRes = testObj.getWinnerId(0);
        Assert.assertEquals(testRes,0);
        //!whiteLose, blackWin
        testObj.currentPositions[3][3]= new Field(checker.white);
        testObj.getkWhiteQuantuty1_pub();
        testObj.changeLastStroke(2);
        testRes = testObj.getWinnerId(240000);
        Assert.assertEquals(testRes,2);
        //whiteLose, blackWin
        testObj.getkWhiteQuantuty_pub();
        testRes = testObj.getWinnerId(240000);
        Assert.assertEquals(testRes,2);
        //whiteLose, !blackWin
        testObj.changeLastStroke(1);
        testRes = testObj.getWinnerId(0);
        Assert.assertEquals(testRes,2);
    }

    @Test
    public void kingCanEatTestOneMore() {
        GameSession testObj = new GameSession(1,2,8,3);
        int i,j;
        for (i=0;i<8;i++) {
            for (j=0;j<8;j++) {
                if (i!=6 & i!=1) {
                    testObj.currentPositions[i][j]= new Field(checker.nothing);
                }
            }
        }
        //0 0 0 1
        testObj.currentPositions[6][7]= new Field(checker.white);
        testObj.currentPositions[5][6]= new Field(checker.black);
        boolean testRes = testObj.kingCanEat_pub(7,6);
        Assert.assertEquals(testRes,true);
        //0 0 1 1
        testObj.currentPositions[4][7]= new Field(checker.white);
        testRes = testObj.kingCanEat_pub(7,4);
        Assert.assertEquals(testRes,true);
        //0 1 ..
        testObj.currentPositions[7][0]= new Field(checker.white);
        testObj.currentPositions[6][1]= new Field(checker.black);
        testRes = testObj.kingCanEat_pub(0,7);
        Assert.assertEquals(testRes,true);
        //1 ...
        testObj.currentPositions[5][0]= new Field(checker.white);
        testRes = testObj.kingCanEat_pub(0,5);
        Assert.assertEquals(testRes,false);

        testObj.currentPositions[0][0]= new Field(checker.white);
        testObj.currentPositions[1][1]= new Field(checker.black);
        testRes = testObj.kingCanEat_pub(0,0);
        Assert.assertEquals(testRes,true);
    }
}
