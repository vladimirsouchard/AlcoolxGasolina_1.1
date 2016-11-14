package com.souchard.alcoolxgasolina;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.souchard.alcoolxgasolina.dao.CriarBanco;
import com.souchard.alcoolxgasolina.dao.Historico;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.souchard.alcoolxgasolina.R.layout.activity_main;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCalcular;
    EditText EditTextAlcool;
    EditText EditTextGasolina;
    TextView TextViewresultadoPositivo;
    TextView TextViewresultadoNegativo;
    Button buttonLimpar;
    Button buttonHistorico;
    Button buttonCalcConsumo;
    int check = 0;
    private CriarBanco bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = new CriarBanco(this);
        setContentView(activity_main);

        EditTextAlcool = (EditText) findViewById(R.id.EditTextAlcool);
        //Colocando a mascara para forçar somente 3 digitos depois do ponto
        EditTextAlcool.addTextChangedListener(Mask.insert("#.###", EditTextAlcool));
        EditTextGasolina = (EditText) findViewById(R.id.EditTextGasolina);
        //Colocando a mascara para forçar somente 3 digitos depois do ponto
        EditTextGasolina.addTextChangedListener(Mask.insert("#.###", EditTextGasolina));
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        buttonLimpar = (Button) findViewById(R.id.buttonLimpar);
        buttonHistorico = (Button) findViewById(R.id.buttonHistorico);
        buttonCalcConsumo = (Button) findViewById(R.id.buttonCalcConsumo);
        // TextViewresultado = (TextView) findViewById(R.id.resultado);
        TextViewresultadoPositivo = (TextView) findViewById(R.id.resultadoPositivo);
        TextViewresultadoNegativo = (TextView) findViewById(R.id.resultadoNegativo);

        btnCalcular.setOnClickListener(this);
        buttonLimpar.setOnClickListener(this);
        buttonHistorico.setOnClickListener(this);
        buttonCalcConsumo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        bd = new CriarBanco(this);
        Historico historico = new Historico();
        switch (view.getId()) {
            case R.id.btnCalcular:
                //If validando se os campos álcool e gasolina possuem valores
                if (EditTextAlcool.getText().toString().equals("") && EditTextGasolina.getText().toString().equals("")) {
                    AlertDialog.Builder dialogo = new
                            AlertDialog.Builder(MainActivity.this);
                    dialogo.setTitle("ALERTA");
                    dialogo.setMessage("Informe os valores da gasolina e do álcool");
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();
                    //If validando se o campo álcool possui valor
                } else if (EditTextAlcool.getText().toString().equals("")){
                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("ALERTA");
                dialogo.setMessage("Informe o valor do álcool.");
                dialogo.setNeutralButton("OK", null);
                dialogo.show();
                    //If validando se o campo gasolina possui valor
                 } else if (EditTextGasolina.getText().toString().equals("")) {
                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("ALERTA");
                dialogo.setMessage("Informe o valor da gasolina.");
                dialogo.setNeutralButton("OK", null);
                dialogo.show();
            }else {

                    //Obtendo os valores para o cálculo da porcentagem no resultado
                    double VAlcool = Double.parseDouble(EditTextAlcool.getText().toString());
                    double VGasolina = Double.parseDouble(EditTextGasolina.getText().toString());
                    //Cálculo da porcentagem
                    double calculo = VAlcool / VGasolina;
                    //Obtendo os valores para o cálculo do abastecimento
                    final EditText alcool = (EditText) findViewById(R.id.EditTextAlcool);
                    final EditText gasolina = (EditText) findViewById(R.id.EditTextGasolina);
                    //Enviando os dados para o BD
                    historico.setAlcool(alcool.getText().toString());
                    historico.setGasolina(gasolina.getText().toString());
                    //Chamada do BD
                    bd.addHistorico(historico);
                    //Ocultando o teclado, após o botão cálculo
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(EditTextGasolina.getWindowToken(), 0);
                    //Mensagem de cadastro com sucesso no BD do Android
                    Toast.makeText(getBaseContext(), "Cadastro com sucesso", Toast.LENGTH_SHORT).show();
                    {
                        if (calculo <= 0.70) {
                            //Calculo da porcentagem para o álcool
                            Double porcentagem = calculo * 100;
                            BigDecimal porcentagem_exata = new BigDecimal(porcentagem).setScale(2, RoundingMode.HALF_DOWN);
                            TextViewresultadoPositivo.setText("Neste momento o melhor é o ÁLCOOL, pois o valor está " + porcentagem_exata +  "% do valor da gasolina.");
                            EditTextAlcool.setText("");
                            EditTextGasolina.setText("");
                            TextViewresultadoNegativo.setText("");
                            EditTextAlcool.requestFocus();
                        } else {
                            //Calculo da porcentagem para a gasolina
                            Double porcentagem = calculo * 100;
                            BigDecimal porcentagem_exata = new BigDecimal(porcentagem).setScale(2, RoundingMode.HALF_DOWN);
                            TextViewresultadoNegativo.setText("Neste momento melhor é GASOLINA, pois o valor está " + porcentagem_exata + "% do valor do álcool.");
                            EditTextAlcool.setText("");
                            EditTextGasolina.setText("");
                            TextViewresultadoPositivo.setText("");
                            EditTextAlcool.requestFocus();
                        }
                    }
                }
                break;
            case R.id.buttonHistorico:
                //Chamada para a activity histórico
                Intent intent = new Intent(MainActivity.this, HistoricoActivity.class);
                startActivity(intent);
                break;
                //Chamada para o botão limpar (TextView,EditText)
            case R.id.buttonLimpar:
                EditTextAlcool.setText("");
                EditTextGasolina.setText("");
                TextViewresultadoPositivo.setText("");
                TextViewresultadoNegativo.setText("");
                EditTextAlcool.requestFocus();
                break;

            case R.id.buttonCalcConsumo:
                //Chamada para a activity Cálculo
                Intent intentCalc = new Intent(MainActivity.this, CalculoActivity.class);
                startActivity(intentCalc);
                break;
        }
    }
}