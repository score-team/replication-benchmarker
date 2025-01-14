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
package jbenchmarker.sim;

import crdt.simulator.CausalSimulator;
import crdt.simulator.Trace;
import crdt.simulator.random.RandomTrace;
import crdt.simulator.random.StandardDiffProfile;
import crdt.simulator.random.StandardSeqOpProfile;
import jbenchmarker.factories.LogootSFactory;
import static jbenchmarker.sim.CausalDispatcherTest.assertConsistency;
import static jbenchmarker.sim.CausalDispatcherTest.assertGoodViewLength;
import jbenchmarker.trace.TraceGenerator;
import org.junit.Ignore;
import org.junit.Test;


public class IntegrationLogootSplit {
    
    @Ignore
    @Test
    public void testLogootSplitG1Run() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationLogootSplit.class.getResource("G1.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new LogootSFactory());

        assertConsistency(cd, trace);
    }
    
    @Ignore
    @Test
    public void testLogootSplitG2Run() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationLogootSplit.class.getResource("G2.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator( new LogootSFactory());

        assertConsistency(cd, trace);
    }
    
    @Ignore
    @Test
    public void testLogootSplitG3Run() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationLogootSplit.class.getResource("G3.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new LogootSFactory());

        assertConsistency(cd, trace);
    }
    
    @Ignore
    @Test
    public void testLogootSplitSerieRun() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationLogootSplit.class.getResource("Serie.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new LogootSFactory());

        assertConsistency(cd, trace);
    }
    
    
    @Ignore
    @Test
    public void testLogootSplitProfile() throws Exception {
        for (int i = 0; i < 20; ++i) {
            Trace trace = new RandomTrace(4200, RandomTrace.FLAT, new StandardSeqOpProfile(0.8, 0.1, 40, 5.0), 0.1, 10, 3.0, 13);
            CausalSimulator cd = new CausalSimulator(new LogootSFactory());

            cd.run(trace);
        }    
    }
    
    
    @Test
    public void testLogootSplitRandom() throws Exception {
        Trace trace = new RandomTrace(4200, RandomTrace.FLAT, new StandardSeqOpProfile(0.8, 0.1, 40, 5.0), 0.1, 10, 3.0, 13);
        CausalSimulator cd = new CausalSimulator(new LogootSFactory());

        assertConsistency(cd, trace);  
        assertGoodViewLength(cd);
    }
    
    @Test
    public void testLogootSplitRandomDiff() throws Exception {
        Trace trace = new RandomTrace(420, RandomTrace.FLAT, StandardDiffProfile.BASIC, 0.1, 10, 3.0, 13);
        CausalSimulator cd = new CausalSimulator(new LogootSFactory());

        assertConsistency(cd, trace);  
        //assertGoodViewLength(cd);
    }
}
