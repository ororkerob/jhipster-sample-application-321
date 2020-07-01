package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class OsGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OsGroup.class);
        OsGroup osGroup1 = new OsGroup();
        osGroup1.setId(1L);
        OsGroup osGroup2 = new OsGroup();
        osGroup2.setId(osGroup1.getId());
        assertThat(osGroup1).isEqualTo(osGroup2);
        osGroup2.setId(2L);
        assertThat(osGroup1).isNotEqualTo(osGroup2);
        osGroup1.setId(null);
        assertThat(osGroup1).isNotEqualTo(osGroup2);
    }
}
