package com.mustafa.queueingproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    LinearLayout DD1Lin, MM1Lin, MMKLin, inputsLin, resultLin;
    EditText λField1, μField1, KField1, MField1, λField2, μField2, λField3, μField3, KField3;
    TextView resultField, systemField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        initViews();
    }

    private void initViews()
    {
        inputsLin = findViewById ( R.id.inputs );
        resultLin = findViewById ( R.id.result );

        resultField = findViewById ( R.id.result_field );
        systemField = findViewById ( R.id.system );

        DD1Lin = findViewById ( R.id.D_D_1 );
        MM1Lin = findViewById ( R.id.M_M_1 );
        MMKLin = findViewById ( R.id.M_M_K );

        λField1 = findViewById ( R.id.λ1 );
        μField1 = findViewById ( R.id.μ1 );
        KField1 = findViewById ( R.id.K1 );
        MField1 = findViewById ( R.id.M1 );

        λField2 = findViewById ( R.id.λ2 );
        μField2 = findViewById ( R.id.μ2 );

        λField3 = findViewById ( R.id.λ3 );
        μField3 = findViewById ( R.id.μ3 );
        KField3 = findViewById ( R.id.K3 );
    }

    public void DD1(View view)
    {
        DD1Lin.setVisibility ( View.VISIBLE );
        MM1Lin.setVisibility ( View.GONE );
        MMKLin.setVisibility ( View.GONE );

        λField1.requestFocus ();

        Button button = (Button) view;
        systemField.setText ( "System : " + button.getText () );
    }

    public void MM1(View view)
    {
        DD1Lin.setVisibility ( View.GONE );
        MM1Lin.setVisibility ( View.VISIBLE );
        MMKLin.setVisibility ( View.GONE );

        λField2.requestFocus ();

        Button button = (Button) view;
        systemField.setText ( "System : " + button.getText () );
    }

    public void MMK(View view)
    {
        DD1Lin.setVisibility ( View.GONE );
        MM1Lin.setVisibility ( View.GONE );
        MMKLin.setVisibility ( View.VISIBLE );

        λField3.requestFocus ();

        Button button = (Button) view;
        systemField.setText ( "System : " + button.getText () );
    }

    public void resultDD1(View view)
    {
        if(λField1.getText ().toString ().isEmpty ())
        {
            λField1.requestFocus ();
            Toast.makeText ( this, "enter λ", Toast.LENGTH_SHORT ).show ();
            return;
        }

        if(μField1.getText ().toString ().isEmpty ())
        {
            μField1.requestFocus ();
            Toast.makeText ( this, "enter μ", Toast.LENGTH_SHORT ).show ();
            return;
        }

        if(KField1.getText ().toString ().isEmpty ())
        {
            KField1.requestFocus ();
            Toast.makeText ( this, "enter K", Toast.LENGTH_SHORT ).show ();
            return;
        }

        double λ = 1/Double.parseDouble ( λField1.getText ().toString () );
        double μ = 1/Double.parseDouble ( μField1.getText ().toString () );
        int K = 1+Integer.parseInt ( KField1.getText ().toString () );
        String m = MField1.getText ().toString ();

        if(λ < μ || λ == μ)
        {
            if(m.isEmpty ())
            {
                MField1.setVisibility ( View.VISIBLE );
                Toast.makeText ( this, "enter M", Toast.LENGTH_SHORT ).show ();
                MField1.requestFocus ();
                return;
            }else
                {
                    int M = Integer.parseInt ( m );
                    int ti = 0;

                    if(λ == μ)
                    {
                        resultField.setText (
                                "ti= " + ti + '\n' +
                                "n(t)= " + M + "\n\n" +

                                "Wq(n)= " + (M-1)*(1/μ) );
                    }else
                        {
                            for(int i = 0 ; i > -1 ; i ++)
                            {
                                if(M == Math.floor ( i / (1/μ) ) - Math.floor ( i / (1/λ) ) )
                                {
                                    ti = i;
                                    break;
                                }
                            }

                            resultField.setText (
                                    "ti= " + ti + '\n' +
                                    "n(t)= " + M + "|λt|-|μt|   at t<" + ti + '\n' +
                                    "n(t)= 0 or 1   at t=>" + ti + "\n\n" +

                                    "Wq(n)= " + Math.floor ( (M-1)/(2*μ) ) + "   at n=0" + '\n' +
                                    "Wq(n)= (M-1+n)(1/μ)–n(1/λ)   at n<" + (int)(λ*ti) + '\n' +
                                    "Wq(n)= 0   at n=>" + (int)(λ*ti));
                        }
                    result ();
                }

        }else if(λ > μ)
        {
            int ti = 0;

            for(int i = 0 ; i > -1 ; i ++)
            {
                if(K == Math.floor ( i / (1/λ) ) - Math.floor ( i / (1/μ) - ((1/λ) / (1/μ)) ) )
                {
                    ti = i;
                    break;
                }
            }

            if(λ % μ == 0)
            {
                resultField.setText (
                        "ti= "+ ti + '\n' +
                        "n(t)=0   at t<" + 1/λ + '\n' +
                        "n(t)=|λt|-|μt-μ/λ|   at " + λ + "<t<" + ti + '\n' +
                        "n(t)=" + (K - 1) + "   at t=>" + ti + "\n\n" +
                        "Wq(n)= 0   at n=0" + '\n' +
                        "Wq(n)=" + (1/μ - 1/λ) + "(n-1)   at n<" + (int)(λ * ti) + '\n' +
                        "Wq(n)=" + (int)(1/μ - 1/λ)*(λ*ti-2) +"   at n=>" + (int)(λ * ti));
            }else
                {
                    resultField.setText(
                            "ti= "+ ti + '\n' +
                            "n(t)= 0   at t<" + 1/λ + '\n' +
                            "n(t)= |λti|-|μti-μ/λ|   at " + 1/λ + "<t<" + ti + '\n' +
                            "n(t)= " + (K - 1) + " or " + (K - 2) + "   at t=>" + ti + "\n\n" +

                            "Wq(n)= " + (int)(1/μ- 1/λ) + "(n-1)    at n<" + (int)(λ * ti) + '\n' +
                            "Wq(n)= " + (int)(1/μ - 1/λ)*(λ*ti-2) + " or " + (int)(1/μ - 1/λ)*(λ*ti-3) + "   at n=>" + (int)(λ * ti));
                }
            result ();

        }

    }

    public void resultMM1(View view)
    {
        if(λField2.getText ().toString ().isEmpty ())
        {
            λField2.requestFocus ();
            Toast.makeText ( this, "enter λ", Toast.LENGTH_SHORT ).show ();
            return;
        }

        if(μField2.getText ().toString ().isEmpty ())
        {
            μField2.requestFocus ();
            Toast.makeText ( this, "enter μ", Toast.LENGTH_SHORT ).show ();
            return;
        }

        double λ = Double.parseDouble ( λField2.getText ().toString () );
        double μ = Double.parseDouble ( μField2.getText ().toString () );

        double L = λ / ( μ - λ );
        double Lq = ( λ * λ ) / ( μ * ( μ - λ ) );
        double W = L / λ;
        double Wq = Lq / λ;

        resultField.setText (
                "L= " + L + '\n' +
                "Lq= " + Lq + '\n' +
                "W= " + W + '\n' +
                "Wq= " + Wq);

        result ();
    }

    public void resultMMK(View view)
    {
        if(λField3.getText ().toString ().isEmpty ())
        {
            λField3.requestFocus ();
            Toast.makeText ( this, "enter λ", Toast.LENGTH_SHORT ).show ();
            return;
        }

        if(μField3.getText ().toString ().isEmpty ())
        {
            μField3.requestFocus ();
            Toast.makeText ( this, "enter μ", Toast.LENGTH_SHORT ).show ();
            return;
        }

        if(KField3.getText ().toString ().isEmpty ())
        {
            KField3.requestFocus ();
            Toast.makeText ( this, "enter K", Toast.LENGTH_SHORT ).show ();
            return;
        }

        double λ = Double.parseDouble ( λField3.getText ().toString () );
        double μ = Double.parseDouble ( μField3.getText ().toString () );
        int K = Integer.parseInt ( KField3.getText ().toString () );

        double P = λ / μ;
        double Pk;
        double L;

        if(P != 1)
        {
            Pk = Math.pow ( P,K ) * ( ( 1 - P ) / ( 1 - Math.pow ( P,K+1 )));
            L = P * ( ( 1 - ( ( K + 1) * Math.pow ( P,K ) ) + (K * Math.pow ( P,K+1 )) ) / (( 1 - P ) * ( 1 - Math.pow ( P,K+1 ))));
        }else
            {
                Pk = 1 / ( K + 1 );
                L = K / 2;
            }

        double W = L / ( λ * ( 1 - Pk ) );
        double Wq = W - ( 1 / μ );
        double Lq = λ * ( 1 - Pk ) * Wq;

        resultField.setText (
                "L= " + L + '\n' +
                "Lq= " + Lq + '\n' +
                "W= " + W + '\n' +
                "Wq= " + Wq);

        result ();
    }

    public void result()
    {
        inputsLin.setVisibility ( View.GONE );
        resultLin.setVisibility ( View.VISIBLE );

        λField1.setText ( "" );
        λField2.setText ( "" );
        λField3.setText ( "" );

        μField1.setText ( "" );
        μField2.setText ( "" );
        μField3.setText ( "" );

        KField1.setText ( "" );
        KField3.setText ( "" );

        MField1.setText ( "" );
    }

    public void finish(View view)
    {
        inputsLin.setVisibility ( View.VISIBLE );
        resultLin.setVisibility ( View.GONE );

        systemField.setText ( "choose the system" );

        DD1Lin.setVisibility ( View.GONE );
        MM1Lin.setVisibility ( View.GONE );
        MMKLin.setVisibility ( View.GONE );
    }
}