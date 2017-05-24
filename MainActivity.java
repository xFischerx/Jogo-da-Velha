package com.jogodavelha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;




public class MainActivity extends Activity{

    //CONSTANTE DE CADA BOTAO
    private final String QUADRADO = "quadrado"; //constante que recupera o botão através do metodo getQuadrado
    private final String BOLA = "O"; //constante que vai ser impressa no text do botao
    private final String XIS = "X";//constante que vai ser impressa no text do botao

    private String lastPlay = "X";//constante que guarda o ultimo valor jogado

    private View view;//guarda a instancia da view

    int [][] estadoFinal = new int [][]{ //matriz que define as condições para o jogo acabar

            //verifica as linhas
            {1,2,3}, //condição 1
            {4,5,6},//condição 2
            {7,8,9},//condição 3

            //verifica as colunas
            {1,4,7},//condição 4
            {2,5,8},//condição 5
            {3,6,9},//condição 6

            //verifica as diagonais
            {1,5,9},//condição 7
            {3,5,7},//condição 8

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) { //guarda a instancia de VIEW
        super.onCreate(savedInstanceState);
        setView(getLayoutInflater().inflate(R.layout.activity_main, null));
        setContentView( getView());
    }



    public Button getQuadrado(int tagNum){
        return (Button)getView().findViewWithTag(QUADRADO+tagNum); //retorna o quadrado requerido pela variavel tagNum
    }

    public void newGame(View v){

        setEnableQuadrado(true); ;// ativa os quadrados
        setColorBlack(); //pinta os quadrados de preto

        for(int i=1; i<=9; ++i){ //percorre todos os botões
            if(getQuadrado(i)!=null){ // verifica se o botao é diferente de null
                getQuadrado(i).setText(""); // limpa os campos
            }
        }

        RadioButton rX = (RadioButton)getView().findViewById(R.id.rbX); // retorna instancia do radiobutton rbX e armazena na variavel rx
        RadioButton rO = (RadioButton)getView().findViewById(R.id.rbO); // retorna instancia do radiobutton rbO

        if(rX.isChecked()){ //verifica se rX está checado
            setLastPlay( BOLA ); // altera o lastPlay para o inverso, pois sempre se joga o oposto que está contido no lastPlay
        }else{
            if(rO.isChecked()){
                setLastPlay( XIS ); //indica o XIS como ultimo a jogar para começar com a bola
            }
        }
    }

    public void setEnableQuadrado(boolean b){
        for(int i=1; i<=9; ++i){//percorre todos os itens
            if(getQuadrado(i)!=null){ //verifica se o quadrado é diferente de null
                getQuadrado(i).setEnabled( b ); //passa o valor de b para o quadrado
            }
        }
    }

    public void setColorQuadrados(int btn, int colorX){
        getQuadrado(btn).setTextColor(getResources().getColor(colorX));
        // recebe o numero do botao pela var btn, passa a instancia da cor pela variavel colorX, recupera o botao e é setado o textColor com a cor passada pela variável colorX
    }

    public void setColorBlack(){
        for(int i=0; i<=9; ++i){//percorre todos os itens
            if(getQuadrado(i)!=null){//verifica se o quadrado é diferente de null
                setColorQuadrados(i,R.color.black);// envia cor preta para o metodo serColorQuadrados
            }
        }
    }

    public boolean isFim(){
        for(int x=0; x<=7; ++x){ //percorre todas as condições da matriz

            String s1 = getQuadrado(estadoFinal[x][0]).getText().toString();
            String s2 = getQuadrado(estadoFinal[x][1]).getText().toString();
            String s3 = getQuadrado(estadoFinal[x][2]).getText().toString();

            if ((!s1.equals(""))&& //verifica se s1,s2 e s3 são diferentes de vazio
                    (!s2.equals(""))&&
                    (!s3.equals(""))){

                if(s1.equals(s2)&& s2.equals(s3)){ //if s1==s2 && s1==s3
                    //pinta os botoes
                    setColorQuadrados(estadoFinal[x][0],R.color.red);
                    setColorQuadrados(estadoFinal[x][1],R.color.red);
                    setColorQuadrados(estadoFinal[x][2],R.color.red);
                    Toast.makeText(getView().getContext(),"Fim do jogo!", Toast.LENGTH_LONG).show();
                    // indica que o jogo acabou
                    return true; //verdadeiro
                }
            }
        }

        return false;
    }

    public String getLastPlay() {
        return lastPlay;
    }
    public void setLastPlay(String lastPlay) {
        this.lastPlay = lastPlay;
    }

    public View getView() {

        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void clickQuadrado(View v) {

            if(!isFim()){//verifica se o jogo acabou

                if (((Button)v).getText().equals( "" )){ //verifica se o texto do botao é diferente de vazio
                    if(getLastPlay().equals(XIS)){ //verifica se o ultimo valor jogado é igual a x
                        ((Button)v).setText( BOLA ); //joga bola
                        setLastPlay(BOLA); //seta lastPlay como bola
                    }else{
                        ((Button)v).setText( XIS ); //seta texto do botão como x
                        setLastPlay(XIS);//seta lastPlay como x
                    }

                }else{
                    //imprimi uma mensagem dizendo que já foi jogado
                    Toast.makeText( getView().getContext(),"Escolha outro quadrado, esse já foi selecionado!", Toast.LENGTH_LONG).show();
                }
                isFim(); //verifica se é o fim do jogo
            }
    }
}
