package biblioteca.domain.notifications;

/**
 * Console notification implementation.
 * Sends notifications by printing to console/terminal.
 * 
 * Demonstrates inheritance from Notification base class.
 */
public class ConsoleNotification extends Notification {

    /**
     * Constructor
     * 
     * @param id            Notification ID
     * @param message       Message to send
     * @param recipientInfo Recipient user ID or name
     */
    public ConsoleNotification(String id, String message, String recipientInfo) {
        super(id, message, recipientInfo);
    }

    @Override
    public String getChannelName() {
        return "Consola";
    }

    @Override
    protected boolean send() {
        try {
            // Simulate sending notification to console
            System.out.println();
            System.out.println("=".repeat(80));
            System.out.println("NOTIFICACIÓN DEL SISTEMA");
            System.out.println("-".repeat(80));
            System.out.println("Para: " + recipientInfo);
            System.out.println("Fecha: " + formatDateTime(createdAt));
            System.out.println();
            System.out.println(message);
            System.out.println("=".repeat(80));
            System.out.println();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
