package com.elkanah.gads;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.elkanah.gads.ui.main.PageViewModel;

import java.util.Objects;

public class SubmitActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnImage;
    private ConstraintLayout constraintLayout;
    private Button btnSend;
    private TextView tvFirstName, tvLastName, tvEMail, tvLink;
    private PageViewModel pageViewModel;
    private AlertDialog bu;
    private Button tvYes;
    private ProgressBar progressBar;
    private AlertDialog dialog;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        init();
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setAlpha(1f);
            }
        });
    }

    private void init() {
        initiateFeilds();
        observer();
    }

    private void observer() {
        pageViewModel.isDecision.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if (!TextUtils.isEmpty(s)) {
                        if (s.equals("OK")) {
                            progressBar.setVisibility(View.INVISIBLE);
                            openDecisionDailog(1);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            openDecisionDailog(2);
                        }
                    }
                }
            }
        });

        pageViewModel.validate.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if (!TextUtils.isEmpty(s)) {
                        if (s.equals("f")) {
                            bu.dismiss();
                            Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_SHORT).show();
                        } else if (s.equals("su")) {
                            progressBar.setVisibility(View.VISIBLE);
                            bu.dismiss();
                        }
                    }
                }
            }
        });
    }

    private void openDecisionDailog(int value) {
        AlertDialog.Builder buid = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Widget_AppCompat_Light_ActionBar));
        LayoutInflater inflater = getLayoutInflater();
        View custom = inflater.inflate(R.layout.success_page, null);
        ImageView fromDecisionImage = custom.findViewById(R.id.imageView);
        TextView tvDecisionMessage = custom.findViewById(R.id.decision_msg_can);
        if (value == 1) {
            fromDecisionImage.setImageResource(R.drawable.ic_check);
            tvDecisionMessage.setText(R.string.success);
        } else if (value == 2) {
            fromDecisionImage.setImageResource(R.drawable.ic_warning);
            tvDecisionMessage.setText(R.string.failure);
        }
        buid.setView(custom);
        dialog = buid.create();
        dialog.show();
        constraintLayout.setAlpha(0.5f);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                constraintLayout.setAlpha(1f);
            }
        });
    }

    private void initiateFeilds() {
        btnImage = findViewById(R.id.title_submit);
        constraintLayout = findViewById(R.id.parent_layout);
        btnImage.setOnClickListener(this);
        btnSend = findViewById(R.id.button2);
        btnSend.setOnClickListener(this);
        tvFirstName = findViewById(R.id.firstName);
        tvLastName = findViewById(R.id.last_name);
        tvEMail = findViewById(R.id.email_id);
        tvLink = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                if (isConnectionActive()) {
                    validateInputFields();
                } else {
                    Toast.makeText(this, getString(R.string.connection), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageView:
                gotoCancelDailog();
                break;
            case R.id.button3:
                proceedWithData();
                break;
        }
    }

    private void proceedWithData() {
        if (tvFirstName.getText() != null && tvLastName.getText() != null && tvEMail.getText() != null && tvLink.getText() != null) {
            pageViewModel.validateInput(tvFirstName.getText(), tvLastName.getText(), tvEMail.getText(), tvLink.getText());
        }
    }

    private void gotoCancelDailog() {
        if (bu != null) {
            bu.dismiss();
        }
    }

    private void validateInputFields() {
        AlertDialog.Builder buid = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Widget_AppCompat_Light_ActionBar));
        LayoutInflater inflater = getLayoutInflater();
        View custom = inflater.inflate(R.layout.validate_click, null);
        //constraintLayout.setAlpha(0.5f);
        tvYes = custom.findViewById(R.id.button3);
        ImageView imgCancel = custom.findViewById(R.id.imageView_can);

        tvYes.setOnClickListener(this);
        buid.setView(custom);
        bu = buid.create();
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bu.dismiss();
            }
        });
        bu.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isConnectionActive() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}