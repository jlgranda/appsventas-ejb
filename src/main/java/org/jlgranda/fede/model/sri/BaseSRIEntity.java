package org.jlgranda.fede.model.sri;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class BaseSRIEntity {

    @Column ( name="access_key" )
    @NaturalId
    @OneToOne
    @JoinColumn(name = "sri_clave_acceso")
    private String accessKey;

    @Column ( name="sri_version" )
    private String sriVersion;

    @Column ( name="issue_date" )
    private Date issueDate;

    @Column ( name="xml_content" )
    @Type(type = "XMLType")
    private String xmlContent;

    @Column ( name="authorization_date" )
    private Timestamp authorizationDate;

    @Column ( name="internal_status_id" )
    private long internalStatusId;

    @Column ( name="xml_authorization" )
    @Type(type = "XMLType")
    private String xmlAuthorization;

    @Column ( name="is_deleted" )
    private boolean isDeleted;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSriVersion() {
        return sriVersion;
    }

    public void setSriVersion(String sriVersion) {
        this.sriVersion = sriVersion;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    public Timestamp getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(Timestamp authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public long getInternalStatusId() {
        return internalStatusId;
    }

    public void setInternalStatusId(long internalStatusId) {
        this.internalStatusId = internalStatusId;
    }

    public String getXmlAuthorization() {
        return xmlAuthorization;
    }

    public void setXmlAuthorization(String xmlAuthorization) {
        this.xmlAuthorization = xmlAuthorization;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    
}