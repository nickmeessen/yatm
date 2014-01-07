package nl.enterprisecoding.android.sufficient.controllers;

import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.Calendar;

public interface ITaskManager  {

    public Category createCategory(String title, int colour);

    public Task createTask(String title, long categoryId, Calendar date, boolean important);

}
