package biblioteca.application.socios.modificar;

import biblioteca.data.database.MemberRepository;
import biblioteca.domain.entities.Member;

/**
 * Use case for modifying an existing library member
 */
public class ModifyMemberUseCase {
    private final MemberRepository memberRepository;

    public ModifyMemberUseCase(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ModifyMemberResult execute(ModifyMemberRequest request) {
        try {
            // Validate input data
            if (request.getId() == null || request.getId().trim().isEmpty()) {
                return ModifyMemberResult.failure("El ID del socio es obligatorio");
            }

            // Find existing member
            Member existingMember = memberRepository.findById(request.getId().trim());
            if (existingMember == null) {
                return ModifyMemberResult.failure("No se encontró un socio con ID: " + request.getId());
            }

            // Validate new data
            if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                // Create temporary member to validate email format
                Member tempMember = new Member();
                tempMember.setEmail(request.getEmail().trim());
                if (!tempMember.isValidEmail()) {
                    return ModifyMemberResult.failure("El formato del email no es válido");
                }

                // Check if email is already used by another member
                Member memberWithEmail = memberRepository.findByEmail(request.getEmail().trim());
                if (memberWithEmail != null && !memberWithEmail.getId().equals(existingMember.getId())) {
                    return ModifyMemberResult.failure("Ya existe otro socio con el email: " + request.getEmail());
                }
            }

            if (request.getPhone() != null && request.getPhone().trim().isEmpty()) {
                return ModifyMemberResult.failure("El teléfono no puede estar vacío");
            }

            // Create updated member
            Member updatedMember = new Member(
                    existingMember.getId(),
                    existingMember.getName(), // Name cannot be changed according to requirements
                    request.getEmail() != null ? request.getEmail().trim() : existingMember.getEmail(),
                    request.getPhone() != null ? request.getPhone().trim() : existingMember.getPhone(),
                    request.getType() != null ? request.getType() : existingMember.getType());

            // Check if there are any changes
            boolean hasChanges = !existingMember.getEmail().equals(updatedMember.getEmail()) ||
                    !existingMember.getPhone().equals(updatedMember.getPhone()) ||
                    !existingMember.getType().equals(updatedMember.getType());

            if (!hasChanges) {
                return ModifyMemberResult.failure("No se detectaron cambios en los datos del socio");
            }

            // Update member
            Member savedMember = memberRepository.update(updatedMember);
            if (savedMember == null) {
                return ModifyMemberResult.failure("Error al actualizar el socio");
            }

            return ModifyMemberResult.success(savedMember);

        } catch (Exception e) {
            return ModifyMemberResult.failure("Error interno al modificar el socio: " + e.getMessage());
        }
    }
}