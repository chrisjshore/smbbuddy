package io.cshore.smbbuddy;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.utils.SmbFiles;

public class SMBBuddy {
	public static void main(String[] args) {

		String sharepath = "";		
		File source = new File(args[0]);
		Destination d = new Destination(args[1]);
		
		Path dir = FileSystems.getDefault().getPath(source.getParent());
		
		try {
			//get all files matching source file pattern
			DirectoryStream<Path> stream = Files.newDirectoryStream(dir, source.getName());
			SMBClient client = new SMBClient();
			
			//connect to server
			//try (Connection connection = client.connect(server)) {
			try (Connection connection = client.connect(d.getHostname())) {	
		        //AuthenticationContext ac = new AuthenticationContext(username, password.toCharArray(), domain);
		        AuthenticationContext ac = new AuthenticationContext(d.getUsername(), d.getPassword().toCharArray(), d.getDomain());
		        Session session = connection.authenticate(ac);
		        
		        //connect to share
		        //try (DiskShare share = (DiskShare) session.connectShare(sharename)) {
		        try (DiskShare share = (DiskShare) session.connectShare(d.getShare())) {
		        	// copy file(s) to share here		        	
		        	for (Path file : stream) {
		        		
		        		//if (unc.length > 2) {
		        		if(d.getPath() != null) {
		        			//sharepath = unc[2];
		        			sharepath = d.getPath();
		        			
		        			if (share.folderExists(sharepath)) {	
		        				//copying to a subfolder with original file name
		        				SmbFiles.copy(file.toFile(), share, sharepath + File.separator + file.toFile().getName(), true);
		        			}
		        			else {
		        				//assume the sharepath is actually a different destination file name, don't glob source file
		        				SmbFiles.copy(file.toFile(), share, sharepath, true);
		        			}
		        		}
		        		else {
		        			//copy to root of the share with original file name
		        			SmbFiles.copy(file.toFile(), share, file.toFile().getName(), true);
		        		}
					}
		        }
		        catch(SMBApiException e) {
		        	System.out.println("Unable to connect to share");
		        	System.out.println(e.getMessage());
		        }
		    }
			catch(IOException e) {
				System.out.println("Unable to connect to server");
				System.out.println(e.getMessage());
			}
			finally {
				client.close();
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
