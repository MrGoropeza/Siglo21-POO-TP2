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
        Member pedroSanchez = findMemberById(members, "1004");
        Member lauraJimenez = findMemberById(members, "1005");
        Member anaMartinez = findMemberById(members, "2001");
        Member diegoRodriguez = findMemberById(members, "2002");
        Member robertoSilva = findMemberById(members, "3001");

        // Buscar ejemplares específicos
        Copy copy1_1 = findCopyByCode(copies, "COPY-001-1");
        Copy copy1_2 = findCopyByCode(copies, "COPY-001-2");
        Copy copy2_1 = findCopyByCode(copies, "COPY-002-1");
        Copy copy3_1 = findCopyByCode(copies, "COPY-003-1");
        Copy copy3_2 = findCopyByCode(copies, "COPY-003-2");
        Copy copy4_1 = findCopyByCode(copies, "COPY-004-1");
        Copy copy4_2 = findCopyByCode(copies, "COPY-004-2");
        Copy copy5_1 = findCopyByCode(copies, "COPY-005-1");
        Copy copy5_2 = findCopyByCode(copies, "COPY-005-2");

        // ════════════════════════════════════════════════════════════════
        // PRÉSTAMOS ACTIVOS (sin vencer) - Para probar devoluciones normales
        // ════════════════════════════════════════════════════════════════

        // LOAN0001 - Juan Pérez (STANDARD) - COPY-001-1 - Activo, vence en 9 días
        // Caso de prueba: Devolución sin retraso
        if (juanPerez != null && copy1_1 != null) {
            Loan loan1 = new Loan(
                    "LOAN0001",
                    juanPerez,
                    copy1_1,
                    LocalDate.now().minusDays(5),
                    LocalDate.now().plusDays(9), // Vence en 9 días
                    LoanState.ACTIVE);
            loans.add(loan1);
            copy1_1.markAsLoaned();
        }

        // LOAN0005 - Carlos López (STANDARD) - COPY-005-1 - Activo, vence en 5 días
        if (carlosLopez != null && copy5_1 != null) {
            Loan loan5 = new Loan(
                    "LOAN0005",
                    carlosLopez,
                    copy5_1,
                    LocalDate.now().minusDays(9),
                    LocalDate.now().plusDays(5),
                    LoanState.ACTIVE);
            loans.add(loan5);
            copy5_1.markAsLoaned();
        }

        // ════════════════════════════════════════════════════════════════
        // PRÉSTAMOS VENCIDOS - Para generar multas al devolver
        // ════════════════════════════════════════════════════════════════

        // LOAN0002 - Ana Martínez (STUDENT) - COPY-002-1 - VENCIDO hace 6 días
        // Caso de prueba: Devolución con multa STUDENT (50% descuento)
        // Multa esperada: 6 días × $5.00 = $30.00 → 50% desc = $15.00
        if (anaMartinez != null && copy2_1 != null) {
            Loan loan2 = new Loan(
                    "LOAN0002",
                    anaMartinez,
                    copy2_1,
                    LocalDate.now().minusDays(20), // Prestado hace 20 días
                    LocalDate.now().minusDays(6), // Vencido hace 6 días
                    LoanState.OVERDUE);
            loans.add(loan2);
            copy2_1.markAsLoaned();
        }

        // LOAN0003 - María García (STANDARD) - COPY-003-1 - VENCIDO hace 6 días
        // Préstamo vencido para demostrar estado OVERDUE
        if (mariaGarcia != null && copy3_1 != null) {
            Loan loan3 = new Loan(
                    "LOAN0003",
                    mariaGarcia,
                    copy3_1,
                    LocalDate.now().minusDays(20),
                    LocalDate.now().minusDays(6),
                    LoanState.OVERDUE);
            loans.add(loan3);
            copy3_1.markAsLoaned();
        }

        // LOAN0006 - Pedro Sánchez (STANDARD) - COPY-005-2 - VENCIDO hace 6 días
        // Caso de prueba: Devolución con multa STANDARD
        // Multa esperada: 6 días × $5.00 = $30.00 (sin descuento)
        if (pedroSanchez != null && copy5_2 != null) {
            Loan loan6 = new Loan(
                    "LOAN0006",
                    pedroSanchez,
                    copy5_2,
                    LocalDate.now().minusDays(20),
                    LocalDate.now().minusDays(6),
                    LoanState.OVERDUE);
            loans.add(loan6);
            copy5_2.markAsLoaned();
        }

        // LOAN0007 - Roberto Silva (RETIRED) - COPY-004-2 - VENCIDO hace 4 días
        // Caso de prueba: Devolución con multa RETIRED
        // Multa esperada: 4 días × $5.00 = $20.00 (sin descuento, pero tuvo +días)
        if (robertoSilva != null && copy4_2 != null) {
            Loan loan7 = new Loan(
                    "LOAN0007",
                    robertoSilva,
                    copy4_2,
                    LocalDate.now().minusDays(25), // Prestado hace 25 días (14+7=21 días)
                    LocalDate.now().minusDays(4), // Vencido hace 4 días
                    LoanState.OVERDUE);
            loans.add(loan7);
            copy4_2.markAsLoaned();
        }

        // ════════════════════════════════════════════════════════════════
        // PRÉSTAMOS DEVUELTOS CON MULTA - Para historial y consultas
        // ════════════════════════════════════════════════════════════════

        // LOAN0004 - Roberto Silva (RETIRED) - COPY-004-1 - DEVUELTO con retraso
        // Devuelto hace 1 día, con 8 días de retraso ($40 de multa)
        if (robertoSilva != null && copy4_1 != null) {
            Loan loan4 = new Loan(
                    "LOAN0004",
                    robertoSilva,
                    copy4_1,
                    LocalDate.now().minusDays(30), // Prestado hace 30 días
                    LocalDate.now().minusDays(9), // Vencía hace 9 días
                    LoanState.RETURNED);
            loan4.setReturnDate(LocalDate.now().minusDays(1)); // Devuelto hace 1 día (8 días tarde)
            loans.add(loan4);
            copy4_1.markAsAvailable();
        }

        // LOAN0008 - Laura Jiménez (STANDARD) - COPY-003-2 - DEVUELTO con retraso
        // Devuelto hace 2 días, con 5 días de retraso ($25 de multa)
        // Para historial "solo con multa"
        if (lauraJimenez != null && copy3_2 != null) {
            Loan loan8 = new Loan(
                    "LOAN0008",
                    lauraJimenez,
                    copy3_2,
                    LocalDate.now().minusDays(21),
                    LocalDate.now().minusDays(7),
                    LoanState.RETURNED);
            loan8.setReturnDate(LocalDate.now().minusDays(2)); // Devuelto con 5 días de retraso
            loans.add(loan8);
            copy3_2.markAsAvailable();
        }

        // LOAN0009 - Diego Rodríguez (STUDENT) - COPY-001-2 - DEVUELTO con retraso
        // Devuelto hace 3 días, con 4 días de retraso ($10 de multa con 50% desc)
        // Para historial "solo con multa" con descuento STUDENT
        if (diegoRodriguez != null && copy1_2 != null) {
            Loan loan9 = new Loan(
                    "LOAN0009",
                    diegoRodriguez,
                    copy1_2,
                    LocalDate.now().minusDays(25),
                    LocalDate.now().minusDays(7),
                    LoanState.RETURNED);
            loan9.setReturnDate(LocalDate.now().minusDays(3)); // Devuelto con 4 días de retraso
            loans.add(loan9);
            copy1_2.markAsAvailable();
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