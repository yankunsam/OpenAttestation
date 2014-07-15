#!/bin/bash
#undeploy OpenAttestation 2.x

mysql_password="______ MySQL PASSWD ______"

conf_dir=${conf_dir:-/etc/intel/cloudsecurity}
tomcat_dir=${tomcat_dir:-/var/lib/tomcat6}
oat_home_dir=${oat_home_dir:-$HOME/.oat}

delete()
{
  rm -rf $1
}

delete "$conf_dir/*"
delete "$oat_home_dir/*"

mysql -uroot -p$mysql_password -e 'drop database mw_as';
line="`grep -n "<\/Service>" $tomcat_dir/conf/server.xml | \
       awk -F: '{print $1}'`"
line=$[$line - 1]
str="`sed -n "$line p" $tomcat_dir/conf/server.xml | grep "Connector"`"
[[ ! -z $str ]] && \
    sed -i "$line d" $tomcat_dir/conf/server.xml

delete "$tomcat_dir/webapps/HisPrivacyCAWebServices2*"
delete "$tomcat_dir/webapps/WLMService*"
delete "$tomcat_dir/webapps/WhiteListPortal*"
delete "$tomcat_dir/webapps/AttestationService*"
delete "$tomcat_dir/webapps/TrustDashBoard*"
