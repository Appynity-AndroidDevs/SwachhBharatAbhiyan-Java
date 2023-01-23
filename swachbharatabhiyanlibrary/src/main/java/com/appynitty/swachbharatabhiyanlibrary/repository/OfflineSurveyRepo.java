package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.appynitty.swachbharatabhiyanlibrary.daos.SurveyDao;
import com.appynitty.swachbharatabhiyanlibrary.db.AppDataBase;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

public class OfflineSurveyRepo {
    SurveyDao surveyDao;
    LiveData<List<OfflineSurvey>> allSurvey;
    List<OfflineSurvey> offlineSurveyList;

    public OfflineSurveyRepo() {
        AppDataBase db = AppDataBase.getInstance(AUtils.mainApplicationConstant);
        surveyDao = db.surveyDao();
        allSurvey = surveyDao.getAllSurvey();
        offlineSurveyList = surveyDao.getAllSurveyList();
    }

    public void insertSurvey(OfflineSurvey offlineSurvey) {
        new InsertSurveyAsyncTask(surveyDao).execute(offlineSurvey);
    }

    public void updateSurvey(OfflineSurvey offlineSurvey) {
        new UpdateSurveyAsyncTask(surveyDao).execute(offlineSurvey);
    }

    public void deleteSurvey(OfflineSurvey offlineSurvey) {
        new DeleteSurveyAsyncTask(surveyDao).execute(offlineSurvey);
    }
    public void deleteSurveyById(int mId) {
        surveyDao.deleteById(mId);
    }
    public void deleteAllSurvey() {
        new DeleteAllSurveyAsyncTask(surveyDao).execute();
    }

    public LiveData<List<OfflineSurvey>> getAllSurvey() {
        return allSurvey;
    }

    public List<OfflineSurvey> getOfflineSurveyList() {
        return offlineSurveyList;
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


}
