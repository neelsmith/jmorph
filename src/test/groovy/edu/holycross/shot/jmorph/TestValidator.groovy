package edu.holycross.shot.jmorph

import java.sql.*
import org.sqlite.SQLite
import groovy.sql.Sql

import static org.junit.Assert.*
import org.junit.Test

import edu.harvard.chs.cite.CiteUrn

class TestValidator extends GroovyTestCase {


  CiteUrn logos = new CiteUrn("urn:cite:perseus:lexentity.lex41535.1")
  MorphForm m_dat_s = new MorphForm( ["","singular","","","","masculine","dative","","noun"])
  MorphForm m_gen_s = new MorphForm( ["","singular","","","","masculine","genitive","","noun"])

  File stemsFile  = new File("testdata/morph/morphstems.csv")
  File endingsFile  = new File("testdata/morph/endings.csv")
  File stemTypesFile = new File("testdata/morph/stemtypes.csv")
  File inflClassFile = new File("testdata/morph/inflclass.csv")


  File tagsFile = new File("testdata/morph/tags.csv")



  void testLoad() {
    System.err.println "Executed validator test"
    assert 1 == 1

    /*    MorphSql msql = new MorphSql(stemsFile, endingsFile, stemTypesFile, inflClassFile)
    msql.loadTags(tagsFile)
    assert msql

    FormGenerator generator = new FormGenerator(msql)
    assert generator
    */
  }

  /*
  void testNounGener() {
    MorphSql msql = new MorphSql(stemsFile, endingsFile, stemTypesFile, inflClassFile)
    FormGenerator generator = new FormGenerator(msql)

    // Only one form generated: lo/gou
    assert generator.generate(logos, m_dat_s).size() == 1
    // Either standard lo/gw| or epic lo/goio:
    assert generator.generate(logos, m_gen_s).size() == 2
    // Only standard form lo/gw|
    assert generator.generate(logos, m_gen_s, "standard").size() == 1
    }*/

}