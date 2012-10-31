/**
 * Replication Benchmarker
 * https://github.com/score-team/replication-benchmarker/
 * Copyright (C) 2012 LORIA / Inria / SCORE Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jbenchmarker.logoot;

import java.util.List;
import jbenchmarker.factories.LogootListFactory;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mehdi urso
 */
public class BoundaryListStrategyTestInt {
    LogootDocument LD;
    
    LogootListPosition newPos(int v) {
        return new LogootListPosition(32, v);
    }

    LogootListPosition newPos(int[] v) {
        IntContent c = new IntContent(v);
        return new LogootListPosition(c);
    }
    
    @Before
    public void setUp() throws Exception {
        LD = LogootListFactory.createDoc(1, 32);
        LD.incClock();
    }

    @Test
    public void testgenerateLineIdentifiersCas1() {
        System.out.println("Test Boundary Strategy...");

        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(-54);
        LogootListPosition Q = newPos(105);

        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 100);

        assertEquals(100, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(3, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
    
    @Test
    public void testgenerateLineIdentifiersCas2() {
        LogootStrategy BS = new BoundaryListStrategy(32);
 
        LogootListPosition P = newPos(-54);
        LogootListPosition Q = newPos(105);

        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 200);

        assertEquals(200, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(4, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
    
    @Test
    public void testgenerateLineIdentifiersCas3() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(new int[] { 42, 123, -12 });
        LogootListPosition Q = newPos(new int[] { 42, 123, 1 });
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 200);

        assertEquals(200, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(6, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
    
    @Test
    public void testgenerateLineIdentifiersCas4() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(42);
        LogootListPosition Q = newPos(new int[] { 42, -13 });
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 100);

        assertEquals(100, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(4, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
        
    @Test
    public void testgenerateLineIdentifiersCas5() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(42);
        LogootListPosition Q = newPos(new int[] { 42, Integer.MIN_VALUE + 78});
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 100);

        assertEquals(100, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(5, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
    
    @Test
    public void testgenerateLineIdentifiersCas6() {
        System.out.println("Test Boundary Strategy...");

        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(-54);
        LogootListPosition Q = newPos(-53);

        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 100);

        assertEquals(100, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(4, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
    
    @Test
    public void testgenerateLineIdentifiersCas7() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(42);
        LogootListPosition Q = newPos(new int[] { 43, -13 });
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 100);

        assertEquals(100, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(4, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
    
    @Test
    public void testgenerateLineIdentifiersCas16() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(new int[] { 42, 12 });
        LogootListPosition Q = newPos(new int[] { 43 });
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 100);

        assertEquals(100, patch.size());

        for (int i = 1; i < patch.size(); i++) {
            assertEquals(4, patch.get(i).length());
            assertTrue(patch.get(i).compareTo(P) > 0);
            assertTrue(patch.get(i).compareTo(patch.get(i - 1)) > 0);
            assertTrue(patch.get(i).compareTo(Q) < 0);
        }
    }
        
    @Test
    public void testgenerateLineIdentifiersCas9() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(new int[] { 42, 120 });
        LogootListPosition Q = newPos(new int[] { 43, -32 });
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 1);

        assertEquals(1, patch.size());

        assertEquals(4, patch.get(0).length());
        assertTrue(patch.get(0).compareTo(P) > 0);
        assertTrue(patch.get(0).compareTo(Q) < 0);
    }
    
    @Test
    public void testgenerateLineIdentifiersCas10() {
        LogootStrategy BS = new BoundaryListStrategy(32);

        LogootListPosition P = newPos(new int[] { 42, Integer.MAX_VALUE });
        LogootListPosition Q = newPos(new int[] { 43, Integer.MIN_VALUE });
        
        List<ListIdentifier> patch = BS.generateLineIdentifiers(LD, P, Q, 1);

        assertEquals(1, patch.size());

        assertEquals(5, patch.get(0).length());
        assertTrue(patch.get(0).compareTo(P) > 0);
        assertTrue(patch.get(0).compareTo(Q) < 0);
    }
}