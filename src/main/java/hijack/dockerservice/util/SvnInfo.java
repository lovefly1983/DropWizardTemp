package hijack.dockerservice.util;


import com.google.common.base.Predicate;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnList;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;

public class SvnInfo {

    private static final List<String> branches = new ArrayList<String>();

    public static void init(String url, String name, String password) {
        /*
         * Initializes the library (it must be done before ever using the
         * library itself)
         */
        setupLibrary();

        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(url);
            //repository = SVNRepositoryFactory.create(repositoryURL);
        } catch (SVNException svne) {
            /*
             * Perhaps a malformed URL is the cause of this exception
             */
            System.err
                    .println("error while creating an SVNRepository for the location '"
                            + url + "': " + svne.getMessage());
            System.exit(1);
        }
        System.out.println("Connect to svn server ....");

        SVNRevision revision = SVNRevision.HEAD;
        SvnOperationFactory operationFactory = new SvnOperationFactory();
        operationFactory.setAuthenticationManager(new BasicAuthenticationManager(name, password));
        SvnList list = operationFactory.createList();
        list.setDepth(SVNDepth.IMMEDIATES);
        list.setRevision(revision);
        list.addTarget(SvnTarget.fromURL(repositoryURL, revision));
        list.setReceiver(new ISvnObjectReceiver<SVNDirEntry>() {
            public void receive(SvnTarget target, SVNDirEntry object) throws SVNException {
                String name = object.getRelativePath();
                if(name!=null && !name.isEmpty()){
                    branches.add(name);
                }
            }
        });
        try {
            list.run();
        } catch (SVNException ex) {
            System.out.println(ex);
        }

        for (String branch : branches) {
            System.out.println(branch);
        }
        //return branches;
    }

    /**
     *
     * @param name
     * @return
     */
    public static Collection<String> getBranches(String name){
        if (name != null) {
            final String lowcaseName = name.toLowerCase();
            Predicate<String> validPersonPredicate = new Predicate<String>() {
                public boolean apply(String branch) {
                    return branch.toLowerCase().contains(lowcaseName);
                }
            };

            Collection<String> result = filter(branches, validPersonPredicate);
            return result;
        }
       return null;
    }

    /*
     * Initializes the library to work with a repository via
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();

        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }
}