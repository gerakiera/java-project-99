package hexlet.code.util;

import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskUtils {

    public Specification<Task> build(TaskParamsDTO paramsDTO) {
        return withTitleCont(paramsDTO.getTitleCont())
                .and(withAssigneeId(paramsDTO.getAssigneeId()))
                .and(withTaskStatus(paramsDTO.getStatus()))
                .and(withLabelId(paramsDTO.getLabelId()));
    }

    private Specification<Task> withTitleCont(String data) {
        return (root, query, criteriaBuilder) -> data == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("titleCont")), "%" + data + "%");
    }

    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, criteriaBuilder) -> assigneeId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
    }

    private Specification<Task> withTaskStatus(String slug) {
        return (root, query, criteriaBuilder) -> slug == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("taskStatus").get("slug"), slug);
    }

    private Specification<Task> withLabelId(Long labelId) {
        return (root, query, criteriaBuilder) -> labelId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("labels").get("id"), labelId);
    }
}
