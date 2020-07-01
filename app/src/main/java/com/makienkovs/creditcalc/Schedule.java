package com.makienkovs.creditcalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class Schedule extends AppCompatActivity {

    double amount;
    int period;
    double percent;
    double payment;
    double totalPayment;
    double totalPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ListView lvNotes = findViewById(R.id.list);
        init();
        Adapter adapter = new Adapter(MainActivity.notes, this);
        lvNotes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                save();
                break;
            case R.id.share:
                share();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void init() {
        amount = getIntent().getDoubleExtra("amount", -1);
        period = getIntent().getIntExtra("period", -1);
        percent = getIntent().getDoubleExtra("percent", -1);
        payment = getIntent().getDoubleExtra("payment", -1);
        totalPayment = getIntent().getDoubleExtra("totalPayment", -1);
        totalPercent = getIntent().getDoubleExtra("totalPercent", -1);
    }

    private StringBuilder fillOutputHTML() {
        @SuppressLint("DefaultLocale")
        String message1 = getString(R.string.sum) + ":\u0020" + String.format("%.2f", amount);
        @SuppressLint("DefaultLocale")
        String message2 = getString(R.string.period) + "\u0020(" + getString(R.string.month) + "):\u0020" + period;
        @SuppressLint("DefaultLocale")
        String message3 = getString(R.string.percent) + ":\u0020" + String.format("%.2f", percent);
        @SuppressLint("DefaultLocale")
        String message4 = getString(R.string.payment) + ":\u0020" + String.format("%.2f", payment);
        @SuppressLint("DefaultLocale")
        String message5 = getString(R.string.totalPayment) + String.format("%.2f", totalPayment);
        @SuppressLint("DefaultLocale")
        String message6 = getString(R.string.totalPercent) + String.format("%.2f", totalPercent);

        StringBuilder output = new StringBuilder();
        Note n0 = MainActivity.notes.get(0);
        output
                .append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<title>Document</title>")
                .append("<style>")
                .append("table { width: 100%; border-spacing: 1px; border: 1px solid; }")
                .append("td, th { border: 1px solid; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<table>")
                .append("<tr>")
                .append("<th>").append(n0.getNumberString()).append("</th>")
                .append("<th>").append(n0.getDate()).append("</th>")
                .append("<th>").append(n0.getAmountString()).append("</th>")
                .append("<th>").append(n0.getMainString()).append("</th>")
                .append("<th>").append(n0.getPercentString()).append("</th>")
                .append("<th>").append(n0.getRemainsString()).append("</th>")
                .append("</tr>");

        for (int i = 1; i < MainActivity.notes.size(); i++) {
            Note n = MainActivity.notes.get(i);
            output
                    .append("<tr>")
                    .append("<td>").append(n.getNumberString()).append("</td>")
                    .append("<td>").append(n.getDate()).append("</td>")
                    .append("<td>").append(n.getAmountString()).append("</td>")
                    .append("<td>").append(n.getMainString()).append("</td>")
                    .append("<td>").append(n.getPercentString()).append("</td>")
                    .append("<td>").append(n.getRemainsString()).append("</td>")
                    .append("</tr>");
        }
        output
                .append("</table>")
                .append("</br>")
                .append("<h3>").append(message1).append("</h3>")
                .append("<h3>").append(message2).append("</h3>")
                .append("<h3>").append(message3).append("</h3>")
                .append("<h3>").append(message4).append("</h3>")
                .append("<h3>").append(message5).append("</h3>")
                .append("<h3>").append(message6).append("</h3>")
                .append("</body>")
                .append("</html>");

        return output;
    }

    private StringBuilder fillOutputTXT() {
        @SuppressLint("DefaultLocale")
        String message1 = getString(R.string.sum) + ":\u0020" + String.format("%.2f", amount);
        @SuppressLint("DefaultLocale")
        String message2 = getString(R.string.period) + "\u0020(" + getString(R.string.month) + "):\u0020" + period;
        @SuppressLint("DefaultLocale")
        String message3 = getString(R.string.percent) + ":\u0020" + String.format("%.2f", percent);
        @SuppressLint("DefaultLocale")
        String message4 = getString(R.string.payment) + ":\u0020" + String.format("%.2f", payment);
        @SuppressLint("DefaultLocale")
        String message5 = getString(R.string.totalPayment) + String.format("%.2f", totalPayment);
        @SuppressLint("DefaultLocale")
        String message6 = getString(R.string.totalPercent) + String.format("%.2f", totalPercent);

        StringBuilder output = new StringBuilder();
        Note n0 = MainActivity.notes.get(0);
        output
                .append(n0.getNumberString()).append("\t")
                .append(n0.getDate()).append("\t").append("\t")
                .append(n0.getAmountString()).append("\t").append("\t")
                .append(n0.getMainString()).append("\t")
                .append(n0.getPercentString()).append("\t")
                .append(n0.getRemainsString()).append("\n");

        for (int i = 1; i < MainActivity.notes.size(); i++) {
            Note n = MainActivity.notes.get(i);
            output
                    .append(n.getNumberString()).append("\t")
                    .append(n.getDate()).append("\t")
                    .append(n.getAmountString()).append("\t").append("\t")
                    .append(n.getMainString()).append("\t").append("\t")
                    .append(n.getPercentString()).append("\t").append("\t")
                    .append(n.getRemainsString()).append("\n");
        }
        output
                .append("\n")
                .append(message1)
                .append("\n")
                .append(message2)
                .append("\n")
                .append(message3)
                .append("\n")
                .append(message4)
                .append("\n")
                .append(message5)
                .append("\n")
                .append(message6);
        return output;
    }

    private void save() {
        final String output = fillOutputHTML().toString();

        if (FileUtil.isExternalStorageWritable()) {
            final View saveLayout = getLayoutInflater().inflate(R.layout.save, null);
            final EditText editText = saveLayout.findViewById(R.id.editTextSave);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            new AlertDialog.Builder(this)
                    .setTitle(R.string.save)
                    .setIcon(R.drawable.ic_menu_save)
                    .setView(saveLayout)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String fileName = editText.getText().toString();
                            File dir = getExternalFilesDir(null);
                            File file = new File(dir, fileName + ".html");
                            FileUtil.writeStringAsFile(output, file);
                            Toast.makeText(Schedule.this, getString(R.string.file) + fileName + ".html", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .create()
                    .show();

        } else {
            Toast.makeText(this, getText(R.string.external), Toast.LENGTH_LONG).show();
        }
    }

    private void share() {
        String output = fillOutputTXT().toString();
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        share.putExtra(Intent.EXTRA_SUBJECT, R.string.calculation);
        share.putExtra(Intent.EXTRA_TEXT, output);
        startActivity(Intent.createChooser(share, getString(R.string.share)));
    }
}