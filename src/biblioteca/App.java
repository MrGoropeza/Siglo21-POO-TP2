package biblioteca;

import biblioteca.console.ioc.DependencyContainer;
import biblioteca.console.utils.DisplayHelper;

/**
 * Main application entry point
 */
public class App {

    public static void main(String[] args) {
        try {
            DisplayHelper.printInfo("Inicializando sistema de biblioteca...");

            DependencyContainer container = new DependencyContainer();
            container.initialize();

            DisplayHelper.printSuccess("Sistema inicializado correctamente");

            container.getMainController().start();

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error fatal en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}