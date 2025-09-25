package biblioteca.data.dummy;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Member;
import biblioteca.domain.entities.MemberType;

/**
 * Datos dummy para socios de la biblioteca
 * Proporciona una lista de socios de prueba para inicialización del sistema
 */
public class MemberDummyData {

    /**
     * Genera una lista de socios de prueba con diferentes tipos y estados
     * 
     * @return Lista de socios dummy
     */
    public static List<Member> getMembers() {
        List<Member> members = new ArrayList<>();

        // Socios estándar
        members.add(new Member("1001", "Juan Pérez", "juan.perez@email.com", "555-0101", MemberType.STANDARD));
        members.add(new Member("1002", "María García", "maria.garcia@email.com", "555-0102", MemberType.STANDARD));
        members.add(new Member("1003", "Carlos López", "carlos.lopez@email.com", "555-0103", MemberType.STANDARD));
        members.add(new Member("1004", "Pedro Sánchez", "pedro.sanchez@email.com", "555-0104", MemberType.STANDARD));
        members.add(new Member("1005", "Laura Jiménez", "laura.jimenez@email.com", "555-0105", MemberType.STANDARD));

        // Estudiantes
        members.add(new Member("2001", "Ana Martínez", "ana.martinez@estudiante.edu", "555-0201", MemberType.STUDENT));
        members.add(new Member("2002", "Diego Rodríguez", "diego.rodriguez@estudiante.edu", "555-0202",
                MemberType.STUDENT));
        members.add(new Member("2003", "Lucía Fernández", "lucia.fernandez@estudiante.edu", "555-0203",
                MemberType.STUDENT));
        members.add(
                new Member("2004", "Miguel Torres", "miguel.torres@estudiante.edu", "555-0204", MemberType.STUDENT));

        // Jubilados
        members.add(new Member("3001", "Roberto Silva", "roberto.silva@email.com", "555-0301", MemberType.RETIRED));
        members.add(new Member("3002", "Elena Morales", "elena.morales@email.com", "555-0302", MemberType.RETIRED));
        members.add(new Member("3003", "José Herrera", "jose.herrera@email.com", "555-0303", MemberType.RETIRED));

        return members;
    }

    /**
     * Obtiene un socio específico por ID para testing
     * 
     * @param memberId ID del socio a buscar
     * @return Socio encontrado o null si no existe
     */
    public static Member getMemberById(String memberId) {
        return getMembers().stream()
                .filter(member -> member.getId().equals(memberId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene socios por tipo para testing
     * 
     * @param memberType Tipo de socio a filtrar
     * @return Lista de socios del tipo especificado
     */
    public static List<Member> getMembersByType(MemberType memberType) {
        return getMembers().stream()
                .filter(member -> member.getType() == memberType)
                .toList();
    }

    /**
     * Obtiene el siguiente ID disponible para nuevos socios
     * 
     * @return Próximo ID de socio disponible
     */
    public static String getNextAvailableId() {
        List<Member> members = getMembers();
        int maxId = members.stream()
                .mapToInt(member -> Integer.parseInt(member.getId()))
                .max()
                .orElse(1000);
        return String.valueOf(maxId + 1);
    }
}