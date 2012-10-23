/**
 * Replication Benchmarker
 * https://github.com/score-team/replication-benchmarker/ Copyright (C) 2012
 * LORIA / Inria / SCORE Team
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package jbenchmarker.treedoc;

import crdt.CRDT;
import crdt.PreconditionException;
import java.util.LinkedList;
import java.util.List;
import jbenchmarker.core.LocalOperation;
import jbenchmarker.core.Operation;
import jbenchmarker.core.SequenceOperation;
import jbenchmarker.factories.TreedocFactory;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Smoke tests for tree-based Treedoc implementation.
 *
 * @author mzawirski
 */
public class TreedocMergeTest {

    private static final int REPLICA_ID = 7;
    private TreedocMerge replica;

    @Before
    public void setUp() throws Exception {
        replica = (TreedocMerge) new TreedocFactory().create(REPLICA_ID);
    }

    @Test
    public void testEmptyTree() {
        assertEquals("", replica.lookup());
    }

    @Test
    public void testDelete() throws PreconditionException {
        String content = "abcdefghijk";
        int pos = 3, off = 4;       
        replica.applyLocal(SequenceOperation.insert(0, content));
        assertEquals(content, replica.lookup());
        replica.localDelete(SequenceOperation.delete(pos, off));
        assertEquals(content.substring(0, pos) + content.substring(pos+off), replica.lookup());        
    }
}