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

public class LoginWithInvalidCredentialsTest {
    @Test
    public void login_withInvalidCredentials_staysOnSameScreen() {
        //Launch MainActivity
        try (ActivityScenario<LoginPage> scenario = ActivityScenario.launch(LoginPage.class)){
            // Typing in the invalid username and passwords
            onView(withId(R.id.nomDutilisateur)).perform(typeText("invalid_username"), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.motDePasse)).perform(typeText("invalid_password"), ViewActions.closeSoftKeyboard());

            // Initialize Espresso-Intents
            Intents.init();

            //Clicking the logging button
            onView(withId(R.id.seConnecter)).perform(click());

            // Check that neither MainActivity2 nor MainActivity3 is started
            intended(not(hasComponent(HomePageUser.class.getName())), times(0));
            intended(not(hasComponent(HomePageAdmin.class.getName())), times(0));

            // Release Espresso-Intents
            Intents.release();
        }
    }
}
