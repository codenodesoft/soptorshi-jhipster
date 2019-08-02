package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ItemCategorySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ItemCategorySearchRepositoryMockConfiguration {

    @MockBean
    private ItemCategorySearchRepository mockItemCategorySearchRepository;

}
