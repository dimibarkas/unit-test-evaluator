package de.hsrw.dimitriosbarkas.ute.services;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

public interface StreamingService {

    Mono<Resource> getVideo(String title);
}
