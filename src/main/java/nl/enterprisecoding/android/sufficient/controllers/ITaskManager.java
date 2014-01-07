package nl.enterprisecoding.android.sufficient.controllers;

import java.util.Calendar;

public interface ITaskManager {

    void createCategory(String title, int colour);

    long createTask(String title, long categoryId, Calendar date, boolean important);

}
