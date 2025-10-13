package biblioteca.data.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import biblioteca.domain.entities.Copy;
import biblioteca.domain.entities.Loan;
import biblioteca.domain.entities.Member;
import biblioteca.domain.enums.LoanState;

/**
 * Repositorio para la gestión de préstamos en el sistema de biblioteca.
 * Proporciona operaciones CRUD y búsquedas especializadas para préstamos.
 */
public class LoanRepository {
    private List<Loan> loans;
    private int nextId;

    public LoanRepository() {
        this.loans = new ArrayList<>();
        this.nextId = 1;
        // Los datos dummy se cargarán desde DependencyContainer
    }

    /**
     * Carga datos dummy desde el archivo dedicado
     */
    public void loadDummyData(List<Loan> dummyLoans) {
        this.loans.clear();
        this.loans.addAll(dummyLoans);

        // Actualizar el contador de IDs para nuevos préstamos
        if (!loans.isEmpty()) {
            // Encontrar el ID más alto y configurar nextId
            int maxId = loans.stream()
                    .mapToInt(loan -> {
                        String id = loan.getId();
                        try {
                            return Integer.parseInt(id.replace("LOAN", ""));
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .max()
                    .orElse(0);
            nextId = maxId + 1;
        }
    }

    /**
     * Encuentra todos los préstamos en el sistema.
     */
    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }

    /**
     * Encuentra un préstamo por su ID.
     */
    public Loan findById(String id) {
        return loans.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Encuentra todos los préstamos de un socio específico.
     */
    public List<Loan> findByMember(Member member) {
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(member.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra todos los préstamos de un socio específico por ID (método de
     * compatibilidad).
     */
    public List<Loan> findByMemberId(String memberId) {
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos por estado específico.
     */
    public List<Loan> findByState(LoanState state) {
        return loans.stream()
                .filter(loan -> loan.getState() == state)
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos activos de un socio específico.
     */
    public List<Loan> findActiveLoansByMember(Member member) {
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(member.getId()))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos activos de un socio específico por ID (método de
     * compatibilidad).
     */
    public List<Loan> findActiveLoansByMemberId(String memberId) {
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos vencidos (fecha de vencimiento pasada y aún activos).
     */
    public List<Loan> findOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .filter(loan -> loan.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos por código de ejemplar.
     */
    public List<Loan> findByCopyCode(String copyCode) {
        return loans.stream()
                .filter(loan -> loan.getCopy().getCode().equals(copyCode))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos por ejemplar específico.
     */
    public List<Loan> findByCopy(Copy copy) {
        return loans.stream()
                .filter(loan -> loan.getCopy().getCode().equals(copy.getCode()))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra un préstamo activo para un ejemplar específico.
     */
    public Loan findActiveLoanByCopy(Copy copy) {
        return loans.stream()
                .filter(loan -> loan.getCopy().getCode().equals(copy.getCode()))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    /**
     * Encuentra préstamos vencidos de un socio específico.
     */
    public List<Loan> findOverdueLoansByMemberId(String memberId) {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .filter(loan -> loan.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra préstamos vencidos de un socio específico.
     */
    public List<Loan> findOverdueLoansByMember(Member member) {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(member.getId()))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .filter(loan -> loan.getDueDate().isBefore(today))
                .collect(Collectors.toList());
    }

    /**
     * Cuenta los préstamos activos de un socio.
     */
    public long countActiveLoansByMemberId(String memberId) {
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .count();
    }

    /**
     * Cuenta los préstamos activos de un socio.
     */
    public long countActiveLoansByMember(Member member) {
        return loans.stream()
                .filter(loan -> loan.getMember().getId().equals(member.getId()))
                .filter(loan -> loan.getState() == LoanState.ACTIVE)
                .count();
    }

    /**
     * Guarda un préstamo (nuevo o actualización).
     */
    public Loan save(Loan loan) {
        if (loan.getId() == null || loan.getId().isEmpty()) {
            // Nuevo préstamo
            loan = new Loan(
                    generateId(),
                    loan.getMember(),
                    loan.getCopy(),
                    loan.getLoanDate(),
                    loan.getDueDate(),
                    loan.getState());
            loans.add(loan);
        } else {
            // Actualización
            for (int i = 0; i < loans.size(); i++) {
                if (loans.get(i).getId().equals(loan.getId())) {
                    loans.set(i, loan);
                    break;
                }
            }
        }
        return loan;
    }

    /**
     * Elimina un préstamo por ID.
     */
    public void deleteById(String id) {
        loans.removeIf(loan -> loan.getId().equals(id));
    }

    /**
     * Verifica si existe un préstamo con el ID especificado.
     */
    public boolean existsById(String id) {
        return loans.stream()
                .anyMatch(loan -> loan.getId().equals(id));
    }

    /**
     * Genera un ID único para un nuevo préstamo.
     */
    public String generateId() {
        return "LOAN" + String.format("%04d", nextId++);
    }

}