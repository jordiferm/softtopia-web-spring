package com.softtopia.web.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Technology.
 */

@Document(collection = "T_TECHNOLOGY")
public class Technology implements Serializable {

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("post_id")
    private String postId;

    @Field("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Technology technology = (Technology) o;

        if (id != null ? !id.equals(technology.id) : technology.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "id=" + id +
                ", description='" + description + "'" +
                ", postId='" + postId + "'" +
                ", url='" + url + "'" +
                '}';
    }
}
