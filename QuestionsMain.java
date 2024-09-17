// QuestionsMain contains a main program that plays N-Questions with a user.
// It asks the user where to read the questions from before playing, and always
// writes the result to that file to make itself better next time.

import java.io.*;
import java.util.*;

public class QuestionMain {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to the 20 Questions Game!");
        System.out.println();
        System.out.print("Which questions file would you like to use? ");
        String filename = console.nextLine().trim(); // "filename" is the variable name of the file you are reading from

        /* Create the Questions File if it doesn't exist */
        File questionsFile = new File(filename);
        if (!questionsFile.exists()) {
            questionsFile.createNewFile();         //creates a new file
        }

        Scanner questions = new Scanner(questionsFile); //sets up the scanner for the file

        QuestionGame game;                     // just creates a new game variable

        /* Check if the file has anything in it.  If it does, use it.  Otherwise, initialize
         * a new game. */
        if (!questions.hasNext()) {
            System.out.println("There are no objects to guess in that questions file.");
            System.out.print("Can you provide me with an initial object? ");
            String initialObject = console.nextLine().toLowerCase().trim();
            game = new QuestionGame(initialObject);               // just asks for one object, will create a tree
        }                                                         // with just a root with that object
        else {                         // so the file has contents, so initialize a new game
            game = new QuestionGame(questions);     // calls the second constructor with scanner in QuestionGame class
        }                                           // "questions" is the file you are reading from

        System.out.print("Let's play!  ");
        do {
            System.out.println("Please choose your object, and I'll start guessing.");
            System.out.println("Press Enter when you're ready to begin!");
            console.nextLine();
            game.play(console);
            System.out.println();
            game.saveQuestions(new PrintStream(questionsFile));
            System.out.print("Do you want to play again (y/n)? ");
        } while (console.nextLine().trim().toLowerCase().startsWith("y"));
    }
}
