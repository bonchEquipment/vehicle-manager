package utility;

/**
 * class for displaying information in console
 */
public class ConsoleOutputSystem implements AbstractOutputSystem {

    /**
     * showing message in the console
     * @param message that needed to be displayed
     */
    public void showMessage (String message){
        System.out.println(message);
    }
}
