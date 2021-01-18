package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.baseapp2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import java.lang.ref.Reference;

public class MainActivity extends IntroActivity  {


    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main); //Vai ser substituidos pelo addSlide

        //Fragments Slide
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()


                .background(R.color.CorFundoApp)
                .fragment(R.layout.slide_11)
               // .canGoForward(false) //nao pode ir adiante
                .build()
            );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.CorFundoApp)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false) //nao pode ir adiante
                .build()
            );

    }
        public void btEntrar(View view){ //Ao clicar vai ser redirecionado para as devidas Activitys
                startActivity(new Intent(this,LoginActivity.class));
        }
        public void btCadastrar(View view){
            startActivity(new Intent(this,CadastroActivity.class));

        }


    }
