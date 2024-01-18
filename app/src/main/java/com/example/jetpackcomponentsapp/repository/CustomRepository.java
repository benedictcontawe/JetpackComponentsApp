package com.example.jetpackcomponentsapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.room.CustomDAO;
import com.example.jetpackcomponentsapp.room.CustomDatabase;
import com.example.jetpackcomponentsapp.room.CustomEntity;

import java.util.List;

public class CustomRepository implements BaseRepository {

    private final CustomDAO customDAO;
    private static CustomRepository INSTANCE;

    public static CustomRepository getInstance(Application applicationContext) {
        if (INSTANCE == null) INSTANCE = new CustomRepository(applicationContext);
        return INSTANCE;
    }

    private CustomRepository(Application applicationContext) {
        CustomDatabase database = CustomDatabase.getInstance(applicationContext);
        customDAO = database.customDAO();
    }

    @Override
    public void insert(CustomModel customModel) {
        new insertAsyncTask(customDAO).execute (
            ConvertList.toEntity(customModel)
        );
    }

    @Override
    public void update(CustomModel customModel) {
        new updateAsyncTask(customDAO).execute (
            ConvertList.toEntity(customModel)
        );
    }

    @Override
    public void delete(CustomModel customModel) {
        new deleteAsyncTask(customDAO).execute (
            ConvertList.toEntity(customModel)
        );
    }

    @Override
    public void deleteAll() {
        new deleteAllAsyncTask(customDAO).execute();
    }

    @Override
    public LiveData<List<CustomModel>> getAll() {
        return ConvertList.toLiveDataListModel(
                customDAO.getAll()
        );
    }

    //region Static Class For Repository
    private static class insertAsyncTask extends AsyncTask<CustomEntity, Void, Void> {
        private final CustomDAO customDAO;

        private insertAsyncTask(CustomDAO customDAO) {
            this.customDAO = customDAO;
        }

        @Override
        protected Void doInBackground(CustomEntity... customEntities) {
            customDAO.insert(customEntities[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<CustomEntity, Void, Void> {
        private final CustomDAO customDAO;

        private updateAsyncTask(CustomDAO customDAO) {
            this.customDAO = customDAO;
        }

        @Override
        protected Void doInBackground(CustomEntity... customEntities) {
            customDAO.update(customEntities[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<CustomEntity, Void, Void> {
        private final CustomDAO customDAO;

        private deleteAsyncTask(CustomDAO customDAO) {
            this.customDAO = customDAO;
        }

        @Override
        protected Void doInBackground(CustomEntity... customEntities) {
            customDAO.delete(customEntities[0].getId());
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CustomDAO customDAO;

        private deleteAllAsyncTask(CustomDAO customDAO) {
            this.customDAO = customDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            customDAO.deleteAll();
            return null;
        }
    }
    //endregion
}