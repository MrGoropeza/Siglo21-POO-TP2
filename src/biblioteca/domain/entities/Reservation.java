package biblioteca.domain.entities;

import java.time.LocalDate;

import biblioteca.domain.enums.ReservationState;

public class Reservation {
    private String id;
    private Member member;
    private Book book;
    private LocalDate reservationDate;
    private ReservationState state;

    public Reservation(String id, Member member, Book book, LocalDate reservationDate, ReservationState state) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.reservationDate = reservationDate;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
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
