package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.appynitty.swachbharatabhiyanlibrary.daos.SurveyDao;
import com.appynitty.swachbharatabhiyanlibrary.db.AppDataBase;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

public class OfflineSurveyRepo {
    AppDataBase appDataBase;
    SurveyDao surveyDao;
    LiveData<List<OfflineSurvey>> allSurvey;
    LiveData<List<OfflineSurvey>> getSurveyByLimit;
    int limit = 10;

    public OfflineSurveyRepo(Application application) {
        appDataBase = AppDataBase.getAppRoomDataBase(application.getApplicationContext());
        surveyDao = appDataBase.surveyDao();
        allSurvey = surveyDao.getAllSurvey();
        getSurveyByLimit = surveyDao.getLimitList(limit);
        /*offlineSurveyList = surveyDao.getAllSurveyList();*/
    }

    public void insertSurvey(OfflineSurvey offlineSurvey){
        AppDataBase.databaseWriteExecutor.execute(() -> surveyDao.insert(offlineSurvey));
    }

    public void updateSurvey(OfflineSurvey offlineSurvey) {
        new UpdateSurveyAsyncTask(surveyDao).execute(offlineSurvey);
    }

    public void deleteSurvey(OfflineSurvey offlineSurvey) {
        new DeleteSurveyAsyncTask(surveyDao).execute(offlineSurvey);
    }
    public void deleteSurveyById(String houseId) {
        surveyDao.deleteById(houseId);
    }
    public void deleteAllSurvey() {
        new DeleteAllSurveyAsyncTask(surveyDao).execute();
    }
    public void getSurveyRow(){
        new GetCountSurveyAsyncTask(surveyDao).execute();
    }
    public int getOfflineCount() {
        int count1;
        count1 = surveyDao.getCount();
        return count1;
    }

    public LiveData<List<OfflineSurvey>> getAllOfflineSurvey() {
        return allSurvey;
    }

    public LiveData<List<OfflineSurvey>> getOfflineSurveyByLimit() {
        return getSurveyByLimit;
    }




    private static class InsertSurveyAsyncTask extends AsyncTask<OfflineSurvey, Void, Void> {
        private SurveyDao surveyDao;

        public InsertSurveyAsyncTask(SurveyDao surveyDao) {
            this.surveyDao = surveyDao;
        }

        @Override
        protected Void doInBackground(OfflineSurvey... offlineSurveys) {
            surveyDao.insert(offlineSurveys[0]);
            return null;
        }
    }

    private static class UpdateSurveyAsyncTask extends AsyncTask<OfflineSurvey, Void, Void> {
        private SurveyDao surveyDao;

        public UpdateSurveyAsyncTask(SurveyDao surveyDao) {
            this.surveyDao = surveyDao;
        }

        @Override
        protected Void doInBackground(OfflineSurvey... offlineSurveys) {
            surveyDao.update(offlineSurveys[0]);
            return null;
        }
    }

    private static class DeleteSurveyAsyncTask extends AsyncTask<OfflineSurvey, Void, Void> {
        private SurveyDao surveyDao;

        public DeleteSurveyAsyncTask(SurveyDao surveyDao) {
            this.surveyDao = surveyDao;
        }

        @Override
        protected Void doInBackground(OfflineSurvey... offlineSurveys) {
            surveyDao.delete(offlineSurveys[0]);
            return null;
        }
    }
    private static class DeleteAllSurveyAsyncTask extends AsyncTask<OfflineSurvey, Void, Void> {
        private SurveyDao surveyDao;

        public DeleteAllSurveyAsyncTask(SurveyDao surveyDao) {
            this.surveyDao = surveyDao;
        }

        @Override
        protected Void doInBackground(OfflineSurvey... offlineSurveys) {
            surveyDao.deleteAllSurvey();
            return null;
        }
    }

    private static class GetCountSurveyAsyncTask extends AsyncTask<OfflineSurvey, Void, Void> {
        private SurveyDao surveyDao;

        public GetCountSurveyAsyncTask(SurveyDao surveyDao) {
            this.surveyDao = surveyDao;
        }

        @Override
        protected Void doInBackground(OfflineSurvey... offlineSurveys) {
            surveyDao.getCount();
            return null;
        }
    }


}
