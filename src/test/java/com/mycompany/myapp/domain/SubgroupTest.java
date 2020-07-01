package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SubgroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subgroup.class);
        Subgroup subgroup1 = new Subgroup();
        subgroup1.setId(1L);
        Subgroup subgroup2 = new Subgroup();
        subgroup2.setId(subgroup1.getId());
        assertThat(subgroup1).isEqualTo(subgroup2);
        subgroup2.setId(2L);
        assertThat(subgroup1).isNotEqualTo(subgroup2);
        subgroup1.setId(null);
        assertThat(subgroup1).isNotEqualTo(subgroup2);
    }
}
