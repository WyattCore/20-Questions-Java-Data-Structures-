import java.io.PrintStream;
import java.util.Scanner;

public class QuestionGame {
    QuestionNode head;

    private QuestionNode root;
    //////////////////////////////////////////Constructors///////////////////////////////////////////////
    public QuestionGame(String object){   // For when an empty file is given, creates a binary tree with just one object
         head = root = new QuestionNode(object);
         root.type = "A:";

    }

    public QuestionGame(Scanner input){


         root = preOrder(input);
         head = root;
    } //constructor that accepts a file

    /////////////////////////////////////////////ConstructorHelper////////////////////////////////////////////
    public QuestionNode preOrder(Scanner input){
         if(input.nextLine().equals("A:")){       //same base case, recurses after find answer
            QuestionNode answer = new QuestionNode(input.nextLine());
            answer.type = "A:";
            return answer;
        }else{
            QuestionNode node = new QuestionNode(input.nextLine());
            node.type = "Q:";
            node.yes = preOrder(input);
            node.no = preOrder(input);
            return node;
        }

    }
    ///////////////////////////////////////SavesGameStuff//////////////////////////////////////////////////
    public void saveQuestions(PrintStream output){
        if(output == null){
            throw new IllegalArgumentException("PrintStream is null");
        }
        printTree(head,output);

    }
    ///////////////////////////////////////CallToPlay//////////////////////////////////////////////////
    public void play(Scanner console) {
        boolean bruh;

        while (!root.isLeaf()) {
            bruh = yesTo(root.getValue() , console);
            if (bruh) {                                      //if yes
                root = root.yes;
            } else {                                         //if no
                root = root.no;
            }
        }
        System.out.println("I guess that your object is a " + root.getValue());
        bruh = yesTo("Am I right?", console);
        //char input2 = console.next(".").charAt(0);
        if (bruh) {
            System.out.println("Haha! I win!");
        } else {
            System.out.println("Dang, that's weird. Please help me get better!");
            System.out.println("What is your object");
            String input3 = console.nextLine();                     //the object you guessed
            System.out.println("Please give me a yes/no question that distinguishes between a " + root.getValue()
                    + " and a " + input3 + ".");
            String input4 = console.nextLine();                       //the question

            if (!input4.isEmpty()) {
                System.out.println("Q:" + input4);
            }
            bruh = yesTo("Is the answer, 'yes' for " + input3 + "? ", console);
            //char input5 = console.next(".").charAt(0);
            if (bruh) {
                QuestionNode yesLeaf = new QuestionNode(input3);
                yesLeaf.type = "A:";
                QuestionNode noLeaf = new QuestionNode(root.getValue());
                noLeaf.type = "A:";
                root.yes = yesLeaf;
                root.no = noLeaf;
            } else {
                QuestionNode yesLeaf = new QuestionNode(root.getValue());
                yesLeaf.type = "A:";
                QuestionNode noLeaf = new QuestionNode(input3);
                noLeaf.type = "A:";
                root.no = noLeaf;
                root.yes = yesLeaf;
            }
            root.data = input4;
            root.type = "Q:";

        }
        root = head;
    }

    ////////////////////////////////////////CallToPrint/////////////////////////////////////////////////
    public void printTree(QuestionNode rt, PrintStream output){
        if(rt == null){ return;}
        else{
            output.println(rt.getType());
            output.println(rt.getValue());
            printTree(rt.yes, output);
            printTree(rt.no, output);
        }
    }
    /////////////////////////////////////////YesToMethod//////////////////////////////////////////////////
    public boolean yesTo(String prompt, Scanner console) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
    /////////////////////////////////////////ClassForNodes////////////////////////////////////////////////
    public class QuestionNode {      //fields representing data must be "final"

        String type;
        private String data;

        QuestionNode yes, no;

        public QuestionNode(String data,QuestionNode yes, QuestionNode no){
            this.data = data;
            this.yes = yes;
            this.no = no;
        }
        ////////////////////////////////////////////Constructor/////////////////////////////////////////////
        public QuestionNode(String data) {
            this.data = data;
        }

        //////////////////////////////////////////Setters///////////////////////////////////////////////
        public void setValue(String set){data = set;}

        //////////////////////////////////////////Getters///////////////////////////////////////////////
        public String getValue(){return data;}

        public String getType(){return type;}
        ///////////////////////////////////////////IsItALeaf//////////////////////////////////////////////
        public boolean isLeaf() {
            if (this.yes == null && this.no == null) {
                return true;
            }else {
                return false;
            }
        }
    }
}
