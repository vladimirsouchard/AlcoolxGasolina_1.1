package com.souchard.alcoolxgasolina;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.souchard.alcoolxgasolina.dao.CriarBanco;
import com.souchard.alcoolxgasolina.dao.Historico;
import java.util.ArrayList;

/**
 * Created by Vladimir on 12/11/2016.
 * Activity para criar a lista de histórico do BD
 */

public class HistoricoActivity extends AppCompatActivity {
    private CriarBanco bd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = new CriarBanco(this);
        ArrayList<Historico> listaHistorico;
        setContentView(R.layout.historico);
        ListView lista = (ListView) findViewById(R.id.lvHistorico);
        listaHistorico = bd.getAllHistorico();
        HistoricoAdapter adapter = new HistoricoAdapter(this, listaHistorico);
        lista.setAdapter(adapter);
    }
}
