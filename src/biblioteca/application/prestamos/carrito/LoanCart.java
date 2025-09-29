package biblioteca.application.prestamos.carrito;

import java.util.ArrayList;
import java.util.List;

import biblioteca.domain.entities.Copy;

/**
 * Carrito de préstamos que permite agregar múltiples ejemplares
 * antes de confirmar un préstamo conjunto.
 */
public class LoanCart {
    private String memberId;
    private List<Copy> items;
    private boolean confirmed;

    public LoanCart(String memberId) {
        this.memberId = memberId;
        this.items = new ArrayList<>();
        this.confirmed = false;
    }

    /**
     * Agrega un ejemplar al carrito si no está ya presente.
     */
    public boolean addItem(Copy copy) {
        if (!items.contains(copy)) {
            items.add(copy);
            return true;
        }
        return false; // Ya existe en el carrito
    }

    /**
     * Remueve un ejemplar del carrito.
     */
    public boolean removeItem(Copy copy) {
        return items.remove(copy);
    }

    /**
     * Remueve un ejemplar por su código.
     */
    public boolean removeItemByCode(String copyCode) {
        return items.removeIf(copy -> copy.getCode().equals(copyCode));
    }

    /**
     * Verifica si el carrito está vacío.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Obtiene la cantidad de items en el carrito.
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Limpia todos los items del carrito.
     */
    public void clear() {
        items.clear();
        confirmed = false;
    }

    /**
     * Marca el carrito como confirmado.
     */
    public void confirm() {
        this.confirmed = true;
    }

    /**
     * Verifica si un ejemplar específico está en el carrito.
     */
    public boolean containsCopy(Copy copy) {
        return items.contains(copy);
    }

    /**
     * Verifica si un ejemplar por código está en el carrito.
     */
    public boolean containsCopyByCode(String copyCode) {
        return items.stream()
                .anyMatch(copy -> copy.getCode().equals(copyCode));
    }

    // Getters
    public String getMemberId() {
        return memberId;
    }

    public List<Copy> getItems() {
        return new ArrayList<>(items); // Defensive copy
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Carrito de Préstamos:\n");
        sb.append("Socio: ").append(memberId).append("\n");
        sb.append("Items: ").append(items.size()).append("\n");

        for (int i = 0; i < items.size(); i++) {
            Copy copy = items.get(i);
            sb.append(String.format("%d. %s - %s\n",
                    i + 1,
                    copy.getCode(),
                    copy.getBook().getTitle()));
        }

        return sb.toString();
    }
}