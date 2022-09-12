package io.cshore.smbbuddy;

import java.io.File;
import java.util.regex.Pattern;

public class Destination {
	
	public Destination(String path){
		String[] split = path.split("@");
        parseCredentials(split[0]);
        parseHostPath(split[1]);
	}
	
	private String domain;
    private String username;
    private String password;
    private String hostname;
    private String share;
    private String path;

    public String getDomain(){
        return this.domain;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getHostname(){
        return this.hostname;
    }

    public void setHostname(String hostname){
        this.hostname = hostname;
    }

    public String getShare(){
        return this.share;
    }

    public void setShare(String share){
        this.share = share;
    }

    public String getPath(){
        return this.path;
    }

    public void setPath(String path){
        this.path = path;
    }

    void parseCredentials(String creds){
        //split credentials into domain, username, and password
        String[] dup = creds.split(":");
        String[] du = dup[0].split(Pattern.quote(File.separator));
        setPassword(dup[1]);
        setDomain(du[0]);
        setUsername(du[1]);
    }

    void parseHostPath(String unc){
        //split hostPath into hostname, share, and path
        String[] hsp = unc.split(Pattern.quote(File.separator),3);
        setHostname(hsp[0]);
        setShare(hsp[1]);
        
        if(hsp.length > 2) {
        	setPath(hsp[2]);
        }
    }
}
