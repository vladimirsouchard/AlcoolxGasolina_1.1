package com.souchard.alcoolxgasolina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.souchard.alcoolxgasolina.dao.Historico;

import java.util.ArrayList;

/**
 * Created by Vladimir on 05/11/2016.
 */

public class HistoricoAdapter extends ArrayAdapter<Historico> {

    private final Context context;
    private final ArrayList<Historico> elemento;

    public HistoricoAdapter(Context context, ArrayList<Historico> elemento){
        super(context, R.layout.linha, elemento);
        this.context = context;
        this.elemento = elemento;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.linha, parent, false);

        TextView data = (TextView) rowView.findViewById(R.id.txtdata);
        TextView alcool = (TextView) rowView.findViewById(R.id.txtAlcool);
        TextView gasolina = (TextView) rowView.findViewById(R.id.txtGasolina);

        data.setText(elemento.get(position).getData());
        alcool.setText(elemento.get(position).getAlcool());
        gasolina.setText(elemento.get(position).getGasolina());

        return rowView;
    }
}
