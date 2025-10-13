package biblioteca.data.dummy;

import java.time.LocalDate;
import java.util.List;

import biblioteca.data.database.FineRepository;
import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Fine;
import biblioteca.domain.entities.Member;

/**
 * Dummy data generator for fines
 */
public class FineDummyData {

    public static void loadDummyFines(FineRepository fineRepository, MemberRepository memberRepository) {
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty()) {
            System.out.println("⚠️  No members found. Load member dummy data first.");
            return;
        }

        // Fine 1: Juan Pérez - Unpaid fine from old loan
        if (members.size() > 0) {
            Member juan = members.get(0); // Assuming Juan is first
            Fine fine1 = new Fine(
                    null, // ID will be generated
                    juan,
                    150.0, // $150 fine
                    LocalDate.now().minusDays(10) // Issued 10 days ago
            );
            fineRepository.save(fine1);
        }

        // Fine 2: María García - Paid fine
        if (members.size() > 1) {
            Member maria = members.get(1);
            Fine fine2 = new Fine(
                    null,
                    maria,
                    75.0, // $75 fine
                    LocalDate.now().minusDays(15));
            fine2.pay(LocalDate.now().minusDays(5)); // Paid 5 days ago
            fineRepository.save(fine2);
        }

        // Fine 3: Carlos López - Recent unpaid fine
        if (members.size() > 2) {
            Member carlos = members.get(2);
            Fine fine3 = new Fine(
                    null,
                    carlos,
                    100.0,
                    LocalDate.now().minusDays(2));
            fineRepository.save(fine3);
        }

        // Fine 4: Roberto Silva - Fine from returned loan (7 days late)
        // This fine corresponds to the returned loan in LoanDummyData
        Member robertoSilva = memberRepository.findById("3001");
        if (robertoSilva != null) {
            // The return was done 1 day ago (see LoanDummyData)
            // 7 days late * $5 per day = $35 fine with 50% retired discount = $17.50
            Fine fine4 = new Fine(
                    null,
                    robertoSilva,
                    17.50, // $5/day * 7 days * 50% retired discount
                    LocalDate.now().minusDays(1) // Same date as return date
            );
            fine4.pay(LocalDate.now().minusDays(1)); // Paid immediately on return
            fineRepository.save(fine4);
        }

        // ========================================
        // TESTING SCENARIOS: Unpaid fines for each member type
        // These allow testing fine payment with correct discount calculations
        // ========================================

        // Fine 5: ESTUDIANTE - Ana Martínez (2001) - Unpaid fine with 50% student
        // discount
        // Scenario: Book returned 10 days late
        // Base calculation: 10 days * $5/day = $50
        // With 50% student discount: $50 * 0.5 = $25
        Member anaEstudiante = memberRepository.findById("2001");
        if (anaEstudiante != null) {
            Fine fine5 = new Fine(
                    null,
                    anaEstudiante,
                    25.0, // $5/day * 10 days * 50% student discount
                    LocalDate.now().minusDays(3) // Fine issued 3 days ago
            );
            // NOT PAID - Allows testing PayFineForm
            fineRepository.save(fine5);
        }

        // Fine 6: JUBILADO - Elena Morales (3002) - Unpaid fine with 50% retired
        // discount
        // Scenario: Book returned 6 days late
        // Base calculation: 6 days * $5/day = $30
        // With 50% retired discount: $30 * 0.5 = $15
        Member elenaJubilada = memberRepository.findById("3002");
        if (elenaJubilada != null) {
            Fine fine6 = new Fine(
                    null,
                    elenaJubilada,
                    15.0, // $5/day * 6 days * 50% retired discount
                    LocalDate.now().minusDays(5) // Fine issued 5 days ago
            );
            // NOT PAID - Allows testing PayFineForm
            fineRepository.save(fine6);
        }

        // Fine 7: ESTÁNDAR - Pedro Sánchez (1004) - Unpaid fine without discount
        // Scenario: Book returned 8 days late
        // Base calculation: 8 days * $5/day = $40
        // No discount for standard members: $40 * 1.0 = $40
        Member pedroEstandar = memberRepository.findById("1004");
        if (pedroEstandar != null) {
            Fine fine7 = new Fine(
                    null,
                    pedroEstandar,
                    40.0, // $5/day * 8 days * no discount (100%)
                    LocalDate.now().minusDays(2) // Fine issued 2 days ago
            );
            // NOT PAID - Allows testing PayFineForm
            fineRepository.save(fine7);
        }
    }
}
