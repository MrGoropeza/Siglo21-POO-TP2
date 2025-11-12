package biblioteca.console.controllers;

import java.util.List;

import biblioteca.application.usecases.notification.ListNotificationsUseCase;
import biblioteca.application.usecases.notification.ListNotificationsUseCase.NotificationFilter;
import biblioteca.application.usecases.notification.ListNotificationsUseCase.NotificationStats;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.NotificationRepository;
import biblioteca.domain.notifications.Notification;

/**
 * Controller for notification management functionality.
 * Shows notification history sent automatically by the system.
 */
public class NotificationController {

    private final ListNotificationsUseCase listNotificationsUseCase;

    /**
     * Constructor with dependency injection
     * 
     * @param notificationRepository Notification repository
     */
    public NotificationController(NotificationRepository notificationRepository) {
        this.listNotificationsUseCase = new ListNotificationsUseCase(notificationRepository);
    }

    /**
     * Show notification management menu
     */
    public void showMenu() {
        while (true) {
            DisplayHelper.renderTitle("GESTIÓN DE NOTIFICACIONES");
            System.out.println();

            // Show statistics
            NotificationStats stats = listNotificationsUseCase.getStats();
            System.out.println("Estadísticas:");
            System.out.println(
                    "Total: " + stats.total() + " | Enviadas: " + stats.sent() + " | Pendientes: " + stats.pending());
            System.out.println();

            System.out.println("Opciones:");
            System.out.println();
            System.out.println("1. Ver historial de notificaciones");
            System.out.println();
            System.out.println("0. Volver al menú principal");
            System.out.println();

            int option = InputHelper.leerEnteroEnRango("Seleccione una opción", 0, 1);

            if (option == 0) {
                return;
            }

            if (option == 1) {
                viewNotificationsMenu();
            }

            System.out.println();
            InputHelper.pausar();
        }
    }

    /**
     * Show notifications history menu
     */
    private void viewNotificationsMenu() {
        DisplayHelper.renderSubtitle("Historial de Notificaciones");
        System.out.println();

        // Select filter
        System.out.println("Filtrar por:");
        System.out.println("1. Todas las notificaciones");
        System.out.println("2. Solo enviadas");
        System.out.println("3. Solo pendientes");
        System.out.println();

        int filterOption = InputHelper.leerEnteroEnRango("Filtro", 1, 3);

        NotificationFilter filter = switch (filterOption) {
            case 1 -> NotificationFilter.ALL;
            case 2 -> NotificationFilter.SENT;
            case 3 -> NotificationFilter.PENDING;
            default -> NotificationFilter.ALL;
        };

        List<Notification> notifications = listNotificationsUseCase.execute(filter);

        System.out.println();
        System.out.println(DisplayHelper.SEPARATOR);
        System.out
                .println("Mostrando: " + filter.getDisplayName() + " (" + notifications.size() + " notificación(es))");
        System.out.println(DisplayHelper.SEPARATOR);
        System.out.println();

        if (notifications.isEmpty()) {
            DisplayHelper.printInfo("No hay notificaciones para mostrar.");
            return;
        }

        // Display notifications
        for (Notification notification : notifications) {
            String status = notification.isSent() ? "ENVIADA" : "PENDIENTE";
            System.out.println("ID: " + notification.getId() + " | Canal: " + notification.getChannelName()
                    + " | Estado: " + status);
            System.out.println("Para: " + notification.getRecipientInfo());
            System.out.println("Creada: " + notification.formatDateTime(notification.getCreatedAt()));
            if (notification.isSent()) {
                System.out.println("Enviada: " + notification.formatDateTime(notification.getSentAt()));
            }
            System.out.println("Mensaje: " + notification.getMessage());
            System.out.println("-".repeat(80));
            System.out.println();
        }
    }
}
