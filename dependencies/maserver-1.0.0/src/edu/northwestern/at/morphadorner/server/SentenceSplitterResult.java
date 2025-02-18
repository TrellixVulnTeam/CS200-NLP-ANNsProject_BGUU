package edu.northwestern.at.morphadorner.server;

/*  Please see the license information at the end of this file. */

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.data.Form;

import com.thoughtworks.xstream.annotations.*;

import edu.northwestern.at.morphadorner.corpuslinguistics.adornedword.*;
import edu.northwestern.at.morphadorner.server.converters.*;
import edu.northwestern.at.utils.Formatters;

/** Sentence splitter results.
 */

@XStreamAlias("SentenceSplitterResult")
public class SentenceSplitterResult extends WordTokenizerResult
{
    /** Melded sentences. */

    @XStreamConverter(MeldedSentencesListConverter.class)
    public List<String> meldedSentences;

    /** Create empty sentence splitter results.
     */

    public SentenceSplitterResult()
    {
        this.text               = "";
        this.corpusConfig       = "";
        this.langCode           = "";
        this.sentences          = null;
        this.meldedSentences    = null;
    }

    /** Create populated sentence splitter results.
     *
     *  @param  text                Text to tag.
     *  @param  corpusConfig        Corpus configuration.
     *  @param  langCode            Language.
     *  @param  sentences           Word and sentences.
     *  @param  meldedSentences     Melded (reconstituted) sentences.
     */

    public SentenceSplitterResult
    (
        String text ,
        String corpusConfig ,
        String langCode ,
        List<List<String>> sentences ,
        List<String> meldedSentences
    )
    {
        this.text               = text;
        this.corpusConfig       = corpusConfig;
        this.langCode           = langCode;
        this.sentences          = sentences;
        this.meldedSentences    = meldedSentences;
    }

    /** Return string version of class entry values.
     *
     *  @return     String of class entry values.
     */

    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        int nSent   = meldedSentences.size();

        switch ( nSent )
        {
            case 0  : sb.append( "No sentences found.\n" );
                      break;

            case 1  : sb.append( "1 sentence found.\n" );
                      break;

            default : sb.append
                        (
                            Formatters.formatIntegerWithCommas( nSent ) +
                            " sentences found.\n"
                        );
        }

        sb.append( "S#\tSentence\n" );

        for ( int i = 0 ; i < meldedSentences.size() ; i++ )
        {
            String sentence = meldedSentences.get( i );

            sb.append( ( i + 1 ) + "\t" );
            sb.append( sentence );
            sb.append( "\n" );
        }

        return sb.toString();
    }
}

/*
Copyright (c) 2012, 2013 by Northwestern University.
All rights reserved.

Developed by:
   Academic and Research Technologies
   Northwestern University
   http://www.it.northwestern.edu/about/departments/at/

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal with the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimers.

    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimers in the documentation and/or other materials provided
      with the distribution.

    * Neither the names of Academic and Research Technologies,
      Northwestern University, nor the names of its contributors may be
      used to endorse or promote products derived from this Software
      without specific prior written permission.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE CONTRIBUTORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
*/



