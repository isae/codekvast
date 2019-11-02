#!/usr/bin/env bash
#---------------------------------------------------------------------------------------------------
# Deploys Codekvast Dashboard to the staging environment
#---------------------------------------------------------------------------------------------------

source $(dirname $0)/.check-requirements.sh

ansible-playbook playbooks/dashboard.yml --limit tag_Env_staging $*
