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
 * Author(s):           Marcus Craske           marcus.craske@uea.ac.uk
 * Version:             1.2
 *******************************************************************************
 * Change-log:
 *      2013-11-30      New headers.
 *******************************************************************************
 */
package com.uea.stemmer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A tokenizer which generates tokens from whitespace for alpha characters only.
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
