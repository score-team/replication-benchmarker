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

import jbenchmarker.TraceSimul2XML;
import org.junit.Ignore;
import crdt.CRDT;
import crdt.simulator.Trace;
import crdt.simulator.random.RandomTrace;
import crdt.simulator.random.StandardSeqOpProfile;
import crdt.simulator.CausalSimulator;
import crdt.simulator.random.StandardDiffProfile;
import jbenchmarker.factories.LogootFactory;
import static jbenchmarker.factories.WootFactories.WootHFactory;
import jbenchmarker.trace.TraceGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import static jbenchmarker.sim.CausalDispatcherTest.*;

/**
 *
 * @author urso
 */
public class IntegrationWOOTH {

//    @Ignore
    @Test
    public void testWootHExempleRun() throws Exception {
        System.out.println("Integration test with WootH");
        Trace trace = TraceGenerator.traceFromXML(IntegrationWOOTH.class.getResource("exemple.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());

        cd.run(trace);
        String r = "Salut Monsieurjour MehdiFin";

        assertEquals(r, cd.getReplicas().get(0).lookup());
        assertEquals(r, cd.getReplicas().get(2).lookup());
        assertEquals(r, cd.getReplicas().get(4).lookup());
    }
    
    @Ignore
    @Test
    public void testWootHG1Run() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationWOOTH.class.getResource("G1.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());

        assertConsistency(cd,trace);
    }
    
    @Ignore
    @Test
    public void testWootHG2Run() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationWOOTH.class.getResource("G2.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());

        assertConsistency(cd,trace);
    }
    
    @Ignore
    @Test
    public void testWootHG3Run() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationWOOTH.class.getResource("G3.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());

        assertConsistency(cd,trace);
    }
    
    @Ignore
    @Test
    public void testWootHSerieRun() throws Exception {
        Trace trace = TraceGenerator.traceFromXML(IntegrationWOOTH.class.getResource("Serie.xml").getPath(), 1);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());

        assertConsistency(cd,trace);
    }

    @Ignore
    @Test
    public void testWootHStress() throws Exception {
        int i = 0;
        while (true) {
            Trace trace = new RandomTrace(100, RandomTrace.FLAT, new StandardSeqOpProfile(0.6, 1, 10, 5.0), 1, 10, 3.0, 5);
            CausalSimulator cd = new CausalSimulator(new WootHFactory());

            assertConsistency(cd,trace);
            assertGoodViewLength(cd);
        }
    }
    
    
    //@Ignore
    @Test
    public void testWootHRandom() throws Exception {
        Trace trace = new RandomTrace(2000, RandomTrace.FLAT, new StandardSeqOpProfile(0.8, 0.1, 40, 5.0), 1, 10, 3.0, 5);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());
        
        assertConsistency(cd, trace);
        assertGoodViewLength(cd);
    }
        
    @Test
    public void testWootHRandomDiff() throws Exception {
        Trace trace = new RandomTrace(420, RandomTrace.FLAT, StandardDiffProfile.BASIC, 0.1, 10, 3.0, 13);
        CausalSimulator cd = new CausalSimulator(new WootHFactory());

        assertConsistency(cd, trace);  
        //assertGoodViewLength(cd);
    }
    
    @Test
    public void testWootHSimulXML() throws Exception {
        Trace trace = new RandomTrace(2000, RandomTrace.FLAT, new StandardSeqOpProfile(0.8, 0.1, 40, 5.0), 0.1, 10, 3.0, 5);
        CausalSimulator cdSim = new CausalSimulator(new WootHFactory());
        cdSim.setLogging("trace.log");
        cdSim.run(trace);
         
        TraceSimul2XML mn = new TraceSimul2XML();
        String[] args = new String[]{"trace.log", "trace.xml"};
        mn.main(args);
        
        Trace real = TraceGenerator.traceFromXML("trace.xml", 1);
        CausalSimulator cdReal = new CausalSimulator(new WootHFactory());
        cdReal.run(real);
        
//        String s = (String) cdSim.getReplicas().get(0).lookup();
//        String r = (String) cdReal.getReplicas().get(0).lookup();
//        assertEquals(s,r);
        
//        compare all replica
        for (CRDT crdtSim : cdSim.getReplicas().values()) {
            for (CRDT crdtReal : cdReal.getReplicas().values()) {
                assertEquals(crdtSim.lookup(), crdtReal.lookup());
            }
        }
    }
}
