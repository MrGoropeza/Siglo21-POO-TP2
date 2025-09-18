package biblioteca.data.dummy;

import java.util.List;

import biblioteca.domain.entities.Publisher;

/**
 * Dummy data provider for publishers
 */
public class PublisherDummyData {

    /**
     * Returns a list of dummy publishers in Spanish
     */
    public static List<Publisher> getPublishers() {
        return List.of(
                new Publisher(1, "Penguin Random House"),
                new Publisher(2, "Planeta"),
                new Publisher(3, "Santillana"),
                new Publisher(4, "Alfaguara"),
                new Publisher(5, "Anagrama"),
                new Publisher(6, "Tusquets"),
                new Publisher(7, "Seix Barral"),
                new Publisher(8, "Destino"),
                new Publisher(9, "Crítica"),
                new Publisher(10, "Taurus"),
                new Publisher(11, "Cátedra"),
                new Publisher(12, "Alianza Editorial"),
                new Publisher(13, "Espasa"),
                new Publisher(14, "Sudamericana"),
                new Publisher(15, "Emecé"),
                new Publisher(16, "Fondo de Cultura Económica"),
                new Publisher(17, "Siglo XXI"),
                new Publisher(18, "Paidós"),
                new Publisher(19, "Ariel"),
                new Publisher(20, "Gedisa"));
    }
}