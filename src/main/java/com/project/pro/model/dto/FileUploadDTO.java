package com.project.pro.model.dto;

import com.project.pro.exception.CustomException;
import com.project.pro.utils.StringUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Base64;

@Data
public class FileUploadDTO implements Serializable {
    private String filename;
    private String content;
    private boolean publicImg;

    public byte[] getBytes() {
        if (StringUtil.isNotNullOrEmpty(content)) {
            try {
                return Base64.getDecoder().decode(content.getBytes());
            } catch (Exception e) {
                throw new CustomException(e);
            }
        }
        return null;
    }

    public void setContent(String content) {
        if (StringUtil.isNotNullOrEmpty(content)) {
            final String[] split = content.split(",");
            if (split != null && split.length > 1) {
                final String datatype = split[0];
                if (StringUtil.isNotNullOrEmpty(datatype) && StringUtils.isEmpty(filename)) {
                    filename = "filename." + datatype.substring(datatype.indexOf("/") + 1, datatype.indexOf(";")).toLowerCase();
                }
                content = split[1];
            }
        }

        this.content = content;
    }

    public void setContent(byte[] bytes) {
        try {
            content = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}