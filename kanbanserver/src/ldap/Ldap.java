package ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Ldap
{
 private String Host = null;
 private String SearchBase = null;

 private DirContext LdapContext = null;

 /**
  *  Constructor for the Ldap object. Call LDAP Connect() method
  *  @param Hostname IP and port for LDAP server (ldaps://xx.xx.xx.xx:xxx)
  *  @param LdapDirectory search directory (ou=accounts,dc=linuxmuster-net,dc=lokal)
  * @throws NamingException
  */
 public Ldap(String Hostname, String LdapDirectory) throws NamingException
 {
     this.Host = Hostname;
     this.SearchBase = LdapDirectory;
 }

  /**
  * Connect to LDAP server
  * Load Data from Ldap into local Ldap context
  * @return true connection successful
  * @return false connection error
  */
 private boolean connect()
     throws NamingException
 {
     try
     {
         String ConnType = "none";
         Hashtable<String, String> Table = new Hashtable<String, String>();

         Table.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
         Table.put(Context.PROVIDER_URL, Host);		//set Hostname
         Table.put("java.naming.ldap.factory.socket", "test.MySSLSocketFactory");	//SSL authentication factory
         Table.put(Context.SECURITY_AUTHENTICATION, ConnType);	//none = no login data required

         LdapContext = new InitialDirContext(Table);

         System.out.println("Connection successful");

         return true;
      }
      catch(Exception ex)
      {
         System.out.println("Ldap connect - " + ex);
         return false;
      }
 }

  /**
  * Close the Ldap context
  */
 private void disconnect()
 {
     try
     {
         if (LdapContext != null)
         {
             LdapContext.close();
             LdapContext = null;
         }
     }
     catch (NamingException ex)
     {
         System.out.println("Ldap disconnect - " + ex);
     }
 }

 /**
  * Filter the data in the ldap context for the given search base.
  *
  * @param  searchBase
  * @param  searchFilter filter this value from the base (only HEMS uid)
  */
 private boolean search(String SearchBase, String SearchFilter)
     throws NamingException
 {
     SearchControls SearchControl = new SearchControls(SearchControls.SUBTREE_SCOPE,
                                     1L, 	//count limit
                                     0,  	//time limit
                                     null,	//attributes (null = all)
                                     false,	// return object
                                     false);// dereference links

     NamingEnumeration<SearchResult> NE =  LdapContext.search(SearchBase, "uid=" + SearchFilter, SearchControl);

     if (NE.hasMore())	//Hems uid found true or false
     {
         SearchResult Result = (SearchResult) NE.next();
         System.out.println(Result.getAttributes().get("uid"));	//print uid to console
     }
     else		//uid not found on ldap
     {
         System.out.println(SearchFilter +" not found on Ldap");
     }

    return NE.hasMore();
 }

 /**
  * Login User with Hems uid
  * @param HemsUid Hems username
  */
 public boolean login(String HemsUid)
 {
    Boolean Result = false;
    try
    {
        Result = connect();
        Result = search(SearchBase, HemsUid);
    } catch (NamingException ex)
    {
        // TODO Auto-generated catch block
        ex.printStackTrace();
    }
    disconnect();
    return Result;
 }
}


