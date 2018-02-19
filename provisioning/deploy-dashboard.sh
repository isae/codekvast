#!/usr/bin/env bash
#---------------------------------------------------------------------------------------------------
# Deploys Codekvast to the production environment
#---------------------------------------------------------------------------------------------------

source $(dirname $0)/.check-requirements.sh

ansible-playbook --private-key ~/.ssh/codekvast-amazon.pem playbooks/dashboard.yml --limit tag_Env_staging $*
ansible-playbook --private-key ~/.ssh/codekvast-amazon.pem playbooks/dashboard.yml --limit tag_Env_prod $*

