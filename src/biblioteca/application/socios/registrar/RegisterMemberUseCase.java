package biblioteca.application.socios.registrar;

import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Use case for registering a new library member
 */
public class RegisterMemberUseCase {
    private final MemberRepository memberRepository;

    public RegisterMemberUseCase(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public RegisterMemberResult execute(RegisterMemberRequest request) {
        try {
            // Validate input data
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                return RegisterMemberResult.failure("El nombre es obligatorio");
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return RegisterMemberResult.failure("El email es obligatorio");
            }

            if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
                return RegisterMemberResult.failure("El teléfono es obligatorio");
            }

            if (request.getType() == null) {
                return RegisterMemberResult.failure("La categoría es obligatoria");
            }

            // Create member instance for validation
            Member member = new Member(
                    request.getId(),
                    request.getName().trim(),
                    request.getEmail().trim(),
                    request.getPhone().trim(),
                    request.getType());

            // Validate email format
            if (!member.isValidEmail()) {
                return RegisterMemberResult.failure("El formato del email no es válido");
            }

            // Check if ID already exists (if provided)
            if (request.getId() != null && !request.getId().trim().isEmpty()) {
                if (memberRepository.existsById(request.getId().trim())) {
                    return RegisterMemberResult.failure("Ya existe un socio con el ID: " + request.getId());
                }
            }

            // Check if email already exists
            if (memberRepository.existsByEmail(request.getEmail().trim())) {
                return RegisterMemberResult.failure("Ya existe un socio con el email: " + request.getEmail());
            }

            // Save the member
            Member savedMember = memberRepository.save(member);

            return RegisterMemberResult.success(savedMember);

        } catch (Exception e) {
            return RegisterMemberResult.failure("Error interno al registrar el socio: " + e.getMessage());
        }
    }
}