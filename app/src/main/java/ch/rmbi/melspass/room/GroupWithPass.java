package ch.rmbi.melspass.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class GroupWithPass implements Serializable {

    @Embedded public Group group;

    @Relation(parentColumn = "id" ,entityColumn = "group_id") public List<Pass> passList;

}
