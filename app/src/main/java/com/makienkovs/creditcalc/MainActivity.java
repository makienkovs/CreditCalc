package com.makienkovs.creditcalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Note> notes;

    private boolean month;
    private String pickedDate;
    private int param;

    double amount;
    int period;
    double percent;
    double payment;
    double totalPayment;
    double totalPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        month = true;
        param = 1;
        Date now = new Date();
        TextView date = findViewById(R.id.date);
        pickedDate = (String) DateFormat.format("dd.MM.yyyy", now.getTime());
        date.setText(pickedDate);
        EditText paymentAmount = findViewById(R.id.paymentAmount);
        paymentAmount.setVisibility(View.INVISIBLE);

        Button schedule = findViewById(R.id.schedule);
        schedule.setVisibility(View.INVISIBLE);
    }

    public void monthYear(View v) {
        Button button = findViewById(R.id.my);
        if (button.getText().equals(getString(R.string.month))) {
            button.setText(R.string.year);
            month = false;
        } else {
            button.setText(R.string.month);
            month = true;
        }
    }

    public void parameter(View v) {
        RadioButton radioPayment = findViewById(R.id.radioPayment);
        RadioButton radioPeriod = findViewById(R.id.radioPeriod);
        RadioButton radioAmount = findViewById(R.id.radioAmount);
        RadioButton radioPercent = findViewById(R.id.radioPercent);
        EditText paymentAmount = findViewById(R.id.paymentAmount);
        EditText periodAmount = findViewById(R.id.periodAmount);
        EditText sumAmount = findViewById(R.id.sumAmount);
        EditText percentAmount = findViewById(R.id.percentAmount);
        Button my = findViewById(R.id.my);
        switch (v.getId()) {
            case (R.id.radioPayment):
                param = 1;
                radioPayment.setChecked(true);
                radioPercent.setChecked(false);
                radioPeriod.setChecked(false);
                radioAmount.setChecked(false);
                paymentAmount.setVisibility(View.INVISIBLE);
                periodAmount.setVisibility(View.VISIBLE);
                my.setVisibility(View.VISIBLE);
                sumAmount.setVisibility(View.VISIBLE);
                percentAmount.setVisibility(View.VISIBLE);
                break;
            case (R.id.radioPercent):
                param = 2;
                radioPayment.setChecked(false);
                radioPercent.setChecked(true);
                radioPeriod.setChecked(false);
                radioAmount.setChecked(false);
                paymentAmount.setVisibility(View.VISIBLE);
                periodAmount.setVisibility(View.VISIBLE);
                my.setVisibility(View.VISIBLE);
                sumAmount.setVisibility(View.VISIBLE);
                percentAmount.setVisibility(View.INVISIBLE);
                break;
            case (R.id.radioPeriod):
                param = 3;
                radioPayment.setChecked(false);
                radioPercent.setChecked(false);
                radioPeriod.setChecked(true);
                radioAmount.setChecked(false);
                paymentAmount.setVisibility(View.VISIBLE);
                periodAmount.setVisibility(View.INVISIBLE);
                my.setVisibility(View.INVISIBLE);
                sumAmount.setVisibility(View.VISIBLE);
                percentAmount.setVisibility(View.VISIBLE);
                break;
            case (R.id.radioAmount):
                param = 4;
                radioPayment.setChecked(false);
                radioPercent.setChecked(false);
                radioPeriod.setChecked(false);
                radioAmount.setChecked(true);
                paymentAmount.setVisibility(View.VISIBLE);
                periodAmount.setVisibility(View.VISIBLE);
                my.setVisibility(View.VISIBLE);
                sumAmount.setVisibility(View.INVISIBLE);
                percentAmount.setVisibility(View.VISIBLE);
                break;
            default:
                param = 1;
        }
        clear(v);
    }

    public void calendar(View v) {
        final View calendarViewLayout = getLayoutInflater().inflate(R.layout.calendar, null);
        final TextView date = findViewById(R.id.date);
        final CalendarView calendar = calendarViewLayout.findViewById(R.id.calendarView);
        final String[] dateText = new String[1];
        dateText[0] = (String) DateFormat.format("dd.MM.yyyy", calendar.getDate());
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String monthSrting;
                String dayString;
                if (month < 9) {
                    monthSrting = "0" + (month + 1);
                } else {
                    monthSrting = "" + (month + 1);
                }
                if (dayOfMonth < 10) {
                    dayString = "0" + dayOfMonth;
                } else {
                    dayString = "" + dayOfMonth;
                }
                dateText[0] = "" + dayString + "." + monthSrting + "." + year;
            }
        });
        new AlertDialog.Builder(this)
                .setView(calendarViewLayout)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        date.setText(dateText[0]);
                        pickedDate = dateText[0];
                    }
                })
                .create()
                .show();
    }

    public void clear(View v) {
        EditText sumAmount = findViewById(R.id.sumAmount);
        sumAmount.setText("");

        EditText periodAmount = findViewById(R.id.periodAmount);
        periodAmount.setText("");

        EditText percentAmount = findViewById(R.id.percentAmount);
        percentAmount.setText("");

        EditText paymentAmount = findViewById(R.id.paymentAmount);
        paymentAmount.setText("");

        TextView resultText = findViewById(R.id.resultText);
        resultText.setText("");

        TextView result = findViewById(R.id.result);
        result.setText("");

        Button my = findViewById(R.id.my);
        my.setText(R.string.month);
        month = true;

        Button schedule = findViewById(R.id.schedule);
        schedule.setVisibility(View.INVISIBLE);
    }

    public void calculate(View v) {
        EditText sumAmount = findViewById(R.id.sumAmount);
        String sumTextString = sumAmount.getText().toString();

        EditText periodAmount = findViewById(R.id.periodAmount);
        String periodTextString = periodAmount.getText().toString();

        EditText percentAmount = findViewById(R.id.percentAmount);
        String percentTextString = percentAmount.getText().toString();

        EditText paymentAmount = findViewById(R.id.paymentAmount);
        String paymentTextString = paymentAmount.getText().toString();

        Button schedule = findViewById(R.id.schedule);

        boolean a = sumTextString.equals("") || Double.parseDouble(sumTextString) == 0;
        boolean b = periodTextString.equals("") || Integer.parseInt(periodTextString) == 0;
        boolean c = percentTextString.equals("") || Double.parseDouble(percentTextString) == 0;
        boolean d = paymentTextString.equals("") || Double.parseDouble(paymentTextString) == 0;

        if (param == 1) {
            if (a) {
                Toast.makeText(this, R.string.wrongAmount, Toast.LENGTH_SHORT).show();
            } else {
                amount = Double.parseDouble(sumTextString);
                if (b) {
                    Toast.makeText(this, R.string.wrongPeriod, Toast.LENGTH_SHORT).show();
                } else {
                    period = Integer.parseInt(periodTextString);
                    if (!month) {
                        period *= 12;
                    }
                    if (c) {
                        Toast.makeText(this, R.string.wrongPercent, Toast.LENGTH_SHORT).show();
                    } else {
                        percent = Double.parseDouble(percentTextString);
                        if (percent > 1200) {
                            Toast.makeText(this, R.string.percentShouldNot, Toast.LENGTH_SHORT).show();
                        } else {
                            percent = percent / 1200;
                            payment = amount * (percent + percent / (Math.pow(1 + percent, period) - 1));
                            total();
                            writeOutput();
                            schedule.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } else if (param == 2) {
            if (a) {
                Toast.makeText(this, R.string.wrongAmount, Toast.LENGTH_SHORT).show();
            } else {
                amount = Double.parseDouble(sumTextString);
                if (b) {
                    Toast.makeText(this, R.string.wrongPeriod, Toast.LENGTH_SHORT).show();
                } else {
                    period = Integer.parseInt(periodTextString);
                    if (!month) {
                        period *= 12;
                    }
                    if (d) {
                        Toast.makeText(this, R.string.wrongPayment, Toast.LENGTH_SHORT).show();
                    } else {
                        payment = Double.parseDouble(paymentTextString);
                        if (payment < amount / period) {
                            Toast.makeText(this, R.string.paymentShould, Toast.LENGTH_SHORT).show();
                        } else if (payment > amount) {
                            Toast.makeText(this, R.string.paymentShouldNot, Toast.LENGTH_SHORT).show();
                        } else {
                            percent = 0;
                            do {
                                percent += 0.000001;
                            } while (payment >= amount * (percent + percent / (Math.pow(1 + percent, period) - 1)));
                            payment = amount * (percent + percent / (Math.pow(1 + percent, period) - 1));
                            total();
                            writeOutput();
                            schedule.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } else if (param == 3) {
            if (a) {
                Toast.makeText(this, R.string.wrongAmount, Toast.LENGTH_SHORT).show();
            } else {
                amount = Double.parseDouble(sumTextString);
                if (c) {
                    Toast.makeText(this, R.string.wrongPercent, Toast.LENGTH_SHORT).show();
                } else {
                    percent = Double.parseDouble(percentTextString);
                    if (percent > 1200) {
                        Toast.makeText(this, R.string.percentShouldNot, Toast.LENGTH_SHORT).show();
                    } else {
                        percent = percent / 1200;
                        if (d) {
                            Toast.makeText(this, R.string.wrongPayment, Toast.LENGTH_SHORT).show();
                        } else {
                            payment = Double.parseDouble(paymentTextString);
                            if (payment > amount) {
                                Toast.makeText(this, R.string.paymentShouldNot, Toast.LENGTH_SHORT).show();
                            } else {
                                period = 0;
                                do {
                                    period++;
                                    if (period > 600) {
                                        Toast.makeText(this, R.string.paymentShould, Toast.LENGTH_SHORT).show();
                                        period = 0;
                                        break;
                                    }
                                } while (payment <= amount * (percent + percent / (Math.pow(1 + percent, period) - 1)));
                                if (period > 0) {
                                    payment = amount * (percent + percent / (Math.pow(1 + percent, period) - 1));
                                    total();
                                    writeOutput();
                                    schedule.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (b) {
                Toast.makeText(this, R.string.wrongPeriod, Toast.LENGTH_SHORT).show();
            } else {
                period = Integer.parseInt(periodTextString);
                if (!month) {
                    period *= 12;
                }
                if (c) {
                    Toast.makeText(this, R.string.wrongPercent, Toast.LENGTH_SHORT).show();
                } else {
                    percent = Double.parseDouble(percentTextString);
                    if (percent > 1200) {
                        Toast.makeText(this, R.string.percentShouldNot, Toast.LENGTH_SHORT).show();
                    } else {
                        percent = percent / 1200;
                        if (d) {
                            Toast.makeText(this, R.string.wrongPayment, Toast.LENGTH_SHORT).show();
                        } else {
                            payment = Double.parseDouble(paymentTextString);
                            amount = payment / (percent + percent / (Math.pow(1 + percent, period) - 1));
                            total();
                            writeOutput();
                            schedule.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    private void total() {
        notes = new ArrayList<>();

        Note n0 = new Note();
        n0.setNumberString(getString(R.string.numberPeyment));
        n0.setDate(getString(R.string.datePayment));
        n0.setAmountString(getString(R.string.payment));
        n0.setMainString(getString(R.string.principalDebt));
        n0.setPercentString(getString(R.string.addedPercent));
        n0.setRemainsString(getString(R.string.remainingDebt));
        notes.add(n0);

        Note np = new Note();
        np.setRemains(amount);

        double mainPayment;
        double percentPayment;
        double remainsPayment;
        totalPayment = 0;
        totalPercent = 0;

        for (int i = 1; i <= period; i++) {
            Note n = new Note();

            remainsPayment = np.getRemains();
            percentPayment = remainsPayment * percent;
            mainPayment = payment - percentPayment;
            remainsPayment -= mainPayment;

            n.setNumber(i);
            n.setAmount(payment);
            n.setMain(mainPayment);
            n.setPercent(percentPayment);
            n.setRemains(remainsPayment);
            n.writeStringParams();
            notes.add(n);
            np = n;
        }

        for (int i = 1; i < notes.size(); i++) {
            Note n = notes.get(i);
            totalPayment += n.getAmount();
            totalPercent += n.getPercent();
        }
    }

    private String incrementDate(String previousDate) {
        String day;
        String month;
        String year;
        int dayInt = Integer.parseInt(pickedDate.substring(0, 2));
        int monthInt = Integer.parseInt(previousDate.substring(3, 5));
        int yearInt = Integer.parseInt(previousDate.substring(6));
        if (monthInt == 12) {
            month = "01";
            year = "" + (yearInt + 1);
        } else if (monthInt < 9) {
            month = "0" + (monthInt + 1);
            year = "" + yearInt;
        } else {
            month = "" + (monthInt + 1);
            year = "" + yearInt;
        }

        if (dayInt < 9) {
            day = "0" + dayInt;
        } else if (dayInt < 28) {
            day = "" + dayInt;
        } else if (monthInt == 1) {
            day = "28";
        } else if ((monthInt == 3 || monthInt == 5 || monthInt == 8 || monthInt == 10 || monthInt == 12) && dayInt == 31) {
            day = "30";
        } else {
            day = "" + dayInt;
        }

        return day + "." + month + "." + year;
    }

    private void writeOutput() {
        TextView resultText = findViewById(R.id.resultText);
        TextView result = findViewById(R.id.result);
        String resultTextString = getString(R.string.sum) + ":\u0020\n" +
                getString(R.string.period) + "\u0020(" + getString(R.string.month) + "):\u0020\n" +
                getString(R.string.percent) + ":\u0020\n" +
                getString(R.string.payment) + ":\u0020\n" +
                getString(R.string.totalPayment) + "\n" +
                getString(R.string.totalPercent);
        resultText.setText(resultTextString);

        @SuppressLint("DefaultLocale")
        String resultString = String.format("%.2f", amount) + "\n" +
                period + "\n" +
                String.format("%.2f", percent * 1200) + "\n" +
                String.format("%.2f", payment) + "\n" +
                String.format("%.2f", totalPayment) + "\n" +
                String.format("%.2f", totalPercent);
        result.setText(resultString);
    }

    public void startSchedule(View v) {
        Note np = new Note();
        np.setDate(pickedDate);
        for (int i = 1; i <= period; i++) {
            Note n = notes.get(i);
            n.setDate(incrementDate(np.getDate()));
            np = n;
        }

        Intent intent = new Intent(MainActivity.this, Schedule.class);
        intent.putExtra("amount", amount);
        intent.putExtra("period", period);
        intent.putExtra("percent", percent * 1200);
        intent.putExtra("payment", payment);
        intent.putExtra("totalPayment", totalPayment);
        intent.putExtra("totalPercent", totalPercent);
        startActivity(intent);
    }
}