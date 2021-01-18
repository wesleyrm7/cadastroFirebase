package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baseapp2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import config.ConfiguracaoFireBase;
import model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText Textemail,TextSenha;
    private Button buttonLogin;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);

        Textemail=findViewById(R.id.editEmailL);
        TextSenha=findViewById(R.id.editSenhaL);
        buttonLogin=findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Textemail.getText().toString();
                String senha = TextSenha.getText().toString();

                //Varificao se os campos estao preenchidos

                if ( !email.isEmpty() ){
                    if ( !senha.isEmpty() ){

                        //Obejto usuario configurado
                        usuario=new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);

                        validarUsuario();

                    }else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void validarUsuario(){

        //utilizar o metodo static la do usuario que ja esta pronto
        autenticacao= ConfiguracaoFireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(LoginActivity.this,
                            "sucesso ao fazer Login",
                            Toast.LENGTH_SHORT).show();

                }else{
                    //Trata as excessoes
                    String excecao = "";
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuario nao Cadastrado ";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e Senha nao corresponde, e um cadastro";
                    }catch (Exception e){
                        excecao = "Erro verifique sua conexao com internet "  + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(LoginActivity.this,
                             excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) ;
    }
   /* public void abrirTelaPrincipal(){
        startActivity(new Intent(this,VisualUsuarioActivity.class));
        finish();//fecha essa activity de login
    }*/
}