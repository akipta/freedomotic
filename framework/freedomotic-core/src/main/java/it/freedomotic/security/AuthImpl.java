/**
 *
 * Copyright (c) 2009-2013 Freedomotic team http://freedomotic.com
 *
 * This file is part of Freedomotic
 *
 * This Program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 *
 * This Program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Freedomotic; see the file COPYING. If not, see
 * <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.freedomotic.security;

import com.google.inject.Inject;
import it.freedomotic.api.Plugin;
import it.freedomotic.app.AppConfig;
import it.freedomotic.util.Info;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.PropertiesRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;

/**
 *
 * @author Matteo Mazzoni <matteo@bestmazzo.it>
 */
public class AuthImpl implements Auth{

    private final static String BASE_REALM_NAME = "it.freedomotic.security";
    private final static String PLUGIN_REALM_NAME = "it.freedomotic.plugins.security";
    private boolean realmInited = false;
    private PropertiesRealm baseRealm = new PropertiesRealm();
    private SimpleAccountRealm pluginRealm = new SimpleAccountRealm(PLUGIN_REALM_NAME);
    private String DEFAULT_PERMISSION = "*";
    private ArrayList<Realm> realmCollection = new ArrayList<Realm>();
    @Inject AppConfig config;
    
    @Override
    public boolean isInited(){
        return realmInited;
    }
    
    @Override
    public void initBaseRealm() {
        DefaultSecurityManager securityManager = null;
        if (!realmInited && config.getBooleanProperty("KEY_SECURITY_ENABLE", true)) {
            baseRealm.setName(BASE_REALM_NAME);
            baseRealm.setResourcePath(new File(Info.PATH_WORKDIR + "/config/security.properties").getAbsolutePath());
            baseRealm.init();

            pluginRealm.init();

             securityManager = new DefaultSecurityManager();
            //securityManager = injector.getInstance(DefaultSecurityManager.class);

            realmCollection.add(baseRealm);
            realmCollection.add(pluginRealm);
            securityManager.setRealms(realmCollection);

            realmInited = true;
        }
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Override
    public boolean login(String subject, char[] password) {
        String pwdString = String.copyValueOf(password);
        return login(subject, pwdString);
    }

    @Override
    public boolean login(String subject, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(subject, password);
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            currentUser.getSession().setTimeout(-1);
            return true;
        } catch (Exception e) {
            LOG.warning(e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();

    }

    @Override
    public boolean isPermitted(String permission) {
        if (realmInited) {
            return SecurityUtils.getSubject().isPermitted(permission);
        } else {
            return true;
        }
    }

    @Override
    public Subject getSubject() {
        if (isInited()) {
            return SecurityUtils.getSubject();
        } else {
            return null;
        }
    }

    @Override
    public Object getPrincipal() {
        if (isInited()) {
            return SecurityUtils.getSubject().getPrincipal();
        } else {
            return null;
        }
    }

    @Override
    public void pluginExecutePrivileged(Plugin plugin, Runnable action) {
        executePrivileged(plugin.getClassName(), action);
    }

    private void executePrivileged(String classname, Runnable action) {
        if (isInited()) {
            //LOG.info("Executing privileged for plugin: " + classname);
            PrincipalCollection plugPrincipals = new SimplePrincipalCollection(classname, pluginRealm.getName());
            Subject plugSubject = new Subject.Builder().principals(plugPrincipals).buildSubject();
            plugSubject.getSession().setTimeout(-1);
            plugSubject.execute(action);
        } else {
            action.run();
        }
    }

    @Override
    public void setPluginPrivileges(Plugin plugin, String permissions) {
        if (!pluginRealm.accountExists(plugin.getClassName())) {
            // check whether declared permissions correspond the ones requested at runtime
            if (plugin.getConfiguration().getStringProperty("permissions", getPluginDefaultPermission()).equals(permissions)) {
                LOG.log(Level.INFO, "Setting permissions for plugin {0}: {1}", new Object[]{plugin.getClassName(), permissions});
                String plugrole = UUID.randomUUID().toString();

                pluginRealm.addAccount(plugin.getClassName(), UUID.randomUUID().toString(), plugrole);
                pluginRealm.addRole(plugrole + "=" + permissions);
            } else {
                LOG.log(Level.SEVERE, "Plugin {0} tried to request incorrect privileges", plugin.getName());
            }
        }
    }

    @Deprecated
    @Override
    public String getPluginDefaultPermission() {
        return DEFAULT_PERMISSION;
    }

    @RequiresPermissions("auth:realms:create")
    @Override
    public void addRealm(Realm rm) {
        if (!realmCollection.contains(rm)) {
            realmCollection.add(rm);
        }
    }

    @RequiresPermissions("auth:realms:delete")
    public void deleteRealm(Realm rm) {
        if (!rm.equals(baseRealm) && !rm.equals(pluginRealm)) {
            realmCollection.remove(rm);
        }
    }

    @RequiresPermissions("auth:fakeUser")
    @Override
    public boolean bindFakeUser(String userName) {
        if (baseRealm.accountExists(userName)) {
            PrincipalCollection principals = new SimplePrincipalCollection(userName, BASE_REALM_NAME);
            Subject subj = new Subject.Builder().principals(principals).buildSubject();
            ThreadState threadState = new SubjectThreadState(subj);
            threadState.bind();
            return true;
        }
        return false;
    }
    private static final Logger LOG = Logger.getLogger(AuthImpl.class.getName());
}
