package ca.fourthreethreefour.commands;

public class Print {

    private final String str;

    public Print(String str) {
        this.str = str;
    }

    public void print() {
        System.out.println(str);
    }
}
