public class MKBankApp {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.printWelcomeMassage();
        int userChoice = 0;
        int quitApp = 8;
        while (userChoice != quitApp) {
            menu.printOptions();
            userChoice = menu.getUserInput();
            menu.chooseOption(userChoice);
        }
    }
}