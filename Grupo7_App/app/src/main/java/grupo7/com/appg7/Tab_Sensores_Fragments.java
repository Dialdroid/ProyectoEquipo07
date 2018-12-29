    package grupo7.com.appg7;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import grupo7.com.appg7.R;


    public class Tab_Sensores_Fragments extends Fragment {



        public static Tab_Sensores_Fragments newInstance() {
            Tab_Sensores_Fragments fragment = new Tab_Sensores_Fragments();
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_tab_sensores, container, false);



            Button altura = (Button) v.findViewById(R.id.Altura);
            altura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = "IF1eN0WK3bU";
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + id));
                        try {
                            getContext().startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            getContext().startActivity(webIntent);
                    }
                }
            });


            Button gas = (Button) v.findViewById(R.id.Gas);
            gas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = "PrxSu2Dr5FQ";
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + id));
                    try {

                        getContext().startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        getContext().startActivity(webIntent);
                    }
                }
            });

            Button movimiento = (Button) v.findViewById(R.id.Movimiento);
            movimiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = "JF1yVCdyebA";
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + id));
                    try {

                        getContext().startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        getContext().startActivity(webIntent);
                    }
                }
            });
            return v;

        }
    }
