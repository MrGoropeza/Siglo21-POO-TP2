package biblioteca.data.dummy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Copy;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.LoanState;

/**
 * Datos dummy para préstamos de la biblioteca
 * Proporciona una lista de préstamos de prueba para inicialización del sistema
 */
public class LoanDummyData {

    /**
     * Genera una lista de préstamos de prueba con diferentes estados y fechas
     * Requiere que los miembros y ejemplares ya estén cargados
     * 
     * @param members Lista de miembros disponibles
     * @param copies  Lista de ejemplares disponibles
     * @return Lista de préstamos dummy
     */
    public static List<Loan> getLoans(List<Member> members, List<Copy> copies) {
        List<Loan> loans = new ArrayList<>();

        if (members.isEmpty() || copies.isEmpty()) {
            return loans; // No se pueden crear préstamos sin miembros o ejemplares
        }

        // Buscar miembros específicos por ID para los préstamos
        Member juanPerez = findMemberById(members, "1001");
        Member mariaGarcia = findMemberById(members, "1002");
        Member carlosLopez = findMemberById(members, "1003");
        Member anaMartinez = findMemberById(members, "2001");
        Member robertoSilva = findMemberById(members, "3001");

        // Buscar ejemplares específicos
        Copy copy1 = findCopyByCode(copies, "COPY-001-1");
        Copy copy2 = findCopyByCode(copies, "COPY-002-1");
        Copy copy3 = findCopyByCode(copies, "COPY-003-1");
        Copy copy4 = findCopyByCode(copies, "COPY-004-1");
        Copy copy5 = findCopyByCode(copies, "COPY-005-1");

        // Préstamo activo 1 - Juan Pérez
        if (juanPerez != null && copy1 != null) {
            Loan loan1 = new Loan(
                    "LOAN0001",
                    juanPerez,
                    copy1,
                    LocalDate.now().minusDays(5),
                    LocalDate.now().plusDays(9), // Vence en 9 días
                    LoanState.ACTIVE);
            loans.add(loan1);
            copy1.markAsLoaned(); // Marcar el ejemplar como prestado
        }

        // Préstamo activo 2 - Ana Martínez (estudiante)
        if (anaMartinez != null && copy2 != null) {
            Loan loan2 = new Loan(
                    "LOAN0002",
                    anaMartinez,
                    copy2,
                    LocalDate.now().minusDays(3),
                    LocalDate.now().plusDays(16), // Estudiante: 14 + 5 días extra
                    LoanState.ACTIVE);
            loans.add(loan2);
            copy2.markAsLoaned(); // Marcar el ejemplar como prestado
        }

        // Préstamo vencido - María García
        if (mariaGarcia != null && copy3 != null) {
            Loan loan3 = new Loan(
                    "LOAN0003",
                    mariaGarcia,
                    copy3,
                    LocalDate.now().minusDays(20),
                    LocalDate.now().minusDays(6), // Vencido hace 6 días
                    LoanState.OVERDUE);
            loans.add(loan3);
            copy3.markAsLoaned(); // Marcar el ejemplar como prestado
        }

        // Préstamo devuelto - Roberto Silva (jubilado)
        if (robertoSilva != null && copy4 != null) {
            Loan loan4 = new Loan(
                    "LOAN0004",
                    robertoSilva,
                    copy4,
                    LocalDate.now().minusDays(25),
                    LocalDate.now().minusDays(8),
                    LoanState.RETURNED);
            // Marcar como devuelto
            loan4.setReturnDate(LocalDate.now().minusDays(1));
            loans.add(loan4);
            // El ejemplar vuelve a estar disponible
            copy4.markAsAvailable();
        }

        // Préstamo activo 3 - Carlos López
        if (carlosLopez != null && copy5 != null) {
            Loan loan5 = new Loan(
                    "LOAN0005",
                    carlosLopez,
                    copy5,
                    LocalDate.now().minusDays(2),
                    LocalDate.now().plusDays(5), // 7 días estándar
                    LoanState.ACTIVE);
            loans.add(loan5);
            copy5.markAsLoaned(); // Marcar el ejemplar como prestado
        }

        return loans;
    }

    /**
     * Busca un miembro por su ID
     */
    private static Member findMemberById(List<Member> members, String id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un ejemplar por su código
     */
    private static Copy findCopyByCode(List<Copy> copies, String code) {
        return copies.stream()
                .filter(copy -> copy.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}