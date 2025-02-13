/**
 * Replication Benchmarker
 * https://github.com/score-team/replication-benchmarker/ Copyright (C) 2013
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
import crdt.Operation;
import crdt.simulator.IncorrectTraceException;
import java.util.LinkedList;
import java.util.List;
import jbenchmarker.core.MergeAlgorithm;
import jbenchmarker.core.SequenceOperation;

/**
 *
 * @author mzawirski
 */
public class TreedocMerge extends MergeAlgorithm {

    public TreedocMerge(int r) {
        super(new TreedocDocument(UniqueTag.createGenerator()), r);
    }

    @Override
    protected void integrateRemote(crdt.Operation message) throws IncorrectTraceException {
        getDoc().apply(message);
    }

    @Override
    protected List<Operation> localInsert(SequenceOperation opt)
            throws IncorrectTraceException {
        final TreedocDocument doc = ((TreedocDocument) getDoc());
        final List<Operation> ops = new LinkedList<Operation>();
        
        final TreedocIdentifier id = doc.insertAt(
                restrictedIndex(opt.getPosition(), true), opt.getContent(), getReplicaNumber());
        ops.add(new TreedocOperation(id, opt.getContent()));
    //System.out.println("--- localInsert ---"+id);

        return ops;
    }

    @Override
    protected List<Operation> localDelete(SequenceOperation opt)
            throws IncorrectTraceException {
        final TreedocDocument doc = ((TreedocDocument) getDoc());
        final List<Operation> ops = new LinkedList<Operation>();


        // TODO: implement batch delete more efficiently?
        for (int i = opt.getPosition(); i < opt.getPosition()
                + opt.getLenghOfADel(); i++) {
            final TreedocIdentifier deletedId = doc
                    .deleteAt(restrictedIndex(opt.getPosition(), false));
            ops.add(new TreedocOperation(deletedId));
            //System.out.println("---- localDelete --- "+deletedId);
        }

        return ops;
    }

    protected int restrictedIndex(final int index, final boolean insert) {
        // FIXME: Hack with restricting index within the range!
        // It seems to be caused by Simulator replaying delete blindly without
        // verifying replica document size first. Not 100% sure though.
        return Math.min(index, ((TreedocDocument) getDoc()).getContentSize()
                - (insert ? 0 : 1));
    }

    @Override
    public CRDT<String> create() {
        return new TreedocMerge(0);
        // FIXME: what is the semantics: what replica number should we use!?
    }
}
