package biblioteca.domain.entities;

import java.time.LocalDate;

import biblioteca.domain.enums.ReservationState;

public class Reservation {
    private String id;
    private String memberId;
    private String bookId;
    private LocalDate reservationDate;
    private ReservationState state;

    public Reservation(String id, String memberId, String bookId, LocalDate reservationDate, ReservationState state) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }
}
