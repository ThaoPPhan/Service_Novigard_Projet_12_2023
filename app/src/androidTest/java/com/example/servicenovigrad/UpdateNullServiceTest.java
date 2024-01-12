package com.example.servicenovigrad;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;

import org.junit.Test;

public class UpdateNullServiceTest {
    @Test
    public void updateService_withNullElements_staysOnSameScreen() {
        //Test with Both Null
        // Launch ServiceViewer (to create a Service from the admin panel)
        try (ActivityScenario<ServiceUpdateActivity> scenario = ActivityScenario.launch(ServiceUpdateActivity.class)){
            // Typing in the null Service name and description
            onView(withId(R.id.serviceNameEditText)).perform(replaceText(""), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.serviceDescriptionEditText)).perform(replaceText(""), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.updateService)).perform(click());

            // Check that ServiceListAdmin has not started
            intended(not(hasComponent(ServiceListAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();

            // Typing in the null Service name
            onView(withId(R.id.serviceNameEditText)).perform(replaceText(""), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.serviceDescriptionEditText)).perform(replaceText("testing"), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.updateService)).perform(click());

            // Check that ServiceListAdmin has not started
            intended(not(hasComponent(ServiceListAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();

            // Typing in the null Service name
            onView(withId(R.id.serviceNameEditText)).perform(replaceText("testing"), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.serviceDescriptionEditText)).perform(replaceText(""), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.updateService)).perform(click());

            // Check that ServiceListAdmin has not started
            intended(not(hasComponent(ServiceListAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();
        }
    }
}
