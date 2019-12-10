package com.example.recipe;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class RecipeForm extends AppCompatActivity {

    private RecipeDatabaseHelper recipeDB;
    private Date endOfWarrantyDate;


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
        Drawable drawable = imageView.getDrawable();
        saveRecipe(recipe);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private Recipe getRecipe() {
        EditText recipeName = findViewById(R.id.recipeNameField);
        return Recipe.builder()
                .name(recipeName.getText().toString())
                .endOfWarrantyDate(this.endOfWarrantyDate.toString())
                .build();
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(RecipeForm.this,
                (datePicker, year, month, day) -> {
                    this.endOfWarrantyDate = new GregorianCalendar(year, month - 1, day).getTime();
                    TextView endOfWarrantyField = findViewById(R.id.recipeEndOfWarrantyField);
                    DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
                    String redableData = df.format(this.endOfWarrantyDate);
                    SpannableString content = new SpannableString(redableData);
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    endOfWarrantyField.setText(content);
                }, 2019, 11, 5);
        datePickerDialog.show();
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

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
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

    private void createImage(String name) {
        Bitmap image = null;

        File path = Environment.getExternalStorageDirectory();

        File dir = new File(path+"/recipeHolder/");
        dir.mkdirs();

        File file = new File(dir, name + "png");

        OutputStream outputStream = null;


    }
    String imageFilePath;

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
}
