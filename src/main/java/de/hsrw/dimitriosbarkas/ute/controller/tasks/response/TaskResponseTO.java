package de.hsrw.dimitriosbarkas.ute.controller.tasks.response;

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

    private String encodedTestTemplate;

    public static TaskResponseTO fromTask(Task task) {
        return new TaskResponseTO(
                task.getId(),
                task.getDescription(),
                task.getTargetDescription(),
                task.getEncodedFile(),
                task.getEncodedTestTemplate()
        );
    }
}
