package de.hsrw.dimitriosbarkas.ute.controller.config.response;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponseTO {

    private String id;

    private String description;

    private String targetDescription;

    private String encodedFile;

    public static TaskResponseTO fromTask(Task task) {
        return new TaskResponseTO(
                task.getId(),
                task.getDescription(),
                task.getTargetDescription(),
                task.getEncodedFile()
        );
    }
}
