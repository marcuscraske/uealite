UEA-Lite Stemmer
================
A Java (originally by Richard Churchill) and Matlab port of the UEA-Lite stemmer.

About
------
Similar to other stemmers, UEA-Lite operates on a set of rules which are used as steps. There are two groups of rules: the first to clean the tokens, and the second to alter suffixes.
The first group of rules first avoids a small list of six frequent problem words. An improvement to the stemmer would be to expand this list by adding other problem words which the second rule set cannot deal with. Second, possessive apostrophes are removed and contractions are expanded. All hyphens are removed and tokens containing digits are left untouched. Strings which are all upper case and digits are left untouched unless there is a lower case terminal 's' (i.e. transforming plural forms of acronyms to singular forms).

Proper nouns should not usually be stemmed, except to remove possessives; our implementation will respect PoS tags if they are present. If the text is untagged the stemmer uses the simple heuristic that any capitalized token not preceded by sentence breaking punctuation is a proper noun.

Many texts, particularly scientific papers, contain sequences of digits, single letters, and other non-word tokens. Our implementation ignores tokens containing digits, single-letter tokens, and tokens with embedded punctuation.

The second group of rules contains 139 suffix rules, each testing for a specific type of suffix. The rules are set in a particular order so that the longest suffix applicable is used rather a shorter one which could lead to nonsense words and more words not stemmed entirely to their root form.

Usage
-------
An example of the Java port can be found at:

Stemmer/src/stemmer/Stemmer.java

Refer to the file header for the Matlab port documentation and usage, located at:
Matlab/stemmer_uealite.m

License
-------
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

More Information
----------------
* http://www2.cmp.uea.ac.uk/~djs/projects/UEAlite/stemmer.html