package edu.holycross.shot.greekmorph


import edu.holycross.shot.orthography.GreekString
import edu.holycross.shot.orthography.GreekOrthography
import edu.holycross.shot.orthography.GreekWord
import edu.holycross.shot.phonology.Accent
import edu.holycross.shot.phonology.AccentPattern
import edu.holycross.shot.phonology.Syllable

class LiteraryGreekAdjectiveAccent {
static debug =  10

    /**
     */
    static GreekWord addAdjUltima(GreekWord gw, AdjectiveForm adjForm, String inflectionClass) {

      def syllables = gw.getSyllables()
      Integer lastIndex = syllables.size() - 1
      String lastSyll = syllables[lastIndex]

      switch (adjForm.cas) {
      case GrammaticalCase.GENITIVE:
      case GrammaticalCase.DATIVE:
      System.err.println "ADD PERISPOMENON TO " + adjForm + " for case " + adjForm.cas
      syllables[lastIndex] = Accent.accentSyllable(lastSyll, "=")
      break
      default :
      System.err.println "ADD OXYTONE TO " + adjForm + " for case " + adjForm.cas
      System.err.println "${lastSyll} is of class " + lastSyll.getClass()

      String accentedSyll =  Accent.accentSyllable(lastSyll, "/")
      System.err.println "Accented is " + accentedSyll
      syllables[lastIndex] = accentedSyll
      break
      }

      GreekWord resultWord = new GreekWord(syllables.join(""))
      return  resultWord
    }


    static  GreekWord getAccentedAdjForm(GreekString inflectionalString, FstAnalysisParser analysisInfo) {
        GreekWord accented
        System.err.println "ACCEWNT ADJ: " + inflectionalString

        // Analysis data:
        AnalysisTriple triple = analysisInfo.getTriple()
        MorphForm form = triple.getMorphForm()
        AdjectiveForm adjAnalysis = form.getAnalysis()
        GreekWord retrievedForm = new GreekWord(analysisInfo.getSurfaceStem() + analysisInfo.getSurfaceInflection())

        def inflectionSyllables = Syllable.getSyllables(inflectionalString)
        def accPattern = adjAnalysis.getPersistentAccent()

        def maxOffset = AccentPattern.values().size() - 1
        if (debug > 0) {
          System.err.println "Acc. pattern is " + accPattern
          System.err.println "Checking adj w  persistent accent " + accPattern + " and ordinal " + accPattern.ordinal()
          def sylloffset = (inflectionSyllables.size() - 1)
          def max1 = sylloffset + accPattern.ordinal()
          def max2 = 2 // ie, PersistentAccent.size() - 1
          def shiftidx = Math.min(max1, max2)
          def allVals = PersistentAccent.values()
          System.err.println "TRY INDEX " + shiftidx + " = " + allVals[shiftidx]
        }

        String inflectionTag = analysisInfo.getInflectionTag()
        if (debug > 0) {System.err.println "Adj to accent ${retrievedForm}, look at " + adjAnalysis.getPersistentAccent()}
        switch (adjAnalysis.getPersistentAccent()) {
        case PersistentAccent.STEM_PENULT:
        // need to check for polysyllabic ending:
        accented = retrievedForm.accent(AccentPattern.RECESSIVE)
        break

        case PersistentAccent.STEM_ULTIMA:
        // need to check for polysyllabic ending:
        accented =  Accent.accentWord(retrievedForm, AccentPattern.PENULT)
        break

        case PersistentAccent.INFLECTIONAL_ENDING:
        // need to check for polysyllabic ending:
        accented = LiteraryGreekAdjectiveAccent.addAdjUltima(retrievedForm, adjAnalysis, analysisInfo.getInflectionTag())
        break


        case PersistentAccent.IRREGULAR_ACCENT:
        // need to check for polysyllabic ending:
        accented = retrievedForm
        break
        }
        return accented
      }


}