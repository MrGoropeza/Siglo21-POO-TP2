package biblioteca.domain.notifications;

/**
 * SMS notification implementation.
 * Simulates sending notifications via SMS/text message.
 * 
 * Demonstrates inheritance and polymorphism.
 */
public class SMSNotification extends Notification {

    private static final int MAX_SMS_LENGTH = 160;

    /**
     * Get maximum SMS length
     * 
     * @return max length
     */
    public static int getMaxSmsLength() {
        return MAX_SMS_LENGTH;
    }

    /**
     * Truncate message to SMS maximum length
     * 
     * @param message Original message
     * @return Truncated message
     */
    private static String truncateMessage(String message) {
        if (message.length() <= MAX_SMS_LENGTH) {
            return message;
        }
        return message.substring(0, MAX_SMS_LENGTH - 3) + "...";
    }

    /**
     * Constructor
     * 
     * @param id            Notification ID
     * @param message       SMS message (will be truncated if > 160 chars)
     * @param recipientInfo Phone number
     */
    public SMSNotification(String id, String message, String recipientInfo) {
        super(id, truncateMessage(message), recipientInfo);
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }

    @Override
    protected boolean send() {
        try {
            // Validate phone number format (basic validation)
            if (!isValidPhoneNumber(recipientInfo)) {
                System.out.println("Número de teléfono inválido: " + recipientInfo);
                return false;
            }

            // Simulate sending SMS (in real app, would use Twilio or similar)
            System.out.println();
            System.out.println("[SIMULACIÓN] Enviando SMS...");
            System.out.println("Para: " + recipientInfo);
            System.out.println("Mensaje: " + message);
            System.out.println("Caracteres: " + message.length() + "/" + MAX_SMS_LENGTH);
            System.out.println("SMS enviado exitosamente");
            System.out.println();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Basic phone number validation
     * 
     * @param phone Phone number to validate
     * @return true if valid format
     */
    private boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Basic validation: contains only digits, spaces, +, -, (, )
        return phone.matches("[\\d\\s+\\-()]+") && phone.replaceAll("[^\\d]", "").length() >= 8;
    }
}
