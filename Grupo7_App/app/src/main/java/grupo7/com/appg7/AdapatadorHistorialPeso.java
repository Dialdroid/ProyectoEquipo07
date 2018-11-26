package grupo7.com.appg7;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdapatadorHistorialPeso
        extends RecyclerView.Adapter<AdapatadorHistorialPeso.HistorialHolder> {
    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

    ArrayList<HistorialVo> listaHistorial;

    public AdapatadorHistorialPeso(ArrayList<HistorialVo> listaHistorial){
        this.listaHistorial=listaHistorial;
    }

    @NonNull
    @Override
    public HistorialHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_historial_datos,null, false);
        return new HistorialHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialHolder historialHolder, int i) {
        historialHolder.etiPeso.setText(listaHistorial.get(i).getPeso());
        //historialHolder.etiInfo.setText(listaHistorial.get(i).getInfo());
        //historialHolder.foto.setImageResource(listaHistorial.get(i).getFoto());

    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }

    public class HistorialHolder extends RecyclerView.ViewHolder {

        TextView etiPeso, etiInfo;
        ImageView foto;

        public HistorialHolder(@NonNull View itemView) {
            super(itemView);
            etiPeso = (TextView) itemView.findViewById(R.id.idPeso);
            etiInfo = (TextView) itemView.findViewById(R.id.idInfo);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
