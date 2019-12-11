package com.example.recipe;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class RecipeForm extends AppCompatActivity {

    private RecipeDatabaseHelper recipeDB;
    private Date endOfWarrantyDate;
    private String readableData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);
        recipeDB = new RecipeDatabaseHelper(this);
        this.imageView = this.findViewById(R.id.imageView1);
    }

    public void saveRecipe(View view) {
        Recipe recipe = getRecipe();
        if (recipe.getName().isEmpty()) {
            Toast.makeText(this, "Pleas enter a name", Toast.LENGTH_LONG).show();
            return;
        }
        if(photoTaken!=null) {
            createImage(recipe.getName(), photoTaken);
            saveRecipe(recipe);
        }else {
            Toast.makeText(this, "Pleas take a photo of recipe", Toast.LENGTH_LONG).show();
            return;
        }
        this.registerNotificationToShow(endOfWarrantyDate,recipe.getName());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private Recipe getRecipe() {
        EditText recipeName = findViewById(R.id.recipeNameField);
        return Recipe.builder()
                .name(recipeName.getText().toString())
                .endOfWarrantyDate(this.readableData)
                .build();
    }

    public void openDatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DATE);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(RecipeForm.this,
                (datePicker, year, month, day) -> {
                    this.endOfWarrantyDate = new GregorianCalendar(year, month - 1, day).getTime();
                    openTimePicker(year, month, day);
                }, currentYear, currentMonth, currentDay);
        datePickerDialog.show();
    }

    private void openTimePicker(int year, int mounth, int day) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    this.endOfWarrantyDate = new GregorianCalendar(year, mounth, day, hourOfDay, minute1).getTime();
                    TextView endOfWarrantyField = findViewById(R.id.recipeEndOfWarrantyField);
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    this.readableData = df.format(this.endOfWarrantyDate);
                    SpannableString content = new SpannableString(readableData);
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    endOfWarrantyField.setText(content);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void registerNotificationToShow(Date date, String name) {
        long timeDifference = date.getTime() - Calendar.getInstance().getTime().getTime();
        System.out.println(Calendar.getInstance().getTime());
        Intent timeOutIntent = new Intent(this.getApplicationContext(), EndOfRecepieReciver.class);
        timeOutIntent.putExtra("r_name", name);
        PendingIntent pendingTimeOutIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, timeOutIntent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeDifference, pendingTimeOutIntent);
    }

    private void saveRecipe(Recipe recipe) {
        if (recipeDB.create(recipe))
            Toast.makeText(this, "Successfully save recipe", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Failed to save recipe", Toast.LENGTH_LONG).show();
    }


    public void takePhoto(View view){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    //cammera

    private static final int CAMERA_REQUEST = 2;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private Bitmap photoTaken;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoTaken = photo;
            imageView.setImageBitmap(photo);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createImage(String name, Bitmap image) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/recipeHolder/");
        dir.mkdirs();
        File file = new File(dir, name + ".png");
        OutputStream outputStream = null;
        Toast.makeText(this,file.getAbsolutePath(),Toast.LENGTH_LONG).show();
        try{
            outputStream = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
