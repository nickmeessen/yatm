package nl.enterprisecoding.android.sufficient.controllers;

import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.Calendar;

public interface ITaskManager  {

    public void createCategory(String title, int colour);

    public long createTask(String title, long categoryId, Calendar date, boolean important);

}
