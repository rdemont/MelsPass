package ch.rmbi.melspass.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PassWithGroup {


    @Embedded
    public Pass pass;

    @Relation(parentColumn = "group_id" ,entityColumn = "id") public Group group ;

}
