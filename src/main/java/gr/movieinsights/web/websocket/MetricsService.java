package gr.movieinsights.web.websocket;

import io.github.jhipster.config.metric.JHipsterMetricsEndpoint;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.management.ThreadDumpEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class MetricsService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final JHipsterMetricsEndpoint metricsService;
    private final ThreadDumpEndpoint threadDumpEndpoint;
    private final HealthEndpoint healthEndpoint;
    public MetricsService(SimpMessageSendingOperations messagingTemplate, JHipsterMetricsEndpoint metricsService, ThreadDumpEndpoint threadDumpEndpoint, HealthEndpoint healthEndpoint, MetricsEndpoint metricsEndpoint) {
        this.messagingTemplate = messagingTemplate;
        this.metricsService = metricsService;
        this.threadDumpEndpoint = threadDumpEndpoint;
        this.healthEndpoint = healthEndpoint;
    }

    enum Metric {
        METRICS,
        THREADDUMP,
        HEALTH
    }

    static class Response {
        public static Response of(Metric type, Object data) { return new Response(type,data ); }
        Metric type;
        Object data;

        public Response(Metric type, Object data) {
            this.type = type;
            this.data = data;
        }

        public Metric getType() {
            return type;
        }

        public void setType(Metric type) {
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    @Scheduled(fixedRate = 1000)
    public void sendMetrics() {
        try {
            messagingTemplate.convertAndSend("/topic/metrics", Response.of(Metric.METRICS,metricsService.allMetrics()));
        } catch (Exception ignored) {
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendThreadDump() {
        try {
            messagingTemplate.convertAndSend("/topic/metrics", Response.of(Metric.THREADDUMP,threadDumpEndpoint.threadDump()));
        } catch (Exception ignored) {
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendHealth() {
        try {
            messagingTemplate.convertAndSend("/topic/metrics", Response.of(Metric.HEALTH,healthEndpoint.health()));
        } catch (Exception ignored) {
        }
    }
}
