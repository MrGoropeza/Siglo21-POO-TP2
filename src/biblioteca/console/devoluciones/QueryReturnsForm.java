package biblioteca.console.devoluciones;

import java.time.LocalDate;
import java.util.List;

import biblioteca.application.devoluciones.consultar.QueryReturnsRequest;
import biblioteca.application.devoluciones.consultar.QueryReturnsResult;
import biblioteca.application.devoluciones.consultar.QueryReturnsUseCase;
import biblioteca.console.utils.DisplayHelper;
import biblioteca.console.utils.InputHelper;
import biblioteca.data.database.FineRepository;
import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Loan;

/**
 * Form for querying returns history with filters
 */
public class QueryReturnsForm {
    private final QueryReturnsUseCase queryReturnsUseCase;
    private final FineRepository fineRepository;

    public QueryReturnsForm(QueryReturnsUseCase queryReturnsUseCase, FineRepository fineRepository) {
        this.queryReturnsUseCase = queryReturnsUseCase;
        this.fineRepository = fineRepository;
    }

    /**
     * Display form and process query
     */
    public void display() {
        DisplayHelper.renderSubtitle("CONSULTAR HISTORIAL DE DEVOLUCIONES");

        try {
            // Show filter options
            System.out.println("\nOpciones de filtrado:");
            System.out.println("1. Ver todas las devoluciones");
            System.out.println("2. Filtrar por socio");
            System.out.println("3. Filtrar por libro");
            System.out.println("4. Filtrar por rango de fechas");
            System.out.println("5. Ver solo devoluciones con multa");
            System.out.println("6. Ver solo devoluciones sin multa");
            System.out.println("7. Volver");

            int option = InputHelper.leerEnteroEnRango("Seleccione una opción", 1, 7);

            QueryReturnsRequest request = null;

            switch (option) {
                case 1:
                    request = QueryReturnsRequest.all();
                    break;
                case 2:
                    String memberId = InputHelper.leerTextoObligatorio("Ingrese ID del socio");
                    request = QueryReturnsRequest.byMember(memberId);
                    break;
                case 3:
                    String bookId = InputHelper.leerTextoObligatorio("Ingrese ID del libro");
                    request = QueryReturnsRequest.byBook(bookId);
                    break;
                case 4:
                    request = createDateRangeRequest();
                    break;
                case 5:
                    request = QueryReturnsRequest.withFines();
                    break;
                case 6:
                    request = QueryReturnsRequest.withoutFines();
                    break;
                case 7:
                    return;
            }

            if (request != null) {
                executeQuery(request);
            }

        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Error al consultar devoluciones: " + e.getMessage());
        }
    }

    private QueryReturnsRequest createDateRangeRequest() {
        System.out.println("\n=== FILTRO POR RANGO DE FECHAS ===");
        System.out.println("Nota: Deje vacío para no aplicar límite en esa fecha");

        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            String startInput = InputHelper.leerTexto("Fecha de inicio (dd/MM/yyyy) - Dejar vacío para omitir");
            if (startInput != null && !startInput.trim().isEmpty()) {
                startDate = parseDate(startInput.trim());
            }

            String endInput = InputHelper.leerTexto("Fecha de fin (dd/MM/yyyy) - Dejar vacío para omitir");
            if (endInput != null && !endInput.trim().isEmpty()) {
                endDate = parseDate(endInput.trim());
            }
        } catch (Exception e) {
            DisplayHelper.printErrorMessage("Formato de fecha inválido. Use dd/MM/yyyy");
            return QueryReturnsRequest.all();
        }

        return QueryReturnsRequest.byDateRange(startDate, endDate);
    }

    private LocalDate parseDate(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido");
        }
    }

    private void executeQuery(QueryReturnsRequest request) {
        QueryReturnsResult result = queryReturnsUseCase.execute(request);

        System.out.println("\n" + DisplayHelper.SEPARATOR);
        System.out.println("📋 RESULTADOS DE LA BÚSQUEDA");
        System.out.println(DisplayHelper.SEPARATOR);

        if (result.isSuccess()) {
            DisplayHelper.printSuccess(result.getMessage());

            if (result.hasReturns()) {
                displayReturns(result.getReturns());
            }
        } else {
            DisplayHelper.printErrorMessage(result.getMessage());
        }
    }

    private void displayReturns(List<Loan> returns) {
        System.out.println("\n=== DEVOLUCIONES ENCONTRADAS ===\n");

        for (int i = 0; i < returns.size(); i++) {
            Loan loan = returns.get(i);
            System.out.println((i + 1) + ". " + formatReturn(loan));
            System.out.println("   " + "-".repeat(60));
        }

        System.out.println("\nTotal: " + returns.size() + " devolución(es)");
    }

    private String formatReturn(Loan loan) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Socio: %s (%s)%n",
                loan.getMember().getName(),
                loan.getMember().getId()));
        sb.append(String.format("   Libro: %s%n",
                loan.getCopy().getBook().getTitle()));
        sb.append(String.format("   Ejemplar: %s%n",
                loan.getCopy().getCode()));
        sb.append(String.format("   Fecha préstamo: %s%n",
                DisplayHelper.formatDate(loan.getLoanDate())));
        sb.append(String.format("   Fecha vencimiento: %s%n",
                DisplayHelper.formatDate(loan.getDueDate())));
        sb.append(String.format("   Fecha devolución: %s%n",
                DisplayHelper.formatDate(loan.getReturnDate())));

        // Calculate delay and check for fine
        if (loan.getReturnDate() != null && loan.getReturnDate().isAfter(loan.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                    loan.getDueDate(),
                    loan.getReturnDate());
            sb.append(String.format("   ⚠️  Devolución con %d día(s) de retraso%n", daysLate));

            // Look for fine generated on return date
            Fine fine = fineRepository.findByMember(loan.getMember()).stream()
                    .filter(f -> f.getIssueDate().equals(loan.getReturnDate()))
                    .findFirst()
                    .orElse(null);

            if (fine != null) {
                sb.append(String.format("   💰 Multa generada: $%.2f (%s)",
                        fine.getAmount(),
                        fine.isPaid() ? "Pagada" : "Pendiente"));
            }
        } else {
            sb.append("   ✓ Devolución a tiempo - Sin multa");
        }

        return sb.toString();
    }
}
