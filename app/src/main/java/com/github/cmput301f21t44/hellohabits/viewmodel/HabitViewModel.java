package com.github.cmput301f21t44.hellohabits.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.cmput301f21t44.hellohabits.firebase.CatchFunction;
import com.github.cmput301f21t44.hellohabits.firebase.ResultFunction;
import com.github.cmput301f21t44.hellohabits.firebase.ThenFunction;
import com.github.cmput301f21t44.hellohabits.model.habit.Habit;
import com.github.cmput301f21t44.hellohabits.model.habit.HabitRepository;

import java.time.Instant;
import java.util.List;

/**
 * ViewModel class for viewing all Habits
 * Also keeps track of the data on a selected Habit
 */
public class HabitViewModel extends ViewModel {
    /**
     * Repository class from which to fetch Habit data
     */
    private final HabitRepository mRepository;
    /**
     * LiveData list of a user's Habits
     */
    private final LiveData<List<Habit>> mAllHabits;
    /**
     * LiveData class for keeping track of a single HabitEvent's data
     */
    private final MutableLiveData<Habit> mSelectedHabit = new MutableLiveData<>();

    /**
     * Constructor for HabitViewModel
     * Accesses the data layer through dependency injection
     *
     * @param habitRepository HabitRepository from which to fetch data
     */
    public HabitViewModel(HabitRepository habitRepository) {
        mRepository = habitRepository;
        mAllHabits = mRepository.getAllHabits();
    }

    /**
     * Gets the currently selected habit
     *
     * @return LiveData of the selected Habit
     */
    public LiveData<Habit> getSelectedHabit() {
        return mSelectedHabit;
    }

    /**
     * Sets the currently selected habit
     *
     * @param habit Habit to keep track of
     */
    public void setSelectedHabit(Habit habit) {
        mSelectedHabit.setValue(habit);
    }

    /**
     * @return LiveData List of the user's habits
     */
    public LiveData<List<Habit>> getAllHabits() {
        return mAllHabits;
    }

    /**
     * Get public habits of another user
     *
     * @param email Another user's email
     * @return LiveData of the list of public habits
     */
    public LiveData<List<Habit>> getUserPublicHabits(String email) {
        return mRepository.getUserPublicHabits(email);
    }

    /**
     * Inserts a habit in the repository
     *
     * @param title           title of the Habit
     * @param reason          Reason for the Habit
     * @param dateStarted     The starting date for the Habit
     * @param daysOfWeek      A boolean array of days of when the Habit is scheduled
     * @param isPrivate       Whether the habit is invisible to followers
     * @param successCallback Callback for when the operation succeeds
     * @param failCallback    Callback for when the operation fails
     */
    public void insert(String title, String reason, Instant dateStarted, boolean[] daysOfWeek,
                       boolean isPrivate, ThenFunction successCallback,
                       CatchFunction failCallback) {
        mRepository.insert(title, reason, dateStarted, daysOfWeek, isPrivate, successCallback,
                failCallback);
    }

    /**
     * Updates a Habit in the repository
     *
     * @param id              UUID of the Habit
     * @param title           Title of the Habit
     * @param reason          Reason for the Habit
     * @param dateStarted     The starting date for the Habit
     * @param daysOfWeek      A boolean array of days of when the Habit is scheduled
     * @param isPrivate       Whether the habit is invisible to followers
     * @param  index The index of the
     * @param successCallback Callback for when the operation succeeds
     * @param failCallback    Callback for when the operation fails
     */
    public void update(String id, String title, String reason, Instant dateStarted,
                       boolean[] daysOfWeek, boolean isPrivate, int index,
                       ResultFunction<Habit> successCallback,
                       CatchFunction failCallback) {
        mRepository.update(id, title, reason, dateStarted, daysOfWeek, isPrivate, index,
                successCallback, failCallback);
    }

    /**
     * Delete a Habit from the repository
     *
     * @param habit           Habit to delete
     * @param successCallback Callback for when the operation succeeds
     * @param failCallback    Callback for when the operation fails
     */
    public void delete(Habit habit, ThenFunction successCallback, CatchFunction failCallback) {
        mRepository.delete(habit, successCallback, failCallback);
    }
}
