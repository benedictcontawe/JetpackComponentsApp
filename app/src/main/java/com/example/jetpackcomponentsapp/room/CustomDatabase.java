package com.example.jetpackcomponentsapp.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database (
    entities = {CustomEntity.class},
    version = 1
)
public abstract class CustomDatabase extends RoomDatabase {

    private static CustomDatabase instance;

    public abstract CustomDAO customDAO();

    public static synchronized CustomDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CustomDatabase.class,"custom_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private CustomDAO customDAO;

        private PopulateDatabaseAsyncTask(CustomDatabase db) {
            this.customDAO = db.customDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //customDAO.insert(new CustomEntity("name 0"));
            //customDAO.insert(new CustomEntity("name 1"));
            //customDAO.insert(new CustomEntity("name 2"));
            //customDAO.insert(new CustomEntity("name 3"));
            //customDAO.insert(new CustomEntity("name 4"));
            return null;
        }
    }
}