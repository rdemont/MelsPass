package ch.rmbi.melspass.room;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "Pass_tbl",
        foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = "id",
        childColumns = "group_id",
        onDelete = ForeignKey.CASCADE))
public class Pass implements Serializable {


    public Pass(Long group_id,String name,String username, String userPass, String url, String description,int orderNumber) {
        this.group_id = group_id;
        this.username = username;
        this.userPass = userPass;
        this.url = url;
        this.description = description;
        this.name = name ;
        this.orderNumber = orderNumber;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;


    private long group_id ;
    private String username ;
    private String userPass ;
    private String url ;
    private String description ;
    private String name;



    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    private int orderNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getGroup_id() {
        return group_id;
    }





    public String getUsername() {
        return username;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }





}
