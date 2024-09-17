package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {
    private int index;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @NotBlank
    private String title;

    private String content;

    @NotBlank
    private String status;

    private List<Long> taskLabelIds;
}
