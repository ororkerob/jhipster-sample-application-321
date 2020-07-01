package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class OsSubgroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OsSubgroup.class);
        OsSubgroup osSubgroup1 = new OsSubgroup();
        osSubgroup1.setId(1L);
        OsSubgroup osSubgroup2 = new OsSubgroup();
        osSubgroup2.setId(osSubgroup1.getId());
        assertThat(osSubgroup1).isEqualTo(osSubgroup2);
        osSubgroup2.setId(2L);
        assertThat(osSubgroup1).isNotEqualTo(osSubgroup2);
        osSubgroup1.setId(null);
        assertThat(osSubgroup1).isNotEqualTo(osSubgroup2);
    }
}
