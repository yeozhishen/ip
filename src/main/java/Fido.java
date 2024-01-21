public class Fido {
    public void Greet() {
        String greeting = "Hello! I'm Fido\n"
                + "What can I do for you?";
        printlnHorizontalLine();
        System.out.println(greeting);
        printlnHorizontalLine();
    }
    public void Exit() {
        String exitString = "Bye. Hope to see you again soon!";
        System.out.println(exitString);
        printlnHorizontalLine();
    }
    static void printlnHorizontalLine() {
        String line = "--------------------------------------";
        System.out.println(line);
    }
    public static void main(String [] args){
        Fido chatbot = new Fido();
        chatbot.Greet();
        chatbot.Exit();
    }
}
