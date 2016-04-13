package edu.holycross.shot.greekmorph

import edu.holycross.shot.orthography.GreekString

import org.junit.Test
import static groovy.test.GroovyAssert.shouldFail


/**
*/
class TestSmyth406 {

      String fstBinary = "build/smyth/greek.a"
      File urnReg = new File("sampledata/smyth/urnregistry/collectionregistry.csv")
      UrnManager umgr = new UrnManager(urnReg)
      // The parser:
      LiteraryGreekParser mp = new LiteraryGreekParser(fstBinary, umgr, 8)
      TableTester tester = new TableTester(mp)

      @Test
      void testPerfect() {
        File dataFile =  new File("unit_tests_data/smyth/smyth406/smyth406pft.csv")
        assert tester.testFile(dataFile)
      }

      @Test
      void testPlPerfect() {
        File dataFile =  new File("unit_tests_data/smyth/smyth406/smyth406plupft.csv")
        assert tester.testFile(dataFile)
      }

}
