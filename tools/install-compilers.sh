#!/bin/bash

test -f $HOME/.sdkman/bin/sdkman-init.sh || {
    echo "Installing SDKMAN..."
    curl -s https://get.sdkman.io | bash
}

source $HOME/.sdkman/bin/sdkman-init.sh

declare configBackupSuffix=".$(date --iso-8601=seconds)"
if grep -q "sdkman_auto_answer=false" $HOME/.sdkman/etc/config; then
    echo "Setting sdkman_auto_answer=true"
    sed --in-place=${configBackupSuffix} 's/sdkman_auto_answer=false/sdkman_auto_answer=true/g' $HOME/.sdkman/etc/config
fi

set -e
cd $(dirname $0)
awk '/sdkmanJavaVersion_/ {print $3}' ../gradle.properties |while read version; do
    if test -x $HOME/.sdkman/candidates/java/${version}/bin/javac; then
        echo Java SDK ${version} is already installed
    else
        echo sdk install java ${version}
        sdk install java ${version}
    fi
done

declare defaultJava=$(grep sdkmanJavaDefault ../gradle.properties | awk '{print $3}')
case $(readlink $HOME/.sdkman/candidates/java/current) in
    $HOME/.sdkman/candidates/java/${defaultJava})
        echo Java SDK ${defaultJava} is already default;;
    *)
        sdk default java ${defaultJava};;
esac

if test -f $HOME/.sdkman/etc/config${configBackupSuffix}; then
    mv -f $HOME/.sdkman/etc/config${configBackupSuffix} $HOME/.sdkman/etc/config
    echo "Restored $HOME/.sdkman/etc/config"
fi
