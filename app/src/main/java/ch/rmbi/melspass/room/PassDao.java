package ch.rmbi.melspass.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PassDao {

    @Insert
    void insert(Pass pass);

    @Update
    void update(Pass... pass);

    @Delete
    void delete(Pass... pass);


    @Query("SELECT * FROM pass_tbl")
    List<Pass> getAllPass();

    @Query("DELETE FROM pass_tbl")
    void deleteAll();
}
