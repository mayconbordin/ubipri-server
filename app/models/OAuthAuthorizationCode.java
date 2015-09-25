package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="oauth_authorization_codes")
public class OAuthAuthorizationCode extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth_authorization_codes_id_seq")
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name="account_id")
    private User account;

    @ManyToOne(optional=false)
    @JoinColumn(name="oauth_client_id")
    private OAuthClient client;

    @Constraints.Required
    @Constraints.MaxLength(100)
    private String code;

    @Constraints.Required
    @Constraints.MaxLength(2000)
    @Column(name="redirect_uri", nullable=false)
    private String redirectUri;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="created_at", nullable=false)
    private Date createdAt;

    public OAuthAuthorizationCode() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
