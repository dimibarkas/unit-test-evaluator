package de.hsrw.dimitriosbarkas.ute.controller.feedback;

import de.hsrw.dimitriosbarkas.ute.services.StreamingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@Log4j2
@RestController
public class FeedbackController {

    private final StreamingService streamingService;

    public FeedbackController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @GetMapping(value = "/api/video/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range) {
//        log.info("range in bytes(): " + range);
        return streamingService.getVideo(title);
    }

}
