package com.example.d308vacationplanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308vacationplanner.UI.LoginScreenActivity;

import org.junit.Test;

public class LoginScreenActivityTest {


    //task D
    // Test when username and password are both empty
    @Test
    public void testValidateCredentials_EmptyFields() {
        boolean result = LoginScreenActivity.validateCredentials("", "");
        assertFalse(result);
    }

    // Test when username and password are valid
    @Test
    public void testValidateCredentials_ValidFields() {
        boolean result = LoginScreenActivity.validateCredentials("testuser", "testpassword");
        assertTrue(result);
    }

    // test when only one field is empty
    @Test
    public void testValidateCredentials_UsernameOnly() {
        boolean result = LoginScreenActivity.validateCredentials("testuser", "");
        assertFalse(result);
    }

    @Test
    public void testValidateCredentials_PasswordOnly() {
        boolean result = LoginScreenActivity.validateCredentials("", "testpassword");
        assertFalse(result);
    }


}
