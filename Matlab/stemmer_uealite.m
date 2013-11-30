% MATLAB port of UEA-Lite
% -------------------------------------------------------------------------
% -- max_length_word:   The maximum length of a word.
% -- word:              The word to be stemmed.
%
% Returns:
% -- stem:              The stemmed variant of the word.
% -- rule:              The stemming rule applied.
%
% *************************************************************************
% Created by Marcus Craske (marcus.craske@uea.ac.uk), using the Java port
% by Richard Churchill.
% *************************************************************************
% Originally by:
% - Marie-Claire Jenkins and Dr. Dan J Smith,
%   University of East Anglia.
% *************************************************************************
% Licensed under the Apache License, Version 2.0 (the "License");
% you may not use this file except in compliance with the License.
% You may obtain a copy of the License at:
%
%   http://www.apache.org/licenses/LICENSE-2.0
%
% Unless required by applicable law or agreed to in writing, software
% distributed under the License is distributed on an "AS IS" BASIS,
% WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
% See the License for the specific language governing permissions and
% limitations under the License.
% *************************************************************************
%
function [stem, rule] = stemmer_uealite(max_length_word, word)
    stem = word;
    rule = 0;
    % Check if it is a problem word
    if strcmp(stem, 'is') || strcmp(stem, 'as') || strcmp(stem, 'this') || strcmp(stem, 'has') || strcmp(stem, 'was') || strcmp(stem, 'during')
        rule = 90;
    % Check word length
    elseif length(stem) > max_length_word
       rule = 95;
    % Check for apostrophe
    elseif ~isempty(strfind(stem, ''''))
        % rule 94 - contains apstrophe
        if ~isempty(regexp(stem, '^.*''[sS]$'))   % Remove posessive singular
           stem = remove(stem, '''s');
        end
        if ~isempty(regexp(stem, '^.*''$'))       % Remove posessive plural
           stem = remove(stem, '''');
        end
        % Expand contractions
        stem = strrep(stem, 'n''t', 'not');
        stem = strrep(stem, '''ve', 'have');
        stem = strrep(stem, '''re', 'are');
        stem = strrep(stem, '''m', 'am');
        rule = 94;
    elseif ~isempty(regexp(stem, '^\\d+$'))
        rule = 90.3;
    elseif ~isempty(regexp(stem, '^\\w+-\\w+$'))
        rule = 90.2;
    elseif ~isempty(regexp(stem, '^.*-.*$'))
        rule = 90.1;
    elseif ~isempty(regexp(stem, '^.*_.*$'))
        rule = 90;
    elseif ~isempty(regexp(stem, '^\\p{Upper}+s$'))
        rule = 91.1;
    elseif ~isempty(regexp(stem, '^\\p{Upper}+$'))
        rule = 90;
    elseif ~isempty(regexp(stem, '^.*\\p{Upper}.*\\p{Upper}.*$')) || ~isempty(regexp(stem, '^\\p{Upper}{1}.*$'))
        rule = 92;
    elseif endsWith(stem, 'aceous') 
        stem = remove(stem, 'aceous');
        rule = 1;  % 1
    elseif endsWith(stem, 'ces') 
        stem = remove(stem, 's');
        rule = 2;   % 2
    elseif endsWith(stem, 'cs') 
        rule = 3;  % 3
    elseif endsWith(stem, 'sis') 
        rule = 4;  % 4
    elseif endsWith(stem, 'tis') 
        rule = 5;  % 5
    elseif endsWith(stem, 'ss') 
        rule = 6;  % 6
    % rules 7 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem, 'eed') 
        rule = 7;  % 7
    elseif endsWith(stem, 'eeds') 
        stem = remove(stem, 's' );
        rule = 7;  % 7
    elseif endsWith(stem, 'ued') 
        stem = remove(stem, 'd');
        rule = 8;  % 8
    elseif endsWith(stem, 'ues') 
        stem = remove(stem, 's');
        rule = 9;  % 9
    elseif endsWith(stem, 'ees') 
        stem = remove(stem, 's');
        rule = 10;  % 10
    elseif endsWith(stem, 'iases') 
        stem = remove(stem, 'es');
        rule = 11.4;  % 11.4
    elseif endsWith(stem, 'uses') 
        stem = remove(stem, 's');
        rule = 11.3;  % 11.3
    elseif endsWith(stem, 'sses') 
        stem = remove(stem, 'es');
        rule = 11.2;  % 11.2
    elseif endsWith(stem, 'eses') 
        stem = remove(stem, 'es');
        stem = strcat(stem, 'is');
        rule = 11.1;  %11.1
    elseif endsWith(stem, 'ses') 
        stem = remove(stem, 's');
        rule = 11;  % 11
    elseif endsWith(stem, 'tled') 
        stem = remove(stem, 'd');
        rule = 12.5;  % 12.5
    elseif endsWith(stem, 'pled') 
        stem = remove(stem, 'd');
        rule = 12.4;  % 12.4
    elseif endsWith(stem, 'bled') 
        stem = remove(stem, 'd');
        rule = 12.3;  % 12.3
    elseif endsWith(stem, 'eled') 
        stem = remove(stem, 'ed');
        rule = 12.2;  % 12.2
    elseif endsWith(stem, 'lled') 
        stem = remove(stem, 'ed');
        rule = 12.1;  % 12.1
    elseif endsWith(stem, 'led') 
        stem = remove(stem, 'ed');
        rule = 12;  % 12
    elseif endsWith(stem,  'ened')
        stem = remove(stem, 'ed' );
        rule = 13.7;        % 13.7
    elseif endsWith(stem,  'ained')
        stem = remove(stem, 'ed' );
        rule = 13.6;        % 13.6
    elseif endsWith(stem,  'erned')
        stem = remove(stem, 'ed' );
        rule = 13.5;        % 13.5
    elseif endsWith(stem,  'rned')
        stem = remove(stem, 'ed' );
        rule = 13.4;        % 13.4
    elseif endsWith(stem,  'nned')
        stem = remove(stem, 'ned' );
        rule = 13.3;        % 13.3
    elseif endsWith(stem,  'oned')
        stem = remove(stem, 'ed' );
        rule = 13.2;        % 13.2
    elseif endsWith(stem,  'gned')
        stem = remove(stem, 'ed' );
        rule = 13.1;        % 13.1
    elseif endsWith(stem,  'ned')
        stem = remove(stem, 'd' );
        rule = 13;        % 13
    elseif endsWith(stem,  'ifted')
        stem = remove(stem, 'ed' );
        rule = 14;        % 14
    elseif endsWith(stem,  'ected')
        stem = remove(stem, 'ed' );
        rule = 15;        % 15
    elseif endsWith(stem,  'vided')
        stem = remove(stem, 'd' );
        rule = 16;        % 16
    elseif endsWith(stem,  'ved')
        stem = remove(stem, 'd' );
        rule = 17;        % 17
    elseif endsWith(stem,  'ced')
        stem = remove(stem, 'd' );
        rule = 18;        % 18
    elseif endsWith(stem,  'erred')
        stem = remove(stem, 'red' );
        rule = 19;        % 19
    elseif endsWith(stem,  'urred')
        stem = remove(stem, 'red' );
        rule = 20.5;        % 20.5
    elseif endsWith(stem,  'lored')
        stem = remove(stem, 'ed' );
        rule = 20.4;        % 20.4
    elseif endsWith(stem,  'eared')
        stem = remove(stem, 'ed' );
        rule = 20.3;        % 20.3
    elseif endsWith(stem,  'tored')
        stem = replaceFirst(stem, 'ed', 'e');
        rule = 20.2; 
    elseif endsWith(stem,  'ered')
        stem = remove(stem, 'ed' );
        rule = 20.1;        % 20.1
    % rules 20 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'red')
        stem = remove(stem, 'd' );
        rule = 20;        % 20
    elseif endsWith(stem,  'reds')
        stem = remove(stem, 'ds' );
        rule = 20;        % 20
    elseif endsWith(stem,  'tted')
        stem = remove(stem, 'ted' );
        rule = 21;        % 21
    elseif endsWith(stem,  'noted')
        stem = remove(stem, 'd' );
        rule = 22.4;        % 22.4
    elseif endsWith(stem,  'leted')
        stem = remove(stem, 'd' );
        rule = 22.3;        % 22.3
    elseif endsWith(stem,  'uted')
        stem = remove(stem, 'd' );
        rule = 22.2;        % 22.2
    elseif endsWith(stem,  'ated')
        stem = remove(stem, 'd' );
        rule = 22.1;        % 22.1
    elseif endsWith(stem,  'ted')
        stem = remove(stem, 'ed' );
        rule = 22;        % 22
    elseif endsWith(stem,  'anges')
        stem = remove(stem, 's' );
        rule = 23;        % 23
    elseif endsWith(stem,  'aining')
        stem = remove(stem, 'ing' );
        rule = 24;        % 24
    elseif endsWith(stem,  'acting')
        stem = remove(stem, 'ing' );
        rule = 25;        % 25
    % rules 26 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'tting')
        stem = remove(stem, 'ting' );
        rule = 26;        % 26
    elseif endsWith(stem,  'ttings')
        stem = remove(stem, 'tings' );
        rule = 26;        % 26
    elseif endsWith(stem,  'viding')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 27;        % 27
    elseif endsWith(stem,  'ssed')
        stem = remove(stem, 'ed' );
        rule = 28;        % 28
    elseif endsWith(stem,  'sed')
        stem = remove(stem, 'd' );
        rule = 29;        % 29
    elseif endsWith(stem,  'titudes')
        stem = remove(stem, 's' );
        rule = 30;        % 30
    elseif endsWith(stem,  'umed')
        stem = remove(stem, 'd' );
        rule = 31;        % 31
    elseif endsWith(stem,  'ulted')
        stem = remove(stem, 'ed' );
        rule = 32;        % 32
    elseif endsWith(stem,  'uming')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 33;        % 33
    elseif endsWith(stem,  'fulness')
        stem = remove(stem, 'ness' );
        rule = 34;        % 34
    elseif endsWith(stem,  'ousness')
        stem = remove(stem, 'ness' );
        rule = 35;        % 35
    % rules 36.1 ~ in the perl version these are all in one regrex ( r[aeiou]bed$ )
    elseif endsWith(stem,  'rabed')
        stem = remove(stem, 'd' );
        rule = 36.1;        % 36.1
    elseif endsWith(stem,  'rebed')
        stem = remove(stem, 'd' );
        rule = 36.1;        % 36.1
    elseif endsWith(stem,  'ribed')
        stem = remove(stem, 'd' );
        rule = 36.1;        % 36.1
    elseif endsWith(stem,  'robed')
        stem = remove(stem, 'd' );
        rule = 36.1;        % 36.1
    elseif endsWith(stem,  'rubed')
        stem = remove(stem, 'd' );
        rule = 36.1;        % 36.1
    % rules 36, 37 and 38 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'bed')
        stem = remove(stem, 'ed' );
        rule = 36;        % 36
    elseif endsWith(stem,  'beds')
        stem = remove(stem, 'eds' );
        rule = 36;        % 36
    elseif endsWith(stem,  'ssing')
        stem = remove(stem, 'ing' );
        rule = 37;        % 37
    elseif endsWith(stem,  'ssings')
        stem = remove(stem, 'ings' );
        rule = 37;        % 37
    elseif endsWith(stem,  'ulting')
        stem = remove(stem, 'ing' );
        rule = 38;        % 38
    % rules 39 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'ving')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 39;        % 39
    elseif endsWith(stem,  'vings')
        stem = replaceFirst(stem, 'ings', 'e');
        rule = 39;        % 39
    elseif endsWith(stem,  'eading')
        stem = remove(stem, 'ing' );
        rule = 40.7;        % 40.7
    elseif endsWith(stem,  'eadings')
        stem = remove(stem, 'ings' );
        rule = 40.7;      % 40.7
    elseif endsWith(stem,  'oading')
        stem = remove(stem, 'ing' );
        rule = 40.6;        % 40.6
    elseif endsWith(stem,  'oadings')
        stem = remove(stem, 'ings' );
        rule = 40.6;        % 40.6
    elseif endsWith(stem,  'eding')
        stem = remove(stem, 'ing' );
        rule = 40.5;        % 40.5
    elseif endsWith(stem,  'edings')
        stem = remove(stem, 'ings' );
        rule = 40.5;        % 40.5
    elseif endsWith(stem,  'dding')
        stem = remove(stem, 'ding' );
        rule = 40.4;        % 40.4
    elseif endsWith(stem,  'ddings')
        stem = remove(stem, 'dings' );
        rule = 40.4;        % 40.4
    elseif endsWith(stem,  'lding')
        stem = remove(stem, 'ing' );
        rule = 40.3;        % 40.3
    elseif endsWith(stem,  'ldings')
        stem = remove(stem, 'ings' );
        rule = 40.3;        % 40.3
    elseif endsWith(stem,  'rding')
        stem = remove(stem, 'ing' );
        rule = 40.2;        % 40.2
    elseif endsWith(stem,  'rdings')
        stem = remove(stem, 'ings' );
        rule = 40.2;        % 40.2
    elseif endsWith(stem,  'nding')
        stem = remove(stem, 'ing' );
        rule = 40.1;        % 40.1
    elseif endsWith(stem,  'ndings')
        stem = remove(stem, 'ings' );
        rule = 40.1;        % 40.1
    % rules 40, 41, 42 and 42.x ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'ding')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 40;        % 40
    elseif endsWith(stem,  'dings')
        stem = replaceFirst(stem, 'ings', 'e');
        rule = 40;      % 40
    elseif endsWith(stem,  'lling')
        stem = remove(stem, 'ling' );
        rule = 41;  		% stem ends in -lling 41
    elseif endsWith(stem,  'llings')
        stem = remove(stem, 'lings' );
        rule = 41;  		% stem ends in -lling 41
    elseif endsWith(stem,  'ealing')
        stem = remove(stem, 'ing' );
        rule = 42.4;  		% stem ends in -ealing 42.4
    elseif endsWith(stem,  'ealings')
        stem = remove(stem, 'ings' );
        rule = 42.4;  		% stem ends in -ealing 42.4
    elseif endsWith(stem,  'oling')
        stem = remove(stem, 'ing' );
        rule = 42.3;  		% stem ends in -oling 42.3
    elseif endsWith(stem,  'olings')
        stem = remove(stem, 'ings' );
        rule = 42.3;  		% stem ends in -oling 42.3
    elseif endsWith(stem,  'ailing')
        stem = remove(stem, 'ing' );
        rule = 42.2;  		% stem ends in -ailing 42.2
    elseif endsWith(stem,  'ailings')
        stem = remove(stem, 'ings' );
        rule = 42.2;  		% stem ends in -ailing 42.2
    elseif endsWith(stem,  'eling')
        stem = remove(stem, 'ing' );
        rule = 42.1;  		% stem ends in -ling 42.1
    elseif endsWith(stem,  'elings')
        stem = remove(stem, 'ings' );
        rule = 42.1;  		% stem ends in -ling 42.1
    elseif endsWith(stem,  'ling')
        stem = remove(stem, 'ing' );
        stem = strcat(stem, 'e' );
        rule = 42; 			% stem ends in -ting 48
    elseif endsWith(stem,  'lings')
        stem = remove(stem, 'ings' );
        stem = strcat(stem, 'e' );
        rule = 42; 			% stem ends in -ting 48
    elseif endsWith(stem,  'nged')
        stem = remove(stem, 'd' );
        rule = 43.2;  		% stem ends in -nged  43.2
    elseif endsWith(stem,  'gged')
        stem = remove(stem, 'ged' );
        rule = 43.1;  		% stem ends in -gged  43.1
    elseif endsWith(stem,  'ged')
        stem = remove(stem, 'd' );
        rule = 43;  			% stem ends in -ged  43
    % rules 44.3 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'mming')
        stem = remove(stem, 'ming' );
        rule = 44.3;  		% stem ends in -mming  44.3
    elseif endsWith(stem,  'mmings')
        stem = remove(stem, 'mings' );
        rule = 44.3;  		% stem ends in -mming  44.3
    elseif endsWith(stem,  'rming')
        stem = remove(stem, 'ing' );
        rule = 44.2;  		% stem ends in -rming  44.2
    elseif endsWith(stem,  'lming')
        stem = remove(stem, 'ing' );
        rule = 44.1;  		% stem ends in -lming  44.1
    % rules 44, 45 and 45.x ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'ming')
        stem = remove(stem, 'ing' );
        stem = strcat(stem, 'e' );
        rule = 44;  			% stem ends in -ting 48
    elseif endsWith(stem,  'mings')
        stem = remove(stem, 'ings' );
        stem = strcat(stem, 'e' );
        rule = 44;  			% stem ends in -ting 48
    elseif endsWith(stem,  'nging')
        stem = remove(stem, 'ing' );
        rule = 45.2;  		% stem ends in -ging 45.2
    elseif endsWith(stem,  'ngings')
        stem = remove(stem, 'ings' );
        rule = 45.2;  		% stem ends in -ging 45.2
    elseif endsWith(stem,  'gging')
        stem = remove(stem, 'ging' );
        rule = 45.1;  		% stem ends in -ging 45.1
    elseif endsWith(stem,  'ggings')
        stem = remove(stem, 'gings' );
        rule = 45.1;  		% stem ends in -ging 45.1
    elseif endsWith(stem,  'ging')
        stem = remove(stem, 'ing' );
        stem = strcat(stem, 'e' );
        rule = 45; 
    elseif endsWith(stem,  'gings')
        stem = remove(stem, 'ings' );
        stem = strcat(stem, 'e' );
        rule = 45; 
    elseif endsWith(stem,  'aning')
        stem = remove(stem, 'ing' );
        rule = 46.6;  		% stem ends in -aning 46.6
    elseif endsWith(stem,  'ening')
        stem = remove(stem, 'ing' );
        rule = 46.5;  		% stem ends in -ening 46.5
    elseif endsWith(stem,  'gning')
        stem = remove(stem, 'ing' );
        rule = 46.4;  		% stem ends in -gning 46.4
    elseif endsWith(stem,  'nning')
        stem = remove(stem, 'ning' );
        rule = 46.3;  		% stem ends in -nning 46.3
    elseif endsWith(stem,  'oning')
        stem = remove(stem, 'ing' );
        rule = 46.2;  		% stem ends in -oning 46.2
    elseif endsWith(stem,  'rning')
        stem = remove(stem, 'ing' );
        rule = 46.1;  		% stem ends in -rning 46.1
    elseif endsWith(stem,  'ning')
        stem = remove(stem, 'ing' );
        stem = strcat(stem, 'e' );
        rule = 46;  			% stem ends in -ting 46
    % rules 47 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'sting')
        stem = remove(stem, 'ing' );
        rule = 47;  		% stem ends in -sting 47
    elseif endsWith(stem,  'stings')
        stem = remove(stem, 'ings' );
        rule = 47;  		% stem ends in -sting 47
    % rules 48.4 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'eting')
        stem = remove(stem, 'ing' );
        rule = 48.4;  		% stem ends in -pting 48.4
    elseif endsWith(stem,  'etings')
        stem = remove(stem, 'ings' );
        rule = 48.4;  		% stem ends in -pting 48.4
    elseif endsWith(stem,  'pting')
        stem = remove(stem, 'ing' );
        rule = 48.3;  		% stem ends in -pting 48.3
    % rules 48.2 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'nting')
        stem = remove(stem, 'ing' );
        rule = 48.2;  		% stem ends in -nting 48.2
    elseif endsWith(stem,  'ntings')
        stem = remove(stem, 'ings' );
        rule = 48.2;  		% stem ends in -nting 48.2
    elseif endsWith(stem,  'cting')
        stem = remove(stem, 'ing' );
        rule = 48.1;  		% stem ends in -cting 48.1
    % rules 48 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'ting')
        stem = remove(stem, 'ing' ); stem = strcat(stem, 'e' );
        rule = 48;  			% stem ends in -ting 48
    elseif endsWith(stem,  'tings')
        stem = remove(stem, 'ings' ); stem = strcat(stem, 'e' );
        rule = 48;  			% stem ends in -ting 48
    elseif endsWith(stem,  'ssed')
        stem = remove(stem, 'ed' );
        rule = 49;  			% stem ends in -ssed 49
    elseif endsWith(stem,  'les')
        stem = remove(stem, 's' );
        rule = 50;  			% stem ends in -les 50
    elseif endsWith(stem,  'tes')
        stem = remove(stem, 's' );
        rule = 51;  			% stem ends in -tes 51
    elseif endsWith(stem,  'zed')
        stem = remove(stem, 'd' );
        rule = 52;  			% stem ends in -zed 52
    elseif endsWith(stem,  'lled')
        stem = remove(stem, 'ed' );
        rule = 53;  			% stem ends in -lled 53
    % rules 54.4, 54.3 and 54.2 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'iring')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 54.4; 
    elseif endsWith(stem,  'irings')
        stem = replaceFirst(stem, 'ings', 'e');
        rule = 54.4; 
    elseif endsWith(stem,  'uring')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 54.3; 
    elseif endsWith(stem,  'urings')
        stem = replaceFirst(stem, 'ings', 'e');
        rule = 54.3; 
    elseif endsWith(stem,  'ncing')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 54.2; 
    elseif endsWith(stem,  'ncings')
        stem = replaceFirst(stem, 'ings', 'e');
        rule = 54.2; 
    elseif endsWith(stem,  'zing')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 54.1; 
    % rules 54 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'sing')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 54; 
    elseif endsWith(stem,  'sings')
        stem = replaceFirst(stem, 'ings', 'e');
        rule = 54; 
    elseif endsWith(stem,  'lling')
        stem = remove(stem, 'ing' );
        rule = 55; 
    elseif endsWith(stem,  'ied')
        stem = replaceFirst(stem, 'ied', 'y');
        rule = 56; 
    elseif endsWith(stem,  'ating')
        stem = replaceFirst(stem, 'ing', 'e');
        rule = 57; 
    % rules 58.1 ~ plural change - this differs from Perl v1.03
    elseif endsWith(stem,  'thing')
        rule = 58.1; 
    % The stem 'things' was being caught by 58.1 so have added this rule, this should really have been caught by 68 but that wasn't happening
    elseif endsWith(stem,  'things')
        stem = remove(stem, 's' );
        rule = 58.1; 
    elseif ~isempty(regexp(stem, '.*\\w\\wings?$'))
        stem = stemWithDuplicateCharacterCheck(stem, 'ing');
        rule = 58; 
    elseif endsWith(stem,  'ies')
        stem = replaceFirst(stem, 'ies', 'y');
        rule = 59; 
    elseif endsWith(stem,  'lves')
        stem = replaceFirst(stem, 'ves', 'f');
        rule = 60.1; 
    elseif endsWith(stem,  'ves')
        stem = remove(stem, 's' );
        rule = 60; 
    elseif endsWith(stem,  'aped')
        stem = remove(stem, 'd' );
        rule = 61.3; 
    elseif endsWith(stem,  'uded')
        stem = remove(stem, 'd' );
        rule = 61.2; 
    elseif endsWith(stem,  'oded')
        stem = remove(stem, 'd' );
        rule = 61.1; 
    elseif endsWith(stem,  'ated')
        stem = remove(stem, 'd' );
        rule = 61; 
    elseif ~isempty(regexp(stem, '.*\\w\\weds?$'))
        stem = stemWithDuplicateCharacterCheck(stem, 'ed');
        rule = 62; 
    elseif endsWith(stem,  'pes')
        stem = remove(stem, 's' );
        rule = 63.8; 
    elseif endsWith(stem,  'mes')
        stem = remove(stem, 's' );
        rule = 63.7; 
    elseif endsWith(stem,  'ones')
        stem = remove(stem, 's' );
        rule = 63.6; 
    elseif endsWith(stem,  'izes')
        stem = remove(stem, 's' );
        rule = 63.5; 
    elseif endsWith(stem,  'ures')
        stem = remove(stem, 's' );
        rule = 63.4; 
    elseif endsWith(stem,  'ines')
        stem = remove(stem, 's' );
        rule = 63.3; 
    elseif endsWith(stem,  'ides')
        stem = remove(stem, 's' );
        rule = 63.2; 
    elseif endsWith(stem,  'ges')
        stem = remove(stem, 's' );
        rule = 63.1; 
    elseif endsWith(stem,  'es')
        stem = remove(stem, 'es' );
        rule = 63; 
    elseif endsWith(stem,  'is')
        stem = replaceFirst(stem, 'is', 'e');
        rule = 64; 
    elseif endsWith(stem,  'ous')
        rule = 65; 
    elseif endsWith(stem,  'ums')
        rule = 66; 
    elseif endsWith(stem,  'us')
        rule = 67; 
    elseif endsWith(stem,  's')
        stem = remove(stem, 's' );
        rule = 68;
    end
end
function [stem] = stemWithDuplicateCharacterCheck(word, remove)
    stem = word;
    if endsWith(stem, 's')
        stem = strcat(stem, 's');
    end
    stem = remove(stem, remove);
    if ~isempty(regexp(stem, '.*(\\w)\\1$'))
        stem = remove(stem, '.');
    end
end
% Removes the end of a word.
function [stem] = remove(word, ending)
    stem = word(1:(length(word)-length(ending)));
end
% Replaces the first occurrence of a string in a string.
function [stem] = replaceFirst(word, original, replacement)
    stem = regexprep(word, original, replacement, 'ONCE');
end
% Checks if a string ends with a string
function [bool] = endsWith(word, ending)
    wl = length(word);
    el = length(ending);
    if el > wl
       bool = 0;
    else
        bool = strcmp(word(end-el+1:end), ending);
    end
end