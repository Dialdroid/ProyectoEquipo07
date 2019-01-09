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


            return v;

        }
    }
