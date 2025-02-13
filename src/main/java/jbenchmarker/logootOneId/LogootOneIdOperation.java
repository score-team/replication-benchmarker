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
package jbenchmarker.logootOneId;

import crdt.Operation;
import jbenchmarker.core.SequenceOperation;
import jbenchmarker.core.SequenceOperation.OpType;

/**
 *
 * @author mehdi urso
 */
public class LogootOneIdOperation<T> implements Operation {

    final private LogootOneIdentifier identif;
    
    final private T content;
    final private OpType type;

    private LogootOneIdOperation(OpType type, LogootOneIdentifier identif, T content) {
        this.type = type;
        this.identif = identif;
        this.content = content;
    }
    
    public OpType getType() {
        return this.type;
    }

    public LogootOneIdentifier getIdentifiant() {
        return identif;
    }
    
    public T getContent() {
        return content;
    }

    static <T> LogootOneIdOperation insert(LogootOneIdentifier idf, T cont) {
        return new LogootOneIdOperation(OpType.insert, idf, cont);
    }

    public static LogootOneIdOperation Delete(LogootOneIdentifier idf) {
        return new LogootOneIdOperation(OpType.delete, idf, null);
    }

    @Override
    public Operation clone() {
        return new LogootOneIdOperation(type, this.identif.clone(), this.content);
    }

}
