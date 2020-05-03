package ch.rmbi.melspass.room;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Group.class,Pass.class,Setup.class}, version = 1, exportSchema = false)
abstract public class LocalRoomDatabase extends androidx.room.RoomDatabase {

    abstract PassDao passDao();
    abstract GroupDao groupDao();
    abstract GroupWithPassDao groupWithPassDao();
    abstract PassWithGroupDao passWithGroupDao();
    abstract SetupDao setupDao();

    public static final String DATABASE_NAME = "mels_pass_db";

    public void BackupDB(String outFileName) {

        close();
        //database path
        final String inFileName = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        //context.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(context, "Backup Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void RestoreDB(String inFileName) {

        final String outFileName = context.getDatabasePath(DATABASE_NAME+".db").toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(context, "Import Completed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private static volatile LocalRoomDatabase instance ;
    private static final int NUMBER_OF_THREADS = 4;

    private Context context ;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LocalRoomDatabase getInstance(final Context context){
        if (instance == null) {
            synchronized (LocalRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            LocalRoomDatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
//        .addMigrations(MIGRATION_1_2)
//        .addMigrations(MIGRATION_2_3)
//        .addMigrations(MIGRATION_3_4)

                    instance.context = context;
                }
            }
        }
        return instance;
    }

//

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


            });
        }
    };
/*
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

 */
 }



