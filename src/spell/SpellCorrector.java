package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {

    private Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner scanner = new Scanner(new File(dictionaryFileName));
        while (scanner.hasNext()){
            String word = scanner.next().toLowerCase();
            trie.add(word);
        }

        scanner.close();
    }
//    @Override
//    public String suggestSimilarWord(String inputWord){
//
//        inputWord = inputWord.toLowerCase();
//        int compared_frequency = 0;
//        Set<String> possible_Words = new TreeSet<>();
//        String answer = "";
//        if(trie.find(inputWord) != null){
//            return inputWord;
//        } else {
//            deletion_distance(inputWord, possible_Words);
//            transpositioin_distance(inputWord, possible_Words);
//            insertion_distance(inputWord, possible_Words);
//            alteration_distance(inputWord, possible_Words);
//            for(String word : possible_Words){
//                if(trie.find(word) != null){
//                    if(trie.find(word).getValue() > compared_frequency){
//                        compared_frequency = trie.find(word).getValue();
//                        answer = word;
//                    }
//                }
//            }
//        }
//        Set<String> possible2 = new TreeSet<>();
//        if(answer.equals("")){
//            for(String word : possible_Words){
//                deletion_distance(word, possible2);
//                transpositioin_distance(word, possible2);
//                insertion_distance(word, possible2);
//                alteration_distance(word, possible2);
//            }
//        }
//
//        compared_frequency = 0;
//        for(String word : possible2){
//            if(trie.find(word) != null){
//                if(trie.find(word).getValue() > compared_frequency){
//                    compared_frequency = trie.find(word).getValue();
//                    answer = word;
//                }
//            }
//        }
//        if(answer.equals("")){
//            return null;
//        } else {
//            return answer;
//        }
//
//
//    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        String lower_input_word = inputWord.toLowerCase();
        String most_possible_word = "";
        Set<String> potential_words = new TreeSet<>();
        int compare_frequency = 0;

        if(trie.find(lower_input_word) != null) {
            return lower_input_word;
        } else {
            //Add all of the potential words in to the set
            deletion_distance(lower_input_word, potential_words);
            transpositioin_distance(lower_input_word, potential_words);
            alteration_distance(lower_input_word, potential_words);
            insertion_distance(lower_input_word, potential_words);

            //Compare all the potential words from the trie
            for(String word : potential_words) {
                if (trie.find(word) != null) {
                    if(trie.find(word).getValue() > compare_frequency){
                        compare_frequency = trie.find(word).getValue();
                        most_possible_word = word;
                    }
                }
            }
        }

        Set<String> potential_words_2 = new TreeSet<>();

        //If there is no likely word, we will have to get potential words again from the distance 2
        if(most_possible_word.equals("")) {
            for(String word : potential_words) {
                deletion_distance(word, potential_words_2);
                transpositioin_distance(word, potential_words_2);
                alteration_distance(word, potential_words_2);
                insertion_distance(word, potential_words_2);
            }
            compare_frequency = 0;
            for(String word : potential_words_2) {
                if(trie.find(word) != null) {
                    if(trie.find(word).getValue() > compare_frequency){
                        compare_frequency = trie.find(word).getValue();
                        most_possible_word = word;
                    }
                }
            }

        }

        if(most_possible_word.equals("")) {
            return null;
        } else {
            return most_possible_word;
        }
    }


    private void deletion_distance(String inputWord, Set<String> potential_words) {
        final int length = inputWord.length();
        StringBuilder build_word = new StringBuilder(inputWord);

        for (int i = 0; i < length; i++) {
            build_word.deleteCharAt(i);
            potential_words.add(build_word.toString());
            build_word = new StringBuilder(inputWord);
        }
    }

    private void transpositioin_distance(String inputWord, Set<String> potential_words) {
        final int length = inputWord.length() - 1;
        StringBuilder build_word = new StringBuilder(inputWord);

        for (int i = 0; i < length; i++) {
            build_word.setCharAt(i, inputWord.charAt(i+1));
            build_word.setCharAt(i+1, inputWord.charAt(i));
            potential_words.add(build_word.toString());
            build_word = new StringBuilder(inputWord);
        }

    }

    private void alteration_distance(String inputWord, Set<String> potential_words) {
        final int ITERATION_TIME = 26;
        final int length = inputWord.length();
        StringBuilder build_word = new StringBuilder(inputWord);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < ITERATION_TIME; j++) {
                build_word.setCharAt(i, (char)('a'+j));
                potential_words.add(build_word.toString());
                build_word = new StringBuilder(inputWord);
            }
        }

    }
    private void insertion_distance(String inputWord, Set<String> potential_words) {
        final int length = inputWord.length() + 1;
        final int ITERATION_TIME = 26;
        StringBuilder build_word = new StringBuilder(inputWord);

        for (int i = 0; i < length; i++) {
            for(int j = 0; j < ITERATION_TIME; j++) {
                build_word.insert(i, (char)('a'+j));
                potential_words.add(build_word.toString());
                build_word = new StringBuilder(inputWord);
            }
        }
    }
}
