package com.hirarki.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
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

public class TambahJadwal extends AppCompatActivity {

    private Spinner sKategori;
    private ArrayAdapter<CharSequence> arrayAdapterKategori;
    private Calendar kalender;
    private EditText edTanggal, edWaktu, edTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        //Inisialisasi komponen pada activity_tambah_jadwal.xml
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
                new DatePickerDialog(TambahJadwal.this, date, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jam = kalender.get(Calendar.HOUR_OF_DAY);
                int menit = kalender.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(TambahJadwal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedJam, int selectedMenit) {
                        edWaktu.setText(selectedJam+":"+selectedMenit);
                    }
                }, jam, menit, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        findViewById(R.id.button_simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void updateEditTanggal(){
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edTanggal.setText(sdf.format(kalender.getTime()));
    }

    private void saveTask(){
        final String simpanKategori = sKategori.getSelectedItem().toString().trim();
        final String simpanTanggal = edTanggal.getText().toString().trim();
        final String simpanJam = edWaktu.getText().toString().trim();
        final String simpanKegiatan = edTask.getText().toString().trim();

        if (simpanTanggal.isEmpty()){
            edTanggal.setError("Tanggal Required");
            edTanggal.requestFocus();
            return;
        }
        if (simpanJam.isEmpty()){
            edWaktu.setError("Jam Required");
            edWaktu.requestFocus();
            return;
        }
        if (simpanKegiatan.isEmpty()){
            edTask.setError("Kegiatan Required");
            edTask.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                Task task = new Task();
                task.setKategori(simpanKategori);
                task.setTanggal(simpanTanggal);
                task.setJam(simpanJam);
                task.setTask(simpanKegiatan);

                //adding to database
                DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase()
                        .taskDao().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
}
