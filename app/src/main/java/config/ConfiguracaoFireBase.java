package config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFireBase {

    //Static pois ao instanciar essa class nao quero que o valor mude
    private static FirebaseAuth autenticacao;

    //Retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao==null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;

        }
}
