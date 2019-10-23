package com.entigrity.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.entigrity.R;
import com.entigrity.databinding.ActivityStripeBinding;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

public class StripePaymentActivity extends AppCompatActivity {
    ActivityStripeBinding binding;
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stripe);
        context = StripePaymentActivity.this;

        final Card card = new Card("4242424242424242", 12, 2020, "123");
// Remember to validate the card object before you use it to save time.
        if (!card.validateCard()) {
            // Do not continue token creation.
        }
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stripe stripe = new Stripe(context, "pk_test_TYooMQauvdEDq54NiTphI7jx");
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                // Send token to your server
                                Toast.makeText(context,
                                        token.getId(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                            public void onError(Exception error) {
                                // Show localized error message
                                Toast.makeText(context,
                                        error.getLocalizedMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );

            }
        });


    }
}
