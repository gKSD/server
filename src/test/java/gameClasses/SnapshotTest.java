package gameClasses;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 07.04.14.
 */
public class SnapshotTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void toStringTest() {
        Field[][] testMatrix = new Field[3][3];
        testMatrix[0][0] = new Field(Field.checker.nothing);
        testMatrix[1][0] = new Field(Field.checker.white);
        testMatrix[0][1] = new Field(Field.checker.nothing);
        testMatrix[1][1] = new Field(Field.checker.black);
        testMatrix[2][0] = new Field(Field.checker.black);
        testMatrix[2][1] = new Field(Field.checker.nothing);
        testMatrix[0][2] = new Field(Field.checker.white);
        testMatrix[1][2] = new Field(Field.checker.nothing);
        testMatrix[2][2] = new Field(Field.checker.black);
        Snapshot testObj = new Snapshot(testMatrix,'b',3,'0');
        String testRes = testObj.toStringTest();
        Assert.assertEquals(testRes, "{'status':'snapshot','next':'0','color':'b','field':[['nothing', 'nothing', 'white'], ['white', 'black', 'nothing'], ['black', 'nothing', 'black']],'king':[['false', 'false', 'false'], ['false', 'false', 'false'], ['false', 'false', 'false']]}");
        testRes = testObj.toString();
        Assert.assertEquals(testRes, "{\"status\":\"snapshot\",\"next\":\"0\",\"color\":\"b\",\"field\":[[\"nothing\", \"nothing\", \"white\"], [\"white\", \"black\", \"nothing\"], [\"black\", \"nothing\", \"black\"]],\"king\":[[\"false\", \"false\", \"false\"], [\"false\", \"false\", \"false\"], [\"false\", \"false\", \"false\"]]}");
        Snapshot testObj2 = new Snapshot(testMatrix,'w',3,'0');
        testRes = testObj2.toStringTest();
        Assert.assertEquals(testRes, "{'status':'snapshot','next':'0','color':'w','field':[['nothing', 'nothing', 'white'], ['white', 'black', 'nothing'], ['black', 'nothing', 'black']],'king':[['false', 'false', 'false'], ['false', 'false', 'false'], ['false', 'false', 'false']]}");
        testRes = testObj2.toString();
        Assert.assertEquals(testRes, "{\"status\":\"snapshot\",\"next\":\"0\",\"color\":\"w\",\"field\":[[\"nothing\", \"nothing\", \"white\"], [\"white\", \"black\", \"nothing\"], [\"black\", \"nothing\", \"black\"]],\"king\":[[\"false\", \"false\", \"false\"], [\"false\", \"false\", \"false\"], [\"false\", \"false\", \"false\"]]}");
   }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
