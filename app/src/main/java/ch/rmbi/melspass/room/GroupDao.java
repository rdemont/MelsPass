package ch.rmbi.melspass.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * from Group_tbl ORDER BY name ASC")
    List<Group> getGroups();

    @Query("SELECT MAX(orderNumber) from Group_tbl")
    int getGroupMaxOrder();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Group group);

    @Update
    void update(Group... group);

    @Delete
    void delete(Group... group);


    @Query("DELETE FROM Group_tbl")
    void deleteAll();

    @Query("SELECT * FROM Group_tbl WHERE id=:id")
    Group Open(int id);

    @Query("SELECT * FROM pass_tbl WHERE group_id=:groupId")
    List<Pass> findPassForGroup(final long groupId);

}
