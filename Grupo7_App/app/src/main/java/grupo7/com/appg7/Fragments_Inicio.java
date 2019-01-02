    package grupo7.com.appg7;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


    public class Fragments_Inicio extends Fragment {

        private AppBarLayout appBar;
        private TabLayout tabs;
        private ViewPager viewPager;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_inicio, container, false);
            //agregado menú tabs
            BottomNavigationView bottomNavigationView = (BottomNavigationView)
                    view.findViewById(R.id.navigation);

            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.action_item1:
                                    selectedFragment = PrincipalFragment.newInstance();
                                    break;
                                case R.id.action_item2:
                                    selectedFragment = Tab_Historial_Fragments.newInstance();
                                    break;
                                case R.id.action_item3:
                                    selectedFragment = Tab_Sensores_Fragments.newInstance();
                                    break;

                            }
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.commit();

                            return true;
                        }


                    });
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, PrincipalFragment.newInstance());
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            transaction.commit();

            return view;
        }




        }


