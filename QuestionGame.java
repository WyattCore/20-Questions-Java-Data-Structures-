

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionGame {
    QuestionGame.QuestionNode head;
    private QuestionGame.QuestionNode root;

    public QuestionGame(String object) {
        this.head = this.root = new QuestionGame.QuestionNode(object);
        this.root.type = "A:";
    }

    public QuestionGame(Scanner input) {
        this.root = this.preOrder(input);
        this.head = this.root;
    }

    public QuestionGame.QuestionNode preOrder(Scanner input) {
        QuestionGame.QuestionNode node;
        if (input.nextLine().equals("A:")) {
            node = new QuestionGame.QuestionNode(input.nextLine());
            node.type = "A:";
            return node;
        } else {
            node = new QuestionGame.QuestionNode(input.nextLine());
            node.type = "Q:";
            node.yes = this.preOrder(input);
            node.no = this.preOrder(input);
            return node;
        }
    }

    public void saveQuestions(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException("PrintStream is null");
        } else {
            this.printTree(this.head, output);
        }
    }

    public void play(Scanner console) {
        boolean bruh;
        while(!this.root.isLeaf()) {
            bruh = this.yesTo(this.root.getValue(), console);
            if (bruh) {
                this.root = this.root.yes;
            } else {
                this.root = this.root.no;
            }
        }

        System.out.println("I guess that your object is a " + this.root.getValue());
        bruh = this.yesTo("Am I right?", console);
        if (bruh) {
            System.out.println("Haha! I win!");
        } else {
            System.out.println("Dang, that's weird. Please help me get better!");
            System.out.println("What is your object");
            String input3 = console.nextLine();
            PrintStream var10000 = System.out;
            String var10001 = this.root.getValue();
            var10000.println("Please give me a yes/no question that distinguishes between a " + var10001 + " and a " + input3 + ".");
            String input4 = console.nextLine();
            if (!input4.isEmpty()) {
                System.out.println("Q:" + input4);
            }

            bruh = this.yesTo("Is the answer, 'yes' for " + input3 + "? ", console);
            QuestionGame.QuestionNode yesLeaf;
            QuestionGame.QuestionNode noLeaf;
            if (bruh) {
                yesLeaf = new QuestionGame.QuestionNode(input3);
                yesLeaf.type = "A:";
                noLeaf = new QuestionGame.QuestionNode(this.root.getValue());
                noLeaf.type = "A:";
                this.root.yes = yesLeaf;
                this.root.no = noLeaf;
            } else {
                yesLeaf = new QuestionGame.QuestionNode(this.root.getValue());
                yesLeaf.type = "A:";
                noLeaf = new QuestionGame.QuestionNode(input3);
                noLeaf.type = "A:";
                this.root.no = noLeaf;
                this.root.yes = yesLeaf;
            }

            this.root.data = input4;
            this.root.type = "Q:";
        }

        this.root = this.head;
    }

    public void printTree(QuestionGame.QuestionNode rt, PrintStream output) {
        if (rt != null) {
            output.println(rt.getType());
            output.println(rt.getValue());
            this.printTree(rt.yes, output);
            this.printTree(rt.no, output);
        }
    }

    public boolean yesTo(String prompt, Scanner console) {
        System.out.print(prompt + " (y/n)? ");

        String response;
        for(response = console.nextLine().trim().toLowerCase(); !response.equals("y") && !response.equals("n"); response = console.nextLine().trim().toLowerCase()) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
        }

        return response.equals("y");
    }

    public class QuestionNode {
        String type;
        private String data;
        QuestionGame.QuestionNode yes;
        QuestionGame.QuestionNode no;

        public QuestionNode(String data, QuestionGame.QuestionNode yes, QuestionGame.QuestionNode no) {
            this.data = data;
            this.yes = yes;
            this.no = no;
        }

        public QuestionNode(String data) {
            this.data = data;
        }

        public void setValue(String set) {
            this.data = set;
        }

        public String getValue() {
            return this.data;
        }

        public String getType() {
            return this.type;
        }

        public boolean isLeaf() {
            return this.yes == null && this.no == null;
        }
    }
}
