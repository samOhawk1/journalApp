package net.engineeringdigest.journalApp.entry;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
@Data   // now this data annotation will generate the getters and setters during runtime (it can also generate more boilerplate code)
public class User {


    @Id
    private ObjectId id;
    @Indexed (unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String sentimentAnalysis;
    private String email;
    @DBRef // list with objects referencing to journal entry db via id
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;


}