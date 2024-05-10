import org.junit.Test;
import org.junit.Assert;

public class SlideGameTest {

    // an instance of SlideGame for testing purposes
    // so that I do not have to make my methods static
    SlideGame s = new SlideGame();

    int[][] g1 = {{1, 2, 4}, {8, 16, 2}, {2, 4, 2}}; 
    int[][] g2 = {{1, 0, 4}, {8, 16, 2}, {2, 4, 2}}; 
    int[][] g3 = {{0, 2, 0}, {0, 16, 0}, {0, 4, 2}}; 
    int[][] g4 = {{0, 2, 4}, {8, 16, 2}, {2, 4, 2}}; 
    int[][] g5 = {{1, 2, 4}, {8, 16, 2}, {2, 4, 0}}; 
    int[][] g5_2 = {{1, 2, 4}, {8, 0, 2}, {2, 4, 1}}; 
    int[][] g6 = {{1, 2, 4}, {0, 16, 2}, {2, 4, 1}}; 
    int[][] g7 = {{1, 2, 4}, {1, 16, 2}, {0, 4, 4}}; 

    @Test
    public void testGetterSetters(){

        Assert.assertEquals(4, s.getMainColumns());
        Assert.assertEquals(4, s.getMainRows());


        s.setMainColumns(40);
        s.setMainRows(20);

        Assert.assertEquals(40, s.getMainColumns());
        Assert.assertEquals(20, s.getMainRows());

    }

    @Test
    public void testGetTotalViable(){

        Assert.assertEquals(0, s.getTotalViable(9, g1));

        // test middle for inner loop
        Assert.assertEquals(1, s.getTotalViable(9, g2)); 

        // test middle for outer loop
        Assert.assertEquals(1, s.getTotalViable(9, g5_2)); 

        // test many
        Assert.assertEquals(5, s.getTotalViable(9, g3)); 

        // test start
        Assert.assertEquals(1, s.getTotalViable(9, g4)); 

         // test last
        Assert.assertEquals(1, s.getTotalViable(9, g5));

    }

    @Test
    public void testGetSpawnLocation() {

        int[] c2 = {0, 1};
        int[] c3_1 = {0, 0};
        int[] c3_2 = {0, 2};
        int[] c3_3 = {1, 0};
        int[] c3_4 = {1, 2};
        int[] c3_5 = {2, 0};
        int[] c4 = {0, 0};
        int[] c5 = {2, 2};

        // test 1, test middle
        Assert.assertArrayEquals(c2, s.getSpawnLocation(1, g2));

        // test many
        Assert.assertArrayEquals((c3_1), s.getSpawnLocation(1, g3));
        Assert.assertArrayEquals((c3_2), s.getSpawnLocation(2, g3));
        Assert.assertArrayEquals((c3_3), s.getSpawnLocation(3, g3));
        Assert.assertArrayEquals((c3_4), s.getSpawnLocation(4, g3));
        Assert.assertArrayEquals((c3_5), s.getSpawnLocation(5, g3));

        // test start
        Assert.assertArrayEquals(c4, s.getSpawnLocation(1, g4));

        // test last
        Assert.assertArrayEquals(c5, s.getSpawnLocation(1, g5));
    }

    @Test
    public void testMoveRowLeft(){

        int[] a1 = {1, 1, 0, 0};
        int[] a2 = {0, 1, 1, 0};
        int[] a3 = {1, 0, 0, 1};
        int[] a4 = {1, 1, 1, 1};
        int[] a5 = {1, 1, 2, 0};
        int[] a6 = {1, 1, 0, 2};
        int[] a7 = {128, 0, 128, 2, 64, 64, 0, 1};

        int[] n1 = {2, 0, 0, 0};
        int[] n2 = {2, 2, 0, 0};
        int[] n3 = {256, 2, 128, 1, 0, 0, 0, 0};

        int[] m1 = {1, 2, 4};
        int[] m2 = {1, 4, 0};
        int[] m3 = {2, 0, 0};

        // all of these test the else statement

        // these test the if statement, which combines the numbers
        Assert.assertArrayEquals(n1, s.moveRowLeft(a1)); 
        Assert.assertArrayEquals(n1, s.moveRowLeft(a2));
        Assert.assertArrayEquals(n1, s.moveRowLeft(a3));

        // checks for combinations
        Assert.assertArrayEquals(n2, s.moveRowLeft(a4));
        Assert.assertArrayEquals(n2, s.moveRowLeft(a5));
        Assert.assertArrayEquals(n2, s.moveRowLeft(a6));

        Assert.assertArrayEquals(n3, s.moveRowLeft(a7));

        // the else if statement is tested in these, isolated from if statement above
        Assert.assertArrayEquals(m1, s.moveRowLeft(g1[0]));
        Assert.assertArrayEquals(m2, s.moveRowLeft(g2[0]));

        Assert.assertArrayEquals(m3, s.moveRowLeft(g3[0]));

    }

    @Test
    public void testGetMoveLeft(){

        int[][] shifted2 = {{1, 4, 0}, {8, 16, 2}, {2, 4, 2}};
        int[][] shifted3 = {{2, 0, 0}, {16, 0, 0}, {4, 2, 0}};
        int[][] shifted6 = {{1, 2, 4}, {16, 2, 0}, {2, 4, 1}}; 
        int[][] shifted7 = {{1, 2, 4}, {1, 16, 2}, {8, 0, 0}}; 

        Assert.assertArrayEquals(g1, s.getMoveLeft(g1));
        Assert.assertArrayEquals(shifted2, s.getMoveLeft(g2));
        Assert.assertArrayEquals(shifted3, s.getMoveLeft(g3));
        Assert.assertArrayEquals(shifted6, s.getMoveLeft(g6));
        Assert.assertArrayEquals(shifted7, s.getMoveLeft(g7));

    }

