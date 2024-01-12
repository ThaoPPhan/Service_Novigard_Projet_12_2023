package com.example.servicenovigrad;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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

public class CreateNullServiceTest {
    @Test
    public void createService_withNullElements_staysOnSameScreen() {
        //Test with Both Null
        // Launch ServiceViewer (to create a Service from the admin panel)
        try (ActivityScenario<ServiceViewer> scenario = ActivityScenario.launch(ServiceViewer.class)){
            // Typing in the invalid username and passwords
            onView(withId(R.id.serviceNameEditText)).perform(typeText(""), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.serviceDescriptionEditText)).perform(typeText(""), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.saveService)).perform(click());

            // Check that ServiceListAdmin has not started
            intended(not(hasComponent(ServiceListAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();
        }
        //Test with serviceDescriptionEditText=null
        // Launch ServiceViewer (to create a Service from the admin panel)
        try (ActivityScenario<ServiceViewer> scenario = ActivityScenario.launch(ServiceViewer.class)){
            // Typing in the invalid username and passwords
            onView(withId(R.id.serviceNameEditText)).perform(typeText("testing"), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.serviceDescriptionEditText)).perform(typeText(""), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.saveService)).perform(click());

            // Check that ServiceListAdmin has not started
            intended(not(hasComponent(ServiceListAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();
        }
        //Test with serviceNameEditText=null
        // Launch ServiceViewer (to create a Service from the admin panel)
        try (ActivityScenario<ServiceViewer> scenario = ActivityScenario.launch(ServiceViewer.class)){
            // Typing in the invalid username and passwords
            onView(withId(R.id.serviceNameEditText)).perform(typeText(""), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.serviceDescriptionEditText)).perform(typeText("testing"), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.saveService)).perform(click());

            // Check that ServiceListAdmin has not started
            intended(not(hasComponent(ServiceListAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();
        }
    }
}
