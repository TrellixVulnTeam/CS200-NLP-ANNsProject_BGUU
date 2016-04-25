package edu.northwestern.at.morphadorner.server.tests;

/*  Please see the license information at the end of this file. */

import java.io.*;
import java.util.*;

import org.restlet.*;
import org.restlet.ext.json.*;
import org.restlet.ext.xml.*;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.restlet.service.*;

import org.restlet.ext.xstream.XstreamRepresentation;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.XStream;

import edu.northwestern.at.morphadorner.server.*;
import edu.northwestern.at.morphadorner.corpuslinguistics.adornedword.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/** Part of speech tagger service tests. */

public class TestPartOfSpeechTaggerService extends BaseTest
{
    /** Text to tag. */

    protected static String sampleText  =
        "Mary had a little lamb whose fleece was white as snow.";

    /** Token list. */

    protected static String[] tokens    =
        new String[]
        {
            "Mary" ,
            "had" ,
            "a" ,
            "little" ,
            "lamb" ,
            "whose" ,
            "fleece" ,
            "was" ,
            "white" ,
            "as" ,
            "snow" ,
            "."
        };

    /** Test part of speech tagger service. */

    @Test
    public void testPartOfSpeechTaggerService()
    {
                                //  Create client resource.

        ClientResource resource =
            createClientResource( "partofspeechtagger" );

                                //  Add query parameters.

        resource.addQueryParameter( "text" , sampleText );
        resource.addQueryParameter( "corpusConfig" , "ncf" );
        resource.addQueryParameter( "media" , "xml" );

                                //  Get result from server.
        try
        {
            Representation xmlResult    = resource.get();

            XStream xstream = new XStream( new StaxDriver() );

            xstream.alias( "token" , String.class );
            xstream.alias( "sentence" , ArrayList.class );
            xstream.alias( "adornedSentence" , ArrayList.class );
            xstream.alias( "adornedWord" , BaseAdornedWord.class );
            xstream.alias( "PartOfSpeechTaggerResult" ,
                edu.northwestern.at.morphadorner.server.
                    PartOfSpeechTaggerResult.class );

            PartOfSpeechTaggerResult result = null;

            try
            {
                result  =
                    (PartOfSpeechTaggerResult)xstream.fromXML
                    (
                        xmlResult.getText()
                    );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                fail();
            }
                                //  Check results.

            assertEquals( 1 , result.sentences.size() );

            List<String> tokSentence    = result.sentences.get( 0 );

            assertEquals( tokens.length , tokSentence.size() );

                                //  Display result.

            for ( int i = 0 ; i < result.adornedSentences.size() ; i++ )
            {
                List<AdornedWord> sentence  =
                    result.adornedSentences.get( i );

                for ( int j = 0 ; j < sentence.size() ; j++ )
                {
                    AdornedWord adornedWord = sentence.get( j );

                    System.out.println
                    (
                        "token: " + adornedWord +
                        ", pos: " + adornedWord.getPartsOfSpeech() +
                        ", lemma: " + adornedWord.getLemmata()
                    );
                }
            }
        }
        catch ( Exception e )
        {
            System.out.println( "Error: " + e.getMessage() );
            fail();
        }
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


