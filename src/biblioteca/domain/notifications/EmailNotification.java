package biblioteca.domain.notifications;

/**
 * Email notification implementation.
 * Simulates sending notifications via email.
 * 
 * Demonstrates inheritance and polymorphism.
 */
public class EmailNotification extends Notification {

    private String subject;

    /**
     * Constructor
     * 
     * @param id            Notification ID
     * @param message       Email body message
     * @param recipientInfo Email address
     */
    public EmailNotification(String id, String message, String recipientInfo) {
        super(id, message, recipientInfo);
        this.subject = "Notificación del Sistema de Biblioteca";
    }

    /**
     * Constructor with custom subject
     * 
     * @param id            Notification ID
     * @param message       Email body message
     * @param recipientInfo Email address
     * @param subject       Email subject
     */
    public EmailNotification(String id, String message, String recipientInfo, String subject) {
        super(id, message, recipientInfo);
        this.subject = subject;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }

    /**
     * Get email subject
     * 
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set email subject
     * 
     * @param subject new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    protected boolean send() {
        try {
            // Simulate sending email (in real app, would use JavaMail API)
            System.out.println();
            System.out.println("[SIMULACIÓN] Enviando email...");
            System.out.println("Para: " + recipientInfo);
            System.out.println("Asunto: " + subject);
            System.out.println("Mensaje: " + message.substring(0, Math.min(50, message.length())) + "...");
            System.out.println("Email enviado exitosamente");
            System.out.println();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
