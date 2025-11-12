package biblioteca.data.dummy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Member;
import biblioteca.domain.notifications.ConsoleNotification;
import biblioteca.domain.notifications.EmailNotification;
import biblioteca.domain.notifications.Notification;
import biblioteca.domain.notifications.SMSNotification;

/**
 * Datos dummy para notificaciones de la biblioteca
 * Proporciona una lista de notificaciones de prueba para inicialización del
 * sistema
 */
public class NotificationDummyData {

    /**
     * Genera una lista de notificaciones de prueba con diferentes canales y estados
     * Simula notificaciones automáticas del sistema (recordatorios de vencimiento,
     * avisos de multas y confirmaciones)
     * 
     * @param members Lista de miembros disponibles
     * @return Lista de notificaciones dummy
     */
    public static List<Notification> getNotifications(List<Member> members) {
        List<Notification> notifications = new ArrayList<>();

        if (members.isEmpty()) {
            return notifications; // No se pueden crear notificaciones sin miembros
        }

        // Obtener algunos miembros para las notificaciones
        Member member1 = members.size() > 0 ? members.get(0) : null;
        Member member2 = members.size() > 1 ? members.get(1) : null;
        Member member3 = members.size() > 2 ? members.get(2) : null;

        if (member1 == null) {
            return notifications;
        }

        // Notificaciones de recordatorio de vencimiento (por consola)
        // LOAN0001 - Juan Pérez tiene "Cien años de soledad" (COPY-001-1) que vence en
        // 9 días
        ConsoleNotification notif1 = new ConsoleNotification(
                "NOTIF-00001",
                "Recordatorio: Su préstamo del libro 'Cien años de soledad' vence en 9 días. Por favor, devuélvalo a tiempo para evitar multas.",
                member1.getName());
        notif1.deliver(); // Marcar como enviada
        notifications.add(notif1);

        if (member2 != null) {
            // Ana Martínez tiene "La casa de los espíritus" (COPY-002-1) VENCIDO hace 6
            // días
            ConsoleNotification notif2 = new ConsoleNotification(
                    "NOTIF-00002",
                    "URGENTE: Su préstamo del libro 'La casa de los espíritus' está vencido hace 6 días. Debe devolverlo inmediatamente para evitar más multas.",
                    member2.getName());
            notif2.deliver(); // Marcar como enviada
            notifications.add(notif2);
        }

        // Notificaciones de multas pendientes (por email)
        if (member1 != null) {
            // Juan Pérez tiene multas acumuladas
            EmailNotification notif3 = new EmailNotification(
                    "NOTIF-00003",
                    "Tiene multas pendientes por un total de $500.00. Por favor, acérquese a la biblioteca para regularizar su situación y poder seguir realizando préstamos.",
                    member1.getEmail(),
                    "Multas Pendientes");
            notif3.deliver(); // Marcar como enviada
            notifications.add(notif3);
        }

        if (member3 != null) {
            // Carlos López devolvió "Veinte poemas de amor..." (COPY-005-1)
            EmailNotification notif4 = new EmailNotification(
                    "NOTIF-00004",
                    "Su devolución del libro 'Veinte poemas de amor y una canción desesperada' fue registrada exitosamente. Gracias por utilizar nuestra biblioteca.",
                    member3.getEmail(),
                    "Devolución Confirmada");
            notif4.deliver(); // Marcar como enviada
            notifications.add(notif4);
        }

        // Notificaciones por SMS (simuladas)
        if (member2 != null) {
            // Diego Rodríguez tiene "Rayuela" (COPY-003-1) VENCIDO hace 3 días
            SMSNotification notif5 = new SMSNotification(
                    "NOTIF-00005",
                    "Biblioteca: Su libro 'Rayuela' está vencido hace 3 días. Debe devolverlo urgentemente para evitar más multas.",
                    "+54 9 11 5555-0102"); // Formato de teléfono válido con código de área
            notif5.deliver(); // Marcar como enviada
            notifications.add(notif5);
        }

        // Notificación pendiente (no enviada aún)
        if (member1 != null) {
            // Juan Pérez - recordatorio pendiente sobre su préstamo activo
            ConsoleNotification notif6 = new ConsoleNotification(
                    "NOTIF-00006",
                    "Recordatorio: Su préstamo del libro 'Cien años de soledad' vence en 9 días. Planifique su devolución.",
                    member1.getName());
            // NO llamar a deliver() - queda como pendiente
            notifications.add(notif6);
        }

        // Otra notificación pendiente por email
        if (member3 != null) {
            // Carlos López - recordatorio sobre préstamo activo
            EmailNotification notif7 = new EmailNotification(
                    "NOTIF-00007",
                    "Su préstamo del libro 'Veinte poemas de amor y una canción desesperada' vence en 5 días. Recuerde realizar la devolución a tiempo.",
                    member3.getEmail(),
                    "Recordatorio de Vencimiento");
            // NO llamar a deliver() - queda como pendiente
            notifications.add(notif7);
        }

        // Ajustar fechas de creación para simular antigüedad (opcional)
        if (notifications.size() > 0) {
            // Las primeras notificaciones son más antiguas
            adjustCreationDate(notifications.get(0), 5); // Hace 5 días
        }
        if (notifications.size() > 1) {
            adjustCreationDate(notifications.get(1), 3); // Hace 3 días
        }
        if (notifications.size() > 2) {
            adjustCreationDate(notifications.get(2), 2); // Hace 2 días
        }
        if (notifications.size() > 3) {
            adjustCreationDate(notifications.get(3), 1); // Hace 1 día
        }

        return notifications;
    }

    /**
     * Método helper para ajustar la fecha de creación de una notificación (simular
     * antigüedad)
     * Usa reflexión para modificar el campo privado createdAt
     * 
     * @param notification Notificación a modificar
     * @param daysAgo      Cuántos días atrás se creó
     */
    private static void adjustCreationDate(Notification notification, int daysAgo) {
        try {
            var field = Notification.class.getDeclaredField("createdAt");
            field.setAccessible(true);
            field.set(notification, LocalDateTime.now().minusDays(daysAgo));
            field.setAccessible(false);
        } catch (Exception e) {
            // Si falla, mantener la fecha actual
        }
    }
}
