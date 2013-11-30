/*******************************************************************************
 * UEA-Lite Stemmer
 *******************************************************************************
 * Copyright (c) University of East Anglia (UEA) 2013.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************
 * Original Authors:    Marie-Claire Jenkins
 *                      Dan Smith               dan.smith@uea.ac.uk
 * 
 *                      https://www.uea.ac.uk/computing/word-stemming
 *******************************************************************************
 * Author(s):           Richard Churchill
 *                      Marcus Craske           marcus.craske@uea.ac.uk
 * Version:             1.2
 *******************************************************************************
 * Change-log:
 *      2013-11-30      New headers, further reformatting of the original
 *                      source.
 *******************************************************************************
 */
package com.uea.stemmer;

/**
 * The UEA Lite-Stemmer.
 * 
 * For more information, visit:
 * https://www.uea.ac.uk/computing/word-stemming
 */
public class UEALite
{
    // Methods - Fields ********************************************************
    private int maxWordLength = "deoxyribonucleicacid".length();    // ...or some other suitable value, e.g antidisestablishmentarianism
    private int maxAcronymLength = "CAVASSOO".length();             // ...or some other suitable value
    // Methods - Constructors **************************************************
    /**
     * Creates a new instance of the UEA Lite stemmer.
     */
    public UEALite()
    {}
    /**
     * Creates a new instance of the UEA Lite stemmer.
     * 
     * @param wordLength The maximum length of a word to be stemmed.
     * @param acronymLength The maximum length of an acronym to be stemmed.
     */
    public UEALite(int wordLength, int acronymLength)
    {
        maxWordLength = wordLength;
        maxAcronymLength = acronymLength;
    }
    // Methods *****************************************************************
    /**
     * Stems a word.
     * 
     * @param word The word to be stemmed; this should be lower-case.
     * @return The stemmed variant of the word.
     */
    public Word stem(String word)
    {
        if(isProblemWord(word))
            return new Word(word, 90);
        else if(word.length() > maxWordLength)
            return new Word(word, 95);
        else if(word.indexOf("'") != -1)
        {                                                           // contains apostrophe(s) - remove and continue 94
            if(word.matches( "^.*'[sS]$"))
                word = remove(word, "'s");                         // remove possessive singular
            if(word.matches( "^.*'$"))
                word = remove(word, "'");                          // remove possessive plural
            word = word.replaceAll( "n't", "not");                 // expand contraction n't
            word = word.replaceAll( "'ve", "have");                // expand contraction 've
            word = word.replaceAll( "'re", "are");                 // expand contraction 're
            word = word.replaceAll( "'m", "am");                   // expand contraction I'm

            return new Word(word, 94);
	}
        else if(word.matches("^\\d+$"))                             return new Word(word, 90.3);
        else if(word.matches("^\\w+-\\w+$"))                        return new Word(word, 90.2);
        else if(word.matches("^.*-.*$"))                            return new Word(word, 90.1);
        else if(word.matches("^.*_.*$"))                            return new Word(word, 90);
        else if(word.matches("^\\p{Upper}+s$"))                     return new Word(remove( word, "s"), 91.1);
        else if(word.matches("^\\p{Upper}+$"))                      return new Word(word, 91);
        else if(word.matches("^.*\\p{Upper}.*\\p{Upper}.*$"))       return new Word(word, 92);
        else if(word.matches("^\\p{Upper}{1}.*$"))                  return new Word(word, 92);
        // should word be stemmed followed by call to private method with text to stem
        else if(word.endsWith("aceous"))                            return new Word(remove(word, "aceous"),1);  // 1
        else if(word.endsWith("ces"))                               return new Word(remove(word, "s"),2);  // 2
        else if(word.endsWith("cs"))                                return new Word(word, 3); // 3
        else if(word.endsWith("sis"))                               return new Word(word, 4); // 4
        else if(word.endsWith("tis"))                               return new Word(word, 5); // 5
        else if(word.endsWith("ss"))                                return new Word(word, 6); // 6
        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("eed"))                               return new Word(word, 7   ); // 7
        else if(word.endsWith("eeds"))                              return new Word(remove(word, "s"),7); // 7
        /***********************************************************************************************************/
        else if(word.endsWith("ued"))                               return new Word(remove(word, "d"),8); // 8
        else if(word.endsWith("ues"))                               return new Word(remove(word, "s"),9); // 9
        else if(word.endsWith("ees"))                               return new Word(remove(word, "s"),10); // 10
        else if(word.endsWith("iases"))                             return new Word(remove(word, "es"),11.4); // 11.4
        else if(word.endsWith("uses"))                              return new Word(remove(word, "s"),11.3); // 11.3
        else if(word.endsWith("sses"))                              return new Word(remove(word, "es"),11.2); // 11.2
        else if(word.endsWith("eses"))                              return new Word(remove(word, "es")+"is", 11.1);//11.1
        else if(word.endsWith("ses"))                               return new Word(remove(word, "s"),11); // 11
        else if(word.endsWith("tled"))                              return new Word(remove(word, "d"),12.5); // 12.5
        else if(word.endsWith("pled"))                              return new Word(remove(word, "d"),12.4); // 12.4
        else if(word.endsWith("bled"))                              return new Word(remove(word, "d"),12.3); // 12.3
        else if(word.endsWith("eled"))                              return new Word(remove(word, "ed"),12.2); // 12.2
        else if(word.endsWith("lled"))                              return new Word(remove(word, "ed"),12.1); // 12.1
        else if(word.endsWith("led"))                               return new Word(remove(word, "ed"),12); // 12
        else if(word.endsWith("ened"))                              return new Word(remove(word, "ed"),13.7);       // 13.7
        else if(word.endsWith("ained"))                             return new Word(remove(word, "ed"),13.6);       // 13.6
        else if(word.endsWith("erned"))                             return new Word(remove(word, "ed"),13.5);       // 13.5
        else if(word.endsWith("rned"))                              return new Word(remove(word, "ed"),13.4);       // 13.4
        else if(word.endsWith("nned"))                              return new Word(remove(word, "ned"),13.3);       // 13.3
        else if(word.endsWith("oned"))                              return new Word(remove(word, "ed"),13.2);       // 13.2
        else if(word.endsWith("gned"))                              return new Word(remove(word, "ed"),13.1);       // 13.1
        else if(word.endsWith("ned"))                               return new Word(remove(word, "d"),13);       // 13
        else if(word.endsWith("ifted"))                             return new Word(remove(word, "ed"),14);       // 14
        else if(word.endsWith("ected"))                             return new Word(remove(word, "ed"),15);       // 15
        else if(word.endsWith("vided"))                             return new Word(remove(word, "d"),16);       // 16
        else if(word.endsWith("ved"))                               return new Word(remove(word, "d"),17);       // 17
        else if(word.endsWith("ced"))                               return new Word(remove(word, "d"),18);       // 18
        else if(word.endsWith("erred"))                             return new Word(remove(word, "red"),19);       // 19
        else if(word.endsWith("urred"))                             return new Word(remove(word, "red"),20.5);       // 20.5
        else if(word.endsWith("lored"))                             return new Word(remove(word, "ed"),20.4);       // 20.4
        else if(word.endsWith("eared"))                             return new Word(remove(word, "ed"),20.3);       // 20.3
        else if(word.endsWith("tored"))                             return new Word(word.replaceFirst("ed", "e"),20.2);
        else if(word.endsWith("ered"))                              return new Word(remove(word, "ed"),20.1);       // 20.1

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("red"))                               return new Word(remove(word, "d"),20);       // 20
        else if(word.endsWith("reds"))                              return new Word(remove(word, "ds"),20);       // 20
        /***********************************************************************************************************/

        else if(word.endsWith("tted"))                              return new Word(remove(word, "ted"),21);       // 21
        else if(word.endsWith("noted"))                             return new Word(remove(word, "d"),22.4);       // 22.4
        else if(word.endsWith("leted"))                             return new Word(remove(word, "d"),22.3);       // 22.3
        else if(word.endsWith("uted"))                              return new Word(remove(word, "d"),22.2);       // 22.2
        else if(word.endsWith("ated"))                              return new Word(remove(word, "d"),22.1);       // 22.1
        else if(word.endsWith("ted"))                               return new Word(remove(word, "ed"),22);       // 22
        else if(word.endsWith("anges"))                             return new Word(remove(word, "s"),23);       // 23
        else if(word.endsWith("aining"))                            return new Word(remove(word, "ing"),24);       // 24
        else if(word.endsWith("acting"))                            return new Word(remove(word, "ing"),25);       // 25

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("tting"))                             return new Word(remove(word, "ting"),26);       // 26
        else if(word.endsWith("ttings"))                            return new Word(remove(word, "tings"),26);       // 26
        /***********************************************************************************************************/

        else if(word.endsWith("viding"))                            return new Word(word.replaceFirst("ing", "e"),27);       // 27
        else if(word.endsWith("ssed"))                              return new Word(remove(word, "ed"),28);       // 28
        else if(word.endsWith("sed"))                               return new Word(remove(word, "d"),29);       // 29
        else if(word.endsWith("titudes"))                           return new Word(remove(word, "s"),30);       // 30
        else if(word.endsWith("umed"))                              return new Word(remove(word, "d"),31);       // 31
        else if(word.endsWith("ulted"))                             return new Word(remove(word, "ed"),32);       // 32
        else if(word.endsWith("uming"))                             return new Word(word.replaceFirst("ing", "e"),33);       // 33
        else if(word.endsWith("fulness"))                           return new Word(remove(word, "ness"),34);       // 34
        else if(word.endsWith("ousness"))                           return new Word(remove(word, "ness"),35);       // 35

        /***********************************************************************************************************/
        // in the perl version these are all in one regrex ( r[aeiou]bed$ )
        else if(word.endsWith("rabed"))                             return new Word(remove(word, "d"),36.1);       // 36.1
        else if(word.endsWith("rebed"))                             return new Word(remove(word, "d"),36.1);       // 36.1
        else if(word.endsWith("ribed"))                             return new Word(remove(word, "d"),36.1);       // 36.1
        else if(word.endsWith("robed"))                             return new Word(remove(word, "d"),36.1);       // 36.1
        else if(word.endsWith("rubed"))                             return new Word(remove(word, "d"),36.1);       // 36.1
        /***********************************************************************************************************/

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("bed"))                               return new Word(remove(word, "ed"),36);       // 36
        else if(word.endsWith("beds"))                              return new Word(remove(word, "eds"),36);       // 36

        else if(word.endsWith("ssing"))                             return new Word(remove(word, "ing"),37);       // 37
        else if(word.endsWith("ssings"))                            return new Word(remove(word, "ings"),37);       // 37
        /***********************************************************************************************************/

        else if(word.endsWith("ulting"))                            return new Word(remove(word, "ing"),38);       // 38

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("ving"))                              return new Word(word.replaceFirst("ing", "e"), 39);       // 39
        else if(word.endsWith("vings"))                             return new Word(word.replaceFirst("ings", "e"), 39);       // 39
        /***********************************************************************************************************/

        else if(word.endsWith("eading"))                            return new Word(remove(word, "ing"),40.7);       // 40.7
        else if(word.endsWith("eadings"))                           return new Word(remove(word, "ings"),40.7);     // 40.7
        else if(word.endsWith("oading"))                            return new Word(remove(word, "ing"),40.6);       // 40.6
        else if(word.endsWith("oadings"))                           return new Word(remove(word, "ings"),40.6);       // 40.6
        else if(word.endsWith("eding"))                             return new Word(remove(word, "ing"),40.5);       // 40.5
        else if(word.endsWith("edings"))                            return new Word(remove(word, "ings"),40.5);       // 40.5
        else if(word.endsWith("dding"))                             return new Word(remove(word, "ding"),40.4);       // 40.4
        else if(word.endsWith("ddings"))                            return new Word(remove(word, "dings"),40.4);       // 40.4
        else if(word.endsWith("lding"))                             return new Word(remove(word, "ing"),40.3);       // 40.3
        else if(word.endsWith("ldings"))                            return new Word(remove(word, "ings"),40.3);       // 40.3
        else if(word.endsWith("rding"))                             return new Word(remove(word, "ing"),40.2);       // 40.2
        else if(word.endsWith("rdings"))                            return new Word(remove(word, "ings"),40.2);       // 40.2
        else if(word.endsWith("nding"))                             return new Word(remove(word, "ing"),40.1);       // 40.1
        else if(word.endsWith("ndings"))                            return new Word(remove(word, "ings"),40.1);       // 40.1

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("ding"))                              return new Word(word.replaceFirst("ing", "e"),40);       // 40
        else if(word.endsWith("dings"))                             return new Word(word.replaceFirst("ings", "e"),40);     // 40

        else if(word.endsWith("lling"))                             return new Word(remove(word, "ling"),41);                   // word ends in -lling 41
        else if(word.endsWith("llings"))                            return new Word(remove(word, "lings"),41);                  // word ends in -lling 41

        else if(word.endsWith("ealing"))                            return new Word(remove(word, "ing"),42.4);                  // word ends in -ealing 42.4
        else if(word.endsWith("ealings"))                           return new Word(remove(word, "ings"),42.4); 		// word ends in -ealing 42.4

        else if(word.endsWith("oling"))                             return new Word(remove(word, "ing"),42.3);                  // word ends in -oling 42.3
        else if(word.endsWith("olings"))                            return new Word(remove(word, "ings"),42.3); 		// word ends in -oling 42.3

        else if(word.endsWith("ailing"))                            return new Word(remove(word, "ing"),42.2);                  // word ends in -ailing 42.2
        else if(word.endsWith("ailings"))                           return new Word(remove(word, "ings"),42.2); 		// word ends in -ailing 42.2

        else if(word.endsWith("eling"))                             return new Word(remove(word, "ing"),42.1);                  // word ends in -ling 42.1
        else if(word.endsWith("elings"))                            return new Word(remove(word, "ings"),42.1);                 // word ends in -ling 42.1

        else if(word.endsWith("ling"))                              return new Word(remove(word, "ing")+"e", 42);               // word ends in -ting 48
        else if(word.endsWith("lings"))                             return new Word(remove(word, "ings")+"e", 42);              // word ends in -ting 48
        /***********************************************************************************************************/

        else if(word.endsWith("nged"))                              return new Word(remove(word, "d"),43.2);                    // word ends in -nged  43.2
        else if(word.endsWith("gged"))                              return new Word(remove(word, "ged"),43.1);                  // word ends in -gged  43.1
        else if(word.endsWith("ged"))                               return new Word(remove(word, "d"),43); 			// word ends in -ged  43

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("mming"))                             return new Word(remove(word, "ming"),44.3); 		// word ends in -mming  44.3
        else if(word.endsWith("mmings"))                            return new Word(remove(word, "mings"),44.3); 		// word ends in -mming  44.3
        /***********************************************************************************************************/

        else if(word.endsWith("rming"))                             return new Word(remove(word, "ing"),44.2);                  // word ends in -rming  44.2
        else if(word.endsWith("lming"))                             return new Word(remove(word, "ing"),44.1);                  // word ends in -lming  44.1

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("ming"))                              return new Word(remove(word, "ing")+"e", 44);               // word ends in -ting 48
        else if(word.endsWith("mings"))                             return new Word(remove(word, "ings")+"e", 44);              // word ends in -ting 48

        else if(word.endsWith("nging"))                             return new Word(remove(word, "ing"),45.2);                  // word ends in -ging 45.2
        else if(word.endsWith("ngings"))                            return new Word(remove(word, "ings"),45.2); 		// word ends in -ging 45.2

        else if(word.endsWith("gging"))                             return new Word(remove(word, "ging"),45.1); 		// word ends in -ging 45.1
        else if(word.endsWith("ggings"))                            return new Word(remove(word, "gings"),45.1); 		// word ends in -ging 45.1

        else if(word.endsWith("ging"))                              return new Word(remove(word, "ing")+"e", 45);
        else if(word.endsWith("gings"))                             return new Word(remove(word, "ings")+"e", 45);
        /***********************************************************************************************************/

        else if(word.endsWith("aning"))                             return new Word(remove(word, "ing"),46.6);                  // word ends in -aning 46.6
        else if(word.endsWith("ening"))                             return new Word(remove(word, "ing"),46.5);                  // word ends in -ening 46.5
        else if(word.endsWith("gning"))                             return new Word(remove(word, "ing"),46.4);                  // word ends in -gning 46.4
        else if(word.endsWith("nning"))                             return new Word(remove(word, "ning"),46.3); 		// word ends in -nning 46.3
        else if(word.endsWith("oning"))                             return new Word(remove(word, "ing"),46.2);                  // word ends in -oning 46.2
        else if(word.endsWith("rning"))                             return new Word(remove(word, "ing"),46.1);                  // word ends in -rning 46.1
        else if(word.endsWith("ning"))                              return new Word(remove(word, "ing")+"e", 46);               // word ends in -ting 46

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("sting"))                             return new Word(remove(word, "ing"),47);                    // word ends in -sting 47
        else if(word.endsWith("stings"))                            return new Word(remove(word, "ings"),47);                   // word ends in -sting 47
        /***********************************************************************************************************/

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("eting"))                             return new Word(remove(word, "ing"),48.4);                  // word ends in -pting 48.4
        else if(word.endsWith("etings"))                            return new Word(remove(word, "ings"),48.4); 		// word ends in -pting 48.4
        /***********************************************************************************************************/

        else if(word.endsWith("pting"))                             return new Word(remove(word, "ing"),48.3);                  // word ends in -pting 48.3

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("nting"))                             return new Word(remove(word, "ing"),48.2);                  // word ends in -nting 48.2
        else if(word.endsWith("ntings"))                            return new Word(remove(word, "ings"),48.2); 		// word ends in -nting 48.2
        /***********************************************************************************************************/

        else if(word.endsWith("cting"))                             return new Word(remove(word, "ing"),48.1);                  // word ends in -cting 48.1

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("ting"))                              return new Word(remove(word, "ing")+"e", 48);               // word ends in -ting 48
        else if(word.endsWith("tings"))                             return new Word(remove(word, "ings")+"e", 48);              // word ends in -ting 48
        /***********************************************************************************************************/

        else if(word.endsWith("ssed"))                              return new Word(remove(word, "ed"),49); 			// word ends in -ssed 49
        else if(word.endsWith("les"))                               return new Word(remove(word, "s"),50); 			// word ends in -les 50
        else if(word.endsWith("tes"))                               return new Word(remove(word, "s"),51); 			// word ends in -tes 51
        else if(word.endsWith("zed"))                               return new Word(remove(word, "d"),52); 			// word ends in -zed 52
        else if(word.endsWith("lled"))                              return new Word(remove(word, "ed"),53); 			// word ends in -lled 53

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("iring"))                             return new Word(word.replaceFirst("ing", "e"),54.4);
        else if(word.endsWith("irings"))                            return new Word(word.replaceFirst("ings", "e"),54.4);

        else if(word.endsWith("uring"))                             return new Word(word.replaceFirst("ing", "e"),54.3);
        else if(word.endsWith("urings"))                            return new Word(word.replaceFirst("ings", "e"),54.3);

        else if(word.endsWith("ncing"))                             return new Word(word.replaceFirst("ing", "e"),54.2);
        else if(word.endsWith("ncings"))                            return new Word(word.replaceFirst("ings", "e"),54.2);
        /***********************************************************************************************************/

        else if(word.endsWith("zing"))                              return new Word(word.replaceFirst("ing", "e"),54.1);

        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("sing"))                              return new Word(word.replaceFirst("ing", "e"),54);
        else if(word.endsWith("sings"))                             return new Word(word.replaceFirst("ings", "e"),54);
        /***********************************************************************************************************/

        else if(word.endsWith("lling"))                             return new Word(remove(word, "ing"),55);
        else if(word.endsWith("ied"))                               return new Word(word.replaceFirst("ied", "y"),56);
        else if(word.endsWith("ating"))                             return new Word(word.replaceFirst("ing", "e"),57);


        /***********************************************************************************************************/
        // plural change - this differs from Perl v1.03
        else if(word.endsWith("thing"))                             return new Word(word, 58.1);

        // the word "things" was being caught by 58.1 so have added this rule, this should really have been caught by 68 but that wasn't happening
        else if(word.endsWith("things"))                            return new Word(remove(word, "s"),58.1);

        else if(word.matches( ".*\\w\\wings?$") )                   return new Word(rule58(word),58);
        /***********************************************************************************************************/

        else if(word.endsWith("ies"))                               return new Word(word.replaceFirst("ies", "y"),59);
        else if(word.endsWith("lves"))                              return new Word(word.replaceFirst("ves", "f"),60.1);
        else if(word.endsWith("ves"))                               return new Word(remove(word, "s"),60);
        else if(word.endsWith("aped"))                              return new Word(remove(word, "d"),61.3);
        else if(word.endsWith("uded"))                              return new Word(remove(word, "d"),61.2);
        else if(word.endsWith("oded"))                              return new Word(remove(word, "d"),61.1);
        else if(word.endsWith("ated"))                              return new Word(remove(word, "d"),61);
        else if(word.matches( ".*\\w\\weds?$"))                     return new Word(rule62( word ),62);
        else if(word.endsWith("pes"))                               return new Word(remove(word, "s"),63.8);
        else if(word.endsWith("mes"))                               return new Word(remove(word, "s"),63.7);
        else if(word.endsWith("ones"))                              return new Word(remove(word, "s"),63.6);
        else if(word.endsWith("izes"))                              return new Word(remove(word, "s"),63.5);
        else if(word.endsWith("ures"))                              return new Word(remove(word, "s"),63.4);
        else if(word.endsWith("ines"))                              return new Word(remove(word, "s"),63.3);
        else if(word.endsWith("ides"))                              return new Word(remove(word, "s"),63.2);
        else if(word.endsWith("ges"))                               return new Word(remove(word, "s"),63.1);
        else if(word.endsWith("es"))                                return new Word(remove(word, "es"),63);
        else if(word.endsWith("is"))                                return new Word(word.replaceFirst("is", "e"),64);
        else if(word.endsWith("ous"))                               return new Word(word, 65);
        else if(word.endsWith("ums"))                               return new Word(word, 66);
        else if(word.endsWith("us"))                                return new Word(word, 66);
        else if(word.endsWith("s"))                                 return new Word(remove(word, "s"),68);
        else
            return new Word(word, 0);
    }
    private String rule58(String word)
    {
        return this.stemWithDuplicateCharacterCheck( word, "ing");
    }
    private String rule62(String word)
    {
        return this.stemWithDuplicateCharacterCheck( word, "ed");
    }
    private String stemWithDuplicateCharacterCheck(String word, String remove)
    {
        if(word.endsWith("s"))
            remove = remove.concat( "s");
        String stemmed_word = remove(word, remove );
        if(stemmed_word.matches( ".*(\\w)\\1$"))
            stemmed_word = remove(stemmed_word, ".");
        
        return stemmed_word;
    }
    private String remove(String word, String suffix)
    {
        return word.substring( 0, word.length() - suffix.length() );
    }
    private boolean isProblemWord(String word)
    {
        return word.equals("is") || word.equals("as") || word.equals("this") || word.equals("has") || word.equals("was") || word.equals("during");
    }
    // Methods - Accessors *****************************************************
    /**
     * @return The maximum length of a word to be stemmed.
     */
    public int getMaxWordLength()
    {
        return maxWordLength;
    }
    /**
     * @return The maximum length of an acronym to be stemmed.
     */
    public int getMaxAcronymLength()
    {
        return maxAcronymLength;
    }
    // Methods - Mutators ******************************************************
    /**
     * @param length The maximum length of a word to be stemmed.
     */
    public void setMaxWordLength(int length)
    {
        maxWordLength = length;
    }
    /**
     * @param length The maximum length of an acronym to be stemmed.
     */
    public void setMaxAcronymLength(int length)
    {
        maxAcronymLength = length;
    }
}
