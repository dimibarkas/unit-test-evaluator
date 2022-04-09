package de.hsrw.dimitriosbarkas.ute.controller.tasks.response;

import de.hsrw.dimitriosbarkas.ute.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponseTO {

    private String id;

    private String name;

    private String description;

    private String shortDescription;

    private String targetDescription;

    private String hint;

    private String encodedFile;

    private String encodedTestTemplate;

    public static TaskResponseTO fromTask(Task task) {
        return new TaskResponseTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getShortDescription(),
                task.getTargetDescription(),
                task.getHint(),
                task.getEncodedFile(),
                task.getEncodedTestTemplate()
        );
    }
}
