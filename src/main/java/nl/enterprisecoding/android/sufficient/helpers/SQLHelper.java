package nl.enterprisecoding.android.sufficient.helpers;

/**
 * Utility class for SQLite statements.
 */
public class SQLHelper {
    public static final String CREATE = "CREATE TABLE ";
    public static final String DESTROY = "DROP TABLE IF EXISTS ";
    public static final Object P_ID_AI = " integer primary key autoincrement, ";
    public static final String INT_NOT_NULL = " integer not null, ";
    public static final String TEXT_NOT_NULL = " text not null, ";

    /**
     * Private constructor for utility class.
     */
    private SQLHelper() {
    }
}
