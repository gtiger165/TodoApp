package com.hirarki.todoapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditJadwal extends AppCompatActivity {

    private Spinner sKategori;
    private ArrayAdapter<CharSequence> arrayAdapterKategori;
    private Calendar kalender;
    private EditText edTanggal, edWaktu, edTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jadwal);

        sKategori = findViewById(R.id.spinner_kategori);
        arrayAdapterKategori = ArrayAdapter.createFromResource(this, R.array.kategoriSpiner, R.layout.spinner_kategori);
        sKategori.setAdapter(arrayAdapterKategori);

        kalender = Calendar.getInstance();
        edTanggal = findViewById(R.id.edit_Text_Tanggal);
        edWaktu = findViewById(R.id.edit_Text_Waktu);
        edTask = findViewById(R.id.edit_Text_Kegiatan);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, monthOfYear);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEditTanggal();
            }
        };

        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditJadwal.this, date, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jam = kalender.get(Calendar.HOUR_OF_DAY);
                int menit = kalender.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(EditJadwal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedJam, int selectedMenit) {
                        edWaktu.setText(selectedJam+":"+selectedMenit);
                    }
                }, jam, menit, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        final Task task = (Task) getIntent().getSerializableExtra("task");

        loadTask(task);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateTask(task);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditJadwal.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(task);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void updateEditTanggal(){
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edTanggal.setText(sdf.format(kalender.getTime()));
    }

    private void loadTask(Task task){
        edTanggal.setText(task.getTanggal());
        edWaktu.setText(task.getJam());
        edTask.setText(task.getTask());
    }

    private void updateTask(final Task task){
        final String sTanggal = edTanggal.getText().toString().trim();
        final String sJam = edWaktu.getText().toString().trim();
        final String sTask = edTask.getText().toString().trim();

        if (sTask.isEmpty()){
            edTask.setError("Kegiatan Required!");
            edTask.requestFocus();
            return;
        }

        class  UpdateTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                task.setTanggal(sTanggal);
                task.setJam(sJam);
                task.setTask(sTask);
                DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(EditJadwal.this, MainActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }

    private void deleteTask(final Task task){
        class DeleteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(EditJadwal.this, MainActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }
}
