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

public class CreateNullAccountTest {
    @Test
    public void createAccount_withNullCredentials_staysOnSameScreen() {
        //Launch Create_account_for_client
        try (ActivityScenario<CreateAccountForClient> scenario = ActivityScenario.launch(CreateAccountForClient.class)){
            // Typing in the invalid username and passwords
            onView(withId(R.id.newUsername)).perform(typeText(""), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.newPassword)).perform(typeText(""), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.updateService)).perform(click());

            // Check that MainActivity has not started
            intended(not(hasComponent(LoginPage.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();
        }
    }
}
