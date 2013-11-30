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
package stemmer;

import com.uea.stemmer.*;
import java.io.IOException;

/**
 * An example of the UEA Lite stemmer; this may require changes before working.
 */
public class Stemmer
{
    public static void main(String[] args) throws IOException
    {
        UEALite ul = new UEALite();
        WhitespaceAlphaTokenizer tokenizer = new WhitespaceAlphaTokenizer();
        FileStemming.stemDirectory(ul, tokenizer, "../../Matlab/Materials/fold1/original-text-files", "../../Matlab/Materials/fold1/uealite", "stem");
        FileStemming.stemDirectory(ul, tokenizer, "../../Matlab/Materials/fold2/original-text-files", "../../Matlab/Materials/fold2/uealite", "stem");
        FileStemming.stemDirectory(ul, tokenizer, "../../Matlab/Materials/fold3/original-text-files", "../../Matlab/Materials/fold3/uealite", "stem");
        FileStemming.stemDirectory(ul, tokenizer, "../../Matlab/Materials/fold4/original-text-files", "../../Matlab/Materials/fold4/uealite", "stem");
        FileStemming.stemDirectory(ul, tokenizer, "../../Matlab/Materials/fold5/original-text-files", "../../Matlab/Materials/fold5/uealite", "stem");
    }
}
