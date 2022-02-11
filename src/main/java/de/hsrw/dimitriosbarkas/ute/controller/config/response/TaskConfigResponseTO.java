package de.hsrw.dimitriosbarkas.ute.controller.config.response;

import de.hsrw.dimitriosbarkas.ute.model.TaskConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TaskConfigResponseTO {

    private List<TaskResponseTO> tasks;

    public static TaskConfigResponseTO fromTaskConfig(TaskConfig taskConfig) {
        return new TaskConfigResponseTO(
                taskConfig.getTasks().stream().map(TaskResponseTO::fromTask).collect(Collectors.toList())
        );
    }
}
