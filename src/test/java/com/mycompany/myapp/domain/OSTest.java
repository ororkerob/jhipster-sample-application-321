package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class OSTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OS.class);
        OS oS1 = new OS();
        oS1.setId(1L);
        OS oS2 = new OS();
        oS2.setId(oS1.getId());
        assertThat(oS1).isEqualTo(oS2);
        oS2.setId(2L);
        assertThat(oS1).isNotEqualTo(oS2);
        oS1.setId(null);
        assertThat(oS1).isNotEqualTo(oS2);
    }
}
