/**
 * Replication Benchmarker
 * https://github.com/score-team/replication-benchmarker/
 * Copyright (C) 2013 LORIA / Inria / SCORE Team
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
package jbenchmarker.treedoc;

import jbenchmarker.core.Document;
import crdt.Operation;

/**
 * An instance of Treedoc document, implemented as a binary-like tree.
 * 
 * @author mzawirski
 */
public class TreedocDocument extends TreedocRoot implements Document {
	public TreedocDocument(final UniqueTagGenerator tagGenerator) {
		super(tagGenerator);
	}

	@Override
	public String view() {
		return getContent();
	}

	@Override
	public void apply(Operation op) {
		final TreedocOperation treedocOp = (TreedocOperation) op;
		switch (treedocOp.getType()) {
		case insert:
//System.out.println("--- Remote Add ---"+treedocOp.getId()+", Content: "+treedocOp.getContent());
			insertAt(treedocOp.getId(), treedocOp.getContent());
			break;
		case delete:
//System.out.println("--- Remote Remove ---"+treedocOp.getId());

			deleteAt(treedocOp.getId());
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation type");
		}
	}

    @Override
    public int viewLength() {
        return getSubtreeSize();
    }
}
