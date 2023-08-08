package com.project.pro.model.beans;

import com.project.pro.utils.ListUtils;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

@Setter
public class PageableBean<C> {

    private List<C> content;

    private Pageable pageable;

    private Integer page;

    private Integer pageSize;

    private Integer totalElements;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPage(Long page) {
        this.page = Math.toIntExact(page);
    }

    private Integer getTotalElements() {
        if (totalElements == null) {
            return ListUtils.size(content);
        }
        return totalElements;
    }

    private List<C> getContent(int start, int end) {
        if (content == null || content.size() < end) {
            return content;
        }

        if (start <= end) {
            return content.subList(start, end);
        }

        return Collections.emptyList();
    }

    public Pageable getPageable() {
        if (pageable != null) {
            page = pageable.isPaged() ? Math.toIntExact(pageable.getOffset()) : 0;
            pageSize = pageable.isPaged() ? pageable.getPageSize() : getTotalElements();

            if (pageable.isPaged()) {
                return pageable;
            }
        }
        return PageRequest.of(page, pageSize);
    }

    public Page<C> getPaged() {
        final Pageable pageable = getPageable();

        int total = getTotalElements();
        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min((start + pageSize), total);

        return new PageImpl<>(getContent(start, end), pageable, total);
    }

    public boolean hasContent() {
        return ListUtils.isNotNullOrEmpty(content);
    }

}
