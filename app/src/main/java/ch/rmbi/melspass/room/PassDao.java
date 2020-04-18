package ch.rmbi.melspass.room;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM pass_tbl WHERE (username LIKE :search) OR (name LIKE :search) OR (description LIKE :search)")
    LiveData<List<Pass>> getSearchPass(String search);

    @Query("SELECT MAX(orderNumber) from pass_tbl")
    int getPassMaxOrder();

    @Query("DELETE FROM pass_tbl")
    void deleteAll();
}
