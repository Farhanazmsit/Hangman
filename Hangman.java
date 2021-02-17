package HangmanProject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    String availableLetters = "abcdefghijklmnopqrstuvwxyz", guessedLetters="";
    static void start(BufferedReader in, PrintWriter out) throws Exception{
        Scanner sc= new Scanner(in);
        Hangman game = new Hangman();
        ArrayList<String> words = game.getWordsList();
        int randomNumber = game.getRandomValue(words.size());
        String secretWord = game.generateWord(words, randomNumber).toLowerCase(), word = "";
        HashMap<Character, Integer> charFrequency = game.getCharFrequency(secretWord);
        out.println("YOUR WORD LENGTH: "+secretWord.length());
        for (int index=0; index<secretWord.length(); index++)
            out.print("-");
        out.print("\n");
        int guesses = secretWord.length()-1, score = 0;
        do{
            out.print("\n");
            out.println("Number of guesses you have: "+ guesses);
            game.showGuessedLetters(out);
            game.showAvailableLetters(out);
            out.println("Enter a character to guess:");
            char guessChar = sc.nextLine().charAt(0); // input from client
            if(game.checkGuessedCharacters(guessChar))
                out.println("You have already guessed it.Try another.");
            else if (charFrequency.containsKey(guessChar)) {
                word = game.buildWord(secretWord, guessChar);
                score += charFrequency.get(guessChar);
                charFrequency.remove(guessChar);
            }
            else{
                out.println("Sorry you have guessed wrong!Try again!");
                guesses--;
            }
            game.guessedLetters += guessChar;
            game.availableLetters = game.updateAvailableChars(guessChar);
            game.printWord(out, word);
            out.println("Your Score: "+score);
        }while(guesses > 0 && !(word.equals(secretWord)));
        if(score == secretWord.length())
            out.println("You won! Congratulations");
        else
            out.println("Better luck next time.The word is "+secretWord);
        out.println("Final Score: "+score);
    }
    ArrayList<String> getWordsList() throws IOException{
        ArrayList<String> words = new ArrayList<>();
        File fileObj = new File("src\\HangmanProject\\words");
        Scanner scan = new Scanner(new FileInputStream(fileObj));
        while (scan.hasNextLine()){
            String temp = scan.nextLine();
            words.add(temp);
        }
        return words;
    }

    int getRandomValue(int value){
        Random rand = new Random();
        return rand.nextInt(value);
    }

    String generateWord(ArrayList<String> list, int randomNumber){
        return list.get(randomNumber);
    }

    void showGuessedLetters(PrintWriter out){
        out.println("Guessed letters so far.");
        for (int index = 0; index < guessedLetters.length(); index++)
            out.print(guessedLetters.charAt(index)+" ");
        out.print("\n");
    }

    void showAvailableLetters(PrintWriter out){
        out.println("Available letters for you to try.");
        for (int index = 0; index < availableLetters.length(); index++)
            out.print(availableLetters.charAt(index)+" ");
        out.print("\n");
    }

    HashMap<Character, Integer> getCharFrequency(String word){
        HashMap<Character, Integer> charFreqDict= new HashMap<>();
        for (int index = 0; index < word.length(); index++) {
            if (charFreqDict.containsKey(word.charAt(index)))
                charFreqDict.put(word.charAt(index), charFreqDict.get(word.charAt(index))+1);
            else
                charFreqDict.put(word.charAt(index),1);
        }
        return charFreqDict;
    }

    String buildWord(String word, char guessChar){
        String temp = "";
        for (int index = 0;index < word.length(); index++)
            if(checkGuessedCharacters(word.charAt(index)) || word.charAt(index) == guessChar )
                temp += word.charAt(index);
            else
                temp += '-';
        return temp;
    }

    boolean checkGuessedCharacters(char guessChar){
        for (int index = 0; index < guessedLetters.length(); index++)
            if (guessChar == guessedLetters.charAt(index))
                return true;
        return false;
    }

    void printWord(PrintWriter out, String word){
        for (int index = 0; index < word.length(); index++)
            out.print(word.charAt(index));
        out.print("\n");
    }

    String updateAvailableChars(char guessChar){
        for(int index = 0; index < availableLetters.length(); index++)
            if (availableLetters.charAt(index) == guessChar) {
                return availableLetters.substring(0, index) + availableLetters.substring(index+1);
            }
        return availableLetters;
    }
}