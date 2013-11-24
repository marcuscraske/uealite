/**
 * <p>Title: UEA Lite Stemmer</p>
 *
 * <p>Description: This is a port of the UEAlite Perl stemmer v1.03, authored by Marie-Claire Jenkins and Dr. Dan J Smith.</p>
 *
 * <p>Copyright: Copyright (c) University of East Anglia 2005</p>
 *
 * <p>Company: University of East Anglia</p>
 *
 * <p>Licence:  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * </p>
 */
package com.uea.stemmer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A tokenizer which generates tokens from whitespace for alpha characters only.
 * 
 * @author  Marcus Craske       marcus.craske@uea.ac.uk
 * @version 1.0
 */
public class WhitespaceAlphaTokenizer implements Tokenizer
{
    // Constants ***************************************************************
    private static final Pattern pws;
    static
    {
        pws = Pattern.compile("([a-zA-Z]+)", Pattern.MULTILINE);
    }
    // Methods - Constructors **************************************************
    public WhitespaceAlphaTokenizer()
    { }
    // Methods *****************************************************************
    /**
     * Tokenizes the data by generating tokens from white-space.
     * 
     * @param data The data to be tokenized.
     * @return Tokens of data, lower-case.
     */
    @Override
    public String[] tokenize(String data)
    {
        ArrayList<String> matches = new ArrayList<>();
        Matcher m =  pws.matcher(data);
        while(m.find())
            matches.add(m.group(1).toLowerCase());
        return matches.toArray(new String[matches.size()]);
    }
}
