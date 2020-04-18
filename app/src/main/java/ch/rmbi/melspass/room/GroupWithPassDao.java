package ch.rmbi.melspass.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GroupWithPassDao {


    @Query("SELECT * from group_tbl")
    public List<GroupWithPass> getGroupsWithPass();


    @Query("SELECT * from group_tbl ORDER BY group_tbl.orderNumber ASC")
    public LiveData<List<GroupWithPass>> getLiveGroupsWithPass();
}
