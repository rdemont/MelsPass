package ch.rmbi.melspass.room;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "Group_tbl")
public class Group {

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String description ;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}