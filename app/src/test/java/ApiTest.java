import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import domain.services.Endpoints;
import domain.services.Persistence;
import domain.services.Session;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ApiTest {
    @Mock Endpoints endpoints;
    @Mock Persistence persistence;
    @Mock Context context;
    @Captor private ArgumentCaptor<Session.Callback<String>> dummyCallbackArgumentCaptor;

    private Session session;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        session = new Session(endpoints, persistence);
    }

    @Test public void testLogin()  {
        Mockito.verify(session).login(context, Mockito.anyString(),
                Mockito.anyString(), dummyCallbackArgumentCaptor.capture());


        dummyCallbackArgumentCaptor.getValue().response(true, null);
        assertThat("Session should be active", session.isActive(context));
    }
}

