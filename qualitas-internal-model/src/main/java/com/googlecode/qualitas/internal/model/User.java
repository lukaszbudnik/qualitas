package com.googlecode.qualitas.internal.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_USERID_GENERATOR", sequenceName="USER_ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_USERID_GENERATOR")
	@Column(name="USER_ID", nullable=false, precision=22)
	private long userId;

	@Column(name="OPENID_USERNAME", unique=true, nullable=false, length=1000)
	private String openIDUsername;

	//bi-directional many-to-one association to Process
	@OneToMany(mappedBy="user")
	private List<Process> processes;

    public User() {
    }

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOpenIDUsername() {
		return this.openIDUsername;
	}

	public void setOpenIDUsername(String openIDUsername) {
		this.openIDUsername = openIDUsername;
	}

	public List<Process> getProcesses() {
		return this.processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((openIDUsername == null) ? 0 : openIDUsername.hashCode());
        result = prime * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (openIDUsername == null) {
            if (other.openIDUsername != null) {
                return false;
            }
        } else if (!openIDUsername.equals(other.openIDUsername)) {
            return false;
        }
        if (userId != other.userId) {
            return false;
        }
        return true;
    }
	
}