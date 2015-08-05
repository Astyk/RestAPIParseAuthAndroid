package ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import domain.services.DaggerServiceComponent;
import domain.services.ServiceComponent;
import domain.services.Session;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject Session mSession;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGraph();
    }

    private void setUpGraph() {
        ServiceComponent component = DaggerServiceComponent.create();
        component.injectClient(this);
    }
}
