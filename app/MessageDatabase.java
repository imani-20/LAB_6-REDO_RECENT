import androidx.room.Database;
import androidx.room.RoomDatabase;

/* This Database class is meant for storing ChatMessage objects and
uses the ChatMessageDAO class for querying data.*/
@Database(entities = { ChatMessage.class}, version = 1)

public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();

}