    @Test
    public void testGetRotatedBoard(){

        int[][] g8 = {
            {1, 2, 3,},
            {4, 5, 6,},
            {7, 8, 9},
            {10, 11, 12},
            {13, 14, 15}};

        int[][] r8 = {
            {13, 10, 7, 4, 1},
            {14, 11, 8, 5, 2,},
            {15, 12, 9, 6, 3}};

        int[][] g9 = {
            {100, 101, 102, 103, 104, 105},
            {106, 107, 108, 109, 110, 111},
            {112, 113, 114, 115, 116, 117},
            {118, 119, 120, 121, 122, 123}};

        int[][] r9 = {
            {118, 112, 106, 100},
            {119, 113, 107, 101},
            {120, 114, 108, 102},
            {121, 115, 109, 103},
            {122, 116, 110, 104},
            {123, 117, 111, 105}};

        // test many
        Assert.assertArrayEquals(r8, s.getRotatedBoard(g8));
        Assert.assertArrayEquals(r9, s.getRotatedBoard(g9));

        int[][] g10 = {
            {1, 1, 1},
            {0, 0, 0},
            {0, 0, 0}};
        
        int[][] r10 = {
            {0, 0, 1},
            {0, 0, 1},
            {0, 0, 1}};

        int[][] g11 = {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0}};
        
        int[][] r11 = {
            {1, 1, 1},
            {0, 0, 0},
            {0, 0, 0}};

        // test start
        Assert.assertArrayEquals(r10, s.getRotatedBoard(g10));
        Assert.assertArrayEquals(r11, s.getRotatedBoard(g11));

        int[][] g12 = {
            {0, 0, 1},
            {0, 0, 1},
            {0, 0, 1}};
        
        int[][] r12 = {
            {0, 0, 0},
            {0, 0, 0},
            {1, 1, 1}};

        int[][] g13 = {
            {0, 0, 0},
            {0, 0, 0},
            {1, 1, 1}};

        int[][] r13 = {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0}};

        // test last
        Assert.assertArrayEquals(r12, s.getRotatedBoard(g12));
        Assert.assertArrayEquals(r13, s.getRotatedBoard(g13));

        int[][] g14 = {
            {0, 0, 0},
            {1, 1, 1},
            {0, 0, 0}};

        int[][] r14 = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}};

        // test middle
        Assert.assertArrayEquals(r14, s.getRotatedBoard(g14));
        Assert.assertArrayEquals(g14, s.getRotatedBoard(r14));

        // test 0: untestable because getRotatedBoard cannot work with any less than 1x1 array

        int[][] g15 = {{1}};

        // test 1
        Assert.assertArrayEquals(g15, s.getRotatedBoard(g15));

    }

    @Test
    public void testGetMoveUp(){

        int[][] g16 = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}};

        int[][] m16 = {
            {0, 2, 0},
            {0, 1, 0},
            {0, 0, 0}};

        Assert.assertArrayEquals(m16, s.getMoveUp(g16));
    }

    @Test
    public void testGetMoveRight(){

        int[][] g17 = {
            {0, 1, 1},
            {0, 1, 0},
            {0, 1, 0}};

        int[][] m17 = {
            {0, 0, 2},
            {0, 0, 1},
            {0, 0, 1}};

        Assert.assertArrayEquals(m17, s.getMoveRight(g17));
    }

    @Test
    public void testGetMoveDown(){

        int[][] g18 = {
            {0, 1, 1},
            {4, 1, 0},
            {4, 1, 0}};

        int[][] m18 = {
            {0, 0, 0},
            {0, 1, 0},
            {8, 2, 1}};

        Assert.assertArrayEquals(m18, s.getMoveDown(g18));
    }

    @Test
    public void testGetMoveTopLeft(){

        int[][] g19 = {
            {0, 1, 1},
            {4, 1, 0},
            {4, 1, 0}};

        int[][] m19 = {
            {1, 1, 1},
            {4, 0, 0},
            {4, 1, 0}};

        Assert.assertArrayEquals(m19, s.getMoveTopLeft(g19));

        int[][] g20 = {
            {2, 1, 8},
            {4, 2, 1},
            {256, 4, 2}};

        int [][] m20 = {
            {4, 2, 8},
            {8, 2, 0},
            {256, 0, 0}};

        Assert.assertArrayEquals(m20, s.getMoveTopLeft(g20));

        int[][] g21 = {
            {2, 1, 8, 4, 0},
            {4, 2, 1, 8, 4},
          {256, 4, 2, 0, 1}};

        int[][] m21 = {
            {4, 2, 16, 8, 0},
            {8, 2, 0, 1, 0},
          {256, 0, 0, 0, 0}};

        // multiple middle rows
        // executes the else block in the loop for the ending rows
        Assert.assertArrayEquals(m21, s.getMoveTopLeft(g21));
        
        int[][] g22 = {
            {2, 1, 8},
            {4, 0, 1},
            {4, 0, 2},
            {8, 0, 2},
            {1, 1, 1}};

        int[][] m22 = {
            {4, 2, 8},
            {4, 0, 0},
            {4, 2, 0},
            {8, 1, 0},
            {1, 1, 0}};   
            
        // multiple middle rows
        // executes the if block in the loop for the ending rows
        Assert.assertArrayEquals(m22, s.getMoveTopLeft(g22));

    }
    
    
}
