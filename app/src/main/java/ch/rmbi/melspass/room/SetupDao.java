package ch.rmbi.melspass.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SetupDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Setup setup);

    @Update
    void update(Setup... setup);

    @Delete
    void delete(Setup... setup);

    @Query("SELECT  * FROM Setup_tbl ORDER BY lastChange DESC LIMIT 1")
    Setup getSetup();

}
