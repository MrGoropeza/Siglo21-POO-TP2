package biblioteca.application.socios.consultar;

/**
 * Request object for querying member information
 */
public class QueryMemberRequest {
    private final String id;

    public QueryMemberRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}