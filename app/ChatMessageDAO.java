import android.app.AlertDialog;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/*DAO FOR ROOM*/
@Dao
/*The Interface is important to manipulate the DataBase*/
public interface ChatMessageDAO {
    @Insert
    public long insertMessage(ChatMessage m);

    @Query("SELECT * FROM chatmessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage m);

}
