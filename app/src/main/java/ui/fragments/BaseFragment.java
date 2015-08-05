package ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import domain.services.DaggerServiceComponent;
import domain.services.ServiceComponent;
import domain.services.Session;

public abstract class BaseFragment extends Fragment {
    @Inject Session mSession;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGraph();
    }

    private void setUpGraph() {
        ServiceComponent component = DaggerServiceComponent.create();
        component.injectClient(this);
    }
}
