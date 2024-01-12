package com.example.servicenovigrad;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;

import org.junit.Test;

public class LoginWithValidCredentialsTest {
    //This test verifies the pre-existing admin account login and the intent to the admin panel
    @Test
    public void login_withValidCredentials_opensMainActivity3() {
        try (ActivityScenario<LoginPage> scenario = ActivityScenario.launch(LoginPage.class)) {
            onView(withId(R.id.nomDutilisateur)).perform(typeText("admin"), ViewActions.closeSoftKeyboard());
            onView(withId(R.id.motDePasse)).perform(typeText("123admin456"), ViewActions.closeSoftKeyboard());

            //Initialize Espresso-Intents
            Intents.init();

            // Click on the login button
            onView(withId(R.id.seConnecter)).perform(click());

            // Check if MainActivity3 is opened
            intended(hasComponent(HomePageAdmin.class.getName()));

            //Release Espresso-Intents
            Intents.release();
        }
    }
}
