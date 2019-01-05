    package grupo7.com.appg7;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import grupo7.com.appg7.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


    public class Tab_Sensores_Fragments extends Fragment {

        public void onButtonShowPopupWindowClick(View view, int stat1) {

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.pop_up, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            //TextView altura = (TextView) popupView.findViewById(R.id.altura);
            //altura.setText(stat1);
            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
        }

        //-----------------------------------------------//



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
            View v = inflater.inflate(R.layout.fragment_estadisticas, container, false);
            ImageButton comedor = (ImageButton) v.findViewById(R.id.comedor);
            comedor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stat1 = 18;
                    onButtonShowPopupWindowClick(v, stat1);
                }


            });


            return v;

        }
    }
