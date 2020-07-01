package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AppletTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applet.class);
        Applet applet1 = new Applet();
        applet1.setId(1L);
        Applet applet2 = new Applet();
        applet2.setId(applet1.getId());
        assertThat(applet1).isEqualTo(applet2);
        applet2.setId(2L);
        assertThat(applet1).isNotEqualTo(applet2);
        applet1.setId(null);
        assertThat(applet1).isNotEqualTo(applet2);
    }
}
