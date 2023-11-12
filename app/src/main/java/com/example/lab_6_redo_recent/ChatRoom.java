package com.example.lab_6_redo_recent;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.lab_6_redo_recent.databinding.ActivityChatRoomBinding;
import com.example.lab_6_redo_recent.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity implements ChatRoom1 {

    /* The DAO variable for Lab7 */
    private ChatMessageDAO mDAO;

    /* Declaring the ArrayList variable */
    ArrayList<ChatMessage> messages;

    /* Declaring myAdapter */
    private RecyclerView.Adapter<MyRowHolder> myAdapter;

    /* Declaring the variables */
    private ActivityChatRoomBinding binding;

    /* Declaring the chatRoom variable */
    ChatRoomViewModel chatModel;

    /* The inner class added */
    class MyRowHolder extends RecyclerView.ViewHolder {

        /* The variables for the sent_message xml */
        TextView messageText;
        /* The variables are of type textView */
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView); /* the superclass */

            /* My Alert Dialog onClickListener */
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

            /* The warning window */
            builder.setMessage("Do you want to delete the message: " + messageText.getText());

            /* The warning window title */
            builder.setTitle("Question:");

            /* The 2 options functions */
            builder.setNegativeButton("No", (dialog, cl) -> {});
            builder.setPositiveButton("Yes", (dialog, cl) -> {
               /* ChatMessage m = messages.get(position);
                mDAO.deleteMessage(m);
                messages.remove(position);
                myAdapter.notifyItemRemoved(position);*/
            });

            itemView.setOnClickListener(clk -> {
                /* The Alert dialog declaration */
                int position = getAbsoluteAdapterPosition();

                ChatMessage m = messages.get(position);
                mDAO.deleteMessage(m);
                messages.remove(position);
                myAdapter.notifyItemRemoved(position);
            });

            /* The window appearing */
            builder.create().show();

            messageText = itemView.findViewById(R.id.message); /* using the findView method to look for the TextView with the id messageText */
            timeText = itemView.findViewById(R.id.time); /* same logic as the previous line */
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        /*Lab 8 MOBILE CODE
        * 
        * DEFINING THE TOOLBAR ELEMENT
        */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(binding.myToolbar);

        /* Lab7 Database Code
         *
         * START
         *
         * This code will open Database using Room
         * */
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(),
                MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        /* The Executor thread that will run using the Runnable() method */
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> runOnUiThread(() -> {
        }));

        super.onCreate(savedInstanceState);

        /* Initializing the chatModel */
        /* Declaring the ViewModel variable */
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        /* Assigning the messages variable to get the Value */

        /* The if condition to verify the messages have never been set the 1st time you enter ChatRoom */
        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<>());
        }

        /* Instructing the binding and making the Act. variable to convert from XML to JAVA */
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());

        /* The send button */
        binding.sendButton.setOnClickListener(click -> {
            messages.add(new ChatMessage(binding.editTextTextPassword.getText().toString(), "", false));
            /* Notifying my adapter about the updates after declaring it */
            myAdapter.notifyItemInserted(messages.size() - 1);
            /* To clear the previous text */
            binding.editTextTextPassword.setText("");
        });

        /* The root is of the inflated layout */
        View view = binding.getRoot();
        setContentView(view);

        /* THE END OF TRANSFORMING THE CONTENT VIEW TO VIEW-BINDING
         *
         *
         * SETTING THE ADAPTER VARIABLE: THE ADAPTER BEING THE MIDDLE MAN BETWEEN THE VARIABLE AND THE DATA
         *
         * */

        /* Adding the Layout Manager code */
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* The line of code below will initialize the 'recyclerView.setAdapter(myAdapter)' class */
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            /* Imported methods */
            @NonNull
            @Override

            /* The onCreateViewHolder is meant to implement the layout */
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                /* The first line is assigning the binding variable with the task to convert the XML elements to Java objects
                 * The second line is to return the 1st XML layout: the root.
                 */
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
                /* The Adapter Functions
                 * These codes are responsible for creating the ViewHolder object, therefore it represents the list
                 * View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
                 * return new MyRowHolder(itemView);
                 */
            }

            @Override
            /* Set the data for the ViewHolder project */
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                holder.timeText.setText("");
                /* The object that will be in the row */
                ChatMessage chatMessages = messages.get(position);
                /* The messageText in the onBindViewHolder folder */
                holder.messageText.setText(chatMessages.getMessage());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            /* The declaration of the method getItemViewType
             * The getItemViewType method starts HERE
             */
            @Override
            public int getItemViewType(int position) {
                return position;
            }

            /* The end of imported methods */
        });

        /* The Binding method of the Receive button
         * This is declaring the variable
         */
        Button receiveButton = binding.receiveButton;
        Button sendButton = binding.sendButton;
        /* Adding a click listener that will respond to the user input...
         * The receiveButton being the UI element in the XML file, and the setOnClickListener is the method being used which needs to be defined
         */
        receiveButton.setOnClickListener(new View.OnClickListener() {

            /* This is where the Receive button will be defined and manipulated */
            @Override
            public void onClick(View v) {
                /* The onClick handler for the Receive button */
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateAndTime = sdf.format(new Date());

                // Creating a new ChatMessage for received message
                messages.add(new ChatMessage());
            }
        });

        /* The send button */
        binding.sendButton.setOnClickListener(click -> {
            /* The SimpleDateFormat will be able to get the current date and time */
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            /* The new ChatMessage object will add it to the messages ArrayList */
        });
    }

    /*This code is meant to load the layout and inflate */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_1:
                // Put your ChatMessage deletion code here. Show an alert dialog.
                break;
            case R.id.about_item:
                // Show a toast with version information.
                Toast.makeText(this, "Version 1.0, created by YourName", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}