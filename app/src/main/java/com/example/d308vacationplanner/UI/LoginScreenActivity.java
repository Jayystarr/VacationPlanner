package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308vacationplanner.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginScreenActivity extends AppCompatActivity {

    // Demo Credentials are stored here - demoUser
    private static final String VALID_USERNAME = "demoUser";

    //Password = password123
    private static final String VALID_PASSWORD_HASH = "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f"; // SHA-256 for "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ImageView appLogo = findViewById(R.id.logoImageView);
        roundImageCorners(appLogo);

        EditText inputUsername = findViewById(R.id.username);
        EditText inputPassword = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.signInButton);

        loginBtn.setOnClickListener(view -> {
            String userEntry = inputUsername.getText().toString().trim();
            String passEntry = inputPassword.getText().toString().trim();

            if (isValidInput(userEntry, passEntry)) {
                String hashedInput = hashSHA256(passEntry);

                if (userEntry.equals(VALID_USERNAME) && hashedInput.equals(VALID_PASSWORD_HASH)) {
                    Intent goToHome = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(goToHome);
                    finish();
                } else {
                    showToast("Wrong username or password");
                }
            }
        });
    }


    private void roundImageCorners(ImageView image) {
        Drawable drawable = image.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap original = ((BitmapDrawable) drawable).getBitmap();
            Bitmap rounded = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
            Canvas canvas = new Canvas(rounded);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(original, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            float radius = 24f;
            canvas.drawRoundRect(0, 0, original.getWidth(), original.getHeight(), radius, radius, paint);

            image.setImageBitmap(rounded);
        }
    }

    private boolean isValidInput(String user, String pass) {
        if (user.isEmpty()) {
            showToast("Username cannot be empty");
            return false;
        }

        if (!user.matches("^[a-zA-Z0-9]+$")) {
            showToast("Username must be alphanumeric");
            return false;
        }

        if (pass.isEmpty()) {
            showToast("Password cannot be empty");
            return false;
        }

        if (pass.length() < 6 || !pass.matches(".*\\d.*")) {
            showToast("Password must be 6 or more characters and include a number");
            return false;
        }

        return true;
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}





