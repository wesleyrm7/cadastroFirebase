package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.baseapp2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


import config.ConfiguracaoFireBase;
import model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private Button butCadastro;
    private EditText Textemail,Textnome,TextSenha;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Textnome=findViewById(R.id.editNome);
        Textemail=findViewById(R.id.editEmail);
        TextSenha=findViewById(R.id.editSenha);
        butCadastro=findViewById(R.id.buttonCadastrar);


        butCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperando Valores
                String textoNome = Textnome.getText().toString();
                String textoEmail = Textemail.getText().toString();
                String textoSenha = TextSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if ( !textoNome.isEmpty() ){
                    if ( !textoEmail.isEmpty() ){
                        if ( !textoSenha.isEmpty() ){

                            usuario = new Usuario();
                            usuario.setNome( textoNome );
                            usuario.setEmail( textoEmail );
                            usuario.setSenha( textoSenha );
                            cadastrarUsuario();

                        }else {
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFireBase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    finish();

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro verifique sua conexao com internet "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}