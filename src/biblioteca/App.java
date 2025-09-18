package biblioteca;

import biblioteca.console.ioc.DependencyContainer;
import biblioteca.console.utils.DisplayHelper;

/**
 * Main application entry point
 */
public class App {

    public static void main(String[] args) {
        try {
            // Initialize dependency injection container
            DisplayHelper.mostrarInfo("Inicializando sistema de biblioteca...");

            DependencyContainer container = new DependencyContainer();
            container.initialize();

            DisplayHelper.mostrarExito("Sistema inicializado correctamente");

            // Start main application
            container.getMainController().start();

        } catch (Exception e) {
            DisplayHelper.mostrarError("Error fatal en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}