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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utilities class for stemming files.
 */
public class FileStemming
{
    // Methods - Static ********************************************************
    /**
     * Stems an entire directory (top-level only).
     * 
     * @param stemmer An instance of the stemmer.
     * @param tokenizer The tokenizer used for reading the tokens of the file.
     * @param pathIn The path of directory to be stemmed.
     * @param pathOut The path of the output directory of the stemmed files.
     * @param extension The extension appended to the stemmed files.
     * @throws IOException Thrown if an issue occurs reading or writing to a file.
     */
    public static void stemDirectory(UEALite stemmer, Tokenizer tokenizer, String pathIn, String pathOut, String extension) throws IOException
    {
        stemDirectory(stemmer, tokenizer, new File(pathIn), new File(pathOut), extension);
    }
    /**
     * Stems an entire directory (top-level only).
     * 
     * @param stemmer An instance of the stemmer.
     * @param tokenizer The tokenizer used for reading the tokens of the file.
     * @param dirIn The directory to be stemmed.
     * @param dirOut The output directory of the stemmed files.
     * @param extension The extension appended to the stemmed files.
     * @throws IOException Thrown if an issue occurs reading or writing to a file.
     */
    public static void stemDirectory(UEALite stemmer, Tokenizer tokenizer, File dirIn, File dirOut, String extension) throws IOException
    {
        // Check both directories are in-fact a directory
        if(!dirIn.isDirectory())
            throw new IOException("Specified input file/path '" + dirIn.getPath() + "' is not a directory!");
        if(!dirOut.isDirectory() && !dirOut.mkdir())
            throw new IOException("Specified output file/path '" + dirIn.getPath() + "' is not a directory!");
        // Stem each file
        File[] files = dirIn.listFiles();
        for(File f : files)
        {
            stemFile(stemmer, tokenizer, f, new File(dirOut.getAbsolutePath() + "/" + f.getName() + "." + extension));
        }
    }
    /**
     * Applies the stemmer to a file.
     * 
     * @param stemmer An instance of the stemmer.
     * @param tokenizer The tokenizer used for reading the tokens of the file.
     * @param fileIn The path of the file to be stemmed; this should be a
     * plain-text file, with tokens separated by whitespace.
     * @return Tokens of file, with stemming applied.
     * @throws FileNotFoundException Thrown if the file to be stemmed cannot be
     * located.
     * @throws IOException Thrown if an issue occurs reading the file.
     */
    public static Word[] stemFile(UEALite stemmer, Tokenizer tokenizer, String fileIn) throws FileNotFoundException, IOException
    {
        return stemFile(stemmer, tokenizer, new File(fileIn));
    }
    /**
     * Applies the stemmer to a file.
     * 
     * @param stemmer An instance of the stemmer.
     * @param tokenizer The tokenizer used for reading the tokens of the file.
     * @param fileIn The path of the file to be stemmed; this should be a
     * plain-text file, with tokens separated by whitespace.
     * @param fileOut The file for outputting the stemmed data, with each token
     * on a new line.
     * @throws FileNotFoundException Thrown if the file to be stemmed cannot be
     * located.
     * @throws IOException Thrown if an issue occurs reading the file.
     */
    public static void stemFile(UEALite stemmer, Tokenizer tokenizer, String fileIn, String fileOut) throws FileNotFoundException, IOException
    {
        stemFile(stemmer, tokenizer, new File(fileIn), new File(fileOut));
    }
    /**
     * Applies the stemmer to a file.
     * 
     * @param stemmer An instance of the stemmer.
     * @param tokenizer The tokenizer used for reading the tokens of the file.
     * @param fileIn The file to be stemmed; this should be a plain-text file,
     * with tokens separated by whitespace.
     * @param fileOut The file for outputting the stemmed data, with each token
     * on a new line (\n).
     * @throws FileNotFoundException Thrown if the file to be stemmed cannot be
     * located.
     * @throws IOException Thrown if an issue occurs reading the file.
     */
    public static void stemFile(UEALite stemmer, Tokenizer tokenizer, File fileIn, File fileOut) throws FileNotFoundException, IOException
    {
        // Stem the file's tokens
        Word[] words = stemFile(stemmer, tokenizer, fileIn);
        // Write back to file
        FileWriter fw = new FileWriter(fileOut);
        for(int i = 0; i < words.length; i++)
        {
            fw.append(words[i].getWord());
            if(i < words.length-1)
                fw.append('\n');
        }
        fw.flush();
        fw.close();
    }
    /**
     * Applies the stemmer to a file.
     * 
     * @param stemmer An instance of the stemmer.
     * @param tokenizer The tokenizer used for reading the tokens of the file.
     * @param fileIn The file to be stemmed; this should be a plain-text file,
     * with tokens separated by whitespace.
     * @return Tokens of file, with stemming applied.
     * @throws FileNotFoundException Thrown if the file to be stemmed cannot be
     * located.
     * @throws IOException Thrown if an issue occurs reading the file.
     */
    public static Word[] stemFile(UEALite stemmer, Tokenizer tokenizer, File fileIn) throws FileNotFoundException, IOException
    {
        // Read the file
        FileReader fr = new FileReader(fileIn);
        char[] buffer = new char[(int)fileIn.length()];
        fr.read(buffer);
        fr.close();
        String data = new String(buffer);
        // Apply tokenizer to data
        String[] tokens = tokenizer.tokenize(data);
        // Stem each token
        Word[] tokensStemmed = new Word[tokens.length];
        for(int i = 0; i < tokens.length; i++)
        {
            tokensStemmed[i] = stemmer.stem(tokens[i]);
        }
        return tokensStemmed;
    }
}
