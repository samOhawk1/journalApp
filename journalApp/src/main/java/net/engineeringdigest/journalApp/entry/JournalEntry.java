package net.engineeringdigest.journalApp.entry;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journalEntries")
@Data   // now this data annotation will generate the getters and setters during runtime (it can also generate more boilerplate code)
@NoArgsConstructor // required using deserialization (meaning json to  pojo )
public class JournalEntry {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;


}