package edu.scu.qjiang.mortgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText rate = (EditText)findViewById(R.id.interestText);
        rate.setText("5.0");
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message;
                        float amountVal, rateVal, mortgage;
                        float taxVal;
                        int termVal = 0;

                        EditText rate = (EditText)findViewById(R.id.interestText);
                        EditText amount = (EditText)findViewById(R.id.amountText);
                        RadioGroup term = (RadioGroup)findViewById(R.id.radioGroup);
                        CheckBox tax = (CheckBox)findViewById(R.id.checkBox);
                        TextView payment = (TextView)findViewById(R.id.paymentView);

                        String a = amount.getText().toString();
                        String b = rate.getText().toString();
                        Boolean checkedTax = tax.isChecked();
                        int selectedId = term.getCheckedRadioButtonId();

                        if (a.length() == 0) {
                            message = "Please enter the amount you want to borrow";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            payment.setText("");
                        }
                        else if (Float.parseFloat(a) < 0.0) {
                            message = "Please enter a positive number of amount";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            payment.setText("");
                        }
                        else if (b.length() == 0) {
                            message = "Please enter the interest rate";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            payment.setText("");
                        }
                        else if (Float.parseFloat(b) < 0.0 || Float.parseFloat(b) > 10.0) {
                            message = "InterestRate is out of boundary";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            payment.setText("");
                        }
                        else if (selectedId == -1) {
                            message = "Please select a loan term";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            payment.setText("");
                        }

                        else {
                            amountVal = Float.parseFloat(a);
                            rateVal = Float.parseFloat(b) / 1200;
                            termVal = Integer.parseInt(((RadioButton) findViewById(selectedId)).getText().toString()) * 12;

                            if (checkedTax == true)
                                taxVal = amountVal * 0.001f;
                            else
                                taxVal = 0.0f;

                            if (rateVal == 0.0f)
                                mortgage = (amountVal / termVal) + taxVal;
                            else
                                mortgage = (float)(amountVal * (rateVal / (1 - Math.pow(1 + rateVal, -termVal)))) + taxVal;

                            payment.setText(Float.toString(mortgage));

                        }
                    }
                }
        );

    }
}
