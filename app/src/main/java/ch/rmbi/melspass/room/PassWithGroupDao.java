package ch.rmbi.melspass.room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PassWithGroupDao {

    @Query("SELECT * from pass_tbl")
    public List<PassWithGroup> getPassWithGroup();
}
