package ch.rmbi.melspass.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Group.class,Pass.class}, version = 4, exportSchema = false)
abstract public class LocalRoomDatabase extends androidx.room.RoomDatabase {

    abstract PassDao passDao();
    abstract GroupDao groupDao();
    abstract GroupWithPassDao groupWithPassDao();


    private static volatile LocalRoomDatabase instance ;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LocalRoomDatabase getInstance(final Context context){
        if (instance == null) {
            synchronized (LocalRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            LocalRoomDatabase.class, "group_database")
                            .addCallback(sRoomDatabaseCallback)
                            .addMigrations(MIGRATION_1_2)
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

    private static androidx.room.RoomDatabase.Callback sRoomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                //GroupDao dao = instance.groupDao();


//                instance.groupDao().deleteAll();
/*
                long key;

                Group grp = new Group("GRP1","C'est le groupe 1");
                grp.setId(instance.groupDao().insert(grp));
                Pass pass = new Pass(grp.getId(),"Bank","rdemont","xxx","url","description");
                instance.passDao().insert(pass);
                pass = new Pass(grp.getId(),"Bank1","rdemont1","xxx","url","description");
                instance.passDao().insert(pass);
                pass = new Pass(grp.getId(),"Bank2","rdemont2","xxx","url","description");
                instance.passDao().insert(pass);

                grp = new Group("GRP2","C'est le groupe 2");
                grp.setId(instance.groupDao().insert(grp));
                pass = new Pass(grp.getId(),"Post","2rdemont1","xxx","url","description");
                instance.passDao().insert(pass);
                pass = new Pass(grp.getId(),"Post2","2rdemont2","xxx","url","description");
                instance.passDao().insert(pass);

                grp = new Group("GRP3","C'est le groupe 3");
                grp.setId(instance.groupDao().insert(grp));
                pass = new Pass(grp.getId(),"Boulot","3rdemont1","xxx","url","description");
                instance.passDao().insert(pass);
                pass = new Pass(grp.getId(),"Boulot","3rdemont2","xxx","url","description");
                instance.passDao().insert(pass);

 */
            });
        }
    };

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Pass_tbl ADD COLUMN name TEXT");
        }
    };


    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Group_tbl ADD COLUMN imageIndex INTEGER NOT NULL DEFAULT -1");
        }
    };


    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Group_tbl ADD COLUMN orderNumber INTEGER NOT NULL DEFAULT -1");
            database.execSQL("ALTER TABLE Pass_tbl ADD COLUMN orderNumber INTEGER NOT NULL DEFAULT -1");
        }
    };



}

