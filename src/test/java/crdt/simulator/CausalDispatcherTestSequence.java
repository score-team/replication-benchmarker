/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crdt.simulator;

import jbenchmarker.sim.StandardOpProfile;
import crdt.CRDT;
import crdt.Factory;
import crdt.PreconditionException;
import crdt.simulator.random.OperationProfile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import crdt.simulator.CausalDispatcher.IncorrectTrace;
import jbenchmarker.abt.ABTFactory;
import jbenchmarker.logoot.LogootFactory;
import jbenchmarker.ot.SOCT2Factory;
import jbenchmarker.rga.RGAFactory;
import jbenchmarker.treedoc.TreedocFactory;
import jbenchmarker.woot.WootFactories.WootFactory;
import jbenchmarker.woot.WootFactories.WootHFactory;
import jbenchmarker.woot.WootFactories.WootOFactory;
import org.junit.Ignore;

/**
 *
 * @author urso
 */
public class CausalDispatcherTestSequence {

    Factory s[] = { new LogootFactory(), //new TreedocFactory(), new jbenchmarker.treedoc.list.TreedocFactory(),
        new WootFactory(), new WootOFactory(), new WootHFactory(), // new ABTFactory(),
        new SOCT2Factory(), new RGAFactory()};
    
    
    //Vector<LinkedList<TimeBench>> result = new Vector<LinkedList<TimeBench>>();
    int scale = 100;
    

   
    public CausalDispatcherTestSequence() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    static final int vocabularySize = 100;
    static final OperationProfile seqopp = new StandardOpProfile(0.8, 0.1, 40, 5.0);
    static final OperationProfile uopp = new StandardOpProfile(0.8, 0, 1, 0);
    
    @Ignore
    @Test
    public void stress() throws PreconditionException, IncorrectTrace {
        int i = 0;
        while (true) {
            System.out.println(" i :" + i++);
            CausalDispatcherTest.testRun(s[0], 100, 4, seqopp);           
        }
    }
    
    @Test
    public void testRunSequencesOneCharacter() throws IncorrectTrace, PreconditionException {
        
        for (Factory<CRDT> sf : s) {
            CausalDispatcherTest.testRun(sf, 1000, 10, uopp);
        }
    }
    
    @Test
    public void testRunSequences() throws IncorrectTrace, PreconditionException {
        
        for (Factory<CRDT> sf : s) {
            CausalDispatcherTest.testRun(sf, 1000, 10, seqopp);
        }
    }
}