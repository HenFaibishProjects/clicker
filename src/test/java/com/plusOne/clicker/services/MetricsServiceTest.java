package com.plusOne.clicker.services;

import com.plusOne.clicker.bigtable.BigTableRepository;
import com.plusOne.clicker.bigtable.MetricsSnapshot;
import com.plusOne.clicker.metrics.MetricKey;
import com.plusOne.clicker.response.MetricsResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MetricsServiceTest {

    @Test
    void readsCorrectKeyAndMapsSnapshotToResponse() {
        BigTableRepository repository = mock(BigTableRepository.class);
        when(repository.get(new MetricKey("campaign-1", "ad-1")))
                .thenReturn(new MetricsSnapshot(10, 4, 2));
        MetricsService service = new MetricsService(repository);

        MetricsResponse response = service.getMetrics("campaign-1", "ad-1");

        assertEquals(new MetricsResponse(10, 4, 2), response);
        verify(repository).get(new MetricKey("campaign-1", "ad-1"));
    }
}
