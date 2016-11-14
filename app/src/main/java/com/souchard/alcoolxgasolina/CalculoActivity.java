package com.souchard.alcoolxgasolina;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonCalc;
    Button buttonLimpar;
    TextView textViewResultado;
    EditText editTextKilometragem;
    EditText editTextLitros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);

        editTextKilometragem = (EditText) findViewById(R.id.editTextKilometragem);
        editTextLitros = (EditText) findViewById(R.id.editTextLitros);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);
        textViewResultado = (TextView) findViewById(R.id.textViewResultado);
        buttonLimpar = (Button) findViewById(R.id.buttonLimpar);

        buttonCalc.setOnClickListener(this);
        buttonLimpar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCalc:
                if (editTextKilometragem.getText().toString().equals("") && editTextLitros.getText().toString().equals("")) {
                    AlertDialog.Builder dialogo = new
                            AlertDialog.Builder(CalculoActivity.this);
                    dialogo.setTitle("ALERTA");
                    dialogo.setMessage("Informe a distância percorrida e os litros gastos.");
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();
                    editTextKilometragem.requestFocus();
                } else if (editTextKilometragem.getText().toString().equals("")){
                    AlertDialog.Builder dialogo = new
                            AlertDialog.Builder(CalculoActivity.this);
                    dialogo.setTitle("ALERTA");
                    dialogo.setMessage("Informe a distância percorrida.");
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();
                    editTextKilometragem.requestFocus();
                } else if (editTextLitros.getText().toString().equals("")) {
                    AlertDialog.Builder dialogo = new
                            AlertDialog.Builder(CalculoActivity.this);
                    dialogo.setTitle("ALERTA");
                    dialogo.setMessage("Informe a quantidade de litros de combustível gasto.");
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();
                    editTextLitros.requestFocus();
                }else {
                    double VKilometragem = Double.parseDouble(editTextKilometragem.getText().toString());
                    double QLitros = Double.parseDouble(editTextLitros.getText().toString());
                    double calculo = VKilometragem / QLitros;
                    //Ocultando o teclado
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextKilometragem.getWindowToken(), 0);
                    // Double resultado = calculo;
                    BigDecimal calculo_extato = new BigDecimal(calculo).setScale(2, RoundingMode.HALF_DOWN);
                    BigDecimal Kilometragem = new BigDecimal(VKilometragem).setScale(0, RoundingMode.HALF_DOWN);
                    BigDecimal Litros = new BigDecimal(QLitros).setScale(0, RoundingMode.HALF_DOWN);
                    textViewResultado.setText("Com " + Litros + " litros de combustível \n seu carro fez " + Kilometragem + "km eo \n consumo médio foi de " + calculo_extato + " km/l.");
                    editTextKilometragem.setText("");
                    editTextLitros.setText("");
                    editTextKilometragem.requestFocus();
                }
                break;
            case R.id.buttonLimpar:
                editTextKilometragem.setText("");
                editTextLitros.setText("");
                textViewResultado.setText("");
                editTextKilometragem.requestFocus();
                break;

    }
    }
}
