package com.Data;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static org.apache.lucene.util.Version.*;

public class KeyWord implements Comparator<KeyWord>{
    private final String stem;
    private final HashSet<String> terms = new HashSet<>();
    private int frequency = 0;

    public KeyWord(String stem){
        this.stem = stem;
    }

    @Override
    public int compare(KeyWord o1, KeyWord o2) {
        return o1.frequency - o2.frequency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof KeyWord)) {
            return false;
        } else {
            return stem.equals(((KeyWord) obj).stem);
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { stem });
    }

    public void add(String term) {
        terms.add(term);
        frequency++;
    }

    public String getStem() {
        return stem;
    }

    public HashSet<String> getTerms() {
        return terms;
    }

    public static String Stemming(String terms) throws IOException {
        TokenStream tokenStream = null;
        try {
            tokenStream = new ClassicTokenizer(LUCENE_36, new StringReader(terms));
            tokenStream = new PorterStemFilter(tokenStream);

            HashSet<String> stems = new HashSet<>();
            CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()){
                stems.add(token.toString());
            }

            if(stems.size() != 1){
                return null;
            }

            String stem = stems.iterator().next();
            if(!stem.matches("[a-zA-Z]+")){
                return null;
            }
            return stem;

        } finally {
            if(tokenStream!=null){
                tokenStream.close();
            }
        }
    }

    public static <T> T Find(Collection<T> collection, T example) {
        for (T element : collection) {
            if (element.equals(example)) {
                return element;
            }
        }
        collection.add(example);
        return example;
    }

    public static List<KeyWord> ExtractKeyWord(String input) throws IOException {
        TokenStream tokenStream = null;
        try{
            // hack to keep dashed words (e.g. "non-specific" rather than "non" and "specific")
            input = input.replaceAll("-+", "-0");
            // replace any punctuation char but apostrophes and dashes by a space
            input = input.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
            // replace most common english contractions
            input = input.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");

            // tokenize input
            tokenStream = new ClassicTokenizer(Version.LUCENE_36, new StringReader(input));
            // to lowercase
            tokenStream = new LowerCaseFilter(Version.LUCENE_36, tokenStream);
            // remove dots from acronyms (and "'s" but already done manually above)
            tokenStream = new ClassicFilter(tokenStream);
            // convert any char to ASCII
            tokenStream = new ASCIIFoldingFilter(tokenStream);
            // remove english stop words
            tokenStream = new StopFilter(Version.LUCENE_36, tokenStream, EnglishAnalyzer.getDefaultStopSet());

            List<KeyWord> keywords = new LinkedList<KeyWord>();
            CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                String term = token.toString();
                // stem each term
                String stem = Stemming(term);
                if (stem != null) {
                    // create the keyword or get the existing one if any
                    KeyWord keyword = Find(keywords, new KeyWord(stem.replaceAll("-0", "-")));
                    // add its corresponding initial token
                    keyword.add(term.replaceAll("-0", "-"));
                }
            }
            // reverse sort by frequency
            Collections.sort(keywords, new Comparator<KeyWord>() {
                @Override
                public int compare(KeyWord o1, KeyWord o2) {
                    return o2.frequency - o1.frequency;
                }
            });

            return keywords;
        } finally {
            if (tokenStream != null) {
                tokenStream.close();
            }
        }
    }

}
