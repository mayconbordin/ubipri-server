package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="oauth_clients")
public class OAuthClient extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth_clients_id_seq")
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name="owner_id")
    private User owner;

    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column(name="grant_type", nullable=false)
    private String grantType;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(name="client_id", nullable=false)
    private String clientId;

    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(name="client_secret", nullable=false)
    private String clientSecret;

    @Constraints.MaxLength(2000)
    @Column(name="redirect_uri", nullable=true)
    private String redirectUri;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="created_at", nullable=false)
    private Date createdAt;

    public OAuthClient() {
        createdAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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
