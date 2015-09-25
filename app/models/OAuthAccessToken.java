package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="oauth_access_tokens")
public class OAuthAccessToken extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth_access_tokens_id_seq")
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name="account_id")
    private User account;

    @ManyToOne(optional=false)
    @JoinColumn(name="oauth_client_id")
    private OAuthClient client;

    @Constraints.Required
    @Constraints.MaxLength(100)
    private String accessToken;

    @Constraints.Required
    @Constraints.MaxLength(100)
    private String refreshToken;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="created_at", nullable=false)
    private Date createdAt;

    public OAuthAccessToken() {
        createdAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public OAuthClient getClient() {
        return client;
    }

    public void setClient(OAuthClient client) {
        this.client = client;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
