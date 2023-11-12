import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* Needed code to enter the database */
@Entity
public class ChatMessage {

    @ColumnInfo(name="message")
    private String message;
    @ColumnInfo(name="timeSent")
    private String timeSent;
    @ColumnInfo(name="sendOrReceive")
    private int sendOrReceive;

    /*This will create the primary key*/
    @PrimaryKey(autoGenerate = true)
    public long id;
}
